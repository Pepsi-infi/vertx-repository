package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class UploadServerVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);
		HttpServer httpServer = vertx.createHttpServer();

		router.route("/").handler(StaticHandler.create("webroot").setIndexPage("index.html"));

		httpServer.requestHandler(request -> {
			request.setExpectMultipart(true);
			request.uploadHandler(upload -> {
				upload.streamToFileSystem("/Users/wanglonghu/temp/" + upload.filename() + System.currentTimeMillis())
						.endHandler(a -> {
							request.response().end("3Q");
						});
			});
		}).listen(9090);
	}
}
