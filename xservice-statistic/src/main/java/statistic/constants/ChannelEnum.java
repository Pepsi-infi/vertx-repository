package statistic.constants;

/**
 * Created by lufei
 * Date : 2017/7/26 15:58
 * Description :
 */
public enum ChannelEnum {
    SOCKET(1),
    GCM(2),
    XIAOMI(3),
    APNS(4);

    private int type;

    ChannelEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
