package helper;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;

public class XProxyHelper {

    private static final Logger PROXY_LOGGER = LoggerFactory.getLogger("proxy");

    private static final DeliveryOptions options;

    private static final long TIMEOUT_SECONDS = 2;
    static {
        options = new DeliveryOptions();
        options.setSendTimeout(3000);
    }

    public static <T> T createProxy(Class<?> sourceClazz, Class<T> proxyClazz, Vertx vertx, String address) {

        PROXY_LOGGER.info("method={}#sourceClazz={}#proxyClazz={}#address={}", "createProxy", sourceClazz, proxyClazz,
                address);

        return ProxyHelper.createProxy(proxyClazz, vertx, address, options);
    }

    /**
     * Registers a service on the event bus.
     * @param clazz
     *            the service class (interface)
     * @param vertx
     *            the vert.x instance
     * @param service
     *            the service object
     * @param address
     *            the address on which the service is published
     * @param <T>
     *            the type of the service interface
     * @return the consumer used to unregister the service
     */
    public static <T> MessageConsumer<JsonObject> registerService(Class<T> clazz, Vertx vertx, T service, String address) {

        PROXY_LOGGER.info("method={}#clazz={}#service={}#address={}", "registerService", clazz, service, address);

        return ProxyHelper.registerService(clazz, vertx, service, address, TIMEOUT_SECONDS);
    }
}
