package tp.cms.request;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/4/25
 * Time: 18:58
 */
public class MutiLangBlockUrl{
    public static String REQ_HOST = "api.cms.le.com";
    public static Integer REQ_PORT = 80;
    private static String REQ_URL="/blockNew/get";
    private String id;
    private String lang;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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

}
