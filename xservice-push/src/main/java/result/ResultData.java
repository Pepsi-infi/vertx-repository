package result;

import java.io.Serializable;

import enums.ErrorCodeEnum;
import io.vertx.core.json.JsonObject;

public class ResultData<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 863932252336268833L;

    public static final int SUCCESS = 0;

    public static final int FAIL = 1;

    public static final String COMMON_ERR_MSG = "网络异常";

    public static final String OBJECT_ISNULL_MSG = "对象不能为空";

    public static final String SYSTEM_ERR_MSG = "系统异常";

    public static final String VALIDATE_ERR_MSG = "上送数据校验未通过";

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回描述
     */
    private String msg;

    /**
     * 返回对象
     */
    private T data;

    public ResultData() {
        this.code = FAIL;
        this.msg = "";
        this.data = null;
    }

    public ResultData(T data) {
        this.code = FAIL;
        this.msg = "";
        this.data = data;
    }

    public ResultData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public ResultData(ErrorCodeEnum errorEnum, T data) {
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public ResultData<T> backResult(ErrorCodeEnum errorEnum,T data){
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
        this.data = data;
        return this;
    }
    
    @Override
    public String toString() {
    	
    	return JsonObject.mapFrom(this).toString();
    }
}
