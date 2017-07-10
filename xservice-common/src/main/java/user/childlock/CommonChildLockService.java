package user.childlock;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import user.childlock.request.ChildLockRequest;
import user.childlock.response.ChildLockTpResponse;

@ProxyGen
@VertxGen
public interface CommonChildLockService {

    public static final String SERVICE_NAME = "childlock.eb.service";

    public static final String SERVICE_ADDRESS = "childlock-eb-service";

    public void setUserChildLock(String uuid, ChildLockRequest childLockRequest,
            Handler<AsyncResult<ChildLockTpResponse>> handler);

    public void checkUserChildLock(String uuid, ChildLockRequest childLockRequest,
            Handler<AsyncResult<ChildLockTpResponse>> handler);
}
