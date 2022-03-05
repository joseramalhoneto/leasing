package allane.service;

import allane.exception.ContractException;
import allane.model.Contract;
import allane.model.Customer;
import allane.model.Vehicle;
import allane.repository.ContractRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ContractServiceTest {

    private Contract contract1, contract2;

    @InjectMocks
    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

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
    void saveContract() {
        when(contractRepository.save(contract1)).thenReturn(contract1);
        Contract result = contractService.saveContract(contract1);

        assertThat(result).isEqualTo(contract1);
        verify(contractRepository, times(1)).save(contract1);
    }

    @Test
    void findAllContract() {
        List<Contract> list = new ArrayList<>();
        list.add(contract1);
        list.add(contract2);

        when(contractRepository.findAll()).thenReturn(list);
        List<Contract> result = contractService.findAllContract();

        assertEquals(2, result.size());
        verify(contractRepository, times(1)).findAll();
    }

    @Test
    void findContractById() {
        when(contractRepository.findById(contract1.getContractId())).thenReturn(Optional.of(contract1));
        Contract contractReturned = contractService.findContractById(contract1.getContractId());

        assertThat(contractReturned).isEqualTo(contract1);
        assertEquals(100L, contractReturned.getContractId());
        assertEquals(100, contractReturned.getContractNumber());
        assertEquals(200.0, contractReturned.getMonthlyRate());
        verify(contractRepository, times(1)).findById(contractReturned.getContractId());
    }

    @Test
    void updateContractWithVehicleBooked() {
        contract1.setContractNumber(500);
        contract1.setMonthlyRate(500);
        contract1.getCustomer().setFirstName("Mike");
        contract1.getCustomer().setLastName("Sulivan");
        contract1.getVehicle().setBrand("BMW");
        contract1.getVehicle().setModel("X6");

        when(contractRepository.existsById(any())).thenReturn(true);
        when(contractRepository.save(contract1)).thenReturn(contract1);

        Exception exception = assertThrows(ContractException.class, () -> contractService.updateContract(contract1));
        String expectedMessage = "The vehicle has already booked. Try another vehicle.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

}