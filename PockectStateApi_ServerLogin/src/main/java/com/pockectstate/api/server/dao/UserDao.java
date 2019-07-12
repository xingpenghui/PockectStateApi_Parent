package com.pockectstate.api.server.dao;

import com.pockectstate.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *@Author feri
 *@Date Created in 2019/7/11 15:44
 */
public interface UserDao {
    @Select("select id,phone,password from t_user where flag=1 and phone=#{phone}")
    @ResultType(User.class)
    User selectByPhone(String phone);
    @Update("update t_user set password=#{password} where phone=#{phone} and flag=1")
    int updatePass(@Param("phone") String phone, @Param("password") String pass);
}
