package com.pockectstate.api.pockectstateapi_serveruser.service;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_serveruser.dto.UserDto;

/**
 *@Author feri
 *@Date Created in 2019/7/9 15:55
 */
public interface UserService {
    R save(UserDto userDto);
    R queryByPhone(String phone);
}
