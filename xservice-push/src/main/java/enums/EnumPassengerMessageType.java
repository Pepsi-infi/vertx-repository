package enums;

/**
 *  消息类型
 */
public enum EnumPassengerMessageType {
	
    SENDPINGMSG(5014, "心跳消息"),
    
    AROUND_CARS_POSITION(5001, "周围车辆位置"),
    CURRENT_TRIP_DRIVER_POSITION(5002, "当前行程司机位置"),
    BIND_ORDER_SUCCESS(5003, "绑单成功"),
    PUSH_DRIVERS_NUMBER(5004, "推送司机数"),
    CANCEL_ORDER(5005, "订单取消"),
    ORDER_STATUS(5006, "订单状态"),
    FORCED_OFFLINE(5007, "被踢下线"),
    ADVERTISEMENT(5008, "广告消息"),
    
    MSG_TYPE_COUNT_INFO(5054, "未消息数"),
    MSG_TYPE_LOGOUT(5056, "用户退出"),
    MSG_TYPE_REDIRECT(5057, "重新登录"),
    
    ORDER_STATUS_INTERNATIONAL(6010, "订单状态"), // 专车国际订单状态

    MSG_TYPE_SUBSCRIBE_SUCCESS(800, "订阅"),
    MSG_TYPE_SUBSCRIBE_CANCEL(801, "取消订阅");

    private int type;
    private String name;

    EnumPassengerMessageType(int type, String name) {
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
