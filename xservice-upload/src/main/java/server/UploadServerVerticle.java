package server;

import java.time.LocalDate;
import java.util.UUID;

import com.baidu.bjf.remoting.protobuf.utils.StringUtils;

import api.RestConstant;
import constant.UploadConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class UploadServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(UploadServerVerticle.class);

	private FileSystem fs;

	private String lastCreated;

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		fs = vertx.fileSystem();
		HttpServer httpServer = vertx.createHttpServer();

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
					String uploadPath = UploadConstant.UPLOAD_FILE_PATH_PREFIX + content;

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
							//content 文件地址 + ts 服务器时间戳 发给 to 的handler

							JsonObject response = new JsonObject();
							response.put("code", 0);
							response.put("time", System.currentTimeMillis());
							request.response().end(response.encode());
						});
					});

					break;
				case "/index":
					request.response().sendFile("webroot/index.html");

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
