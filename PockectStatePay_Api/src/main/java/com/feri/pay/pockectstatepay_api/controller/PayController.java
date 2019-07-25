package com.feri.pay.pockectstatepay_api.controller;

import com.feri.pay.pockectstatepay_api.alipay.model.AliPayDto;
import com.feri.pay.pockectstatepay_api.service.PayServcie;
import com.feri.pay.pockectstatepay_api.wechatpay.model.WechatPayDto;
import com.pockectstate.api.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *@Author feri
 *@Date Created in 2019/7/25 16:27
 */
@RestController
public class PayController {
    @Autowired
    private PayServcie payServcie;

    @PostMapping("payapi/pay/alipayorder.do")
    public R pay(@RequestBody AliPayDto payDto){
        return payServcie.sendAliPay(payDto);
    }
    @PostMapping("payapi/pay/wechatpayorder.do")
    public R wpay(@RequestBody WechatPayDto payDto){
        return payServcie.sendWechatPay(payDto);
    }
    @GetMapping("payapi/pay/alipayquery/{oid}")
    public R query1(@PathVariable String oid){
        return payServcie.queryAliPay(oid);
    }
    @GetMapping("payapi/pay/wechatpayquery/{oid}")
    public R query2(@PathVariable String oid){
        return payServcie.queryWechatPay(oid);
    }
}
