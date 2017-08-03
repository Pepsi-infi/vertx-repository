package enums;

public enum PushTypeEnum {
	
	SOCKET("1","sokit","套接字推送"),
	GCM("2","gcm","谷歌推送"),
	XIAOMI("3","xiaomi","小米推送");


	/**
	 * 上游使用的标志
	 */
	private String srcCode;
	/**
	 * 推送类型
	 */
	private String code;
	/**
	 * 推送类型描述
	 */
	private String msg;
	
	private PushTypeEnum(String srcCode,String code,String msg) {
		this.srcCode = srcCode;
		this.code=code;
		this.msg=msg;
	}

	public String getSrcCode() {
		return srcCode;
	}

	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
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
