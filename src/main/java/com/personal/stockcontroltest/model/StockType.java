package com.personal.stockcontroltest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stock_type")
public class StockType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "id")
    private Set<StockType> stockTypeSet;

    public StockType() {

    }

    public StockType(long id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<StockType> getStockTypeSet() {
        return stockTypeSet;
    }

    public void setStockTypeSet(Set<StockType> stockTypeSet) {
        this.stockTypeSet = stockTypeSet;
    }

    @Override
    public String toString() {
        return "StockType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
