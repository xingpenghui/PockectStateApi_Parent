package com.pockectstate.api.common.model;

import lombok.Data;

/**
 *@Author feri
 *@Date Created in 2019/7/11 16:37
 * JWT 实现登录授权令牌
 * jwt中的有效负载  content
 */
@Data
public class JWTToken {
    private int id; //用户id
    private String phone; //手机号
    public String no;//jwt令牌的序号
    private int device;//设备类型
    private String deviceMac;//设备的mac（唯一地址）

}
