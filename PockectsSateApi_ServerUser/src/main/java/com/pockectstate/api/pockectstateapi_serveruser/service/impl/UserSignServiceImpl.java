package com.pockectstate.api.pockectstateapi_serveruser.service.impl;

import com.alibaba.fastjson.JSON;
import com.pockectstate.api.common.config.RedisKey_Config;
import com.pockectstate.api.common.dto.SignDto;
import com.pockectstate.api.common.model.JWTToken;
import com.pockectstate.api.common.util.JedisUtil;
import com.pockectstate.api.common.util.Jwt_Util;
import com.pockectstate.api.common.util.TimeUtil;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_serveruser.dao.UserSignDao;
import com.pockectstate.api.pockectstateapi_serveruser.dao.UserWalletDao;
import com.pockectstate.api.pockectstateapi_serveruser.dao.WalletlogDao;
import com.pockectstate.api.pockectstateapi_serveruser.service.UserSignService;
import com.pockectstate.entity.goods.UserSign;
import com.pockectstate.entity.goods.Walletlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

/**
 *@Author feri
 *@Date Created in 2019/7/15 14:32
 */
@Service
public class UserSignServiceImpl implements UserSignService {

    private UserSignDao signDao;
    private UserWalletDao walletDao;
    private WalletlogDao walletlogDao;
    private JedisUtil jedisUtil=JedisUtil.getInstance();
    @Autowired
    public UserSignServiceImpl(UserSignDao signDao,WalletlogDao walletlogDao,UserWalletDao walletDao){
        this.signDao=signDao;
        this.walletDao=walletDao;
        this.walletlogDao=walletlogDao;
    }

    //签到
    @Override
    @Transactional
    public R save(String token) {
        //实现签到
        System.out.println("令牌："+token);
        JWTToken jwtToken=Jwt_Util.parseJson(jedisUtil.get(RedisKey_Config.JWTTOKEN_TOKEN+token));
        UserSign userSign=signDao.selectByUidLast(jwtToken.getId());
        UserSign daySign=new UserSign();
        daySign.setUid(jwtToken.getId());

        int e=0;
        int m=new Random().nextInt(5)+1;//积分奖励1-5随机
        if(userSign==null){
            //第一次  签到
            e=50;
            daySign.setDays(1);
            daySign.setSinfo("第一次签到，获取："+m+",额外奖励"+e);
        }else {
            //判断是否连续签到
            int cd=TimeUtil.getDateDay(new Date())-TimeUtil.getDateDay(userSign.getSdate());
            if(cd==1){
                //连续签到
                int d=userSign.getDays()+1;
                daySign.setDays(d);
                if(d%365==0){
                    e=100;
                }else if(d%30==0){
                    e=15+m;
                }else if(d%5==0){
                    e=10;
                }
                if(e>0){
                        daySign.setSinfo("恭喜你连续签到"+d+"天，额外奖励："+e);
                }else {
                    daySign.setSinfo("恭喜你连续签到，继续坚持");
                }
            }else if(cd >1){
                //断签
                daySign.setDays(1);
                daySign.setSinfo("你上一次连续签到"+userSign.getDays()+"天，请坚持下来");
            }
        }
        daySign.setSaward(m);
        daySign.setSextraaward(e);
        signDao.insert(daySign);
        //改变钱包的兜豆
        walletDao.updatePsbean(jwtToken.getId(),e+m);
        //新增流水
        Walletlog walletlog=new Walletlog();
        walletlog.setInfo(daySign.getSinfo());
        walletlog.setMoney(m+e);
        walletlog.setType((byte)1);
        walletlog.setUid(jwtToken.getId());
        walletlog.setWid(daySign.getId().intValue());
        walletlogDao.insert(walletlog);

        return R.setOK("OK",userSign);
    }
    //查询用户当前月的签到数据
    //查询用户最近30天的签到数据
    @Override
    public R queryByUid(String token) {
        JWTToken jwtToken= Jwt_Util.parseJson(jedisUtil.get(RedisKey_Config.JWTTOKEN_TOKEN+token));
        return R.setOK("OK",signDao.selectCurrMonth(jwtToken.getId(),TimeUtil.getMonth()));
    }

    @Override
    public R queryTj(String token) {
        JWTToken jwtToken= Jwt_Util.parseJson(jedisUtil.get(RedisKey_Config.JWTTOKEN_TOKEN+token));
        SignDto signDto=signDao.selectTj(jwtToken.getId());
        signDto.setMonthDays((int)signDao.selectMonth(jwtToken.getId()));
        return R.setOK("OK",signDto);
    }
    //查询用户的所有签到数据
    @Override
    public R queryUidAll(String token) {
        JWTToken jwtToken= Jwt_Util.parseJson(jedisUtil.get(RedisKey_Config.JWTTOKEN_TOKEN+token));
        return R.setOK("OK",signDao.selectByUid(jwtToken.getId()));
    }
    //检查今日是否可以签到  可以签到返回true 不可以false
    @Override
    public R checkSign(String token) {
        JWTToken jwtToken= Jwt_Util.parseJson(jedisUtil.get(RedisKey_Config.JWTTOKEN_TOKEN+token));
        UserSign sign=signDao.selectByUidDay(jwtToken.getId(),TimeUtil.getDate());
        if(sign!=null){
            //签过到
            return R.setERROR("今日已经签到",null);
        }else {
            return R.setOK("今日未签到",null);
        }
    }
}
