package com.example.vehicle_dealership.model;


import lombok.Data;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Класс являющийся суперклассом для сущностей: марка,статус и тип транспортного средства.
 * Не иммеет отображения в БД.
 * @author Rybina Lyuba
 */

@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;

    /**
     * Создает новую сущность
     * @param name Наименование сущности
     */
    public AbstractEntity(String name){
        this.name=name;
    }

    /**
     * Создает новую пустую сущность
     */
    public AbstractEntity(){}

    public String toString(){
        return getName();
    }
}
