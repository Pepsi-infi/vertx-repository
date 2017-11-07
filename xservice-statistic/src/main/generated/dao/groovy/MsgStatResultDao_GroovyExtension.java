package dao.groovy;
public class MsgStatResultDao_GroovyExtension {
  public static void addMsgStatResult(dao.MsgStatResultDao j_receiver, java.util.Map<String, Object> msgStatResultDto, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.addMsgStatResult(msgStatResultDto != null ? new service.dto.MsgStatResultDto(io.vertx.core.impl.ConversionHelper.toJsonObject(msgStatResultDto)) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<utils.BaseResponse>>() {
      public void handle(io.vertx.core.AsyncResult<utils.BaseResponse> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
  public static void updateMsgStatResult(dao.MsgStatResultDao j_receiver, java.util.Map<String, Object> msgStatResultDto, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.updateMsgStatResult(msgStatResultDto != null ? new service.dto.MsgStatResultDto(io.vertx.core.impl.ConversionHelper.toJsonObject(msgStatResultDto)) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<utils.BaseResponse>>() {
      public void handle(io.vertx.core.AsyncResult<utils.BaseResponse> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
  public static void getMsgStatResult(dao.MsgStatResultDao j_receiver, java.util.Map<String, Object> msgStatResultDto, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.getMsgStatResult(msgStatResultDto != null ? new service.dto.MsgStatResultDto(io.vertx.core.impl.ConversionHelper.toJsonObject(msgStatResultDto)) : null,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<service.dto.MsgStatResultDto>>() {
      public void handle(io.vertx.core.AsyncResult<service.dto.MsgStatResultDto> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
  public static void queryMsgStatResultByPage(dao.MsgStatResultDao j_receiver, java.util.Map<String, java.lang.String> params, int page, int limit, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.queryMsgStatResultByPage(params != null ? params.entrySet().stream().collect(java.util.stream.Collectors.toMap(java.util.Map.Entry::getKey, entry -> entry.getValue())) : null,
      page,
      limit,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<service.dto.MsgStatResultPageWrapper>>() {
      public void handle(io.vertx.core.AsyncResult<service.dto.MsgStatResultPageWrapper> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
  public static void delErrorData(dao.MsgStatResultDao j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> resultHandler) {
    j_receiver.delErrorData(resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<utils.BaseResponse>>() {
      public void handle(io.vertx.core.AsyncResult<utils.BaseResponse> ar) {
        resultHandler.handle(ar.map(event -> event != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(event.toJson()) : null));
      }
    } : null);
  }
}
