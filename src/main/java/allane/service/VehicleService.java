package allane.service;

import allane.exception.ResourceNotFoundException;
import allane.model.Vehicle;
import allane.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle saveVehicle(Vehicle Vehicle){
        return vehicleRepository.save(Vehicle);
    }

    public List<Vehicle> findAllVehicle(){
        return vehicleRepository.findAll();
    }

    public Vehicle findVehicleById(Long id){
        return vehicleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    public Vehicle updateVehicle(Vehicle Vehicle){
        return vehicleRepository.save(Vehicle);
    }

    public void deleteVehicleById(Long id){
        vehicleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        vehicleRepository.deleteById(id);
    }
}
