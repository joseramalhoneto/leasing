package allane.service;

import allane.model.Contract;
import allane.model.Customer;
import allane.model.Vehicle;
import allane.repository.ContractRepository;
import allane.repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class VehicleServiceTest {

    private Vehicle vehicle1, vehicle2;

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {
        vehicle1  =  new Vehicle(100L, "BMW", "X6", "ASG7865", 2020, 40.000);
        vehicle2  =  new Vehicle(101L, "VW", "Polo", "AUR1463", 2018, 30.000);
    }

    @Test
    void saveVehicle() {
        when(vehicleRepository.save(vehicle1)).thenReturn(vehicle1);
        Vehicle result = vehicleService.saveVehicle(vehicle1);

        assertThat(result).isEqualTo(vehicle1);
        verify(vehicleRepository, times(1)).save(vehicle1);
    }

    @Test
    void findAllVehicle() {
        List<Vehicle> list = new ArrayList<>();
        list.add(vehicle1);
        list.add(vehicle2);

        when(vehicleRepository.findAll()).thenReturn(list);
        List<Vehicle> result = vehicleService.findAllVehicle();

        assertEquals(2, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void findVehicleById() {
        when(vehicleRepository.findById(vehicle1.getVehicleId())).thenReturn(Optional.of(vehicle1));
        Vehicle vehicleReturned = vehicleService.findVehicleById(vehicle1.getVehicleId());

        assertThat(vehicleReturned).isEqualTo(vehicle1);
        assertEquals(100L, vehicleReturned.getVehicleId());
        assertEquals("BMW", vehicleReturned.getBrand());
        assertEquals("X6", vehicleReturned.getModel());
        assertEquals("ASG7865", vehicleReturned.getVin());
        assertEquals(2020, vehicleReturned.getYear());
        assertEquals(40.000, vehicleReturned.getPrice());
        verify(vehicleRepository, times(1)).findById(vehicleReturned.getVehicleId());
    }

    @Test
    void updateVehicle() {
        vehicle1.setBrand("Porsche");
        vehicle1.setModel("Cayman");

        when(vehicleRepository.existsById(any())).thenReturn(true);
        when(vehicleRepository.save(vehicle1)).thenReturn(vehicle1);
        Vehicle vehicleUpdated = vehicleService.updateVehicle(vehicle1);

        assertEquals("Porsche", vehicleUpdated.getBrand());
        assertEquals("Cayman", vehicleUpdated.getModel());
        verify(vehicleRepository, times(1)).save(vehicle1);
    }

}