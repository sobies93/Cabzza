package com.pri.cabzza.repository;

import com.pri.cabzza.domain.NewStockWallet;
import com.pri.cabzza.domain.PortfolioStore;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PortfolioStore entity.
 */
public interface PortfolioStoreRepository extends JpaRepository<PortfolioStore,Long> {

    List<PortfolioStore> findAllByNewStockWallet(NewStockWallet newStockWallet);

}
