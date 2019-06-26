package com.example.vehicle_dealership.dto;

import lombok.Data;

/**
 * DTO класс для вывода самого популярного типа, марки и статуса транспортного средства.
 */
@Data
public class VehicleCharacteristicsDTO {
    VehicleTypeDTO vehicleType;
    MarqueDTO marque;
    StatusDTO status;

    public VehicleCharacteristicsDTO(){}
}
