package com.message.constant;

public enum PushTypeEnum {
	
	SOCKET("sokit","套接字推送"),
	XIAOMI("xiaomi","小米推送"),
	GCM("gcm","谷歌推送");
	
	private String code;
	private String msg;
	
	private PushTypeEnum(String code,String msg) {
		this.code=code;
		this.msg=msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
