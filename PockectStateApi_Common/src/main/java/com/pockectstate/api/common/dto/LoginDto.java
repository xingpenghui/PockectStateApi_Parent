package com.pockectstate.api.common.dto;

/**
 *@Author feri
 *@Date Created in 2019/7/12 09:56
 */

import lombok.Data;

@Data
public class LoginDto {
    private String phone; //手机号
    private String password; //密码
    private int device;//设备类型
    private String deviceMac;//设备的mac（唯一地址）//app和小程序获取的是mac 如果是网页 记录ip

}
