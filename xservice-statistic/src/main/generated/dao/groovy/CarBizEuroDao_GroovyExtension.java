package dao.groovy;
public class CarBizEuroDao_GroovyExtension {
  public static void queryCarBizEuro(dao.CarBizEuroDao j_receiver, java.lang.String date, int page, int limit, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.util.Map<String, Object>>>> resultHandler) {
    j_receiver.queryCarBizEuro(date,
      page,
      limit,
      resultHandler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<service.dto.CarBizEuroDto>>>() {
      public void handle(io.vertx.core.AsyncResult<java.util.List<service.dto.CarBizEuroDto>> ar) {
        resultHandler.handle(ar.map(event -> event != null ? event.stream().map(elt -> elt != null ? io.vertx.core.impl.ConversionHelper.fromJsonObject(elt.toJson()) : null).collect(java.util.stream.Collectors.toList()) : null));
      }
    } : null);
  }
}
