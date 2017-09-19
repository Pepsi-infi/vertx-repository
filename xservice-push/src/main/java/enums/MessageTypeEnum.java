package enums;

/**
 * 消息类型
 * @author hongye.lv
 * @date 2015/11/03 09:41
 */
public enum MessageTypeEnum {
    SENDPINGMSG(14, "心跳消息"),
    MSG_TYPE_COUNT_INFO(54, "未消息数"),
    MSG_TYPE_LOGOUT(56, "用户退出"),
    MSG_TYPE_REDIRECT(57, "重新登录"),
    SENDCHATMSG(122, "聊天消息"),
    BROADCAST_TYPE(201, "广播消息"),
    ACKNOWLEDGEMSG(313, "消息确认"),
    DYNAMICFLIGHTNO(303, "航班状态变更"),
    SENDNEWORDERMSG(300, "新订单"),//保留，现在用305
    ORDERCANCEL(301, "取消订单"),
    ROBORDERMSG(302, "司机抢单"),
    DRIVERLOGOUT(304, "强制下线"),
    ORDERACCEPTED(305, "订单已受理"),
    WORKTIME(306, "今日在线时长，班制内在线时长"),
    ORDERNOINCOME(307, "今日完成订单数，今日收入，本月收入"),
    DRIVERUPGRADEPUSH(308, "司机端升级推送"),
    DRIVERSTATUS(309, "司机状态"),
    CIRCLEINNEROUTER(310, "圈里圈外"),
    ORDERSTATUS(400, "下单结果通知"),
    ORDERCHANGE(401, "订单状态更改通知"),
    CANCELORDER(402, "司机取消订单"),
    ASSIGNORDER(403, "司机改派订单"),
    COUPON_TYPE(405, "优惠折扣券类型"),
    COMPANY_MESSAGE_PUSH(406,"公司消息推送");

    private int type;
    private String name;

    MessageTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
