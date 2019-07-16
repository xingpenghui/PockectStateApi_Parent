package com.pockectstate.api.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.pockectstate.api.common.config.Jwt_Config;
import com.pockectstate.api.common.config.Key_Config;
import com.pockectstate.api.common.config.RedisKey_Config;
import com.pockectstate.api.common.dto.LoginDto;
import com.pockectstate.api.common.dto.UserDto;
import com.pockectstate.api.common.model.JWTToken;
import com.pockectstate.api.common.util.EncryptionUtil;
import com.pockectstate.api.common.util.IdGenerator;
import com.pockectstate.api.common.util.JedisUtil;
import com.pockectstate.api.common.util.Jwt_Util;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.server.dao.UserDao;
import com.pockectstate.api.server.service.UserService;
import com.pockectstate.api.server.util.DeviceKey_Util;
import com.pockectstate.entity.goods.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Set;

/**
 *@Author feri
 *@Date Created in 2019/7/11 15:51
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private IdGenerator idGenerator;

    private JedisUtil jedisUtil=JedisUtil.getInstance();
    @Override
    public R login(LoginDto loginDto) {
        //Redis什么类型存储什么数据
        //验证当前账号是否被冻结
        if(jedisUtil.exists(RedisKey_Config.LOGINFORCE+loginDto.getPhone())){
            //被冻结
            return R.setERROR("该账号已被冻结，剩余时间："+getTTL(RedisKey_Config.LOGINFORCE+loginDto.getPhone()),null);
        }else{
            boolean login=false;
            //验证手机号是否存在
            User user=userDao.selectByPhone(loginDto.getPhone());
            if(user!=null){
                //验证密码是否正确
                if(Objects.equals(user.getPassword(),EncryptionUtil.AESEnc(Key_Config.PASSKEY,loginDto.getPassword()))){
                    //登录成功之后
                    //生成令牌
                    JWTToken jwtToken=new JWTToken();
                    jwtToken.setDevice(loginDto.getDevice());
                    jwtToken.setDeviceMac(loginDto.getDeviceMac());
                    jwtToken.setPhone(loginDto.getPhone());
                    jwtToken.setId(user.getId());
                    jwtToken.setNo(idGenerator.nextId()+"");
                    String jsonToken=JSON.toJSONString(jwtToken);
                    String token=Jwt_Util.createJWT(jwtToken.getNo(),Jwt_Config.JWTTOKENTIME,jsonToken);
                    //存储到Redis
                    //当前的令牌 值为对应的JwtToken的JSON对象
                    jedisUtil.setExpire(RedisKey_Config.JWTTOKEN_TOKEN+token,jsonToken,Jwt_Config.JWTTOKENTIME*60);
                    //当前的设备和账号信息   值为对应的令牌
                    jedisUtil.setExpire(RedisKey_Config.JWTTOKEN_DEVICE+DeviceKey_Util.createKey(jwtToken),token,Jwt_Config.JWTTOKENTIME*60);
                    login=true;
                    return R.setOK("OK",token);
                }
            }
            if(!login){
                String k=RedisKey_Config.LOGINERROR+loginDto.getPhone();
                if(jedisUtil.exists(k)){
                    int c=Integer.parseInt(jedisUtil.get(k));
                    jedisUtil.setExpire(k,c+1+"",(int)jedisUtil.ttl(k));
                    if(c>=2){
                        jedisUtil.setExpire(RedisKey_Config.LOGINFORCE+loginDto.getPhone(),loginDto.getPhone(),15*60);
                        return R.setERROR("您已经连续输错三次，账号被冻结15分钟",null);
                    }
                }else {
                    //第一次失败
                    jedisUtil.setExpire(k,1+"",300);
                }
            }
            return R.setERROR("手机号或密码不正确",null);
        }

    }

    @Override
    public R loginout(String token) {
        String js=Jwt_Util.parseJWT(token);
        if(js!=null && js.length()>0){
            jedisUtil.del(RedisKey_Config.JWTTOKEN_TOKEN+token);
            JWTToken jwtToken=JSON.parseObject(js,JWTToken.class);
            jedisUtil.del(RedisKey_Config.JWTTOKEN_DEVICE+DeviceKey_Util.createKey(jwtToken));
        }
        return R.setOK("请选择操作：重新登录，切换账号",null);
    }

    @Override
    public R goBackPass(UserDto userDto) {
        if(!jedisUtil.exists(RedisKey_Config.LOGINFORCE+userDto.getPhone())) {
            int r = userDao.updatePass(userDto.getPhone(), EncryptionUtil.AESEnc(Jwt_Config.JWTKEY, userDto.getPsw()));
            if (r > 0) {
                //修改成功之后  相关的key
                Set<String> keys = jedisUtil.keys(RedisKey_Config.JWTTOKEN_DEVICE + userDto.getPhone() + "_*");
                String[] arr = new String[keys.size()];
                int i = 0;
                for (String k : keys) {
                    arr[i] = k;
                    i++;
                }
                jedisUtil.del(arr);
                return R.setOK("密码找回成功，请妥善保管", null);
            }
            return R.setERROR("密码找回失败，请稍后再试", null);
        }else {
            return R.setERROR("亲，你的账号还在冻结中请等待，剩余时间："+getTTL(RedisKey_Config.LOGINFORCE+userDto.getPhone()),null);
        }
    }

    /**校验令牌有效性
     * 1、校验令牌是否符合JWT规则*/
    @Override
    public R checkToken(String token) {
        //验证Token是否有效
        //基于JWT  校验令牌是否符合jwt
        if(Jwt_Util.checkJWT(token)){
            //校验成功  符合jwt格式
            if(jedisUtil.exists(RedisKey_Config.JWTTOKEN_TOKEN+token)){
                //再校验手机号和设备对应的令牌和当前令牌是否一致
                String json=Jwt_Util.parseJWT(token);
                JWTToken jwtToken= JSON.parseObject(json,JWTToken.class);
                String dk=DeviceKey_Util.createKey(jwtToken);
                if(jedisUtil.exists(RedisKey_Config.JWTTOKEN_DEVICE+dk)){
                    //取出 设备对应的令牌
                    String t=jedisUtil.get(RedisKey_Config.JWTTOKEN_DEVICE+dk);
                    if(Objects.equals(t,token)){
                        return R.setOK("OK",null);
                    }else {
                        jedisUtil.del(RedisKey_Config.JWTTOKEN_TOKEN+token);
                        return R.setERROR("已经在其他设备上登录，被迫下线",null);
                    }
                }
            }
        }
        return R.setERROR("登录失效，请重新登录",null);
    }
    private String getTTL(String key){
        return jedisUtil.ttl(key)/60+" 分钟";
    }
}