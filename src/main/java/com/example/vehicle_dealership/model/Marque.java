package com.example.vehicle_dealership.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Марка транспортного средства. Объект-отображения таблицы marque в базе данных.
 * @author Rybina Lyuba
 */


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Marque extends AbstractEntity {
    /**
     * Создает новую марку ТС.
     * @param name Наименование марки.
     */
    public Marque(String name){
        super(name);
    }

    /**
     * Создает новую пустую марку.
     */
    public Marque(){}
}
