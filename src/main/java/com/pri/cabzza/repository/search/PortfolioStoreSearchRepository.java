package com.pri.cabzza.repository.search;

import com.pri.cabzza.domain.PortfolioStore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PortfolioStore entity.
 */
public interface PortfolioStoreSearchRepository extends ElasticsearchRepository<PortfolioStore, Long> {
}
