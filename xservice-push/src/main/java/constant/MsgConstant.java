package constant;

/**
 * Created by weim on 2017/7/25.
 */
public class  MsgConstant {

    public static final int ZERO = 0;

    public enum SendMethod{
        SEND_MSG("sendmsg");
        private String msg;
        SendMethod(String msg){
            this.msg = msg;
        }
        public String getMsg(){
            return msg;
        }
    }


}
