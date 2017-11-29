package service.groovy;
public class PassengerUnSendService_GroovyExtension {
  public static void getUnSendMsg(service.PassengerUnSendService j_receiver, java.util.Map<String, Object> param, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.util.Map<String, Object>>>> resultHandler) {
    j_receiver.getUnSendMsg(param != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(param) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<io.vertx.core.json.JsonObject>>>() {
      public void handle(io.vertx.core.AsyncResult<java.util.List<io.vertx.core.json.JsonObject>> ar) {
        resultHandler.handle(ar.map(event -> event != null ? event.stream().map(elt -> io.vertx.core.impl.ConversionHelper.fromJsonObject(elt)).collect(java.util.stream.Collectors.toList()) : null));
      }
    } : null);
  }
  public static void addUnSendMsg(service.PassengerUnSendService j_receiver, java.util.Map<String, Object> param, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Integer>> resultHandler) {
    j_receiver.addUnSendMsg(param != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(param) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Integer>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.Integer> ar) {
        resultHandler.handle(ar.map(event -> event));
      }
    } : null);
  }
  public static void delUnSendMsg(service.PassengerUnSendService j_receiver, java.util.Map<String, Object> param, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Integer>> resultHandler) {
    j_receiver.delUnSendMsg(param != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(param) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Integer>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.Integer> ar) {
        resultHandler.handle(ar.map(event -> event));
      }
    } : null);
  }
  public static void pushAddUnSendMsg(service.PassengerUnSendService j_receiver, java.util.Map<String, Object> param, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> resultHandler) {
    j_receiver.pushAddUnSendMsg(param != null ? io.vertx.core.impl.ConversionHelper.toJsonObject(param) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        resultHandler.handle(ar.map(event -> event));
      }
    } : null);
  }
}
