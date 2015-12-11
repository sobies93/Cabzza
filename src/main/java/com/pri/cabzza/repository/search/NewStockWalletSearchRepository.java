package com.pri.cabzza.repository.search;

import com.pri.cabzza.domain.NewStockWallet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the NewStockWallet entity.
 */
public interface NewStockWalletSearchRepository extends ElasticsearchRepository<NewStockWallet, Long> {
}
