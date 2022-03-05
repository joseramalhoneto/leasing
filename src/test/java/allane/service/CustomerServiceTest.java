package allane.service;

import allane.model.Customer;
import allane.repository.CustomerRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    private Customer customer1, customer2;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(100L, "Jose", "Neto", LocalDate.of(1980, 1, 8));
        customer2 = new Customer(101L, "Daniel", "Leite", LocalDate.of(1985, 5, 6));
    }

    @Test
    void saveCustomer() {
        when(customerRepository.save(customer1)).thenReturn(customer1);
        Customer result = customerService.saveCustomer(customer1);

        assertThat(result).isEqualTo(customer1);
        verify(customerRepository, times(1)).save(customer1);
    }

    @Test
    void findAllCustomer() {
        List<Customer> list = new ArrayList<>();
        list.add(customer1);
        list.add(customer2);

        when(customerRepository.findAll()).thenReturn(list);
        List<Customer> result = customerService.findAllCustomer();

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void findCustomerById() {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.of(customer1));
        Customer customerReturned = customerService.findCustomerById(customer1.getCustomerId());

        assertThat(customerReturned).isEqualTo(customer1);
        assertEquals(100L, customerReturned.getCustomerId());
        assertEquals("Jose", customerReturned.getFirstName());
        assertEquals("Neto", customerReturned.getLastName());
        verify(customerRepository, times(1)).findById(customerReturned.getCustomerId());
    }

    @Test
    void updateCustomer() {
        customer1.setFirstName("Mike");
        customer1.setLastName("Sulivan");

        when(customerRepository.existsById(any())).thenReturn(true);
        when(customerRepository.save(customer1)).thenReturn(customer1);
        Customer areaUpdated = customerService.updateCustomer(customer1);

        assertEquals("Mike", areaUpdated.getFirstName());
        assertEquals("Sulivan", areaUpdated.getLastName());
        verify(customerRepository, times(1)).save(customer1);
    }

}