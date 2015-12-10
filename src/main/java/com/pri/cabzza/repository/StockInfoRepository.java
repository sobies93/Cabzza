package com.pri.cabzza.repository;

import com.pri.cabzza.domain.StockInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StockInfo entity.
 */
public interface StockInfoRepository extends JpaRepository<StockInfo,Long> {

}
