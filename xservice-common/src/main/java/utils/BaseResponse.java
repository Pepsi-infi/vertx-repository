package utils;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Response基类
 */
@DataObject(generateConverter = true, inheritConverter=true)
public class BaseResponse {

    /**
     * 业务正常状态码
     */
    public static final int RESPONSE_SUC_CODE = 1;
    /**
     * 业务异常状态码
     */
    public static final int RESPONSE_FAIL_CODE = 0;

    
    public BaseResponse() {
    }

    public BaseResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        BaseResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        BaseResponseConverter.toJson(this, json);
        return json;
    }
    
    private Integer status = RESPONSE_SUC_CODE;
    
    private Integer resultStatus = RESPONSE_SUC_CODE;
    
    private String errorCode;

    private String errorMessage;


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}
    
    
}
