package allane.controller;

import allane.model.Vehicle;
import allane.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    private Vehicle vehicle1, vehicle2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicle1 = new Vehicle(100L, "BMW", "X6", "ASG7865", 2020, 40.000);
        vehicle2 = new Vehicle(101L, "VW", "Polo", "AUR1463", 2018, 30.000);
    }

    @Test
    void saveVehicle() throws Exception {
        when(vehicleService.saveVehicle(any()))
                .thenReturn(vehicle1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/vehicle/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(vehicle1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vehicleId").value(100L))
                .andExpect(jsonPath("$.brand").value("BMW"))
                .andExpect(jsonPath("$.model").value("X6"));
    }

    @Test
    void findAllVehicles() throws Exception {
        when(vehicleService
                .findAllVehicle())
                .thenReturn(List.of(vehicle1, vehicle2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/vehicle/find/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].vehicleId").value(100L))
                .andExpect(jsonPath("$[1].vehicleId").value(101L));
    }

    @Test
    void findVehicleById() throws Exception {
        when(vehicleService
                .findVehicleById(100L))
                .thenReturn(vehicle1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/vehicle/find/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(100L))
                .andExpect(jsonPath("$.brand").value("BMW"))
                .andExpect(jsonPath("$.model").value("X6"));
    }

    @Test
    void updateVehicle() throws Exception {
        when(vehicleService.updateVehicle(any()))
                .thenReturn(vehicle1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/vehicle/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(vehicle1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(100L))
                .andExpect(jsonPath("$.brand").value("BMW"))
                .andExpect(jsonPath("$.model").value("X6"));
    }

    @Test
    void deleteVehicleById() throws Exception {
        when(vehicleService.saveVehicle(any()))
                .thenReturn(vehicle1);

        this.mockMvc.perform(delete("/vehicle/delete/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}