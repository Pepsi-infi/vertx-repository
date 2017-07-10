package tp.cms.response;

import utils.BaseResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsPageResponse extends BaseResponse {

    /**
     * 返回码(200:返回成功 2001:必填参数为空 500:服务器错误)
     */
    private int code;

    /**
     * 返回值描述信息
     */
    private String msg;

    /**
     * 首页数据
     */
    private CmsFragListResponse data;

    public CmsPageResponse() {
        super();
    }

    public CmsPageResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsPageResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsPageResponseConverter.toJson(this, json);
        return json;
    }
    
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CmsFragListResponse getData() {
        return data;
    }

    public void setData(CmsFragListResponse data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CmsPageResponse [code=" + code + ", msg=" + msg + ", data=" + data + "]";
    }
}
