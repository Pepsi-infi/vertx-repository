package iservice.groovy;
public class MsgStatService_GroovyExtension {
  public static void statPushMsg(iservice.MsgStatService j_receiver, java.util.List<java.util.Map<String, Object>> msgStatDtos, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> result) {
    j_receiver.statPushMsg(msgStatDtos != null ? msgStatDtos.stream().map(elt -> elt != null ? new iservice.dto.MsgStatDto(io.vertx.core.impl.ConversionHelper.toJsonObject(elt)) : null).collect(java.util.stream.Collectors.toList()) : null,
      result != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        result.handle(ar.map(event -> event));
      }
    } : null);
  }
}
