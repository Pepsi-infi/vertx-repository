package tp.live.request;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class PlayBillCurrentRequest {

    // http://wiki.letv.cn/pages/viewpage.action?pageId=32705577

    public static final String HOST = "api.live.letv.com";

    private static final String REQ_URL = "/v1/playbill/current2/";

    /**
     * http://api.live.letv.com/v1/dictionary/00013
     */
    private int clientId;

    private Integer channelIds;

    public PlayBillCurrentRequest() {
    }

    public PlayBillCurrentRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PlayBillCurrentRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PlayBillCurrentRequestConverter.toJson(this, json);
        return json;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Integer getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(Integer channelIds) {
        this.channelIds = channelIds;
    }

    public String build() {
        return new StringBuffer("http://").append(HOST).append(REQ_URL).append(clientId).append("?channelIds=")
                .append(channelIds).toString();
    }
}
