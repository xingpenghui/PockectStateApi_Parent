package com.pockectstate.search.pockectstateapi_serversearch.controller;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.search.pockectstateapi_serversearch.service.GoodsServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2019/7/22 16:09
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsServcie goodsServcie;

    @GetMapping("search/goodslike.do")
    public R search(@RequestParam String m, HttpServletRequest request){
        return goodsServcie.searchLike(m,request.getRemoteAddr());
    }

}
