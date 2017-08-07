package com.message.push;

import api.StartVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class StartVerticleTest extends AbstractVerticle{

	private static final Logger logger = LoggerFactory.getLogger(StartVerticleTest.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(StartVerticle.class.getName());
	}

}
