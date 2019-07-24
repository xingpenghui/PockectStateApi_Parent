package com.pockectstate.search.pockectstateapi_serversearch.model;

import com.pockectstate.api.common.config.ESIndex_Config;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 *@Author feri
 *@Date Created in 2019/7/22 15:45
 */
@Data
@Document(indexName = ESIndex_Config.PSSEARCH_INDEX,type =ESIndex_Config.PSSEARCH_TYPE)
public class SearchWord {
    private String id;
    private String world;
    private String ipaddr;

}
