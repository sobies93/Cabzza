package com.pri.cabzza.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the StockWallet entity.
 */
public class StockWalletDTO implements Serializable {

    private Long id;

    @NotNull
    private String quoteSymbols;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Long ownerId;

    private String ownerLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuoteSymbols() {
        return quoteSymbols;
    }

    public void setQuoteSymbols(String quoteSymbol) {
        this.quoteSymbols = quoteSymbol;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String userLogin) {
        this.ownerLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockWalletDTO stockWalletDTO = (StockWalletDTO) o;

        if ( ! Objects.equals(id, stockWalletDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StockWalletDTO{" +
            "id=" + id +
            ", quoteSymbol='" + quoteSymbols + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
