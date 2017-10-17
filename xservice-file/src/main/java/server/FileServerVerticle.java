package server;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import api.RestFileConstant;
import constants.IMCmd;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import module.c2c.protocol.MessageBuilder;
import module.c2c.protocol.SQIMBody;
import module.c2c.protocol.SQIMHeader;
import module.transcoding.TranscodingVerticle;
import utils.IPUtil;

public class FileServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(FileServerVerticle.class);

	private FileSystem fs;

	private String lastCreated;

	private EventBus eb;
	private String uploadFilePathPrefix;
	private String downloadFilePathPrefix;

	private String innerIP;

	private Map<String, String> nodeMap = new HashMap<String, String>();

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		fs = vertx.fileSystem();
		eb = vertx.eventBus();
		HttpServer httpServer = vertx.createHttpServer();

		innerIP = IPUtil.getInnerIP();
		JsonArray fileNodes = config().getJsonArray("file");

		for (Object node : fileNodes) {
			JsonObject nodeJson = JsonObject.mapFrom(node);
			nodeMap.put(nodeJson.getString("innerIP"), nodeJson.getString("node"));
		}

		logger.info("nodeMap={}", nodeMap.toString());

		uploadFilePathPrefix = config().getString("upload.file.path.prefix");
		downloadFilePathPrefix = config().getString("download.file.server.prefix");
		logger.info("config uploadFilePathPrefix " + uploadFilePathPrefix);

		httpServer.requestHandler(request -> {
			if (request.method() == HttpMethod.GET) {
				String file = request.getParam("file");
				switch (request.path()) {
				case RestFileConstant.Uri.DOWNLOAD_FILE_PATH:
					String sysFile = uploadFilePathPrefix + file;
					fs.exists(sysFile, res -> {
						if (!res.result()) {
							sendNotFound(request, sysFile);
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
					sendNotFound(request, null);
					break;
				}
			} else {
				switch (request.uri()) {
				case RestFileConstant.Uri.UPLOAD_FILE_PATH:
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
							SQIMBody msgBody = buildIMBody(request.formAttributes());
							logger.info("msgBody={}", msgBody.toString());

							Future<Message<JsonObject>> hashFuture = Future.future();
							DeliveryOptions option = new DeliveryOptions();
							option.setSendTimeout(3000);
							option.addHeader("action", "getInnerNode");

							JsonObject message = new JsonObject();
							message.put("userId", msgBody.getToTel());
							if (StringUtils.isNotEmpty(msgBody.getToTel())) {
								eb.<JsonObject>send("module.hash.IMConsistentHashingVerticle", message, option,
										hashFuture.completer());
							} else {

							}

							// amr->mp3 transcoding
							if (msgBody.getMsgType() != null && 2 == msgBody.getMsgType().intValue()) {
								DeliveryOptions tsOption = new DeliveryOptions();
								tsOption.setSendTimeout(3000);
								tsOption.addHeader("action", TranscodingVerticle.method.amrToMp3);
								logger.info("send msg to TranscodingVerticle, file={}", uploadPath + uuid);
								eb.send(TranscodingVerticle.class.getName() + innerIP, uploadPath + uuid, tsOption);
							}

							hashFuture.setHandler(res -> {
								logger.info("msgRequest, hashFuture={}", res.result());
								if (res.succeeded()) {
									JsonObject param = new JsonObject();

									SQIMHeader header = new SQIMHeader();
									header.setHeaderLength(MessageBuilder.HEADER_LENGTH);
									header.setClientVersion(
											NumberUtils.toInt(request.getFormAttribute("clientVersion")));
									header.setCmdId(IMCmd.MSG_N);

									int bodyLength = 0;
									try {
										bodyLength = Json.encode(msgBody).getBytes("utf-8").length;
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									header.setBodyLength(bodyLength);

									param.put("header", Json.encode(header));
									param.put("body", JsonObject.mapFrom(msgBody));

									DeliveryOptions c2cOption = new DeliveryOptions();
									c2cOption.addHeader("action", "sendMessage");
									c2cOption.setSendTimeout(1000);
									eb.send("module.c2c.C2CVerticle" + res.result().body().getString("host"), param,
											c2cOption);
								}
							});

							JsonObject response = new JsonObject();
							response.put("code", 0);
							response.put("time", System.currentTimeMillis());
							request.response().putHeader("content-type", "application/json; charset=utf-8")
									.end(response.encode());
						});
					});

					break;
				default:
					sendNotFound(request, null);
					break;
				}
			}
		}).listen(RestFileConstant.Server.PORT);
	}

	private SQIMBody buildIMBody(MultiMap attriMap) {
		int msgType = NumberUtils.toInt(attriMap.get("msgType"));
		String fromTel = attriMap.get("fromTel");
		int identity = NumberUtils.toInt(attriMap.get("identity"));
		String toTel = attriMap.get("toTel");
		String msgId = attriMap.get("msgId");
		String sceneId = attriMap.get("sceneId");
		int sceneType = NumberUtils.toInt(attriMap.get("sceneType"));
		int duration = NumberUtils.toInt(attriMap.get("duration"));
		String address = attriMap.get("address");
		String sAddress = attriMap.get("sAddress");
		String content = attriMap.get("content");
		double lat = NumberUtils.toDouble(attriMap.get("lat"));
		double lon = NumberUtils.toDouble(attriMap.get("lon"));

		SQIMBody msgBody = new SQIMBody();
		msgBody.setFromTel(fromTel);
		msgBody.setIdentity(identity);
		msgBody.setToTel(toTel);
		msgBody.setMsgId(msgId);
		msgBody.setMsgType(msgType);
		msgBody.setSceneType(sceneType);
		msgBody.setSceneId(sceneId);

		switch (msgType) {
		case 2:// 1文本 2语音，content为语音下载地址 3定位 4图片 5视频
			msgBody.setContent("http://" + nodeMap.get(innerIP) + downloadFilePathPrefix + content);
			msgBody.setDuration(duration);
			break;
		case 3:
			msgBody.setContent("http://" + nodeMap.get(innerIP) + downloadFilePathPrefix + content);
			msgBody.setLat(lat);
			msgBody.setLon(lon);
			msgBody.setAddress(address);
			msgBody.setsAddress(sAddress);
		default:
			break;
		}

		return msgBody;
	}

	private void sendNotFound(HttpServerRequest request, String sysFile) {
		JsonObject response = new JsonObject();
		response.put("code", 1000);
		if (StringUtils.isNotEmpty(sysFile)) {
			response.put("msg", "Cannot not locate resource " + sysFile);
		} else {
			response.put("msg", "Cannot not locate resource " + request.uri());
		}
		response.put("time", System.currentTimeMillis());
		request.response().setStatusCode(404).putHeader("content-type", "application/json").end(response.encode());
	}
}
