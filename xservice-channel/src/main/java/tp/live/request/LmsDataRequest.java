package tp.live.request;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class LmsDataRequest {
    public static final int FETCHSIZE = 500;// 默认每页查500条
    private int fetchSize = 300; // 默认每页查300条
    private String url;
    private String sourceId;
    private int offSet;
    private String splatid; // 非必填
    private Map<String, Object> paramMap = new TreeMap<String, Object>();

    public LmsDataRequest(){
    }
    
    public LmsDataRequest(JsonObject json) {
        LmsDataRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LmsDataRequestConverter.toJson(this, json);
        return json;
    }

    public void setSourceId(String sourceId) {
        paramMap.put("sourceId", sourceId);
        this.sourceId = sourceId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setOffSet(int offSet) {
        paramMap.put("offSet", offSet);
        this.offSet = offSet;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setFetchSize(int fetchSize) {
        paramMap.put("fetchSize", fetchSize);
        this.fetchSize = fetchSize;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setSplatid(String splatid) {
        this.splatid = splatid;
    }

    public String getSplatid() {
        return splatid;
    }

    public String sign() {
        return url;
    }

    public String build() {
        StringBuffer paramUrl = new StringBuffer();
        if (url.contains("?") && url.split("\\?").length > 1) {
            if (!url.endsWith("&")) {
                paramUrl = paramUrl.append("&");
            }
        } else {
            paramUrl = paramUrl.append("?");
        }
        for (Entry<String, Object> entry : paramMap.entrySet()) {
            paramUrl.append(entry.getKey()).append("=" + entry.getValue() + "&");
        }
        String requestUrl = url + paramUrl.toString().substring(0, paramUrl.toString().length() - 1);

        return requestUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

}
