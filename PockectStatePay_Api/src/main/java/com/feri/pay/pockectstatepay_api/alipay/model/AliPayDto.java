package com.feri.pay.pockectstatepay_api.alipay.model;

/**
 *@Author feri
 *@Date Created in 2019/7/25 11:35
 */
public class AliPayDto {
    private String out_trade_no;
    private double total_amount;
    private String subject;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
