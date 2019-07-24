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
public class TCart  {

    private Integer id;
    private Integer uid;
    private Integer maxcount;
    private Date ctime;

}
