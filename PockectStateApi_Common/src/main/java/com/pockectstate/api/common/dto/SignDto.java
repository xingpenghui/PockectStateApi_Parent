package com.pockectstate.api.common.dto;

import lombok.Data;

/**
 *@Author feri
 *@Date Created in 2019/7/15 14:57
 */
@Data
public class SignDto {
    private int maxSignDays;//连续签到的天数
    private int monthDays;//月签到的天数
    private int sumSignDays;//签到的总天数
    private int sumSignAward;//签到的总奖励
    private int uid;

}
