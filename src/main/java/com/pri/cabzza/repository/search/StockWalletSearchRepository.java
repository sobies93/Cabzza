package com.pri.cabzza.repository.search;

import com.pri.cabzza.domain.StockWallet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the StockWallet entity.
 */
public interface StockWalletSearchRepository extends ElasticsearchRepository<StockWallet, Long> {
}
