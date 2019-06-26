package com.example.vehicle_dealership.repository;

import com.example.vehicle_dealership.model.Marque;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс - репозиторий
 *
 * @author Rybina Lyuba
 */
public interface MarqueRepository extends JpaRepository<Marque,Long> {
     Marque findByName(String name); //поиск марки по наименованию
}
