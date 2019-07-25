package com.feri.pay.pockectstatepay_api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.feri.pay.pockectstatepay_api.alipay.model.AliPayDto;
import com.feri.pay.pockectstatepay_api.alipay.util.AliPayCommon;
import com.feri.pay.pockectstatepay_api.config.PayConfig;
import com.feri.pay.pockectstatepay_api.service.PayServcie;
import com.feri.pay.pockectstatepay_api.wechatpay.model.WechatPayDto;
import com.feri.pay.pockectstatepay_api.wechatpay.util.WechatPayCommon;
import com.feri.pay.pockectstatepay_api.wechatpay.util.XmlUtil;
import com.pockectstate.api.common.config.ResultJson_Config;
import com.pockectstate.api.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2019/7/25 16:13
 */
@Service
public class PayServiceImpl implements PayServcie {
    @Autowired
    private AliPayCommon aliPayCommon;
    @Override
    public R sendAliPay(AliPayDto payDto)  {
        R r =aliPayCommon.preCreatePay(JSON.toJSONString(payDto));
        if(r.getCode()==ResultJson_Config.OK){
            return R.setOK(PayConfig.QRCODE_URL+Base64.getUrlEncoder().
                    encodeToString(r.getMsg().getBytes()),null);
        }
        return r;
    }

    @Override
    public R sendWechatPay(WechatPayDto payDto) {
       String r=WechatPayCommon.weixin_pay(payDto);
       if(r!=null){
           return R.setOK(PayConfig.QRCODE_URL+Base64.getUrlEncoder().
                   encodeToString(r.getBytes()),null);
       }else {
           return R.setERROR("微信支付异常，暂无法完成支付",null);
       }
    }

    @Override
    public R queryAliPay(String oid) {
        return aliPayCommon.queryPayStatus(oid);
    }

    @Override
    public R queryWechatPay(String oid) {
        String r=WechatPayCommon.weixin_query(oid);
        Map map=XmlUtil.doXMLParse(r);
        if(map.containsKey("trade_state"))
        {
            String u=map.get("trade_state").toString();
            return R.setOK(u,null);
        }else {
            return R.setERROR("微信支付异常，请稍后再来",null);
        }
    }
}
