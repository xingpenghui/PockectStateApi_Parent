package com.pockectstate.api.server.util;

import com.pockectstate.api.common.model.JWTToken;

/**
 *@Author feri
 *@Date Created in 2019/7/12 10:11
 */
public class DeviceKey_Util {
    public static String createKey(JWTToken jwtToken){
        StringBuffer buffer=new StringBuffer();
        buffer.append(jwtToken.getPhone());
        buffer.append("_");
        buffer.append(jwtToken.getDevice());
        buffer.append("_");
        buffer.append(jwtToken.getDeviceMac());
        return buffer.toString();
    }
}
