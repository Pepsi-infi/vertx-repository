package constant;

/**
 * Created by weim on 2017/7/25.
 */
public class  MsgConstant {

    //socket推送时的一个字段默认值
    public static final int ZERO = 0;


    public enum SendMethod{
        //以前的下游需要
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
