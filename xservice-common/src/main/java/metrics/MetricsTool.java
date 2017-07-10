package metrics;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import org.apache.commons.lang.StringUtils;

import utils.SystemCache;

public class MetricsTool {

    /**
     * 发布自定义的metirc
     * @param id
     * @param metricsType
     *            Gauge:a gauge is the simplest metric type. It just returns a
     *            value.
     *            Counter:a counter is a simple incrementing and decrementing
     *            64-bit integer.
     * @param value
     */
    public static void publishMetrics(Vertx vertx, String id, MetricsType metricsType, Object value) {
        if (StringUtils.isNotBlank(id) && value != null) {
            JsonObject message = new JsonObject().put("id", id).put("type", metricsType.getType())
                    .put("timestamp", SystemCache.currentTimeMillis).put("value", value);
            vertx.eventBus().send(HawkularConstants.METRICES_EB_ADDRESS, message);
        }
    }
}
