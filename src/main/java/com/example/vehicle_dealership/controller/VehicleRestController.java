package com.example.vehicle_dealership.controller;

import com.example.vehicle_dealership.dto.VehicleCreationDTO;
import com.example.vehicle_dealership.dto.VehicleDTO;
import com.example.vehicle_dealership.dto.VehicleTypeDTO;
import com.example.vehicle_dealership.dto.VehicleUpdateDTO;
import com.example.vehicle_dealership.repository.MarqueRepository;
import com.example.vehicle_dealership.repository.StatusRepository;
import com.example.vehicle_dealership.repository.VehicleRepository;
import com.example.vehicle_dealership.repository.VehicleTypeRepository;
import com.example.vehicle_dealership.model.Marque;
import com.example.vehicle_dealership.model.Status;
import com.example.vehicle_dealership.model.Vehicle;
import com.example.vehicle_dealership.model.VehicleType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.print.attribute.standard.Destination;
import javax.xml.transform.Source;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleRestController {
    public static final Logger logger = LoggerFactory.getLogger(VehicleRestController.class);

    public VehicleRestController(){logger.info("VehicleRestController was created!");}

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MarqueRepository marqueRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private void addStatus() {
        if (statusRepository.count() == 0) {
            Status s1 = new Status("in stock");
            Status s2 = new Status("sold");
            Status s3 = new Status("reserved");
            statusRepository.save(s1);
            statusRepository.save(s2);
            statusRepository.save(s3);
        }
    }

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;



    @Autowired
    private void addVehicleType(){
        if(vehicleTypeRepository.count()==0){
            VehicleType vt1 = new VehicleType("supercar");
            VehicleType vt2 = new VehicleType("jet");
            VehicleType vt3 = new VehicleType("ship");
            VehicleType vt4 = new VehicleType("helicopter");
            vehicleTypeRepository.save(vt1);
            vehicleTypeRepository.save(vt2);
            vehicleTypeRepository.save(vt3);
            vehicleTypeRepository.save(vt4);
        }
    }

    @PostMapping(produces = "application/json",consumes = "application/json")
    public ResponseEntity<VehicleCreationDTO> postVehicle(@RequestBody Vehicle vehicle){
        vehicle.setDateInsert(LocalDateTime.now());
        vehicle.setDatePurchase(LocalDateTime.now());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Vehicle.class,VehicleCreationDTO.class).addMappings(mapper->{
            mapper.map(src -> src.getVehicleType().getName().toLowerCase(),VehicleCreationDTO::setVehicleType);
            mapper.map(src -> src.getMarque().getName().toLowerCase(),VehicleCreationDTO::setMarque);
            mapper.map(src-> src.getStatus().getName().toLowerCase(),VehicleCreationDTO::setStatus);
        });
        VehicleType vehicleType = vehicleTypeRepository.findByNameIgnoreCase(vehicle.getVehicleType().getName());
        if(vehicleType==null) vehicleTypeRepository.save(vehicle.getVehicleType());
        else vehicle.setVehicleType(vehicleType);
        Marque marque = marqueRepository.findByNameIgnoreCase(vehicle.getMarque().getName());
        if(marque==null) marqueRepository.save(vehicle.getMarque());
        else vehicle.setMarque(marque);
        Status status = statusRepository.findByNameIgnoreCase(vehicle.getStatus().getName());
        if(status==null) statusRepository.save(vehicle.getStatus());
        else vehicle.setStatus(status);
        vehicleRepository.save(vehicle);
        VehicleCreationDTO vehicleCreationDTO = modelMapper.map(vehicle,VehicleCreationDTO.class);
        logger.info("Vehicle with quid={} was created!",vehicle.getGuid());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleCreationDTO);
    }

    @PutMapping(produces = "application/json",consumes = "application/json")
    public ResponseEntity<VehicleUpdateDTO> updateVehicle(@RequestBody Vehicle vehicle){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Vehicle.class,VehicleUpdateDTO.class).addMappings(mapper->{
            mapper.map(src -> src.getVehicleType().getName(),VehicleUpdateDTO::setVehicleType);
            mapper.map(src -> src.getMarque().getName(),VehicleUpdateDTO::setMarque);
            mapper.map(src-> src.getStatus().getName(),VehicleUpdateDTO::setStatus);
        });
        return vehicleRepository.findById(vehicle.getGuid()).map(record ->{
            record.setDateUpdate(LocalDateTime.now());
            VehicleType vehicleType = vehicleTypeRepository.findByNameIgnoreCase(vehicle.getVehicleType().getName());
            if(vehicleType==null) vehicleType =vehicleTypeRepository.save(vehicle.getVehicleType());
            record.setVehicleType(vehicleType);
            Marque marque = marqueRepository.findByNameIgnoreCase(vehicle.getMarque().getName());
            if(marque==null) marque=marqueRepository.save(vehicle.getMarque());
            record.setMarque(marque);
            Status status = statusRepository.findByNameIgnoreCase(vehicle.getStatus().getName());
            if(status==null) status = statusRepository.save(vehicle.getStatus());
            record.setStatus(status);
            record.setCostUsd(vehicle.getCostUsd());
            record.setEngine(vehicle.getEngine());
            record.setEnginePowerBhp(vehicle.getEnginePowerBhp());
            record.setModel(vehicle.getModel());
            record.setPrice(vehicle.getPrice());
            record.setTopSpeedMph(vehicle.getTopSpeedMph());
            Vehicle updatedVehicle = vehicleRepository.save(record);
            VehicleUpdateDTO vehicleUpdateDTO = modelMapper.map(updatedVehicle,VehicleUpdateDTO.class);
            logger.info("Vehicle with quid={} was updated!",vehicle.getGuid());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleUpdateDTO);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{guid}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long guid){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Vehicle.class,VehicleDTO.class).addMappings(mapper->{
            mapper.map(src -> src.getVehicleType().getName(),VehicleDTO::setVehicleType);
            mapper.map(src -> src.getMarque().getName(),VehicleDTO::setMarque);
            mapper.map(src-> src.getStatus().getName(),VehicleDTO::setStatus);
        });
        return vehicleRepository.findById(guid).map(record ->{
            VehicleDTO vehicleDTO = modelMapper.map(record,VehicleDTO.class);
            logger.info("Vehicle with quid={} was received!",guid);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleDTO);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/search" )
    public ResponseEntity<List<VehicleDTO>> searchVehicle(@RequestParam("vehicleType")String vehicleType,
                                            @RequestParam("marque")String marque,@RequestParam("model")String model,
                                            @RequestParam(value = "engine",required = false)String engine,
                                            @RequestParam(value = "status",required = false)String status){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Vehicle.class,VehicleDTO.class).addMappings(mapper->{
            mapper.map(src -> src.getVehicleType().getName(),VehicleDTO::setVehicleType);
            mapper.map(src -> src.getMarque().getName(),VehicleDTO::setMarque);
            mapper.map(src-> src.getStatus().getName(),VehicleDTO::setStatus);
        });
        List<Vehicle> vehicles = vehicleRepository.searchByCharacteristics(vehicleType,marque,model,engine,status);
        List<VehicleDTO> vehiclesDTO = new ArrayList<>();
        for (Vehicle vehicle:vehicles) {
            VehicleDTO vehicleDTO = modelMapper.map(vehicle,VehicleDTO.class);
            vehiclesDTO.add(vehicleDTO);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehiclesDTO);
    }

    @GetMapping("/types")
    public ResponseEntity<List<VehicleTypeDTO>> getVehicleType(){
        List<VehicleType> vehicleTypes = vehicleTypeRepository.findAll();
        List<VehicleTypeDTO> vehicleTypeDTOList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (VehicleType vehicleType:vehicleTypes) {
            vehicleType.setCount(vehicleRepository.countAllByVehicleTypeName(vehicleType.getName()));
            VehicleTypeDTO vehicleTypeDTO = modelMapper.map(vehicleType,VehicleTypeDTO.class);
            vehicleTypeDTOList.add(vehicleTypeDTO);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleTypeDTOList);
    }

}
