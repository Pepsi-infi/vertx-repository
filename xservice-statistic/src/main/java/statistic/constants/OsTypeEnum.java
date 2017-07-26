package statistic.constants;

/**
 * Created by lufei
 * Date : 2017/7/25 17:28
 * Description :
 */
public enum OsTypeEnum {
    ANDROID(1), IOS(2);

    private int type;

    OsTypeEnum(int i) {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
