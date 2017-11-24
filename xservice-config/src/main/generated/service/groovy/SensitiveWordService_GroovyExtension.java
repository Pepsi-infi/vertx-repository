package service.groovy;
public class SensitiveWordService_GroovyExtension {
  public static void addSensitiveWord(service.SensitiveWordService j_receiver, java.util.Map<String, Object> jsonObject, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.addSensitiveWord(jsonObject != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(jsonObject) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        result.handle(ar.map(event -> io.vertx.core.impl.ConversionHelper.fromJsonObject(event)));
      }
    } : null);
  }
  public static void getSensitiveWord(service.SensitiveWordService j_receiver, int id, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.getSensitiveWord(id,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        result.handle(ar.map(event -> io.vertx.core.impl.ConversionHelper.fromJsonObject(event)));
      }
    } : null);
  }
  public static void updateSensitiveWord(service.SensitiveWordService j_receiver, java.util.Map<String, Object> jsonObject, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.updateSensitiveWord(jsonObject != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(jsonObject) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        result.handle(ar.map(event -> io.vertx.core.impl.ConversionHelper.fromJsonObject(event)));
      }
    } : null);
  }
  public static void deleteSensitiveWord(service.SensitiveWordService j_receiver, int id, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.deleteSensitiveWord(id,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        result.handle(ar.map(event -> io.vertx.core.impl.ConversionHelper.fromJsonObject(event)));
      }
    } : null);
  }
  public static void querySensitiveWord(service.SensitiveWordService j_receiver, java.util.Map<String, Object> jsonObject, int page, int limit, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.querySensitiveWord(jsonObject != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(jsonObject) : null,
      page,
      limit,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
}
