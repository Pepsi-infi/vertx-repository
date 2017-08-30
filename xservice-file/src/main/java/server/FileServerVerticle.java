package server;

import java.time.LocalDate;
import java.util.UUID;

import com.baidu.bjf.remoting.protobuf.utils.StringUtils;

import api.RestConstant;
import cluster.ConsistentHashingService;
import constant.UploadConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import logic.C2CService;

public class FileServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(FileServerVerticle.class);

	private FileSystem fs;

	private String lastCreated;

	private ConsistentHashingService consistentHashingService;
	private C2CService c2cService;
	private EventBus eb;

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		fs = vertx.fileSystem();
		eb = vertx.eventBus();
		HttpServer httpServer = vertx.createHttpServer();

		c2cService = C2CService.createProxy(vertx);
		consistentHashingService = ConsistentHashingService.createProxy(vertx);

		httpServer.requestHandler(request -> {
			if (request.method() == HttpMethod.GET) {
				String file = request.getParam("file");
				switch (request.path()) {
				case RestConstant.Uri.DOWNLOAD_FILE_PATH:
					String sysFile = UploadConstant.UPLOAD_FILE_PATH_PREFIX + file;
					fs.exists(sysFile, res -> {
						if (!res.result()) {
							sendNotFound(request);
						} else {
							fs.props(sysFile, prop -> {
								FileProps props = prop.result();
								request.response().putHeader("Content-Length", Long.toString(props.size()));
								request.response().sendFile(sysFile);
							});
						}
					});

					break;
				case "/index":
					request.response().sendFile("webroot/index.html");

					break;
				default:
					sendNotFound(request);
					break;
				}
			} else {
				switch (request.uri()) {
				case RestConstant.Uri.UPLOAD_FILE_PATH:
					String uuid = UUID.randomUUID().toString();
					request.setExpectMultipart(true);
					LocalDate date = LocalDate.now();
					String content = date + "/" + uuid;
					String uploadPath = UploadConstant.UPLOAD_FILE_PATH_PREFIX + date + "/";

					if ((StringUtils.isEmpty(lastCreated) || !lastCreated.equalsIgnoreCase(date.toString()))
							&& !fs.existsBlocking(uploadPath)) {
						fs.mkdirBlocking(uploadPath);
						lastCreated = date.toString();
						logger.info("Create dir " + uploadPath + " lastCreated " + lastCreated);
					}
					request.uploadHandler(upload -> {
						upload.streamToFileSystem(uploadPath + uuid).endHandler(a -> {

							String from = request.getFormAttribute("from");
							String to = request.getFormAttribute("to");
							String id = request.getFormAttribute("id");
							String orderId = request.getFormAttribute("orderId");
							String type = request.getFormAttribute("type");
							String clientVersion = request.getFormAttribute("clientVersion");
							// content 文件地址 + ts 服务器时间戳 发给 to 的handler

							Future<String> consistentHashFuture = Future.future();
							consistentHashingService.getNode(to, consistentHashFuture.completer());
							Future<Message<JsonObject>> sessionFuture = Future.future();
							// 根据to去session查出对应handlerID
							consistentHashFuture.setHandler(res -> {
								if (res.succeeded()) {
									DeliveryOptions option = new DeliveryOptions();
									option.addHeader("action", "getHandlerIDByUid");
									option.setSendTimeout(3000);
									JsonObject rMsg = new JsonObject().put("to", to);
									eb.send("session-eb-service" + res.result(), rMsg, option,
											sessionFuture.completer());
								} else {
									logger.error("Consistent Hash ", res.cause());
								}
							});

							Future<JsonObject> c2cFuture = Future.future();
							sessionFuture.setHandler(res -> {
								if (res.succeeded()) {
									JsonObject result = res.result().body();
									String toHandlerID = result.getString("handlerID");

									JsonObject msgBody = new JsonObject();
									msgBody.put("from", from);
									msgBody.put("id", id);
									msgBody.put("type", type);
									JsonObject rMsg = new JsonObject().put("clientVersion", clientVersion)
											.put("body", msgBody).put("toHandlerID", toHandlerID);
									c2cService.doWithFileUpload(rMsg, c2cFuture.completer());
								} else {
									logger.error("Session ", res.cause());
								}
							});

							JsonObject response = new JsonObject();
							response.put("code", 0);
							response.put("time", System.currentTimeMillis());
							request.response().end(response.encode());
						});
					});

					break;
				default:
					sendNotFound(request);
					break;
				}
			}
		}).listen(RestConstant.Server.PORT);
	}

	private void sendNotFound(HttpServerRequest request) {
		JsonObject response = new JsonObject();
		response.put("code", 1000);
		response.put("msg", "Cannot not locate resource " + request.uri());
		response.put("time", System.currentTimeMillis());
		request.response().setStatusCode(404).putHeader("content-type", "application/json").end(response.encode());
	}
}
