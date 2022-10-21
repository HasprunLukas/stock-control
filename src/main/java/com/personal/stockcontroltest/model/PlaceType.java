package com.personal.stockcontroltest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "place_type")
public class PlaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "id")
    private Set<PlaceType> placeTypeSet;

    public PlaceType() {

    }

    public PlaceType(long id, String name, Set<PlaceType> placeTypeSet) {
        this.id = id;
        this.name = name;
        this.placeTypeSet = placeTypeSet;
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

    public Set<PlaceType> getPlaceTypeSet() {
        return placeTypeSet;
    }

    public void setPlaceTypeSet(Set<PlaceType> placeTypeSet) {
        this.placeTypeSet = placeTypeSet;
    }

    @Override
    public String toString() {
        return "PlaceType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", placeTypeSet=" + placeTypeSet +
                '}';
    }
}
