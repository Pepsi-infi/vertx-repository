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
 *  消息工具类
 * Created by weim on 2017/7/25.
 */
public class MsgUtil {

    private static Logger logger = LoggerFactory.getLogger(MsgUtil.class);

    /**
     *  上游推送类型 转换成 下游推送类型
     */
    public static String convertCode(String srcCode){
        for(PushTypeEnum e : PushTypeEnum.values()){
            if(e.getSrcCode().equals(srcCode)){
                return e.getCode();
            }
        }
        logger.error(" PushTypeEnum not found realtion : " + srcCode);
        return null;
    }

    /**
     *  java生成UUID
     * @return
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 替换成系统路径
     * @param filePath
     * @return
     */
    public static String getSystemPath(String filePath){
        return filePath = filePath.replace("/", File.separator);
    }

    /**
     * 对象序列化， 输出byte[]
     */
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
