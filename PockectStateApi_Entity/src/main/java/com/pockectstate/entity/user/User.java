package com.pockectstate.entity.goods;

import lombok.Data;

/**
 *@Author feri
 *@Date Created in 2019/7/9 15:38
 */
@Data
public class User {
    private Integer id;
    private String phone;
    private String password;
    private Integer flag; //1、有效 2无效

}
