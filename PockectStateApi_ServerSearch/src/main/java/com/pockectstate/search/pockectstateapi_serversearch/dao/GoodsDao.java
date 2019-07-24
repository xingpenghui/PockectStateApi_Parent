package com.pockectstate.search.pockectstateapi_serversearch.dao;

import com.pockectstate.search.pockectstateapi_serversearch.model.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *@Author feri
 *@Date Created in 2019/7/22 15:47
 */
public interface GoodsDao extends ElasticsearchRepository<Goods,String> {

}
