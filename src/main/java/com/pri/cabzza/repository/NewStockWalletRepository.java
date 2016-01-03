package com.pri.cabzza.repository;

import com.pri.cabzza.domain.NewStockWallet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NewStockWallet entity.
 */
public interface NewStockWalletRepository extends JpaRepository<NewStockWallet,Long> {

    @Query("select newStockWallet from NewStockWallet newStockWallet where newStockWallet.user.login = ?#{principal.username}")
    List<NewStockWallet> findByUserIsCurrentUser();

}
