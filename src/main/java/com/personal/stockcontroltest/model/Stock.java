package com.personal.stockcontroltest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "checkup_date")
    private Date checkup_date;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_type_id")
    private PlaceType placeType;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_type_id")
    private StockType stockType;

    @ManyToMany(mappedBy = "allergenToStock")
    private Set<Allergen> allergenSet;

    public Stock() {

    }

    public Stock(long id, String name, Date checkup_date, PlaceType placeType, StockType stockType) {
        this.id = id;
        this.name = name;
        this.checkup_date = checkup_date;
        this.placeType = placeType;
        this.stockType = stockType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCheckup_date() {
        return checkup_date;
    }

    public void setCheckup_date(Date checkup_date) {
        this.checkup_date = checkup_date;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }
    public StockType getStockType() {
        return stockType;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public Set<Allergen> getAllergenSet() {
        return allergenSet;
    }

    public void setAllergenSet(Set<Allergen> allergenSet) {
        this.allergenSet = allergenSet;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", checkup_date=" + checkup_date +
                ", placeType=" + placeType +
                ", stockType=" + stockType +
                '}';
    }
}

