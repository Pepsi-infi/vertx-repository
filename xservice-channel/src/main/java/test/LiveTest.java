package test;

import api.RestTvLiveVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import service.impl.LiveServiceImpl;
import tp.cms.impl.CmsTpDaoImpl;
import tp.live.LiveTpDao;
import tp.live.impl.LiveTpDaoImpl;

public class LiveTest {
    private Vertx vertx;

    public LiveTest(Vertx vertx) {
        this.vertx = vertx;
    }

    public static void main(String[] args) {
        try {
            Vertx vertx = Vertx.vertx();
            LiveTest liveTest = new LiveTest(vertx);
            liveTest.deployRestService();
            
            DeploymentOptions deploymentOptions = new DeploymentOptions();
            deploymentOptions.setConfig(config());
            vertx.deployVerticle(CmsTpDaoImpl.class.getName(), deploymentOptions);
            vertx.deployVerticle(LiveTpDaoImpl.class.getName(), deploymentOptions);
            vertx.deployVerticle(LiveServiceImpl.class.getName(), deploymentOptions);
            
            Thread.sleep(3000);
//            io.vertx.core.Vertx delegate = vertx.getDelegate();
            LiveTpDao liveTpDao = LiveTpDao.createProxy(vertx.getDelegate());
            String url = "http://api.lms.le.com/api/http/liveRoom/queryLiveRoomsByCategory?lineTwoCode=28&lineOneCode=5&sourcetype=2&sourceId=5";
            liveTpDao.getZhiBoDataFromLMSBySearchUrl(url, null,0,200,null,result->{
                if(result.succeeded()){
                    System.err.println(result.result());
                }else{
                    System.err.println(result.cause());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private Future<Void> deployVerticle(String name) {
//        Future<String> future = Future.future();
//        DeploymentOptions deploymentOptions = new DeploymentOptions();
//        deploymentOptions.setConfig(config());
//        vertx.deployVerticle(name, deploymentOptions, future.completer());
//        return future.map(r -> null);
//    }

    private Future<Void> deployRestService() {
        Future<String> future = Future.future();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setConfig(config());
        vertx.deployVerticle(RestTvLiveVerticle.class.getName(), deploymentOptions, future.completer());
//        vertx.deployVerticle(RestMobileLiveVerticle.class.getName(), deploymentOptions, future.completer());
        return future.map(r -> null);
    }

    private static JsonObject config() {
        String prop = "{\"service.name\":\"le.tv.live\",\"service.host\":\"127.0.0.1\",\"service.port\":2203,\"service.root\":\"/tv-channel\","
                + "\"redis.properties\":{ \"host\": \"localhost\", \"port\": 6379,\"encoding\": \"UTF-8\",\"tcpKeepAlive\": \"true\",\"tcpNoDelay\": \"true\",\"password\":\"redis\"}}";
        JsonObject jsonObject = new JsonObject(prop);
        return jsonObject;
    }
}
