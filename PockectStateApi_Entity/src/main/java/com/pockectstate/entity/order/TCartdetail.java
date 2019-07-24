package com.pockectstate.entity.order;

import lombok.Data;

import java.util.Date;
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
public class TCartdetail {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private Integer gid;
    private String skuobj;
    private Integer count;
    private Date ctime;

}
