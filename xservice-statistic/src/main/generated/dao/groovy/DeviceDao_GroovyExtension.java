package dao.groovy;
public class DeviceDao_GroovyExtension {
  public static void addDevice(dao.DeviceDao j_receiver, java.util.Map<String, Object> deviceDto, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.addDevice(deviceDto != null ? new iservice.dto.DeviceDto(io.vertx.core.impl.ConversionHelper.toJsonObject(deviceDto)) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<utils.BaseResponse>>() {
      public void handle(io.vertx.core.AsyncResult<utils.BaseResponse> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
  public static void updateDevice(dao.DeviceDao j_receiver, java.util.Map<String, Object> deviceDto, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.updateDevice(deviceDto != null ? new iservice.dto.DeviceDto(io.vertx.core.impl.ConversionHelper.toJsonObject(deviceDto)) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<utils.BaseResponse>>() {
      public void handle(io.vertx.core.AsyncResult<utils.BaseResponse> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
  public static void getDevice(dao.DeviceDao j_receiver, java.util.Map<String, java.lang.String> params, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.getDevice(params != null ? params.entrySet().stream().collect(java.util.stream.Collectors.toMap(java.util.Map.Entry::getKey, entry -> entry.getValue())) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<iservice.dto.DeviceDto>>() {
      public void handle(io.vertx.core.AsyncResult<iservice.dto.DeviceDto> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
  public static void queryDevices(dao.DeviceDao j_receiver, java.util.Map<String, java.lang.String> params, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.util.Map<String, Object>>>> resultHandler) {
    j_receiver.queryDevices(params != null ? params.entrySet().stream().collect(java.util.stream.Collectors.toMap(java.util.Map.Entry::getKey, entry -> entry.getValue())) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<iservice.dto.DeviceDto>>>() {
      public void handle(io.vertx.core.AsyncResult<java.util.List<iservice.dto.DeviceDto>> ar) {
        resultHandler.handle(ar.map(event -> event != null ? event.stream().map(elt -> elt != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(elt.toJson()) : null).collect(java.util.stream.Collectors.toList()) : null));
      }
    } : null);
  }
}
