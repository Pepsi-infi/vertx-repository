package tp.cms.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * 子栏目类别request
 */
@DataObject(generateConverter = true)
public class ColumnListRequest {
//    private String REQ_URL = "http://api.cms.le.com/column/list?";
    private String REQ_URL = "/column/list?";
    /**
     * 父ID
     */
    private Integer pid;
    /**
     * 平台：live
     */
    private String platform = "live";

    /**
     * 语言
     */
    private String lang;

    /**
     * 参数
     */
    private Map<String, String> params = new HashMap<String, String>(3);

    public ColumnListRequest() {
        super();
    }

    public ColumnListRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ColumnListRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ColumnListRequestConverter.toJson(this, json);
        return json;
    }
    
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        if(pid != null) {
            this.pid = pid;
            this.params.put("pid", pid.toString());
        }
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        if(StringUtils.isNotBlank(platform)){
            this.platform = platform;
            this.params.put("platform", platform);
        }
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        if(StringUtils.isNotBlank(lang)){
            this.lang = lang;
            this.params.put("lang", lang);
        }
    }

    public String sign() {
        return this.REQ_URL;
    }

    public String build() {
        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> entry : params.entrySet()) {
            sb.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        if (sb.length() > 1) {
            if (REQ_URL.contains("?")) {
                if (REQ_URL.endsWith("?")) {// 以?结尾则去掉第一个&
                    return REQ_URL + sb.toString().substring(1);
                } else {// 否则直接追加参数
                    return REQ_URL + sb.toString();
                }
            } else {// 没有?则加 ?并去掉第一个参数
                return REQ_URL + "?" + sb.toString().substring(1);
            }
        } else {
            return REQ_URL;
        }

    }

    @Override
    public String toString() {
        return "ColumnListRequest [REQ_URL=" + REQ_URL + ", pid=" + pid + ", platform=" + platform + ", lang="
                + lang + ", params=" + params + "]";
    }

}
