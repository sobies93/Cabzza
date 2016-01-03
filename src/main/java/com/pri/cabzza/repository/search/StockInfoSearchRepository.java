package com.pri.cabzza.repository.search;

import com.pri.cabzza.domain.StockInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the StockInfo entity.
 */
public interface StockInfoSearchRepository extends ElasticsearchRepository<StockInfo, Long> {
}
