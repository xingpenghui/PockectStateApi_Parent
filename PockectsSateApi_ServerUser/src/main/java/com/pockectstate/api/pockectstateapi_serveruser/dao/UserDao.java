package com.pockectstate.api.pockectstateapi_serveruser.dao;

import com.pockectstate.entity.goods.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 *@Author feri
 *@Date Created in 2019/7/9 15:48
 */
public interface UserDao {
    @Insert("insert into t_user(phone,password,flag) values(#{phone},#{password},#{flag})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(User user);
    @Select("select id,phone,password,flag from t_user where phone=#{phone}")
    @ResultType(User.class)
    User selectByPhone(String phone);
}
