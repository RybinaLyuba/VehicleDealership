package com.example.vehicle_dealership.repository;

import com.example.vehicle_dealership.model.Marque;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MarqueRepository extends JpaRepository<Marque,Long> {
     Marque findByNameIgnoreCase(String name);
}
