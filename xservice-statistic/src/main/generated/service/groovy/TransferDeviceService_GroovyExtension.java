package service.groovy;
public class TransferDeviceService_GroovyExtension {
  public static void transferDevice(service.TransferDeviceService j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.transferDevice(result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<utils.BaseResponse>>() {
      public void handle(io.vertx.core.AsyncResult<utils.BaseResponse> ar) {
        result.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
}
