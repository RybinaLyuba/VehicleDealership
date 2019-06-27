package com.example.vehicle_dealership.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO класс для транспортного средства.
 */
@Data
//Игнорирование Null полей
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO {
    UUID guid;

    String vehicleType;

    String marque;

    String model;

    String engine;

    int enginePowerBhp;

    int topSpeedMph;

    int costUsd;

    int price;

    //Форматирую дату для передачи в json
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateInsert;

    //Форматирует дату для передчи в JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime datePurchase;


    //Форматирует дату для передчи в JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateUpdate;

    String status;
}
