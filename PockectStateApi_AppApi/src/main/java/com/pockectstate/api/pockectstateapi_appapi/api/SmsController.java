package com.pockectstate.api.pockectstateapi_appapi.api;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_appapi.service.SmsService;
import com.pockectstate.entity.msg.SmsSend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *@Author feri
 *@Date Created in 2019/7/10 15:57
 */
@Api(value = "短信操作",tags = "短信操作")
@RestController
public class SmsController {
    @Autowired
    private SmsService smsService;
    //发送短信
    @ApiOperation(value ="实现短信验证码的发送" ,notes = "实现短信验证码的发送")
    @PostMapping("api/message/sendsmscode.do")
    @CrossOrigin
    public R sendSms(@RequestParam("phone")String phone){
        return smsService.sendSms(phone);
    }

    //验证短信验证码
    @ApiOperation(value ="实现短信验证码的校验" ,notes = "实现短信验证码的校验")
    @GetMapping("api/message/checksmscode.do")
    @CrossOrigin
    public R check(@RequestParam("phone")String phone, @RequestParam("code")int code){
        return smsService.check(phone, code);
    }
}
