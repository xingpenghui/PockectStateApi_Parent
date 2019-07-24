package com.pockectstate.api.pockectstateapi_appapi.api;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.pockectstateapi_appapi.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author feri
 *@Date Created in 2019/7/22 18:00
 */
@Api(value ="站内搜索",tags = "站内搜索")
@RestController
public class SearchApi {
    @Autowired
    private SearchService searchService;
    @ApiOperation(value = "搜索",notes = "实现搜索，基于Elasticsearch")
    @GetMapping("api/search/goodslike.do")
    public R search(@RequestParam String m){
        return searchService.search(m);
    }
}
