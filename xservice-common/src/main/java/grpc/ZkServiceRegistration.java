package grpc;

import com.google.common.base.Throwables;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.grpc.VertxChannelBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：17/3/5
 * Time: 11:56
 */
public class ZkServiceRegistration implements Closeable,PathChildrenCacheListener {

    protected final static Logger LOGGER = LoggerFactory.getLogger(ZkServiceRegistration.class);
    private Vertx vertx;
    private CuratorFramework curator;
    private JsonObject conf = new JsonObject();
    private static final String CONFIG_FILE = "zookeeper.json";
    private static final String ZK_SYS_CONFIG_KEY = "vertx.zookeeper.config";
    private static final String ZK_SERVICE_PATH_ROOT = "/grpcServices/nodes/";

    private Map<String,PathChildrenCache> pathChildrenCacheMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> serverAddressMap = new ConcurrentHashMap<>();
    private Map<String,Boolean> nodeChangeMap = new ConcurrentHashMap<>();

    private Map<String,ManagedChannel> channelMap = new ConcurrentHashMap<>();
    private int count = 0;

    public ZkServiceRegistration(Vertx vertx) {
        this.vertx = vertx;
        if (curator == null) {
            loadProperties(System.getProperty(ZK_SYS_CONFIG_KEY, CONFIG_FILE));
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(
                    conf.getJsonObject("retry", new JsonObject()).getInteger("initialSleepTime", 1000),
                    conf.getJsonObject("retry", new JsonObject()).getInteger("maxTimes", 5),
                    conf.getJsonObject("retry", new JsonObject()).getInteger("intervalTimes", 10000));

            // Read the zookeeper hosts from a system variable
            String hosts = conf.getString("zookeeperHosts", "127.0.0.1");
            LOGGER.info("ZkMultiServiceRegistration Zookeeper hosts set to " + hosts);
            curator = CuratorFrameworkFactory.builder()
                    .connectString(hosts)
                    .namespace(conf.getString("rootPath", "io.vertx"))
                    .sessionTimeoutMs(conf.getInteger("sessionTimeout", 20000))
                    .connectionTimeoutMs(conf.getInteger("connectTimeout", 3000))
                    .retryPolicy(retryPolicy).build();
        }
        curator.start();
        try {
            if(StringUtils.isNotBlank(conf.getString("auth"))){
                curator.getZookeeperClient().getZooKeeper().addAuthInfo("digest",conf.getString("auth").getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PathChildrenCache setZkNodeListener(String zkNodeName) {
        //加入一个节点服务的同时增加一个对此路径的Listener
        PathChildrenCache serviceNodes = new PathChildrenCache(curator, this.getZkServicePath(zkNodeName), true);
        try {
            serviceNodes.getListenable().addListener(this);
            serviceNodes.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e){

        }
        pathChildrenCacheMap.put(zkNodeName,serviceNodes);
        return serviceNodes;
    }

    /**
     * 服务端启动时
     * 将本地的服务地址存到zk中,address=host:port
     * @throws Exception
     */
    public void registerLocalService(String address,String zkNodeName) throws Exception {
        PathChildrenCache pathChildrenCache = pathChildrenCacheMap.get(zkNodeName);
        if(pathChildrenCache == null){
            this.setZkNodeListener(zkNodeName);
        }
        String serviceId = UUID.randomUUID().toString();
        try {
            //Join to the zookeeper
            curator.create().withMode(CreateMode.EPHEMERAL).forPath(this.getZkServicePath(zkNodeName) + "/" + serviceId, address.getBytes());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public ManagedChannel selectChannel(String zkNodeName){
        String zkServicePath = this.getZkServicePath(zkNodeName);
        boolean nodeChange = nodeChangeMap.get(zkServicePath) == null?false:nodeChangeMap.get(zkServicePath);
        List<String> serverAddress = serverAddressMap.get(this.getZkServicePath(zkNodeName));
        if(serverAddress == null || nodeChange){
            nodeChangeMap.put(zkServicePath,false);
            serverAddress = this.getServerAddress(zkNodeName);
        }
        int totalSize = serverAddress.size();
        int index = count++ % totalSize;
        String address = serverAddress.get(index);
        Lock loopLock = new ReentrantLock();
        loopLock.lock();
        try{
            if(StringUtils.isNotBlank(address)){
                ManagedChannel channel =  channelMap.get(zkServicePath + ":" + address);
                if(channel == null || channel.isShutdown() || channel.isTerminated()){
                    channel = VertxChannelBuilder
                            .forTarget(vertx, address)
                            .usePlaintext(true)
                            .build();
                    channelMap.put(zkServicePath + ":" + address,channel);
                }
                return channel;
            }
        } finally {
            loopLock.unlock();
        }
        return null;
    }


    public List<String> getServerAddress(String zkNodeName) {
        List<String> addressSet = serverAddressMap.get(this.getZkServicePath(zkNodeName));
        if(addressSet == null){
            addressSet  = new ArrayList<>();
        }
        if(addressSet.isEmpty()){
            try {
                addressSet.addAll(this.getServerServicesAddress(zkNodeName));
            } catch (Exception e){

            }
        }
        return addressSet;

    }

    /**
     * 客户端获取地址列表
     * @return
     * @throws Exception
     */
    private List<String> getServerServicesAddress(String zkNodeName) throws Exception {
        PathChildrenCache serviceNodes = pathChildrenCacheMap.get(zkNodeName);
        if(serviceNodes == null){
            serviceNodes = this.setZkNodeListener(zkNodeName);
        }
        List<ChildData> children = serviceNodes.getCurrentData();
        if (children == null || children.isEmpty()) {
            LOGGER.warn("getServicesForNode return empty");
            return null;
        }
        return children.stream().map(child -> {
            try {
                return new String(child.getData());
            } catch (Exception e) {
                throw Throwables.propagate(e);
            }
        }).collect(Collectors.toList());
    }

    private String getZkServicePath(String nodeName){
        return ZK_SERVICE_PATH_ROOT + nodeName;
    }

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        String addressPath = event.getData().getPath();
        String pathKey = addressPath.substring(0,addressPath.lastIndexOf("/"));

        switch (event.getType()) {
            case CHILD_ADDED:
                String addData = new String(event.getData().getData());
                nodeChangeMap.put(pathKey,true);
                List<String> address = serverAddressMap.get(pathKey);
                if(address == null){
                    address = new ArrayList<>();
                }
                address.add(new String(addData));
                serverAddressMap.put(pathKey,address);

                LOGGER.info("Weird event that add node. path:" + event.getData().getPath());
                break;
            case CHILD_REMOVED:
                String removeAddress = new String(event.getData().getData());
                nodeChangeMap.put(pathKey,true);
                List<String> addressr = serverAddressMap.get(pathKey);
                if(addressr != null && addressr.size() > 1){
                    addressr.remove(removeAddress);
                    serverAddressMap.put(pathKey,addressr);
                }
                ManagedChannel channelr = channelMap.get(pathKey + ":" + removeAddress);
                if(channelr != null && channelr.getState(true) != ConnectivityState.SHUTDOWN){
                    channelr.shutdown().awaitTermination(1, TimeUnit.SECONDS);
                }
                LOGGER.info("Weird event that remove node. path:" + event.getData().getPath());
                break;
            case CHILD_UPDATED:
                LOGGER.info("Weird event that update node. path:" + event.getData().getPath());
                break;
            case CONNECTION_SUSPENDED:
                LOGGER.warn("Weird event is CONNECTION_SUSPENDED. path:" + event.getData().getPath());
                return;
            case CONNECTION_LOST:
                LOGGER.warn("Weird event is CONNECTION_LOST. path:" + event.getData().getPath());
                return;
            default:
                return;
        }
    }

    private void loadProperties(String resourceLocation) {
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = ctxClsLoader.getResourceAsStream(resourceLocation);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conf = new JsonObject(sb.toString());
            LOGGER.info("ZkMultiServiceRegistration Loaded zookeeper.json file from resourceLocation=" + resourceLocation);
        } catch (Exception e) {
            LOGGER.error("ZkMultiServiceRegistration Failed to load zookeeper config" + e);
        }
    }

    @Override
    public void close() throws IOException {
        curator.close();
    }

    public static void main(String[] args) {
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5,10000);
            CuratorFramework curator = CuratorFrameworkFactory.builder()
                    .connectString("10.212.25.44:2181,10.212.25.81:2181,10.212.25.35:2181")
                    .namespace("xservice")
                    .sessionTimeoutMs(20000)
                    .connectionTimeoutMs(3000)
                    .retryPolicy(retryPolicy).build();
            curator.start();
            curator.getZookeeperClient().getZooKeeper().addAuthInfo("digest","xservice:xservicexservice".getBytes());
            //        zk.zk.addAuthInfo("digest","xservice:xservicexservicee".getBytes());
            List<String> children = curator.getZookeeperClient().getZooKeeper().getChildren("/xservice/grpcServices/nodes",false);
            int i = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
