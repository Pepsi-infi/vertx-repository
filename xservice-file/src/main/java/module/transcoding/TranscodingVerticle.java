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

	private String FFMPEG_PATH;

	public static interface method {
		public static final String amrToMp3 = "amrToMp3";
	}

	@Override
	public void start() throws Exception {
		super.start();
		innerIP = IPUtil.getInnerIP();

		ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
		FFMPEG_PATH = ctxClsLoader.getResource("ffmpeg").getPath();
		logger.info("FFMPEG_PATH " + FFMPEG_PATH);

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

	public void amr2mp3(String sourcePath) {
		String mp3FileName = uploadFilePathPrefix + "mp3/" + sourcePath + ".mp3";
		String filePath = uploadFilePathPrefix + sourcePath;

		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec(FFMPEG_PATH + " -i " + filePath + " -ar 8000 -ac 1 -y -ab 12.4k " + mp3FileName);
			InputStream in = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.error("amr2mp3, {}", line);
			}
			if (process.exitValue() != 0) {
				logger.error("amr2mp3, fail!");
			}
		} catch (IOException e) {
			logger.error("amr2mp3, e={}", e.getMessage());
		}
	}
}
