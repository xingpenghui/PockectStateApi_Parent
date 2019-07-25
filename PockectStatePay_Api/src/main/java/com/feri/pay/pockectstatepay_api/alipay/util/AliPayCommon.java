package com.feri.pay.pockectstatepay_api.alipay.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.feri.pay.pockectstatepay_api.config.AliPay_Config;
import com.pockectstate.api.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2019/7/25 10:25
 */
@Service
public class AliPayCommon {
    @Autowired
    private AlipayClient client;

    //统一收单线下交易预创建  可以获取支付二维码的链接
    public R preCreatePay(String payJson)  {
      AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(payJson);
        AlipayTradePrecreateResponse response = null;
        try {
            response = client.execute(request);
            if(response.isSuccess()){
                return R.setOK(response.getQrCode(),null);
            } else {
                return R.setERROR("调用失败:"+response.getMsg(),null);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.setERROR("支付宝服务器异常",null);

    }
    //订单支付结果查询
    public R queryPayStatus(String oid){
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{\"out_trade_no\":\""+oid+"\"}");
        AlipayTradeQueryResponse response = null;
        try {
            response = client.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
                return R.setOK(response.getTradeStatus(),null);
            } else {
                System.out.println("调用失败");
                return R.setERROR("调用失败:"+response.getMsg(),null);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.setERROR("支付宝服务器异常",null);
    }
    //取消订单支付
    public String closeOrder(String oid){
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent("{" +
                "\"trade_no\":\""+oid+"\"}");
        AlipayTradeCloseResponse response = null;
        try {
            response = client.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
                return response.getMsg();
            } else {
                System.out.println("调用失败");
                return response.getMsg();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "支付宝服务器异常";
    }
    public String backMoney(String oid){
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"20150320010101001\"," +
                "\"trade_no\":\"2014112611001004680073956707\"," +
                "\"refund_amount\":200.12," +
                "\"refund_currency\":\"USD\"," +
                "\"refund_reason\":\"正常退款\"," +
                "\"out_request_no\":\"HZ01RF001\"," +
                "\"operator_id\":\"OP001\"," +
                "\"store_id\":\"NJ_S_001\"," +
                "\"terminal_id\":\"NJ_T_001\"," +
                "      \"goods_detail\":[{" +
                "        \"goods_id\":\"apple-01\"," +
                "\"alipay_goods_id\":\"20010001\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"categories_tree\":\"124868003|126232002|126252004\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "        }]," +
                "      \"refund_royalty_parameters\":[{" +
                "        \"royalty_type\":\"transfer\"," +
                "\"trans_out\":\"2088101126765726\"," +
                "\"trans_out_type\":\"userId\"," +
                "\"trans_in_type\":\"userId\"," +
                "\"trans_in\":\"2088101126708402\"," +
                "\"amount\":0.1," +
                "\"amount_percentage\":100," +
                "\"desc\":\"分账给2088101126708402\"" +
                "        }]," +
                "\"org_pid\":\"2088101117952222\"" +
                "  }");
        AlipayTradeRefundResponse response = null;
        try {
            response = client.execute(request);
            if(response.isSuccess()){

                System.out.println("调用成功");
                return response.getMsg();
            } else {
                System.out.println("调用失败");
                return response.getMsg();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "支付宝服务器异常";
    }
    //退款进度查询
    public String querybackMoney(String oid){
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizContent("{" +
                "\"trade_no\":\"20150320010101001\"," +
                "\"out_trade_no\":\"2014112611001004680073956707\"," +
                "\"out_request_no\":\"2014112611001004680073956707\"," +
                "\"org_pid\":\"2088101117952222\"" +
                "  }");
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = client.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
                return response.getMsg();
            } else {
                System.out.println("调用失败");
                return response.getMsg();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "支付宝服务器异常";
    }
}