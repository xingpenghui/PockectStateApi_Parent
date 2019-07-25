package com.feri.pay.pockectstatepay_api.service;

import com.feri.pay.pockectstatepay_api.alipay.model.AliPayDto;
import com.feri.pay.pockectstatepay_api.wechatpay.model.WechatPayDto;
import com.pockectstate.api.common.vo.R;

/**
 *@Author feri
 *@Date Created in 2019/7/25 16:09
 */
public interface PayServcie {
    R sendAliPay(AliPayDto payDto);
    R sendWechatPay(WechatPayDto payDto);
    R queryAliPay(String oid);
    R queryWechatPay(String oid);

}
