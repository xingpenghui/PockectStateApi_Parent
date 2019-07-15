package com.pockectstate.api.pockectstateapi_appapi.service;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_appapi.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *@Author feri
 *@Date Created in 2019/7/15 16:00
 */
@FeignClient(name = "UserProvider",configuration = FeignConfig.class)
public interface SignService {
    //检查
    @GetMapping("sign/checkday.do")
    R checkDay();
    //签到
    @GetMapping("sign/savesign.do")
    R save();
    //统计
    @GetMapping("sign/usertj.do")
    R tj();
    //当前月的签到数据
    @GetMapping("sign/usercurrmonth.do")
    R currMonth();
    //用户所有签到数据
    @GetMapping("sign/usersignall.do")
    R all();
}
