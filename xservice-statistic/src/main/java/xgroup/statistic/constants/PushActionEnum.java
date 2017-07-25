package xgroup.statistic.constants;

/**
 * Created by lufei
 * Date : 2017/7/25 17:28
 * Description :
 */
public enum PushActionEnum {
    SEND(1), ARRIVE(2);

    private int type;

    PushActionEnum(int i) {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
