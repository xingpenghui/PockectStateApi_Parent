package com.pockectstate.api.server.service;

import com.pockectstate.api.common.dto.LoginDto;
import com.pockectstate.api.common.dto.UserDto;
import com.pockectstate.api.common.vo.R;

/**
 *@Author feri
 *@Date Created in 2019/7/11 15:50
 */
public interface UserService {
    //登录
    R login(LoginDto loginDto);
    //注销
    R loginout(String token);
    //找回密码
    R goBackPass(UserDto userDto);
    //校验令牌
    R checkToken(String token);
}
