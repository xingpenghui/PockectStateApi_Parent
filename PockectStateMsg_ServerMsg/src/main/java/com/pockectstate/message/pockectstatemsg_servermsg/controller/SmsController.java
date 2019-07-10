package com.pockectstate.message.pockectstatemsg_servermsg.controller;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.message.pockectstatemsg_servermsg.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2019/7/10 15:53
 */
@RestController
public class SmsController {
    @Autowired
    private SmsService smsService;

    //发送短信
    @PostMapping("message/sendsmscode.do")
    public R sendSms(@RequestParam("phone")String phone, HttpServletRequest request){
        return smsService.sendSms(phone,request.getRemoteAddr());
    }

    //验证短信验证码
    @GetMapping("message/checksmscode.do")
    public R check(@RequestParam("phone")String phone, @RequestParam("code")int code){
        return smsService.checkCode(phone, code);
    }
}
