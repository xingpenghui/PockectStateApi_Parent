package com.feri.pay.pockectstatepay_api.wechatpay.util;

import com.feri.pay.pockectstatepay_api.config.WechatPayConfig;
import com.feri.pay.pockectstatepay_api.wechatpay.model.WechatPayDto;
import com.pockectstate.api.common.util.Http_Util;
import com.pockectstate.api.common.util.MD5Util;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *@Author feri
 *@Date Created in 2018/8/9 23:12
 * 微信支付相关方法
 */
public class WechatPayCommon {
    /**
     * 验证签名
     * @return boolean
     */
    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object,
                Object> packageParams, String API_KEY) {
        StringBuffer sb=new StringBuffer();
        Set es= packageParams.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + API_KEY);
        String mysign = MD5Util.MD5Encode(sb.toString(),
                characterEncoding).toLowerCase();
        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();
        return tenpaySign.equals(mysign);
    }
    /**
     * 生成签名
     */
    public static String createSign(String characterEncoding, SortedMap<Object,
            Object> packageParams, String API_KEY) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) &&
                    !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + API_KEY);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }
    /**
     * 封装请求参数为xml格式的字符串
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
                if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            } }
        sb.append("</xml>");
        return sb.toString();
    }
    /*获取指定大小的正整数*/
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }
    //生成支付数据
    /**
     * 下单*/
    public static String weixin_pay(WechatPayDto payDto) {
        String ip= null;
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
            String appid = WechatPayConfig.APP_ID; // appid
            String key = WechatPayConfig.API_KEY; // key
            String currTime = WechatPayCommon.getCurrTime();
            String strTime = currTime.substring(8, currTime.length());
            String strRandom = WechatPayCommon.buildRandom(4) + "";
            String nonce_str = strTime + strRandom;
            //String spbill_create_ip = PayConfig.CREATE_IP; //
            // String notify_url = PayConfig.NOTIFY_URL;
            String trade_type = "NATIVE";//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
            //MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
            SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
            packageParams.put("appid", appid);
            packageParams.put("mch_id", WechatPayConfig.MCH_ID);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", payDto.getBody());
            packageParams.put("out_trade_no", payDto.getOut_trade_no());
            // packageParams.put("total_fee", order_price);
            packageParams.put("total_fee", payDto.getOrder_price());
            packageParams.put("spbill_create_ip", ip);
            packageParams.put("notify_url", "http://"+ip+":8080/paycallback");
            packageParams.put("trade_type", trade_type);
            String sign = WechatPayCommon.createSign("UTF-8", packageParams, key);
            packageParams.put("sign", sign);
            String requestXML = WechatPayCommon.getRequestXml(packageParams);
            System.out.println("请求----->"+requestXML);
            String resXml = Http_Util.postData(WechatPayConfig.UFDOOER_URL, requestXML);
            System.out.println("结果----->"+resXml);
            Map map = XmlUtil.doXMLParse(resXml);
            String urlCode = (String) map.get("code_url");
            return urlCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    //生成查询xml
    public static String weixin_query(String oid){
        String currTime = getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = WechatPayCommon.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", WechatPayConfig.APP_ID);
        packageParams.put("mch_id", WechatPayConfig.MCH_ID);
        packageParams.put("out_trade_no", oid);
        packageParams.put("nonce_str", nonce_str);
        String sign = WechatPayCommon.createSign("UTF-8", packageParams, WechatPayConfig.API_KEY);
        packageParams.put("sign", sign);
        String requestXML = WechatPayCommon.getRequestXml(packageParams);
        System.out.println("查询请求----->"+requestXML);
        String resXml = Http_Util.postData(WechatPayConfig.QUERY_URL, requestXML);
        System.out.println("查询结果----->"+resXml);
        return resXml;
    }
    //关闭订单 https://api.mch.weixin.qq.com/pay/closeorder
    public static String closeOrder(String oid){
        String currTime = getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = WechatPayCommon.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", WechatPayConfig.APP_ID);
        packageParams.put("mch_id", WechatPayConfig.MCH_ID);
        packageParams.put("out_trade_no", oid);
        packageParams.put("nonce_str", nonce_str);
        String sign = WechatPayCommon.createSign("UTF-8", packageParams, WechatPayConfig.API_KEY);
        packageParams.put("sign", sign);
        String requestXML = WechatPayCommon.getRequestXml(packageParams);
        System.out.println("查询请求----->"+requestXML);
        String resXml = Http_Util.postData(WechatPayConfig.CLOSE_URL, requestXML);
        System.out.println("查询结果----->"+resXml);
        return resXml;
    }

}
