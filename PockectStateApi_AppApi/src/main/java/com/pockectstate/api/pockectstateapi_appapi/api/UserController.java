package com.pockectstate.api.pockectstateapi_appapi.api;

import com.pockectstate.api.common.dto.UserDto;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_appapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *@Author feri
 *@Date Created in 2019/7/10 09:53
 */
@Api(value = "会员服务",tags = "会员服务")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @CrossOrigin
    @ApiOperation(value = "校验手机号是否出存在",notes = "校验手机号是否出存在")
    @GetMapping("api/user/checkphone.do")
    public R checkPhone(@RequestParam("phone")String phone){
        return userService.check(phone);
    }
    @PostMapping("api/user/register.do")
    @CrossOrigin
    @ApiOperation(value = "注册会员",notes = "注册会员")
    public R save(@RequestBody UserDto userDto){
        return userService.save(userDto);
    }
}
