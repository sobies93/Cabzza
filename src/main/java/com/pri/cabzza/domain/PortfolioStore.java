package com.pri.cabzza.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PortfolioStore.
 */
@Entity
@Table(name = "portfolio_store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "portfoliostore")
public class PortfolioStore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "percent")
    private Double percent;

    @ManyToOne
    private NewStockWallet newStockWallet;

    @ManyToOne
    private StockInfo stockInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public NewStockWallet getNewStockWallet() {
        return newStockWallet;
    }

    public void setNewStockWallet(NewStockWallet newStockWallet) {
        this.newStockWallet = newStockWallet;
    }

    public StockInfo getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(StockInfo stockInfo) {
        this.stockInfo = stockInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PortfolioStore portfolioStore = (PortfolioStore) o;

        if ( ! Objects.equals(id, portfolioStore.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PortfolioStore{" +
            "id=" + id +
            ", percent='" + percent + "'" +
            '}';
    }
}
