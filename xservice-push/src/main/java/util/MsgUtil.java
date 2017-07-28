package util;

import enums.PushTypeEnum;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

/**
 * Created by weim on 2017/7/25.
 */
public class MsgUtil {

    private static Logger logger = LoggerFactory.getLogger(MsgUtil.class);

    //返回对应code
    public static String convertCode(String srcCode){
        for(PushTypeEnum e : PushTypeEnum.values()){
            if(e.getSrcCode().equals(srcCode)){
                return e.getCode();
            }
        }
        logger.error(" PushTypeEnum not found realtion : " + srcCode);
        return null;
    }


    public static String createMsgId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static String getSystemPath(String filePath){
        return filePath = filePath.replace("/", File.separator);
    }

    public static byte[] objectToByte(Object obj) {
        byte[] bs=null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            bs =  bos.toByteArray();
            oos.close();
            bos.close();
            return bs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bs;
    }



}
