package com.pockectstate.api.pockectstateapi_serveruser.dao;

import com.pockectstate.entity.goods.UserWallet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserWalletDao {
    int insert(UserWallet record);
    //更改兜豆
    int updatePsbean(@Param("uid")int uid,@Param("psbean")int psbean);
}