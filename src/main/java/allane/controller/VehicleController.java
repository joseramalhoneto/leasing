package allane.controller;
import allane.model.Contract;
import allane.model.Vehicle;
import allane.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
    
    @PostMapping("/save")
    public ResponseEntity<Vehicle> saveVehicle(@RequestBody Vehicle Vehicle){
        Vehicle vehicle = vehicleService.saveVehicle(Vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Vehicle>> findAllVehicles(){
        List<Vehicle> allVehicles = vehicleService.findAllVehicle();
        return new ResponseEntity<>(allVehicles, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable("id") Long id){
        Vehicle vehicleById = vehicleService.findVehicleById(id);
        return new ResponseEntity<>(vehicleById, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle Vehicle){
        Vehicle vehicle = vehicleService.updateVehicle(Vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicleById(@PathVariable("id") Long id){
        vehicleService.deleteVehicleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
