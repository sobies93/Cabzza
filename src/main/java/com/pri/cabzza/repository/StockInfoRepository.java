package com.pri.cabzza.repository;

import com.pri.cabzza.domain.StockInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the StockInfo entity.
 */
public interface StockInfoRepository extends JpaRepository<StockInfo,Long> {

    @Query("select stockInfo from StockInfo stockInfo where stockInfo.isInvestorModeAvaiable = :isInvestor")
    List <StockInfo> findAllByMode (@Param("isInvestor") Boolean isInvestor);
}
