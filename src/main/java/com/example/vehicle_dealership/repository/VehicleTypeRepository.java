package com.example.vehicle_dealership.repository;

import com.example.vehicle_dealership.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepository extends JpaRepository<VehicleType,Long> {
    VehicleType findByNameIgnoreCase(String name);

}
