package com.pockectstate.api.pockectstateapi_serveruser.service.impl;

import com.alibaba.fastjson.JSON;
import com.pockectstate.api.common.config.RedisKey_Config;
import com.pockectstate.api.common.model.JWTToken;
import com.pockectstate.api.common.util.JedisUtil;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_serveruser.dao.AddressDao;
import com.pockectstate.api.pockectstateapi_serveruser.service.AddressService;
import com.pockectstate.api.pockectstateapi_serveruser.util.Token_Util;
import com.pockectstate.entity.user.TAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2019/7/24 13:19
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDao addressDao;
    @Autowired
    private Token_Util token_util;

    @Override
    public R save(TAddress address,String token) {
        //令牌 获取 用户信息 2种方式：
        //1、直接去Redis中获取
        //2、反解析Token
//        String k=jedisUtil.get(RedisKey_Config.JWTTOKEN_TOKEN+token);
//        JWTToken jwtToken= JSON.parseObject(k,JWTToken.class);
        address.setUid(token_util.getUid(token));
        addressDao.save(address);
        return R.setOK("OK",null);
    }

    @Override
    public R querySignle(String token) {

        return R.setOK("OK",addressDao.selectByUidDefault(token_util.getUid(token)));
    }

    @Override
    public R queryUid(String token) {
        return R.setOK("OK",addressDao.selectByUid(token_util.getUid(token)));
    }
}
