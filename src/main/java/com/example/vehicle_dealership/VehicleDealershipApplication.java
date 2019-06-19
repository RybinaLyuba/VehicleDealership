package com.example.vehicle_dealership;

import com.example.vehicle_dealership.repository.StatusRepository;
import com.example.vehicle_dealership.repository.VehicleTypeRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
public class VehicleDealershipApplication  {

    public static void main(String[] args) {
        SpringApplication.run(VehicleDealershipApplication.class, args);

    }






}
