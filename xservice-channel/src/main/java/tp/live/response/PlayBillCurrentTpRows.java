package tp.live.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayBillCurrentTpRows {

    private String channelId;// 频道id

    private ProgramTp cur;// 如果当前没有节目单，cur，next和pre都不存在

    private ProgramTp pre;//

    private ProgramTp next;// 下一个节目

    private String errMsg;// 如果当前没有节目单那么返回,"没有当前节目单"

    public PlayBillCurrentTpRows() {
    }

    public PlayBillCurrentTpRows(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PlayBillCurrentTpRowsConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PlayBillCurrentTpRowsConverter.toJson(this, json);
        return json;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public ProgramTp getCur() {
        return cur;
    }

    public void setCur(ProgramTp cur) {
        this.cur = cur;
    }

    public ProgramTp getPre() {
        return pre;
    }

    public void setPre(ProgramTp pre) {
        this.pre = pre;
    }

    public ProgramTp getNext() {
        return next;
    }

    public void setNext(ProgramTp next) {
        this.next = next;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
