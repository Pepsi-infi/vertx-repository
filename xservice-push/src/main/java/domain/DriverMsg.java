package domain;

import java.util.Date;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class DriverMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1938352322313410619L;
	private long id;
	private String title;// 标题
	private String content;// 内容
	private String synopsis;// 内容梗概
	private int isShellsScreen;// 是否弹屏 0-不弹屏 1-弹屏
	private int status;// 推送状态 0-未推送 1-已推送
	private int msgType;// 消息类型 扩展用
	private String jumpUrl;// 跳转地址
	private int isImportant;// 是否重要
	private int enabled;// 是否可用 0-不可用 1-可用
	private String createUser;// 创建人
	private String updateUser;// 修改人
	private String createTime;
    private String updateTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public int getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(int isImportant) {
		this.isImportant = isImportant;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public int getIsShellsScreen() {
		return isShellsScreen;
	}

	public void setIsShellsScreen(int isShellsScreen) {
		this.isShellsScreen = isShellsScreen;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public DriverMsg() {
		super();
	}
	
	public DriverMsg(JsonObject json) {
	    DriverMsgConverter.fromJson(json,this);
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		DriverMsgConverter.toJson(this, json);
		return json;
	}
}
