package enums;

public enum MsgStatusEnum {
	FAIL(0,"失败"),
	SUCCESS(1,"成功"),
	UNKNWON(2,"未知");
	
	private int code;
	private String msg;
	
	private MsgStatusEnum(int code,String msg) {
		this.code=code;
		this.msg=msg;
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
	
	
}
