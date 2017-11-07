package service.groovy;
public class MsgRecordService_GroovyExtension {
  public static void addMessage(service.MsgRecordService j_receiver, java.util.Map<String, Object> dto, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.addMessage(dto != null ? new domain.MsgRecord(io.vertx.core.impl.ConversionHelper.toJsonObject(dto)) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<utils.BaseResponse>>() {
      public void handle(io.vertx.core.AsyncResult<utils.BaseResponse> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
}
