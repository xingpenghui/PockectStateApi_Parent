package com.pockectstate.api.pockectstateapi_appapi.service;

import com.pockectstate.api.common.config.Jwt_Config;
import com.pockectstate.api.common.dto.LoginDto;
import com.pockectstate.api.common.dto.UserDto;
import com.pockectstate.api.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2019/7/13 15:06
 */
@FeignClient(name = "LoginService")
public interface LoginService {
    //登录
    @PostMapping("auth/login.do")
    R login(@RequestBody LoginDto loginDto);
    //注销
    @GetMapping("auth/loginout.do")
    R loginout();
    //检查
    @GetMapping("auth/checktoken.do")
    R check();
    //找回密码
    @PutMapping("auth/getbackpassword.do")
    R goback(@RequestBody UserDto userDto);
}
