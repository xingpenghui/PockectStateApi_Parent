package com.feri.pay.pockectstatepay_api.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.feri.pay.pockectstatepay_api.config.AliPay_Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@Author feri
 *@Date Created in 2019/7/25 10:10
 */
@Configuration
public class AliPayClientConfig {
    @Bean
    public AlipayClient createAC(){
        return new DefaultAlipayClient(AliPay_Config.PAY_URL,AliPay_Config.APP_ID,AliPay_Config.APP_PRIVATE_KEY,"json",AliPay_Config.CHARSET
                ,AliPay_Config.ALIPAY_PUBLIC_KEY,AliPay_Config.SIGN_TYPE);
    }
}
