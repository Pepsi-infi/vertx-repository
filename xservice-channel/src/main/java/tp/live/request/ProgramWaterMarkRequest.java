package tp.live.request;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class ProgramWaterMarkRequest {

    public static final String HOST = "i.api.letv.com";
    public static final String REQ_URL = "/mms/inner/loop/program/watermarks";
    private Integer channelId;
    private String playDate;
    private Integer curPage;
    private Integer pageSize;
    private String platform;

    public ProgramWaterMarkRequest(){}
    public ProgramWaterMarkRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ProgramWaterMarkRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ProgramWaterMarkRequestConverter.toJson(this, json);
        return json;
    }
    
    public String sign() {
        return REQ_URL;
    }

    public String build() {
        try {
            StringBuilder subUrl = new StringBuilder();
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC
                        || (field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                    continue;
                }
                Object value = field.get(this);
                if (value != null && !"".equals(value.toString())) {
                    subUrl.append("&");
                    subUrl.append(field.getName()).append("=");
                    subUrl.append(value);
                }
            }
            String param = subUrl.toString();
            if (param != null && param.startsWith("&")) {
                param = param.substring(1);
            }
            return REQ_URL + "?" + param;
        } catch (Exception e) {
            return this.toString();
        }
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
