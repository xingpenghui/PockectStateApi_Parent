package com.pockectstate.search.pockectstateapi_serversearch.model;

import com.pockectstate.api.common.config.ESIndex_Config;
import com.pockectstate.api.common.config.RedisKey_Config;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 *@Author feri
 *@Date Created in 2019/7/22 15:44
 */
@Data
@Document(indexName = ESIndex_Config.PSGOODS_INDEX,type =ESIndex_Config.PSGOODS_TYPE)
public class Goods {
    private String id;
    private String name;
    private String typeName;
}
