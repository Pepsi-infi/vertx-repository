package module.transcoding;

import java.io.File;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import utils.IPUtil;

public class TranscodingVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(TranscodingVerticle.class);

	private EventBus eb;

	private String innerIP;

	private String uploadFilePathPrefix;

	public static interface method {
		public static final String amrToMp3 = "amrToMp3";
	}

	@Override
	public void start() throws Exception {
		super.start();
		innerIP = IPUtil.getInnerIP();

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
					amrToMp3(res.body());
					break;
				default:
					break;
				}
			}
		});
	}

	public void amrToMp3(String sourcePath) {
		logger.info("amrToMp3, sourcePath={}", sourcePath);
		String targetPath = uploadFilePathPrefix + "mp3/" + sourcePath + ".mp3";
		File source = new File(sourcePath);
		File target = new File(targetPath);
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();

		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);

		try {
			encoder.encode(source, target, attrs);
		} catch (Exception e) {
			logger.error("amrToMp3, e={}", e.getMessage());
		}
	}
}
