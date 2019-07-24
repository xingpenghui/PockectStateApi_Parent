package com.pockectstate.api.pockectstateapi_appapi.api;

import com.pockectstate.api.common.config.Jwt_Config;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_appapi.service.AddressService;
import com.pockectstate.entity.user.TAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author feri
 *@Date Created in 2019/7/24 15:20
 */
@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("/api/addrss/default.do")
    public R defaultad(){
        return addressService.defaultad();
    }
    @GetMapping("api/address/alluid.do")
    public R all(){
        return addressService.all();
    }
    @PostMapping("/api/address/save.do")
    public R save(@RequestBody TAddress address){
        return addressService.save(address);
    }
}
