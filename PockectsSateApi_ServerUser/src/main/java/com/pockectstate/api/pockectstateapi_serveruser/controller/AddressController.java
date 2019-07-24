package com.pockectstate.api.pockectstateapi_serveruser.controller;

import com.pockectstate.api.common.config.Jwt_Config;
import com.pockectstate.api.common.model.JWTToken;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_serveruser.service.AddressService;
import com.pockectstate.entity.user.TAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2019/7/24 15:16
 */
@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("addrss/default.do")
    public R defaultad(HttpServletRequest request){
        return addressService.querySignle(request.getHeader(Jwt_Config.HEADERTOKEN));
    }
    @GetMapping("address/alluid.do")
    public R all(HttpServletRequest request){
        return addressService.queryUid(request.getHeader(Jwt_Config.HEADERTOKEN));
    }
    @PostMapping("/address/save.do")
    public R save(@RequestBody TAddress address,HttpServletRequest request){
        return addressService.save(address,request.getHeader(Jwt_Config.HEADERTOKEN));
    }

}
