package constants;

/**
 * Created by lufei
 * Date : 2017/7/27 15:33
 * Description :
 */
public enum AppCodeEnum {
    SY_PASSENGER(1001),
    SY_DRIVER(1002);

    private int code;

    AppCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
