package com.pockectstate.api.pockectstateapi_serveruser.controller;

import com.pockectstate.api.common.config.Jwt_Config;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_serveruser.service.UserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2019/7/15 15:54
 */
@RestController
public class UserSignController {
    @Autowired
    private UserSignService signService;

    //检查
    @GetMapping("sign/checkday.do")
    public R checkDay(HttpServletRequest request){
        System.out.println("令牌："+request.getHeader(Jwt_Config.HEADERTOKEN));
        return signService.checkSign(request.getHeader(Jwt_Config.HEADERTOKEN));
    }
    //签到
    @GetMapping("sign/savesign.do")
    public R save(HttpServletRequest request){
        return signService.save(request.getHeader(Jwt_Config.HEADERTOKEN));
    }
    //统计
    @GetMapping("sign/usertj.do")
    public R tj(HttpServletRequest request){
        return signService.queryTj(request.getHeader(Jwt_Config.HEADERTOKEN));
    }
    //当前月的签到数据
    @GetMapping("sign/usercurrmonth.do")
    public R currMonth(HttpServletRequest request){
        return signService.queryByUid(request.getHeader(Jwt_Config.HEADERTOKEN));
    }
    //用户所有签到数据
    @GetMapping("sign/usersignall.do")
    public R all(HttpServletRequest request){
        return signService.queryUidAll(request.getHeader(Jwt_Config.HEADERTOKEN));
    }
}
