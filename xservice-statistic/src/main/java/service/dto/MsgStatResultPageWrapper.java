package service.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by lufei
 * Date : 2017/8/3 17:43
 * Description :
 */
@DataObject(generateConverter = true)

public class MsgStatResultPageWrapper {

    private int code;

    private String msg;

    private long time;

    private MsgStatResultPage data;

    public MsgStatResultPageWrapper() {
        super();
    }

    public MsgStatResultPageWrapper(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        MsgStatResultPageWrapperConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        MsgStatResultPageWrapperConverter.toJson(this, json);
        return json;
    }

    public MsgStatResultPage getData() {
        return data;
    }

    public void setData(MsgStatResultPage data) {
        this.data = data;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
