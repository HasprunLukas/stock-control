package com.personal.stockcontroltest.model;

import javax.persistence.*;

@Entity
@Table(name = "place_type")
public class PlaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    public PlaceType() {

    }

    public PlaceType(long id, String name) {
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

    @Override
    public String toString() {
        return "PlaceType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
