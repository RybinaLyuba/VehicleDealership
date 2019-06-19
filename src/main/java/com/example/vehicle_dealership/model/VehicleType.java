package com.example.vehicle_dealership.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class VehicleType extends AbstractEntity {
    @OneToMany(mappedBy = "vehicleType")
    List<Vehicle> vehicles;

    public VehicleType(String name){
        this.name=name;
    }
    public VehicleType(){}
}
