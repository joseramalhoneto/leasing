package allane.controller;

import allane.model.Customer;
import allane.service.CustomerService;
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
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private Customer customer1, customer2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(100L, "Jose", "Neto", LocalDate.of(1980, 1, 8));
        customer2 = new Customer(101L, "Daniel", "Leite", LocalDate.of(1985, 5, 6));
    }

    @Test
    void saveCustomer() throws Exception {
        when(customerService.saveCustomer(any()))
                .thenReturn(customer1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/customer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(customer1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(100L))
                .andExpect(jsonPath("$.firstName").value("Jose"))
                .andExpect(jsonPath("$.lastName").value("Neto"));
    }

    @Test
    void findAllCustomer() throws Exception {
        when(customerService
                .findAllCustomer())
                .thenReturn(List.of(customer1, customer2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customer/find/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].customerId").value(100L))
                .andExpect(jsonPath("$[1].customerId").value(101L));
    }

    @Test
    void findCustomerById() throws Exception {
        when(customerService
                .findCustomerById(100L))
                .thenReturn(customer1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customer/find/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(100L))
                .andExpect(jsonPath("$.firstName").value("Jose"));
    }

    @Test
    void updateCustomer() throws Exception {
        when(customerService.updateCustomer(any()))
                .thenReturn(customer1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/customer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(customer1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jose"))
                .andExpect(jsonPath("$.lastName").value("Neto"));
    }

    @Test
    void deleteCustomerById() throws Exception {
        when(customerService.saveCustomer(any()))
                .thenReturn(customer1);

        this.mockMvc.perform(delete("/customer/delete/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}