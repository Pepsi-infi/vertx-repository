package test;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import proxy.server.UdpProxyServerVerticle;

public class Tester {

	public static void main(String[] args) {
		Vertx v = Vertx.vertx();

		v.deployVerticle(UdpProxyServerVerticle.class.getName(), new DeploymentOptions().setConfig(config()));
	}

	public static JsonObject config() {
		return new JsonObject(
				"{\"udp.port\":9099,\"udp.server\":[\"10.10.10.105:9098\",\"10.10.10.106:9098\"],\"new.udp.server\":[\"10.20.0.11:9099\",\"10.20.0.12:9099\"]}");
	}
}
