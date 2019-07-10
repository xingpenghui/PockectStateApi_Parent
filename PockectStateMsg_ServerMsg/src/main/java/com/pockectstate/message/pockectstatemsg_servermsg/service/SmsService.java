package com.pockectstate.message.pockectstatemsg_servermsg.service;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.entity.msg.SmsSend;

/**
 *@Author feri
 *@Date Created in 2019/7/10 14:28
 */
public interface SmsService {
    R save(SmsSend smsSend);
    R checkCode(String phone,int code);
    R sendSms(String phone,String ip);

}
