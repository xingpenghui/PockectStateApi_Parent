package com.pockectstate.api.pockectstateapi_serveruser.dao;

import com.pockectstate.api.common.dto.SignDto;
import com.pockectstate.entity.user.UserSign;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface UserSignDao {
    int insert(UserSign record);
    UserSign selectByUidDay(@Param("uid") int uid, @Param("date")String date);
    List<UserSign> selectCurrMonth(@Param("uid") int uid, @Param("date")String date);
    List<UserSign> selectByUid(int uid);
    SignDto selectTj(int uid);
    long selectMonth(int uid);
    UserSign selectByUidLast(int uid);


}