package com.pockectstate.entity.goods;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 *@Author feri
 *@Date Created in 2019/7/9 15:38
 */
@Data
public class UserLog {
    private BigInteger id;
    private String content;
    private Integer uid;
    private Integer type; //1、注册 2登录
    private Date ctime;
}
