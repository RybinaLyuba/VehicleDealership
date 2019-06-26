package com.example.vehicle_dealership.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO класс для обнавленного транспортного средства.
 */
@Data
public class VehicleUpdateDTO {
    UUID guid;

    String vehicleType;

    String marque;

    String model;

    String engine;

    int enginePowerBhp;

    int topSpeedMph;

    int costUsd;

    int price;

    //Форматирует дату для передчи в JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateInsert;

    //Игнорирует null дату
    @JsonInclude(JsonInclude.Include.NON_NULL)
    //Форматирует дату для передчи в JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime datePurchase;

    //Форматирует дату для передчи в JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateUpdate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;
}
