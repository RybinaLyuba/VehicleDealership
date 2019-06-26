package com.example.vehicle_dealership.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

/**
 * Тип траспортного средства. Объект-отображения для таблицы vehicle_type в базе данных.
 * @author Rybina Lyuba
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class VehicleType extends AbstractEntity {
    /**
     * Создает новый тип ТС.
     * @param name Наименования типа.
     */
    public VehicleType(String name){
        super(name);
    }

    /**
     * Создает новый пустой тип ТС.
     */
    public VehicleType(){}

}
