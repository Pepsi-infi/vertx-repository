package redis.impl;

import java.util.List;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.impl.Command;
import redis.ClusterCommand;
import redis.RedisClusterOptions;

/**
 * Created by caijt on 2017/1/24.
 */
class RedisClusterClient {

    private final Vertx vertx;
    private RedisClusterCache cache;

    RedisClusterClient(Vertx vertx) {
        this.vertx = vertx;
    }

    void connect(RedisClusterOptions config, Handler<AsyncResult<Void>> handler) {
        this.cache = new RedisClusterCache(vertx);
        this.initializeSlotsCache(vertx, config, 0, handler);
    }

    private void initializeSlotsCache(Vertx vertx, RedisClusterOptions config, int index, Handler<AsyncResult<Void>> handler) {
        if(index < config.size()) {
            RedisClient client = RedisClient.create(vertx, config.getRedisOptions(index));
            Future<Void> future = Future.future();
            cache.discoverClusterNodesAndSlots(client, config, future.setHandler(ar -> {
                if(ar.failed()) {
                    initializeSlotsCache(vertx, config, index + 1, handler);
                } else {
                    vertx.runOnContext(v -> handler.handle(Future.succeededFuture()));
                }
            }));
        } else {
            vertx.runOnContext(v -> handler.handle(Future.failedFuture("connect redis cluster fail.")));
        }
    }

    void send(ClusterCommand clusterCommand) {
        cache.getRedis(clusterCommand.getSlot()).send(clusterCommand.getCommand());
    }

    void sendAll(final Command<?> command) {
        List<RedisConnection> nodesPool = cache.getShuffledNodesPool();
        for(RedisConnection connection : nodesPool) {
            connection.send(command);
        }
    }

    int getConnectionNumber() {
        return cache.getNodeNumber();
    }
}
