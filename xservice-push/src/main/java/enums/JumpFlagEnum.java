package enums;

public enum JumpFlagEnum {
	
	MESSAGE_CENTER_PAGE(0,"sqyc://01zhuanche.com/messageCenter","消息中心页"),
	MEMBER_LEVEL_PAGE(1,"sqyc://01zhuanche.com/memberLevel","用户等级页"),
	MEMBER_ACCOUNT_PAGE(2,"sqyc://01zhuanche.com/account","个人账户页"),
	CREDIT_CARD_PAGE(3,"sqyc://01zhuanche.com/creditCard","信用卡页"),
	MEMBER_JOURNEY_PAGE(4,"sqyc://01zhuanche.com/mytrip","用户行程页"),
	MEMBER_COUPON_PAGE(5,"sqyc://01zhuanche.com/coupon","用户优惠券页"),
	INVITE_FRIENDS_PAGE(6,"sqyc://01zhuanche.com/inviteFriends","邀请好友页");
	
	private int code;
	private String msg;
	private String description;

	JumpFlagEnum(int code, String msg, String description) {
		this.code = code;
		this.msg = msg;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
