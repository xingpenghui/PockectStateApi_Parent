package com.pockectstate.api.pockectstateapi_appapi.api;

import com.pockectstate.api.common.config.Jwt_Config;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_appapi.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2019/7/15 16:01
 */
@Api(value = "",tags = "会员签到")
@RestController
public class SignController {
    @Autowired
    private SignService signService;
    //检查
    @ApiOperation(value = "",notes = "检查今日是否可以签到")
    @GetMapping("api/sign/checkday.do")
    public R checkDay(HttpServletRequest request){
        System.out.println(request.getHeader(Jwt_Config.HEADERTOKEN));
        return signService.checkDay();
    }
    //签到
    @GetMapping("api/sign/savesign.do")
    public R save(){
        return signService.save();
    }
    //统计
    @GetMapping("api/sign/usertj.do")
    public R tj(){
        return signService.tj();
    }
    //当前月的签到数据
    @GetMapping("api/sign/usercurrmonth.do")
    public R currMonth(){
        return signService.currMonth();
    }
    //用户所有签到数据
    @GetMapping("api/sign/usersignall.do")
    public R all(){
        return signService.all();
    }

}
