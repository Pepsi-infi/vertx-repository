package iservice.groovy;
public class DeviceService_GroovyExtension {
  public static void reportDevice(iservice.DeviceService j_receiver, java.util.Map<String, Object> userDeviceDto, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> result) {
    j_receiver.reportDevice(userDeviceDto != null ? new iservice.dto.DeviceDto(io.vertx.core.impl.ConversionHelper.toJsonObject(userDeviceDto)) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<utils.BaseResponse>>() {
      public void handle(io.vertx.core.AsyncResult<utils.BaseResponse> ar) {
        result.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
  public static void queryDevices(iservice.DeviceService j_receiver, java.util.Map<String, java.lang.String> param, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.util.Map<String, Object>>>> result) {
    j_receiver.queryDevices(param != null ? param.entrySet().stream().collect(java.util.stream.Collectors.toMap(java.util.Map.Entry::getKey, entry -> entry.getValue())) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<iservice.dto.DeviceDto>>>() {
      public void handle(io.vertx.core.AsyncResult<java.util.List<iservice.dto.DeviceDto>> ar) {
        result.handle(ar.map(event -> event != null ? event.stream().map(elt -> elt != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(elt.toJson()) : null).collect(java.util.stream.Collectors.toList()) : null));
      }
    } : null);
  }
}
