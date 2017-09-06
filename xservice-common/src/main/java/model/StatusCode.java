package model;

public enum StatusCode {

    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    VALIDATE_ERR(2, "数据校验未通过");

    private int code;
    private String msg;

    private StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
