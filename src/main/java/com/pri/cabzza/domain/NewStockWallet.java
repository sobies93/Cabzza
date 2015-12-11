package com.pri.cabzza.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A NewStockWallet.
 */
@Entity
@Table(name = "new_stock_wallet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "newstockwallet")
public class NewStockWallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "historical_data_date", nullable = false)
    private LocalDate historicalDataDate;

    @NotNull
    @Column(name = "calculatings_date", nullable = false)
    private LocalDate calculatingsDate;

    @Column(name = "prognose_date", nullable = false)
    private LocalDate prognoseDate;

    @Column(name = "riskfree_rate")
    private Double riskfreeRate;

    @Column(name = "expected_return")
    private Double expectedReturn;

    @Column(name = "expected_variation")
    private Double expectedVariation;

    @Column(name = "sharp_ratio")
    private Double sharpRatio;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "newStockWallet")
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

    public LocalDate getHistoricalDataDate() {
        return historicalDataDate;
    }

    public void setHistoricalDataDate(LocalDate historicalDataDate) {
        this.historicalDataDate = historicalDataDate;
    }

    public LocalDate getCalculatingsDate() {
        return calculatingsDate;
    }

    public void setCalculatingsDate(LocalDate calculatingsDate) {
        this.calculatingsDate = calculatingsDate;
    }

    public LocalDate getPrognoseDate() {
        return prognoseDate;
    }

    public void setPrognoseDate(LocalDate prognoseDate) {
        this.prognoseDate = prognoseDate;
    }

    public Double getRiskfreeRate() {
        return riskfreeRate;
    }

    public void setRiskfreeRate(Double riskfreeRate) {
        this.riskfreeRate = riskfreeRate;
    }

    public Double getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(Double expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    public Double getExpectedVariation() {
        return expectedVariation;
    }

    public void setExpectedVariation(Double expectedVariation) {
        this.expectedVariation = expectedVariation;
    }

    public Double getSharpRatio() {
        return sharpRatio;
    }

    public void setSharpRatio(Double sharpRatio) {
        this.sharpRatio = sharpRatio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

        NewStockWallet newStockWallet = (NewStockWallet) o;

        if ( ! Objects.equals(id, newStockWallet.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NewStockWallet{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", historicalDataDate='" + historicalDataDate + "'" +
            ", calculatingsDate='" + calculatingsDate + "'" +
            ", prognoseDate='" + prognoseDate + "'" +
            ", riskfreeRate='" + riskfreeRate + "'" +
            ", expectedReturn='" + expectedReturn + "'" +
            ", expectedVariation='" + expectedVariation + "'" +
            ", sharpRatio='" + sharpRatio + "'" +
            '}';
    }
}
