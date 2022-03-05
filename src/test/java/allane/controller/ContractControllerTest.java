package allane.controller;

import allane.model.Contract;
import allane.model.Customer;
import allane.model.Vehicle;
import allane.service.ContractService;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ContractController.class)
class ContractControllerTest {

    private Contract contract1, contract2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    @BeforeEach
    void setUp() {
        contract1  = new Contract(100L, 100, 200.0,
                new Customer(100, "Jose", "Neto", LocalDate.of(1980, 1, 8)),
                new Vehicle(100, "BMW", "X6", "ASG7865", 2020, 40.000));

        contract2  = new Contract(101L, 101, 300.0,
                new Customer(101, "Daniel", "Leite", LocalDate.of(1985, 5, 6)),
                new Vehicle(101, "VW", "Polo", "AUR1463", 2018, 30.000));
    }

    @Test
    void saveContract() throws Exception {
        when(contractService.saveContract(any()))
                .thenReturn(contract1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/contract/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(contract1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contractId").value(100L))
                .andExpect(jsonPath("$.contractNumber").value(100))
                .andExpect(jsonPath("$.monthlyRate").value(200.0));
    }

    @Test
    void findAllContract() throws Exception {
        when(contractService
                .findAllContract())
                .thenReturn(List.of(contract1, contract2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/contract/find/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].contractId").value(100L))
                .andExpect(jsonPath("$[1].contractId").value(101L));
    }

    @Test
    void findContractById() throws Exception {
        when(contractService
                .findContractById(100L))
                .thenReturn(contract1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/contract/find/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractId").value(100L))
                .andExpect(jsonPath("$.contractNumber").value(100));
    }

    @Test
    void updateContract() throws Exception {
        when(contractService.updateContract(any()))
                .thenReturn(contract1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/contract/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(contract1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractId").value(100L))
                .andExpect(jsonPath("$.contractNumber").value(100))
                .andExpect(jsonPath("$.monthlyRate").value(200.0));
    }

    @Test
    void deleteContractById() throws Exception {
        when(contractService.saveContract(any()))
                .thenReturn(contract1);

        this.mockMvc.perform(delete("/contract/delete/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}