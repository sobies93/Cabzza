package com.pri.cabzza.repository.search;

import com.pri.cabzza.domain.StockQuotes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the StockQuotes entity.
 */
public interface StockQuotesSearchRepository extends ElasticsearchRepository<StockQuotes, Long> {
}
