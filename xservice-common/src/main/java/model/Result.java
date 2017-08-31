package model;

import com.google.common.base.Preconditions;

import java.io.Serializable;

/**
 * 消息中心统一返回结果封装
 *
 * @param <T>
 */
public class Result<T> implements Serializable {


    private int code;
    private String msg;
    private T data;
    private long time;


    public static <T> Result<T> create(StatusCode status, T data) {
        Result response = new Result();
        response.setCode(status.getCode());
        response.setMsg(status.getMsg());
        response.setData(data);
        response.setTime(System.currentTimeMillis());
        return response;
    }

    public static Result success(Object data) {
        Preconditions.checkArgument(data != null);
        return Result.create(StatusCode.SUCCESS, data);
    }

    public static Result fail(StatusCode status, Object data) {
        Preconditions.checkArgument(status != null);
        Preconditions.checkArgument(data != null);
        return Result.create(status, data);
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("code=").append(code);
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
