package server;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import api.RestConstant;
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
import protocol.IMMessage;

public class FileServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(FileServerVerticle.class);

	private FileSystem fs;

	private String lastCreated;

	// private ConsistentHashingService consistentHashingService;
	// private C2CService c2cService;
	private EventBus eb;
	private String uploadFilePathPrefix;

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		fs = vertx.fileSystem();
		eb = vertx.eventBus();
		HttpServer httpServer = vertx.createHttpServer();

		// c2cService = C2CService.createProxy(vertx);

		uploadFilePathPrefix = config().getString("upload.file.path.prefix");
		logger.info("config uploadFilePathPrefix " + uploadFilePathPrefix);

		httpServer.requestHandler(request -> {
			if (request.method() == HttpMethod.GET) {
				String file = request.getParam("file");
				switch (request.path()) {
				case RestConstant.Uri.DOWNLOAD_FILE_PATH:
					String sysFile = uploadFilePathPrefix + file;
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
					String uploadPath = uploadFilePathPrefix + date + "/";

					if ((StringUtils.isEmpty(lastCreated) || !lastCreated.equalsIgnoreCase(date.toString()))
							&& !fs.existsBlocking(uploadPath)) {
						fs.mkdirBlocking(uploadPath);
						lastCreated = date.toString();
						logger.info("Create dir " + uploadPath + " lastCreated " + lastCreated);
					}
					request.uploadHandler(upload -> {
						upload.streamToFileSystem(uploadPath + uuid).endHandler(a -> {

							String from = request.getFormAttribute(IMMessage.key_fromTel);
							String to = request.getFormAttribute(IMMessage.key_toTel);
							String msgId = request.getFormAttribute(IMMessage.key_msgId);
							String sceneId = request.getFormAttribute(IMMessage.key_sceneId);
							String msgType = request.getFormAttribute(IMMessage.key_msgType);
							String sceneType = request.getFormAttribute(IMMessage.key_sceneType);
							// content 文件地址 + ts 服务器时间戳 发给 to 的handler

							Future<Message<JsonObject>> hashFuture = Future.future();
							DeliveryOptions option = new DeliveryOptions();
							option.setSendTimeout(3000);
							option.addHeader("action", "getInnerNode");

							JsonObject message = new JsonObject();
							message.put("userId", to);
							if (StringUtils.isNotEmpty(to)) {
								eb.<JsonObject>send("cluster.impl.IMConsistentHashingVerticle", message, option,
										hashFuture.completer());
							} else {

							}

							hashFuture.setHandler(res -> {
								logger.info("msgRequest, hashFuture={}", res.result());
								if (res.succeeded()) {
									JsonObject param = new JsonObject();

									JsonObject header = new JsonObject();
									header.put("HeaderLength", 12);
									header.put("ClientVersion", 800);
									header.put("CmdId", 2001);
									
									JsonObject body = new JsonObject().put(IMMessage.key_fromTel, from)
											.put(IMMessage.key_toTel, to).put(IMMessage.key_sceneId, sceneId)
											.put(IMMessage.key_sceneType, sceneType).put(IMMessage.key_msgType, msgType)
											.put(IMMessage.key_content, content).put(IMMessage.key_msgId, msgId);

									int bodyLength = body.encode().getBytes(Charset.defaultCharset()).length;
									header.put("CmdBodylengthId", bodyLength);
									
									param.put("header", header);
									param.put("body", body);

									DeliveryOptions c2cOption = new DeliveryOptions();
									c2cOption.addHeader("action", "sendMessage");
									c2cOption.setSendTimeout(1000);
									eb.send("logic.C2CService" + res.result().body().getString("host"), param,
											c2cOption);
								}
							});

							JsonObject response = new JsonObject();
							response.put("code", 0);
							response.put("time", System.currentTimeMillis());
							request.response().putHeader("content-type", "application/json").end(response.encode());
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
