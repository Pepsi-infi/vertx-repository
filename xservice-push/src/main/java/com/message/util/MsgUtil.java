package com.message.util;

import java.util.UUID;

/**
 * Created by weim on 2017/7/25.
 */
public class MsgUtil {

    private String createMsgId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
