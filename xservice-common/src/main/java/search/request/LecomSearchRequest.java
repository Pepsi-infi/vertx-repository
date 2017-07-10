package search.request;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/9/6
 * Time: 11:09
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class LecomSearchRequest extends SearchRequest{
//    private static final String REQ_URL = ApplicationUtils.getConfValue(ApplicationConstants.SEARCH_RESULT_URL, ServerConfPath.LECOM);
    public static String REQ_HOST="le.so.letv.com";
    private static final String REQ_URL = "/interface";
    private String from = "mobile_le49"; // 搜索统计参数，搜索来源 mobile_04代表超级手机
    // vipIds:productid以逗号隔开,lecom的addon新增以下四个参数
    private String vipIds;
    private String sales_area;
    private String user_setting_country;
    private Integer repo_type=1;

    private Integer displayPlatformId=206;
    private Integer displayAppId=111;
    private String coopPlatform="-670056";
    private String city_info="US_0_0_0";

    public LecomSearchRequest() {
    }


    public LecomSearchRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        LecomSearchRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LecomSearchRequestConverter.toJson(this, json);
        return json;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getVipIds() {
        return vipIds;
    }

    @Override
    public void setVipIds(String vipIds) {
        this.vipIds = vipIds;
    }

    @Override
    public String getSales_area() {
        return sales_area;
    }

    @Override
    public void setSales_area(String sales_area) {
        this.sales_area = sales_area;
    }

    @Override
    public String getUser_setting_country() {
        return user_setting_country;
    }

    @Override
    public void setUser_setting_country(String user_setting_country) {
        this.user_setting_country = user_setting_country;
    }

    @Override
    public Integer getRepo_type() {
        return repo_type;
    }

    @Override
    public void setRepo_type(Integer repo_type) {
        this.repo_type = repo_type;
    }

    @Override
    public Integer getDisplayPlatformId() {
        return displayPlatformId;
    }

    public void setDisplayPlatformId(Integer displayPlatformId) {
        this.displayPlatformId = displayPlatformId;
    }

    @Override
    public Integer getDisplayAppId() {
        return displayAppId;
    }

    public void setDisplayAppId(Integer displayAppId) {
        this.displayAppId = displayAppId;
    }

    @Override
    public String getCoopPlatform() {
        return coopPlatform;
    }

    @Override
    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
    }

    @Override
    public String getCity_info() {
        return city_info;
    }

    @Override
    public void setCity_info(String city_info) {
        this.city_info = "US_0_0_0";
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
                    subUrl.append("&");
                    // extraParam较为特殊，里面等格式就是拼接好的搜索参数字符串
                    boolean bool = "extraParam".equals(field.getName());
                    if (!bool) {
                        subUrl.append(field.getName()).append("=");
                    }
                    subUrl.append(value);
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
