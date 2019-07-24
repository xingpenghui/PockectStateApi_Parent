package com.pockectstate.search.pockectstateapi_serversearch.dao;

import com.pockectstate.search.pockectstateapi_serversearch.model.SearchWord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *@Author feri
 *@Date Created in 2019/7/22 15:50
 */
public interface SearchWorldDao extends ElasticsearchRepository<SearchWord,String> {
}
