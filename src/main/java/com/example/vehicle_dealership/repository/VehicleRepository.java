package com.example.vehicle_dealership.repository;

import com.example.vehicle_dealership.model.Status;
import com.example.vehicle_dealership.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    @Query(value = "select a from Vehicle a where lower(a.vehicleType.name)=lower(:vehicleType) AND " +
            "lower(a.marque.name)=lower(:marque) AND lower(a.model)=lower(:model) AND " +
            "(lower(a.engine)=:engine or :engine is null ) AND " +
            "(lower(a.status.name)=:status or :status is null )")
    List<Vehicle> searchByCharacteristics(@Param("vehicleType") String vehicleType,@Param("marque") String marque,
                                          @Param("model") String model,@Param("engine") String engine,@Param("status") String status);
    int countAllByVehicleTypeName(String name);
    

}
