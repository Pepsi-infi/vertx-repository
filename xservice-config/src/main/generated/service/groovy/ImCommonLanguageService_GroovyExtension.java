package service.groovy;
public class ImCommonLanguageService_GroovyExtension {
  public static void addImCommonLanguage(service.ImCommonLanguageService j_receiver, java.util.Map<String, Object> jsonObject, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.addImCommonLanguage(jsonObject != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(jsonObject) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        result.handle(ar.map(event -> io.vertx.core.impl.ConversionHelper.fromJsonObject(event)));
      }
    } : null);
  }
  public static void getImCommonLanguage(service.ImCommonLanguageService j_receiver, int id, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.getImCommonLanguage(id,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        result.handle(ar.map(event -> io.vertx.core.impl.ConversionHelper.fromJsonObject(event)));
      }
    } : null);
  }
  public static void updateImCommonLanguage(service.ImCommonLanguageService j_receiver, java.util.Map<String, Object> jsonObject, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.updateImCommonLanguage(jsonObject != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(jsonObject) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        result.handle(ar.map(event -> io.vertx.core.impl.ConversionHelper.fromJsonObject(event)));
      }
    } : null);
  }
  public static void deleteImCommonLanguage(service.ImCommonLanguageService j_receiver, int id, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.deleteImCommonLanguage(id,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        result.handle(ar.map(event -> io.vertx.core.impl.ConversionHelper.fromJsonObject(event)));
      }
    } : null);
  }
  public static void queryImCommonLanguage(service.ImCommonLanguageService j_receiver, int type, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.util.Map<String, Object>>>> result) {
    j_receiver.queryImCommonLanguage(type,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<io.vertx.core.json.JsonObject>>>() {
      public void handle(io.vertx.core.AsyncResult<java.util.List<io.vertx.core.json.JsonObject>> ar) {
        result.handle(ar.map(event -> event != null ? event.stream().map(elt -> io.vertx.core.impl.ConversionHelper.fromJsonObject(elt)).collect(java.util.stream.Collectors.toList()) : null));
      }
    } : null);
  }
}
