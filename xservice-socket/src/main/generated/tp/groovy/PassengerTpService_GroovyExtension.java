package tp.groovy;
public class PassengerTpService_GroovyExtension {
  public static void auth(tp.PassengerTpService j_receiver, java.util.Map<String, Object> param, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.auth(param != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(param) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
  public static void updateOnlineState(tp.PassengerTpService j_receiver, java.lang.String uid, java.lang.String date, java.util.Map<String, Object> content, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.updateOnlineState(uid,
      date,
      content != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(content) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
  public static void updateOnlineSimple(tp.PassengerTpService j_receiver, java.lang.String uid, java.lang.String date, java.util.Map<String, Object> content, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.updateOnlineSimple(uid,
      date,
      content != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(content) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
  public static void setClientOnline(tp.PassengerTpService j_receiver, java.util.Map<String, Object> param, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.setClientOnline(param != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(param) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
  public static void setClientOffline(tp.PassengerTpService j_receiver, java.util.Map<String, Object> param, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.setClientOffline(param != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(param) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
  public static void subscribe(tp.PassengerTpService j_receiver, java.util.Map<String, Object> msg, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.subscribe(msg != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(msg) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
  public static void unsubscribe(tp.PassengerTpService j_receiver, java.util.Map<String, Object> msg, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.unsubscribe(msg != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(msg) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
}
