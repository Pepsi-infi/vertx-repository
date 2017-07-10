package grpc;

import com.baidu.bjf.remoting.protobuf.ProtobufIDLGenerator;

import cache.dto.Video;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：17/3/9
 * Time: 11:25
 */
public class GetProtoFileMain {
    public static void main(String[] args) {
        String proto = ProtobufIDLGenerator.getIDL(Video.class);
        System.out.println("GetProtoFile ：" + proto);
    }
}
