package com.pockectstate.message.pockectstatemsg_servermsg.service.impl;

import com.pockectstate.api.common.config.RedisKey_Config;
import com.pockectstate.api.common.psenum.MsgLogType;
import com.pockectstate.api.common.util.JedisUtil;
import com.pockectstate.api.common.util.JuHeSms_Util;
import com.pockectstate.api.common.util.Random_Util;
import com.pockectstate.api.common.util.TimeUtil;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.entity.msg.MsgLog;
import com.pockectstate.entity.msg.SmsRestset;
import com.pockectstate.entity.msg.SmsSend;
import com.pockectstate.message.pockectstatemsg_servermsg.dao.MsgLogMapper;
import com.pockectstate.message.pockectstatemsg_servermsg.dao.SmsRestsetMapper;
import com.pockectstate.message.pockectstatemsg_servermsg.dao.SmsSendMapper;
import com.pockectstate.message.pockectstatemsg_servermsg.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.UnsupportedEncodingException;

/**
 *@Author feri
 *@Date Created in 2019/7/10 14:29
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private SmsSendMapper sendMapper;
    @Autowired
    private MsgLogMapper logMapper;
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private SmsRestsetMapper restsetMapper;

    @Override
    public R save(SmsSend smsSend) {
        sendMapper.insert(smsSend);
        MsgLog log=new MsgLog();
        log.setType(MsgLogType.validatecode.getIndex());
//        log.setContent(JSON.toJSONString(smsSend));
        log.setContent("发送："+smsSend.getPhone()+",验证码");
        logMapper.insert(log);

        return R.setOK("OK",null);
    }

    @Override
    public R checkCode(String phone, int code) {
        //验证验证码是否失效
        if(jedisUtil.exists(RedisKey_Config.VCODE_CODE+phone)){
            //校验是否正确
            String c=jedisUtil.get(RedisKey_Config.VCODE_CODE+phone);
            if(code==Integer.parseInt(c)){
                //成功
                return R.setOK("OK",null);
            }else {
                return R.setERROR("验证码不一致",null);
            }
        }else {
            return R.setERROR("验证码已经过期，请重新获取",null);
        }
    }

    @Override
    @Transactional
    public R sendSms(String phone,String ip) {
        //怎么发送短信
        boolean issend=true;//记录是否可以发送短信
        String msg="";//记录返回的内容
        //1、验证手机号是否可发
        if(jedisUtil.exists(RedisKey_Config.VCODE_FIRST+phone)){
            //一分钟内发过
           msg="一分钟内发送过，请查看手机短信";
           issend=false;
        }else if(jedisUtil.exists(RedisKey_Config.VCODE_SECOND+phone)){
            //10分钟 3次
            int c=Integer.parseInt(jedisUtil.get(RedisKey_Config.VCODE_SECOND+phone));
            if(c>=3){
                issend=false;
                msg="10分钟验证码次数已经达到上限";
            }
        }else if(jedisUtil.exists(RedisKey_Config.VCODE_THREE+phone)){
            //1小时4次
            int c=Integer.parseInt(jedisUtil.get(RedisKey_Config.VCODE_THREE+phone));
            if(c>=4){
                issend=false;
                msg="1小时验证码次数已经达到上限";
            }
        }else if(jedisUtil.exists(RedisKey_Config.VCODE_FOUR+phone)){
            //1天20次
            int c=Integer.parseInt(jedisUtil.get(RedisKey_Config.VCODE_FOUR+phone));
            if(c>=20){
                issend=false;
                msg="今天验证码次数已经达到上限";
            }
        }
        boolean isfirst=true;
        int f=1;
        if(issend){
            //说明 手机号 可以发送短信
            int cd=0;
            if(jedisUtil.exists(RedisKey_Config.VCODE_CODE+phone)){
                //如果发过  就取出
                cd=Integer.parseInt(jedisUtil.get(RedisKey_Config.VCODE_CODE+phone));
                isfirst=false;
            }else {
                cd=Random_Util.createNum(6);
            }
            try {
                //发送短信
                JuHeSms_Util.sendMsg(phone,cd);
                //如果短信发送成功 更新次数 4个
                if(isfirst){
                    //验证码 5分钟有效
                    jedisUtil.setExpire(RedisKey_Config.VCODE_CODE+phone,cd+"",300);
                }
                //设置1分钟
                setValue(RedisKey_Config.VCODE_FIRST+phone,60);
                //设置10分钟
                setValue(RedisKey_Config.VCODE_SECOND+phone,600);
                //设置1小时
                setValue(RedisKey_Config.VCODE_THREE+phone,3600);
                //设置1天
                setValue(RedisKey_Config.VCODE_FOUR+phone,TimeUtil.getLastSeconds());
                msg=phone+":验证码:"+cd;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                msg=phone+":验证码:"+cd;
                f=2;
            }
        }
        //数据库记录
        SmsSend smsSend1=sendMapper.selectByMsg(phone,msg);
        if(smsSend1==null){
            //第一次发送
            SmsSend smsSend=new SmsSend();
            smsSend.setPhone(phone);
            smsSend.setContent(msg);
            smsSend.setFlag(f);
            smsSend.setIpaddr(ip);
            sendMapper.insert(smsSend);
        }else {
            //这个验证码 不是第一次
            SmsRestset smsRestset=new SmsRestset();
            smsRestset.setFlag(f);
            smsRestset.setSid(smsSend1.getId());
            restsetMapper.insert(smsRestset);
        }
        MsgLog log=new MsgLog();
        log.setContent(msg);
        log.setType(MsgLogType.validatecode.getIndex());
        logMapper.insert(log);
        return R.setR(f==1,"验证码发送",null);
    }



    private void setValue(String key,int seconds){
        if(jedisUtil.exists(key)){
            jedisUtil.setExpire(key,Integer.parseInt(jedisUtil.get(key))+1+"",(int)jedisUtil.ttl(key));
        }else {
            //第一次 设置有效期
            jedisUtil.setExpire(key,1+"",seconds);
        }
    }
}
