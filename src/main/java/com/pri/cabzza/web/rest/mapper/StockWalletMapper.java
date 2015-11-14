package com.pri.cabzza.web.rest.mapper;

import com.pri.cabzza.domain.*;
import com.pri.cabzza.web.rest.dto.StockWalletDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockWallet and its DTO StockWalletDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StockWalletMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    StockWalletDTO stockWalletToStockWalletDTO(StockWallet stockWallet);

    @Mapping(source = "ownerId", target = "owner")
    StockWallet stockWalletDTOToStockWallet(StockWalletDTO stockWalletDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
