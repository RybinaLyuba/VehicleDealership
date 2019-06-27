package com.example.vehicle_dealership.controller;

import com.example.vehicle_dealership.dto.*;
import com.example.vehicle_dealership.repository.MarqueRepository;
import com.example.vehicle_dealership.repository.StatusRepository;
import com.example.vehicle_dealership.repository.VehicleRepository;
import com.example.vehicle_dealership.repository.VehicleTypeRepository;
import com.example.vehicle_dealership.model.Marque;
import com.example.vehicle_dealership.model.Status;
import com.example.vehicle_dealership.model.Vehicle;
import com.example.vehicle_dealership.model.VehicleType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Класс - контроллер приложения.
 * Отвечает за обработку запросов по адресу  http://localhost:8080/vehicle
 * @author Rybina Lyuba
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleRestController {
    /**
     * Логгер. Необходим для правильного вывода сообщений из кода.
     */
    private static final Logger logger = LoggerFactory.getLogger(VehicleRestController.class);

    /**
     * Создает контроллер.
     */
    public VehicleRestController() {
        logger.info("VehicleRestController was created!");
    }

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MarqueRepository marqueRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;


    /**
     * Метод, отвечающий за обработку post запросов по адресу  http://localhost:8080/vehicle.
     * @param vehicle принимаемый объект ТС.
     * @return dto объект,созданного в базе ТС, в формате json.
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<VehicleDTO> postVehicle(@RequestBody Vehicle vehicle) {
        vehicle.setDateInsert(LocalDateTime.now()); //Добавления даты вставки
        vehicleRepository.save(validateVehicle(vehicle));//сохранение ТС и отсуствующих статуса,марки и типа ТС в БД
        VehicleDTO vehicleDTO = new ModelMapper().map(vehicle, VehicleDTO.class); //преобразования сущности в DTO класс
        logger.info("Vehicle with quid={} was created!", vehicle.getGuid());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleDTO);
    }

    /**
     * Метод отвечающий за обработку put запросов по адресу  http://localhost:8080/vehicle
     * @param vehicle принимаемый объект ТС.
     * @return DTO объект, обновленного в базе ТС , в формате json.
     */
    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<VehicleDTO> updateVehicle(@RequestBody Vehicle vehicle) {
        return vehicleRepository.findById(vehicle.getGuid()).map(record -> {
            record.setDateUpdate(LocalDateTime.now());//добавления даты обнавления
            record.setDatePurchase(vehicle.getDatePurchase());
            record.setDateInsert(vehicle.getDateInsert());
            record.setCostUsd(vehicle.getCostUsd());
            record.setEngine(vehicle.getEngine());
            record.setEnginePowerBhp(vehicle.getEnginePowerBhp());
            record.setModel(vehicle.getModel());
            record.setPrice(vehicle.getPrice());
            record.setTopSpeedMph(vehicle.getTopSpeedMph());
            Vehicle updatedVehicle = vehicleRepository.save(validateVehicle(record));//сохранение ТС и отсуствующих статуса,марки и типа ТС в БД
            VehicleDTO vehicleDTO = new ModelMapper().map(updatedVehicle, VehicleDTO.class);
            logger.info("Vehicle with quid={} was updated!", vehicle.getGuid());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleDTO);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Метод отвечающий за обработку get запросов по адресу  http://localhost:8080/vehicle/{guid}
     * @param guid - первичный ключ ТС в бд, по которуму будет проходить поиск ТС.
     * @return DTO объект, найденного в базе ТС,  в формате json.
     */
    @GetMapping(value = "/{guid}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable UUID guid) {
        //получение ТС по guid
        return vehicleRepository.findById(guid).map(record -> {
            VehicleDTO vehicleDTO = new ModelMapper().map(record, VehicleDTO.class); //преобразования сущности в DTO класс
            logger.info("Vehicle with quid={} was received!", guid);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleDTO);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Метод отвечающий за обработку get запросов по адресу http://localhost:8080/vehicle/search.
     * Метод принимает три обязательных и два необязательных параметра, по которым будет производиться
     * поиск ТС в базе данных.
     * @param vehicleType обязательный параметр тип ТС.
     * @param marque      обязательный параметр марка ТС.
     * @param model       обязательный параметр модель ТС.
     * @param engine      необязательный параметр двигатель ТС.
     * @param status      необязательный параметр статус ТС.
     * @return DTO объект найденного ТС в json формате.
     */
    @GetMapping(value = "/search")
    public ResponseEntity<List<VehicleDTO>> searchVehicle(@RequestParam("vehicleType") String vehicleType,
                                                          @RequestParam("marque") String marque, @RequestParam("model") String model,
                                                          @RequestParam(value = "engine", required = false) String engine,
                                                          @RequestParam(value = "status", required = false) String status) {
        List<Vehicle> vehicles = vehicleRepository.searchByCharacteristics(vehicleType, marque, model, engine, status);//поиск по параметрам
        Type listType = new TypeToken<List<VehicleDTO>>() {}.getType();
        List<VehicleDTO> vehiclesDTO = new ModelMapper().map(vehicles, listType);//преобразования List<Vehicle> в List<VehicleDTO>
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehiclesDTO);
    }

    /**
     * Метод отвечающий за обработку запрос по адресу http://localhost:8080/vehicle/types.
     * Метод возвращает список всех типов ТС с указанием количества ТС каждого из типов
     * @return Список DTO объектов типа ТС в формате json.
     */
    @GetMapping("/types")
    public ResponseEntity<List<VehicleTypeDTO>> getVehicleTypes() {
        List<VehicleType> vehicleTypes = vehicleTypeRepository.findAll(); //получение всех типов ТС из базы
        Type listType = new TypeToken<List<VehicleTypeDTO>>() {}.getType();
        List<VehicleTypeDTO> vehicleTypeDTOList = new ModelMapper().map(vehicleTypes, listType);//преобразования List<VehicleType> в List<VehicleTypeDTO>
        vehicleTypeDTOList.forEach(value->
                value.setCount(vehicleRepository.countAllByVehicleTypeName(value.getName())));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleTypeDTOList);
    }

    /**
     * Метод отвечающий за обработку запрос по адресу http://localhost:8080/vehicle/statuses.
     * Метод возвращает список всех статусов ТС с указанием количества ТС каждого из статусов
     * @return Список DTO объектов статуса ТС в формате json.
     */
    @GetMapping("/statuses")
    public ResponseEntity<List<StatusDTO>> getStatuses() {
        List<Status> statusList = statusRepository.findAll();//получение всех статусов из базы
        Type listType = new TypeToken<List<StatusDTO>>() {}.getType();
        List<StatusDTO> statusDTOList = new ModelMapper().map(statusList, listType);//преобразования List<Status> в List<StatusDTO>
        statusDTOList.forEach(value->
                value.setCount(vehicleRepository.countAllByStatusName(value.getName())));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(statusDTOList);
    }

    /**
     * Метод отвечающий за обработку запрос по адресу http://localhost:8080/vehicle/marques.
     * Метод возвращает список всех марок ТС с указанием количества ТС каждой из марок.
     * @return Список DTO объектов марок ТС в формате json.
     */
    @GetMapping("/marques")
    public ResponseEntity<List<MarqueDTO>> getMarques() {
        List<Marque> marqueList = marqueRepository.findAll();//получение всех марок ТС из БД
        Type listType = new TypeToken<List<MarqueDTO>>() {}.getType();
        List<MarqueDTO> marqueDTOList = new ModelMapper().map(marqueList, listType);//преобразования List<Marque> в List<MarqueDTO>
        marqueDTOList.forEach(value->
                value.setCount(vehicleRepository.countAllByMarqueName(value.getName())));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(marqueDTOList);
    }

    /**
     * Метод отвечающий за обработку запрос по адресу http://localhost:8080/vehicle/characteristics.
     * Метод возвращает наиболее популярный тип, статус и марку ТС.
     * @return DTO объект характеристик ТС в json формате.
     */
    @GetMapping("/characteristics")
    public ResponseEntity<VehicleCharacteristicsDTO> getCharacteristics() {
        List<StatusDTO> statusDTOList = getStatuses().getBody();//получение всех статусов
        List<MarqueDTO> marqueDTOList = getMarques().getBody();//получение всех марок
        List<VehicleTypeDTO> vehicleTypeDTOList = getVehicleTypes().getBody();//получение всех типов ТС
        VehicleCharacteristicsDTO vehicleCharacteristicsDTO = new VehicleCharacteristicsDTO();
        //Сортировка не пустых списков по количеству ТС по каждой характеристики
        if (!statusDTOList.isEmpty()) {
            statusDTOList.sort(Comparator.comparing(StatusDTO::getCount).reversed());
            vehicleCharacteristicsDTO.setStatus(statusDTOList.get(0));
        }
        if (!marqueDTOList.isEmpty()) {
            marqueDTOList.sort(Comparator.comparing(MarqueDTO::getCount).reversed());
            vehicleCharacteristicsDTO.setMarque(marqueDTOList.get(0));
        }
        if (!vehicleTypeDTOList.isEmpty()) {
            vehicleTypeDTOList.sort(Comparator.comparing(VehicleTypeDTO::getCount).reversed());
            vehicleCharacteristicsDTO.setVehicleType(vehicleTypeDTOList.get(0));
        }
        logger.info("Popular vehicle characteristics received!");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleCharacteristicsDTO);

    }

    /**
     * Метод отвечающий за обработку запросов по адресу http://localhost:8080/vehicle/reverse.
     * Метод выбирае случайную запись из БД и заменяет в ответе значения всех строковых параметров
     * на строки с символами в обратном порядке.
     * @return DTO объект измененного ТС в json формате.
     */
    @GetMapping("/reverse")
    public ResponseEntity<VehicleDTO> getVehicleReversed() {
        List<Vehicle> vehicles = vehicleRepository.findAll();//получение всех ТС из базы
        //Если список не пустой, получение рандомного элемента из списка и применение функции reverse ко всем строковым полям
        if (!vehicles.isEmpty()) {
            int randomIndex = (int) (Math.random() * vehicles.size());
            Vehicle vehicle = vehicles.get(randomIndex);
            VehicleDTO vehicleDTO = new ModelMapper().map(vehicle, VehicleDTO.class);
            vehicleDTO.setMarque(reverse(vehicleDTO.getMarque()));
            vehicleDTO.setModel(reverse(vehicleDTO.getModel()));
            vehicleDTO.setEngine(reverse(vehicleDTO.getEngine()));
            vehicleDTO.setStatus(reverse(vehicleDTO.getStatus()));
            vehicleDTO.setVehicleType(reverse(vehicleDTO.getVehicleType()));
            logger.info("Vehicle with quid={} was reversed!", vehicle.getGuid());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(vehicleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Метод переворачивающий строку в обратном порядке
     * @param s заданная строка.
     * @return измененную, либо пустую строку.
     */
    private String reverse(String s) {
        if(s!=null){
            StringBuilder stringBuilder = new StringBuilder(s);
            s = stringBuilder.reverse().toString();
        }
        return s;
    }

    /**
     * Метод добавляющий отсуствующий в БД статус, марку и тип ТС при добавлении и обнавлении ТС в БД.
     * @param vehicle ТС по которому будет проходить проверка.
     * @return проверенное ТС.
     */
    private Vehicle validateVehicle(Vehicle vehicle) {
        VehicleType vehicleType = vehicle.getVehicleType();
        if (vehicleType != null) {
            VehicleType vt = vehicleTypeRepository.findByName(vehicleType.getName());
            if (vt != null) vehicle.setVehicleType(vt);
            else vehicleTypeRepository.save(vehicleType);
        }
        Marque marque = vehicle.getMarque();
        if (marque != null) {
            Marque mq = marqueRepository.findByName(marque.getName());
            if (mq != null) vehicle.setMarque(mq);
            else marqueRepository.save(marque);
        }
        Status status = vehicle.getStatus();
        if (status != null) {
            Status st = statusRepository.findByName(status.getName());
            if (st != null) vehicle.setStatus(st);
            else statusRepository.save(status);
        }
        return vehicle;
    }
}


