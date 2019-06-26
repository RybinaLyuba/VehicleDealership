package com.example.vehicle_dealership.repository;

import com.example.vehicle_dealership.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс - репозиторий
 * @author Rybina Lyuba
 */
public interface StatusRepository extends JpaRepository<Status,Long> {
    Status findByName(String name); //поиск статуса по наименованию
}
