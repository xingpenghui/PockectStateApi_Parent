package com.pockectstate.entity.order;

import java.util.Date;
import lombok.Data;

/**
 * <p>
 * </p>
 * @author Feri
 * @since 2019-07-24
 */
@Data
public class TOrderlog {
    private Integer id;
    private Integer oid;
    private Integer type;
    private String content;
    private Date ctime;

}