package tp.cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * 专题包response
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class SubjectContentTpResponse extends BaseResponse {
    /**
     * 
     */
    private static final long serialVersionUID = 8204068792719545064L;
    private Integer code;// 返回状态码
    private SubjectContent data;// 专题包实体对象
    private String msg;// 返回状态码对应消息

    public SubjectContentTpResponse() {

    }

    public SubjectContentTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SubjectContentTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SubjectContentTpResponseConverter.toJson(this, json);
        return json;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SubjectContent getData() {
        return data;
    }

    public void setData(SubjectContent data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SubjectContentTpResponse{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
