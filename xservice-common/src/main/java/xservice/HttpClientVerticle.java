package xservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;

public class HttpClientVerticle extends AbstractVerticle {

    private static HttpClient httpClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);
        httpClient = vertx.createHttpClient(new HttpClientOptions().setConnectTimeout(1000).setKeepAlive(true)
                .setLogActivity(false).setMaxPoolSize(1000).setTryUseCompression(true).setSendBufferSize(1048576)
                .setReceiveBufferSize(1048576).setReuseAddress(true).setPipelining(true));
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }
}
