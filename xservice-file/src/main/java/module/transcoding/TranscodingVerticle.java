package module.transcoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import utils.IPUtil;

public class TranscodingVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(TranscodingVerticle.class);

	private EventBus eb;

	private String innerIP;

	private String uploadFilePathPrefix;

	private static String FFMPEG_PATH;

	public static interface method {
		public static final String amrToMp3 = "amrToMp3";
	}

	@Override
	public void start() throws Exception {
		super.start();
		innerIP = IPUtil.getInnerIP();
		FFMPEG_PATH = TranscodingVerticle.class.getResource("ffmpeg").getFile();

		uploadFilePathPrefix = config().getString("upload.file.path.prefix");
		if (!uploadFilePathPrefix.endsWith("/")) {
			uploadFilePathPrefix = uploadFilePathPrefix + "/";
		}

		eb = vertx.eventBus();
		eb.<String>consumer(TranscodingVerticle.class.getName() + innerIP, res -> {
			MultiMap headers = res.headers();
			if (headers != null) {
				String action = headers.get("action");
				logger.info("action={}", action);
				switch (action) {
				case "amrToMp3":
					amr2mp3(res.body());
					break;
				default:
					break;
				}
			}
		});
	}

	// public void amrToMp3(String sourcePath) {
	// logger.info("amrToMp3, sourcePath={}", sourcePath);
	// String targetPath = uploadFilePathPrefix + "mp3/" + sourcePath + ".mp3";
	// String filePath = uploadFilePathPrefix + sourcePath;
	// File source = new File(filePath);
	// File target = new File(targetPath);
	// AudioAttributes audio = new AudioAttributes();
	// Encoder encoder = new Encoder();
	//
	// audio.setCodec("libmp3lame");
	// EncodingAttributes attrs = new EncodingAttributes();
	// attrs.setFormat("mp3");
	// attrs.setAudioAttributes(audio);
	//
	// try {
	// encoder.encode(source, target, attrs);
	// } catch (Exception e) {
	// logger.error("amrToMp3, e={}", e.getMessage());
	// }
	// }

	public void amr2mp3(String sourcePath) {
		Runtime runtime = Runtime.getRuntime();
		String mp3FileName = uploadFilePathPrefix + "mp3/" + sourcePath + ".mp3";
		String filePath = uploadFilePathPrefix + sourcePath;
		try {
			Process process = runtime
					.exec(FFMPEG_PATH + " -i " + filePath + " -ar 8000 -ac 1 -y -ab 12.4k " + mp3FileName);
			InputStream in = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.error("amr2mp3, error stream={}", line);
			}
			if (process.exitValue() != 0) {
				logger.error("amr2mp3, fail!");
			}
		} catch (IOException e) {

		}
	}
}
