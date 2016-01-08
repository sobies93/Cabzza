package com.pri.cabzza.repository;

import com.pri.cabzza.domain.StockQuotes;


import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the StockQuotes entity.
 */
public interface StockQuotesRepository extends JpaRepository<StockQuotes,Long> {

    @Query("select stockQuotes from StockQuotes stockQuotes where stockQuotes.stockInfo.id = :id")
    List<StockQuotes> kurwaAllByStockInfoId( @Param("id") Long id);
}
