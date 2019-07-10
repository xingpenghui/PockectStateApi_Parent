package com.pockectstate.api.pockectstateapi_serveruser.service.impl;

import com.pockectstate.api.common.config.Key_Config;
import com.pockectstate.api.common.psenum.LogType;
import com.pockectstate.api.common.util.EncryptionUtil;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_serveruser.dao.UserDao;
import com.pockectstate.api.pockectstateapi_serveruser.dao.UserLogDao;
import com.pockectstate.api.common.dto.UserDto;
import com.pockectstate.api.pockectstateapi_serveruser.service.UserService;
import com.pockectstate.entity.user.User;
import com.pockectstate.entity.user.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@Author feri
 *@Date Created in 2019/7/9 15:57
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserLogDao logDao;
    @Override
    @Transactional
    public R save(UserDto userDto) {
        User user=new User();
        user.setFlag(1);
        user.setPhone(userDto.getPhone());
        user.setPassword(EncryptionUtil.AESEnc(Key_Config.PASSKEY,userDto.getPsw()));
        userDao.insert(user);
        UserLog log=new UserLog();
        log.setContent(userDto.getPhone()+",完成了注册");
        log.setType(1);
        log.setUid(user.getId());
        log.setType(LogType.register.getValue());
        logDao.insert(log);
        return R.setOK("OK",null);
    }

    @Override
    public R queryByPhone(String phone)
    {
        User user=userDao.selectByPhone(phone);
        return R.setR(user!=null,"OK",null);
    }
}
