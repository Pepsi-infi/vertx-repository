package dao.impl;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(CarBizEuroVerticle.class.getName(),
				new DeploymentOptions().setInstances(1).setConfig(config()));
	}

	private static JsonObject config() {
		String prop = "{\"mc-device\":{\"host\":\"10.10.10.178\",\"port\":3306,\"maxPoolSize\":100,\"username\":\"sqyc_message\",\"password\":\"sqyc_message@01zhuanche.com\",\"database\":\"device\",\"charset\":\"UTF-8\",\"queryTimeout\":3000},\"rentcar\":{\"host\":\"10.10.10.103\",\"port\":3306,\"maxPoolSize\":100,\"username\":\"root\",\"password\":\"jiuzhong!@#testdb\",\"database\":\"rentcar\",\"charset\":\"UTF-8\",\"queryTimeout\":3000},\"mc-statistic\":{\"host\":\"10.10.10.178\",\"port\":3306,\"maxPoolSize\":100,\"username\":\"sqyc_message\",\"password\":\"sqyc_message@01zhuanche.com\",\"database\":\"statistic\",\"charset\":\"UTF-8\",\"queryTimeout\":3000},\"redis\":{\"host\":\"10.10.10.126\",\"port\":6381,\"encoding\":\"UTF-8\",\"tcpKeepAlive\":\"true\",\"tcpNoDelay\":\"true\",\"password\":\"\"}}";

		JsonObject jsonObject = new JsonObject(prop);
		return jsonObject;
	}
}
