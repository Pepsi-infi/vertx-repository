package user.childlock.request;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * checkChildLock
 * @param type
 *            操作类型，1--查询用户儿童锁状态，
 *            2--校验用户密码，获取修改PIN码的token；其他值非法，报“非法参数”错误
 * @param ticket
 *            actionType=2时，传值用户密码 md5加密
 * @param pin
 *            actionType=3时，传PIN码
 * @param commonParam
 * @return
 */

/**
 * SetChildLock
 * @param type
 *            操作类型，1--首次设置儿童锁，需要参数pin，
 *            2--使用PIN码开关儿童锁，需要参数pin和status；
 *            3--使用lockToken重置儿童锁并设置对应状态，需要参数pin、status和lockToken；其他值非法，报“非法参数”
 *            错误
 * @param pincode
 *            4位数字
 * @param status
 *            actionType=2或3时必传；0--关闭；1--开启，其他值非法
 * @param lockToken
 *            用户忘记PIN码时（actionType=3），通过校验密码方式获取到的修改重置PIN码token
 *            （参见接口：检查儿童锁状态和获取修改PIN码的token），进行PIN码重置，并设置响应儿童锁状态
 * @param sign
 *            status和pincode加密
 * @param commonParam
 * @return
 */
@DataObject(generateConverter = true)
public class ChildLockRequest {

    private Integer actionType;

    private String ticket;

    private String pin;

    private Integer status;

    private String lockToken;

    //一些通用字段
    private String uid; // 用户id

    private String token; // 验证号

    private String terminalApplication; // 应用,领先版,汽车,电视(letv--乐视自有版，letv-common--通用版，及CIBN版等)

    private String langcode = "zh_cn"; // 语言代码

    private String wcode; // 区域代码

    private String terminalBrand;// 设备品牌

    private String mac;// 设备mac

    /**
     * 销售地，显示内容主要依赖此属性，目前用于定位默认设备所在地域，如：cn,hk,us,in，值从设备系统中获取
     */
    private String salesArea;

    public ChildLockRequest() {
        super();
    }

    public ChildLockRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChildLockRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChildLockRequestConverter.toJson(this, json);
        return json;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLockToken() {
        return lockToken;
    }

    public void setLockToken(String lockToken) {
        this.lockToken = lockToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTerminalApplication() {
        return terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public String getWcode() {
        return wcode;
    }

    public void setWcode(String wcode) {
        this.wcode = wcode;
    }

    public String getTerminalBrand() {
        return terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
