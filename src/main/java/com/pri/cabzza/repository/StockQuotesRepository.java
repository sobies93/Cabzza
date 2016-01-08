package com.pri.cabzza.repository;

import com.pri.cabzza.domain.StockQuotes;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the StockQuotes entity.
 */
public interface StockQuotesRepository extends JpaRepository<StockQuotes,Long> {

    @Query("select stockQuotes from StockQuotes stockQuotes where stockQuotes.stockInfo.id = :id")
    List<StockQuotes> allByStockInfoId( @Param("id") Long id);
}
