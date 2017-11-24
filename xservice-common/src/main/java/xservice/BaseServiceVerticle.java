package xservice;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.servicediscovery.types.HttpEndpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BaseServiceVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceVerticle.class);
	protected ServiceDiscovery discovery;
	protected CircuitBreaker circuitBreaker;
	protected Set<Record> registeredRecords = new ConcurrentHashSet<>();

	private static final String LOG_EVENT_ADDRESS = "events.log";

	@Override
	public void start() throws Exception {
		// discovery = ServiceDiscovery.create(
		// vertx,
		// new ServiceDiscoveryOptions().setBackendConfiguration(new
		// JsonObject()
		// .put("connection",
		// "10.181.117.77:2181,10.100.54.205:2181,10.100.54.206:2181")
		// .put("ephemeral", true).put("guaranteed", true)));

		discovery = ServiceDiscovery.create(vertx);

		// init circuit breaker instance
		JsonObject cbOptions = config().getJsonObject("circuit-breaker") != null
				? config().getJsonObject("circuit-breaker")
				: new JsonObject();
		circuitBreaker = CircuitBreaker.create(cbOptions.getString("name", "circuit-breaker"), vertx,
				new CircuitBreakerOptions().setMaxFailures(cbOptions.getInteger("max-failures", 5))
						.setTimeout(cbOptions.getLong("timeout", 10000L)).setFallbackOnFailure(true)
						.setResetTimeout(cbOptions.getLong("reset-timeout", 30000L)));
	}

	/**
	 * Publish a HTTP service.
	 * 
	 * @param name
	 *            the service name
	 * @param host
	 *            the host (IP or DNS name), it must be the _public_ IP / name
	 * @param port
	 *            the port, it must be the _public_ port
	 * @param root
	 *            the path of the service, "/" if not set
	 */
	protected Future<Void> publishHttpEndpoint(String name, String host, int port, String root) {
		logger.info("name " + name + " host " + host + " port " + port + " root " + root);
		Record record = HttpEndpoint.createRecord(name, host, port, root);

		return publish(record);
	}

	/**
	 * Publish a HTTP service with metadata.
	 * 
	 * @param name
	 *            the service name
	 * @param host
	 *            the host (IP or DNS name), it must be the _public_ IP / name
	 * @param port
	 *            the port, it must be the _public_ port
	 * @param root
	 *            the path of the service, "/" if not set
	 * @param metadata
	 *            additional metadata
	 */
	protected Future<Void> publishHttpEndpoint(String name, String host, int port, String root, JsonObject metadata) {
		logger.info("name " + name + " host " + host + " port " + port + " root " + root + " metadata "
				+ metadata.encodePrettily());
		Record record = HttpEndpoint.createRecord(name, host, port, root, metadata);

		return publish(record);
	}

	protected Future<Void> publishApiGateway(String host, int port) {
		Record record = HttpEndpoint.createRecord("api-gateway", true, host, port, "/", null).setType("api-gateway");
		return publish(record);
	}

	/**
	 * Publish a eventbus service.
	 * 
	 * @param name
	 *            the name of the service
	 * @param itf
	 *            the Java interface
	 * @param address
	 *            the event bus address on which the service available
	 * @return the created record
	 */
	protected Future<Void> publishEventBusService(String name, String address, Class itf) {
		Record record = EventBusService.createRecord(name, address, itf);
		return publish(record);
	}

	/**
	 * Publish a eventbus service with metadata.
	 * 
	 * @param name
	 *            the name of the service.
	 * @param address
	 *            the event bus address on which the service available
	 * @param itf
	 *            the Java interface
	 * @param metadata
	 *            the metadata
	 * @return the created record
	 */
	protected Future<Void> publishEventBusService(String name, String address, Class itf, JsonObject metadata) {
		Record record = EventBusService.createRecord(name, address, itf, metadata);
		return publish(record);
	}

	/**
	 * A helper method that simply publish logs on the event bus.
	 * 
	 * @param type
	 *            log type
	 * @param data
	 *            log message data
	 */
	protected void publishLogEvent(String type, JsonObject data) {
		JsonObject msg = new JsonObject().put("type", type).put("message", data);
		vertx.eventBus().publish(LOG_EVENT_ADDRESS, msg);
	}

	protected Future<Void> publishIMService(String name, String address, JsonObject metadata) {
		Record record = new Record().setType("im-server").setName(name)
				.setLocation(new JsonObject().put(Record.ENDPOINT, address)).setMetadata(metadata);

		return publish(record);
	}

	protected Future<Void> publishSocketService(String name, String address, String type, JsonObject metadata) {
		Record record = new Record().setType(type).setName(name)
				.setLocation(new JsonObject().put(Record.ENDPOINT, address)).setMetadata(metadata);

		return publish(record);
	}

	/**
	 * Publish a service with record.
	 * 
	 * @param record
	 *            service record
	 * @return async result
	 */
	private Future<Void> publish(Record record) {
		if (discovery == null) {
			try {
				start();
			} catch (Exception e) {
				throw new IllegalStateException("Cannot create discovery service");
			}
		}

		Future<Void> future = Future.future();
		// publish the service
		discovery.publish(record, ar -> {
			if (ar.succeeded()) {
				registeredRecords.add(record);
				logger.info("publish record,registration is :" + record.getRegistration());
				future.complete();
			} else {
				future.fail(ar.cause());
			}
		});

		return future;
	}

	@Override
	public void stop(Future<Void> future) throws Exception {
		List<Future> futures = new ArrayList<>();
		for (Record record : registeredRecords) {
			Future<Void> unregistrationFuture = Future.future();
			futures.add(unregistrationFuture);
			logger.info("stop is called. discovery unpublish " + record.toJson());
			discovery.unpublish(record.getRegistration(), unregistrationFuture.completer());
		}

		if (futures.isEmpty()) {
			discovery.close();
			future.complete();
		} else {
			CompositeFuture composite = CompositeFuture.all(futures);
			composite.setHandler(ar -> {
				discovery.close();
				if (ar.failed()) {
					future.fail(ar.cause());
				} else {
					future.complete();
				}
			});
		}
	}
}
