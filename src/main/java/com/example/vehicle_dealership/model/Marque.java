package com.example.vehicle_dealership.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Marque extends AbstractEntity {
    @OneToMany(mappedBy = "marque")
    @JsonIgnore
    List<Vehicle> vehicles=new ArrayList<>();

    public Marque(String name){
        this.name=name;
    }

    public Marque(){}
}
