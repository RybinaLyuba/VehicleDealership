package com.example.vehicle_dealership.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Status extends AbstractEntity {
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    List<Vehicle> vehicles;

    public Status(String name){
        this.name=name;
    }
    public Status(){}

}
