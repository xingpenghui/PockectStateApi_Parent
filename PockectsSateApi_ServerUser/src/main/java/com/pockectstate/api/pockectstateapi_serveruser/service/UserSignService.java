package com.pockectstate.api.pockectstateapi_serveruser.service;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.entity.goods.UserSign;

/**
 *@Author feri
 *@Date Created in 2019/7/15 14:28
 */
public interface UserSignService {
    //实现签到
    R save(String token);
    //查询用户的当前月份的签到数据
    R queryByUid(String token);
    //查询用户的签到统计
    R queryTj(String token);
    //查询用户的所有签到数据
    R queryUidAll(String token);
    //检查指定用户 今天能否签到
    R checkSign(String token);


}
