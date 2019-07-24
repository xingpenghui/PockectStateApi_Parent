package com.pockectstate.api.pockectstateapi_serveruser.util;

import com.alibaba.fastjson.JSON;
import com.pockectstate.api.common.config.RedisKey_Config;
import com.pockectstate.api.common.model.JWTToken;
import com.pockectstate.api.common.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2019/7/24 15:02
 */
@Service
public class Token_Util {
    @Autowired
    private JedisUtil jedisUtil;

    public int getUid(String token){
        String k=jedisUtil.get(RedisKey_Config.JWTTOKEN_TOKEN+token);
        if(k!=null && k.length()>0) {
            JWTToken jwtToken = JSON.parseObject(k, JWTToken.class);
            return jwtToken.getId();
        }else {
            return -1;
        }
    }
}
