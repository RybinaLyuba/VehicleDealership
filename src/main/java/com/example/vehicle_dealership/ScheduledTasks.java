package com.example.vehicle_dealership;

import com.example.vehicle_dealership.model.Vehicle;
import com.example.vehicle_dealership.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    public VehicleRepository vehicleRepository;

    /**
     * Внутрення функция сервиса, выполняющая каждые n минут и
     * заменяющая price одному случайному ТС в БД на 0.
     */
    @Scheduled(fixedRateString = "${spring.boot.schedule.rate}")
    public void priceReset(){
        List<Vehicle> vehicleList = vehicleRepository.findAll();//получение всех ТС из БД
        //Если список не пуст, получение рандомного элемента списка и замена свойства price.
        if(vehicleList.size()>0){
            int randomIndex = (int) (Math.random()*vehicleList.size());
            Vehicle vehicle = vehicleList.get(randomIndex);
            vehicle.setPrice(0);
            vehicleRepository.save(vehicle);
            logger.info("Price of vehicle with guid={} reset",vehicle.getGuid());
        }
    }
}
