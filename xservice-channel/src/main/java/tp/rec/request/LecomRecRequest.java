package tp.rec.request;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/7/22
 * Time: 15:33
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class LecomRecRequest extends RecBaseRequest{
    public static String REQ_HOST="us.rec.letv.com";
    private static final String REQ_URL = "/m_le/";
    private String mpt = "420003_1";
    private String version = "mobile_m";

    //以下三个参数必传
    private String versiontype = "IntelligentOperation";
    private String action = "rec";
    private Integer disable_record_exposure = 1;


    public LecomRecRequest() {
    }


    public LecomRecRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        LecomRecRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LecomRecRequestConverter.toJson(this, json);
        return json;
    }

    @Override
    public String getMpt() {
        return mpt;
    }

    @Override
    public void setMpt(String mpt) {
        this.mpt = mpt;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getVersiontype() {
        return versiontype;
    }

    @Override
    public void setVersiontype(String versiontype) {
        this.versiontype = versiontype;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public Integer getDisable_record_exposure() {
        return disable_record_exposure;
    }

    @Override
    public void setDisable_record_exposure(Integer disable_record_exposure) {
        this.disable_record_exposure = disable_record_exposure;
    }

    /**
     * 将本对象的变量拼接成url参数列表的字符串
     * 字符串开头不包含&符号
     * @return
     */
    @Override
    public String build() {
        try {
            StringBuilder subUrl = new StringBuilder();
            Class o = this.getClass();
            Field[] fields = o.getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC
                        || (field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                    continue;
                }
                Method m = (Method) o.getMethod(
                        "get" + getMethodName(field.getName()));

                Object value = m.invoke(this);
                if (value != null && !"".equals(value.toString())) {
                    // 推荐只有zh_hk的数据，无论语言是什么，香港版的lang字段都只能传zh_hk
                    if ("lang".equals(field.getName()) && "hk".equals(this.getRegion())) {
                        subUrl.append("&lang=zh_hk");
                    } else {
                        subUrl.append("&").append(field.getName()).append("=").append(value);
                    }
                }
            }
            String param = subUrl.toString();
            if (param != null && param.startsWith("&")) {
                param = param.substring(1);
            }
            return REQ_URL + "?" + param;
        } catch (Exception e) {
            return REQ_URL + "?" + this.toString();
        }
    }

    private static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}
