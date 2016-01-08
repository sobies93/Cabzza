package com.pri.cabzza.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StockInfo.
 */
@Entity
@Table(name = "stock_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "stockinfo")
public class StockInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "quotes_start_date")
    private LocalDate quotesStartDate;

    @Column(name = "quotes_end_date")
    private LocalDate quotesEndDate;

    @Column(name = "is_investor_mode_avaiable")
    private Boolean isInvestorModeAvaiable;

    @OneToMany(mappedBy = "stockInfo", cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StockQuotes> stockQuotess = new HashSet<>();

    @OneToMany(mappedBy = "stockInfo", cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PortfolioStore> portfolioStores = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getQuotesStartDate() {
        return quotesStartDate;
    }

    public void setQuotesStartDate(LocalDate quotesStartDate) {
        this.quotesStartDate = quotesStartDate;
    }

    public LocalDate getQuotesEndDate() {
        return quotesEndDate;
    }

    public void setQuotesEndDate(LocalDate quotesEndDate) {
        this.quotesEndDate = quotesEndDate;
    }

    public Boolean getIsInvestorModeAvaiable() {
        return isInvestorModeAvaiable;
    }

    public void setIsInvestorModeAvaiable(Boolean isInvestorModeAvaiable) {
        this.isInvestorModeAvaiable = isInvestorModeAvaiable;
    }

    public Set<StockQuotes> getStockQuotess() {
        return stockQuotess;
    }

    public void setStockQuotess(Set<StockQuotes> stockQuotess) {
        this.stockQuotess = stockQuotess;
    }

    public Set<PortfolioStore> getPortfolioStores() {
        return portfolioStores;
    }

    public void setPortfolioStores(Set<PortfolioStore> portfolioStores) {
        this.portfolioStores = portfolioStores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockInfo stockInfo = (StockInfo) o;

        if ( ! Objects.equals(id, stockInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StockInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", symbol='" + symbol + "'" +
            ", quotesStartDate='" + quotesStartDate + "'" +
            ", quotesEndDate='" + quotesEndDate + "'" +
            ", isInvestorModeAvaiable='" + isInvestorModeAvaiable + "'" +
            '}';
    }
}
