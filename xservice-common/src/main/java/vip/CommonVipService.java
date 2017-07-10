package vip;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import vip.response.GetVipInfoResponse;
import vip.response.LecomGetPackageByProductIdResponse;
import vip.response.PackageInfoResponse;

@ProxyGen
@VertxGen
public interface CommonVipService {
    public static final String SERVICE_NAME = "vip.eb.commonVipservice";

    public static final String SERVICE_ADDRESS = "vip-eb-commonVipservice";

    public void getLecomPackageInfo(Integer packageId, Long uid, Handler<AsyncResult<PackageInfoResponse>> result);

    public void lecomGetPackageByProductId(Integer productId , Handler<AsyncResult<LecomGetPackageByProductIdResponse>> resultHandler);

    public void getUserAddonInfo(Long uid, Handler<AsyncResult<GetVipInfoResponse>> resultHandler);

    static CommonVipService createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(CommonVipService.class, vertx, CommonVipService.SERVICE_ADDRESS);
     }
}
