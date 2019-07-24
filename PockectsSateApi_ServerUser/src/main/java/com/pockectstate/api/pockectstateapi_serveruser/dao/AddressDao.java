package com.pockectstate.api.pockectstateapi_serveruser.dao;

import com.pockectstate.entity.user.TAddress;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/7/24 12:03
 */
public interface AddressDao {
    int save(TAddress address);
    TAddress selectByUidDefault(int uid);
    List<TAddress> selectByUid(int uid);
}
