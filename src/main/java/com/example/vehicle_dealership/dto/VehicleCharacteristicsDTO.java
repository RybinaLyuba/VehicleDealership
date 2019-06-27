package com.example.vehicle_dealership.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * DTO класс для вывода самого популярного типа, марки и статуса транспортного средства.
 */
@Data
//Игнорирование Null полей
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleCharacteristicsDTO {
    VehicleTypeDTO vehicleType;
    MarqueDTO marque;
    StatusDTO status;

    public VehicleCharacteristicsDTO(){}
}
