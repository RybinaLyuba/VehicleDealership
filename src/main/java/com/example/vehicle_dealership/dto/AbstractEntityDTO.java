package com.example.vehicle_dealership.dto;

import lombok.Data;

/**
 *DTO класс, являющийся родителем для классов MarqueDTO, StatusDTO,VehicleDTO
 */
@Data
public abstract class AbstractEntityDTO {
    String name;
    int count;
}
