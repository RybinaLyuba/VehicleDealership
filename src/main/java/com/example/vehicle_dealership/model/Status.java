package com.example.vehicle_dealership.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Статус транспортного средства. Объект-отображения для таблицы status в базе данных.
 * @author Rybina Lyuba
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Status extends AbstractEntity {
    /**
     * Создает новый статус
     * @param name Наименование статуса
     */
    public Status(String name){
        super(name);
    }

    /**
     * Создает новый пустой статус.
     */
    public Status(){}
}
