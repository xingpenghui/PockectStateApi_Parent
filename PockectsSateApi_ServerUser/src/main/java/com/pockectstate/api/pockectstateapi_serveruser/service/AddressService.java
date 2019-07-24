package com.pockectstate.api.pockectstateapi_serveruser.service;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.entity.user.TAddress;

/**
 *@Author feri
 *@Date Created in 2019/7/24 13:18
 */
public interface AddressService {
    R save(TAddress address,String token);
    R querySignle(String token);
    R queryUid(String token);
}
