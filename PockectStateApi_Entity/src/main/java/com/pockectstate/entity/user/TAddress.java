package com.pockectstate.entity.user;

import java.util.Date;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Feri
 * @since 2019-07-24
 */
@Data
public class TAddress  {

    private Integer id;
    private Integer uid;
    private String province;
    private String city;
    private String country;
    private String detail;
    private Date ctime;
    private String phone;
    private String name;
    /**
     * 排序 1默认收货地址 从小到大排序
     */
    private Integer msort;


}
