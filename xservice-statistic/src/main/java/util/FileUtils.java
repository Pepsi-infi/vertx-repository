package util;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.impl.DeviceServiceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lufei
 * Date : 2017/7/31 13:56
 * Description :
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static JsonObject getJsonConf(String configPath) {
        logger.info("file Path: " + configPath);
        JsonObject conf = new JsonObject();
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = ctxClsLoader.getResourceAsStream(configPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conf = new JsonObject(sb.toString());
            logger.info("Loaded  file from [" + configPath + "] and config.json= " + conf.toString());
        } catch (Exception e) {
            logger.error("Failed to load configuration file" + e);
        }
        return conf;
    }
}
