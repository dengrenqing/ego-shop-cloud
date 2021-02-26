package com.whsxt.dao;

import com.whsxt.es.ProdEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author 武汉尚学堂
 */
@Repository
public interface ProdEsDao extends ElasticsearchRepository<ProdEs, Long> {

}
