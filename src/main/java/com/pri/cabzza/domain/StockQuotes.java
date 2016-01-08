package com.pri.cabzza.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StockQuotes.
 */
@Entity
@Table(name = "stock_quotes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "stockquotes")
public class StockQuotes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "value")
    private Double value;

    @Column(name = "split_rate")
    private Double splitRate;

    @Column(name = "dividend")
    private Double dividend;

    @ManyToOne(cascade=CascadeType.ALL)
    private StockInfo stockInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getSplitRate() {
        return splitRate;
    }

    public void setSplitRate(Double splitRate) {
        this.splitRate = splitRate;
    }

    public Double getDividend() {
        return dividend;
    }

    public void setDividend(Double dividend) {
        this.dividend = dividend;
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

        StockQuotes stockQuotes = (StockQuotes) o;

        if ( ! Objects.equals(id, stockQuotes.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StockQuotes{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", value='" + value + "'" +
            ", splitRate='" + splitRate + "'" +
            ", dividend='" + dividend + "'" +
            '}';
    }
}
