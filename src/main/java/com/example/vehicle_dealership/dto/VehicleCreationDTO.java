package com.example.vehicle_dealership.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO класс для созданного транспортного средства.
 */
@Data
public class VehicleCreationDTO {
    UUID guid;


    String vehicleType;

    String marque;

    String model;

    String engine;

    int enginePowerBhp;

    int topSpeedMph;

    int costUsd;

    int price;

    //Форматирует дату для передачи в json
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateInsert;

    //Игнорирует null дату
    @JsonInclude(JsonInclude.Include.NON_NULL)
    //Форматирует дату для передачи в json
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime datePurchase;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;


}
