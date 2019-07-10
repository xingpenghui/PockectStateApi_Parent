package com.pockectstate.api.pockectstateapi_appapi.service;

import com.pockectstate.api.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2019/7/10 15:53
 */
@FeignClient(name = "MsgProvider")
public interface SmsService {
    //发送短信
    @PostMapping("message/sendsmscode.do")
    R sendSms(@RequestParam("phone")String phone);

    //验证短信验证码
    @GetMapping("message/checksmscode.do")
    R check(@RequestParam("phone")String phone, @RequestParam("code")int code);
}
