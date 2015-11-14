package com.pri.cabzza.repository;

import com.pri.cabzza.domain.StockWallet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StockWallet entity.
 */
public interface StockWalletRepository extends JpaRepository<StockWallet,Long> {

    @Query("select stockWallet from StockWallet stockWallet where stockWallet.owner.login = ?#{principal.username}")
    List<StockWallet> findByOwnerIsCurrentUser();

}
