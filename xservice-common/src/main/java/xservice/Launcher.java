package xservice;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import utils.IPUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Launcher extends io.vertx.core.Launcher {

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		// TODO this should not be required, but the beforeStartingVertx method
		// does not yet allow setting whether we
		// start in cluster mode. This is obviously a bug.
		List<String> list = new ArrayList<>(Arrays.asList(args));

		String innerIP = IPUtil.getInnerIP();
		if (list.isEmpty()) {
			list.add("-cluster");
			list.add("-cluster-host");
			list.add(innerIP);
		}

		new Launcher().dispatch(list.toArray(new String[list.size()]));
	}

	@Override
	public void beforeStartingVertx(VertxOptions options) {
		// hawkular
		// options.setMetricsOptions(new
		// VertxHawkularOptions().setEnabled(true).setHost("10.129.28.172").setPort(8080)
		// .setMetricsBridgeEnabled(true));

		options.setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true));

		// NO effect!
		// options.setEventBusOptions(new
		// EventBusOptions().setTcpKeepAlive(false).setUsePooledBuffers(true));

		options.setClusterManager(getZookeeperClusterManager());
	}

	@Override
	public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
		super.beforeDeployingVerticle(deploymentOptions);

		if (deploymentOptions.getConfig() == null) {
			deploymentOptions.setConfig(new JsonObject());
		}

		// String configPath = System.getProperty("config", "mobile");
		// File conf = new File("src/conf/" + configPath + "/config.json");
		// deploymentOptions.getConfig().mergeIn(getConfiguration(conf));

		JsonObject conf = getConfiguration(System.getProperty("config", "") + "/config.json");
		deploymentOptions.getConfig().mergeIn(conf);

	}

	private JsonObject getConfiguration(String configPath) {
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
			logger.info(
					"Loaded config.json file from [" + configPath + "/config.json] and config.json=" + conf.toString());
		} catch (Exception e) {
			logger.error("Failed to load configuration file" + e);
		}
		return conf;
	}

	private ClusterManager getZookeeperClusterManager() {
		ZookeeperClusterManager zkClusterManager = new ZookeeperClusterManager();
		JsonObject conf = zkClusterManager.getConfig();
		if (StringUtils.isBlank(conf.getString("auth"))) {
			return zkClusterManager;
		}
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(
				conf.getJsonObject("retry", new JsonObject()).getInteger("initialSleepTime", 1000),
				conf.getJsonObject("retry", new JsonObject()).getInteger("maxTimes", 5),
				conf.getJsonObject("retry", new JsonObject()).getInteger("intervalTimes", 10000));

		String hosts = conf.getString("zookeeperHosts", "127.0.0.1");
		CuratorFramework curator = CuratorFrameworkFactory.builder().connectString(hosts)
				.namespace(conf.getString("rootPath", "io.vertx"))
				.sessionTimeoutMs(conf.getInteger("sessionTimeout", 20000))
				.connectionTimeoutMs(conf.getInteger("connectTimeout", 3000)).retryPolicy(retryPolicy).build();
		curator.start();
		try {
			curator.getZookeeperClient().getZooKeeper().addAuthInfo("digest", conf.getString("auth").getBytes());
			zkClusterManager = new ZookeeperClusterManager(curator);
		} catch (Exception e) {
			logger.error("CuratorFramework addAuthInfo failure!!");
			e.printStackTrace();
		}
		return zkClusterManager;
	}
}
