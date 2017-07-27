package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

/**
 * Created by weim on 2017/7/25.
 */
public class MsgUtil {

    public static String createMsgId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
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
