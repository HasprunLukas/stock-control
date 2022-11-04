package com.personal.stockcontroltest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "allergen")
public class Allergen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "id")
    private Set<Allergen> allergenSet;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "allergen_to_stock",
            joinColumns = @JoinColumn(name = "allergen_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id"))
    private Set<Stock> allergenToStock;

    public Allergen() {

    }

    public Allergen(long id, String name) {
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

    public Set<Allergen> getAllergenSet() {
        return allergenSet;
    }

    public void setAllergenSet(Set<Allergen> allergenSet) {
        this.allergenSet = allergenSet;
    }

    public Set<Stock> getAllergenToStock() {
        return allergenToStock;
    }

    public void setAllergenToStock(Set<Stock> allergenToStock) {
        this.allergenToStock = allergenToStock;
    }

    @Override
    public String toString() {
        return "Allergen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
