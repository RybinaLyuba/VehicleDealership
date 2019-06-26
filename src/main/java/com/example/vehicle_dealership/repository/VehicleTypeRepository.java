package com.example.vehicle_dealership.repository;

import com.example.vehicle_dealership.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс - реозиторий
 * @author Rybina Lyuba
 */
public interface VehicleTypeRepository extends JpaRepository<VehicleType,Long> {
    VehicleType findByName(String name);//поиск типа ТС по наименованию

}
