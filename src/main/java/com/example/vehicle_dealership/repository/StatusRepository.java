package com.example.vehicle_dealership.repository;

import com.example.vehicle_dealership.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Long> {
    Status findByNameIgnoreCase(String name);
}
