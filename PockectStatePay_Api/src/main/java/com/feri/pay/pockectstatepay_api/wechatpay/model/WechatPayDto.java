package com.feri.pay.pockectstatepay_api.wechatpay.model;

import lombok.Data;

/**
 *@Author feri
 *@Date Created in 2019/7/25 16:10
 */
@Data
public class WechatPayDto {
    private String order_price; // 价格  单位分
    private String body;  //订单名称
    private String out_trade_no; //订单编号
    /* @param order_price 价格 单位 分
     * @param  body 商品描述
     * @param  out_trade_no 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一*/
}
