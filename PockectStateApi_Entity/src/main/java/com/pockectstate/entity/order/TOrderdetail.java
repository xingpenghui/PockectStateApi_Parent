package com.pockectstate.entity.order;

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
public class TOrderdetail{

    private Integer id;
    private Integer oid;
    private Integer gid;
    private String skuobj;
    private Integer price;
    private Integer num;
    private Date ctime;

}
