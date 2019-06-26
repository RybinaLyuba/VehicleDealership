package com.example.vehicle_dealership.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import lombok.ToString;



import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Транспортное средство (ТС). Объект-отображения таблицы vehicle в базе данных.
 * @author Rybina Lyuba
 */
@ToString
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID guid;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "vehicle_type_id",nullable = false)
    VehicleType vehicleType;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "marque_id",nullable = false)
    Marque marque;

    String model;

    String engine;

    int enginePowerBhp;

    int topSpeedMph;

    int costUsd;

    int price;

    @ManyToOne
    @JoinColumn(name = "status_id")
    Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateInsert;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime datePurchase;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateUpdate;


}
