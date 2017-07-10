package metrics;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/12/9
 * Time: 10:49
 */
public enum MetricsType {

    /**
     * A gauge is the simplest metric type. It just returns a value.
     */
    Gauge("gauge"),

    /**
     * A counter is a simple incrementing and decrementing 64-bit integer
     */
    Counter("counter"),
    Availability("availability");

    private String type;

    MetricsType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
