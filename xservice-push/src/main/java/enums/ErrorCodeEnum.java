package enums;

public enum ErrorCodeEnum {
	
	SUCCESS("success","成功"),
	FAIL("fail","失败");
	
	private String code;
	private String msg;
	
	private ErrorCodeEnum(String code,String msg) {
		
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
