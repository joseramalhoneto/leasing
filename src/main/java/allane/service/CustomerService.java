package allane.service;

import allane.exception.ResourceNotFoundException;
import allane.model.Customer;
import allane.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer Customer){
        return customerRepository.save(Customer);
    }

    public List<Customer> findAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id){
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public Customer updateCustomer(Customer Customer){
        return customerRepository.save(Customer);
    }

    public void deleteCustomerById(Long id){
        customerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        customerRepository.deleteById(id);
    }
    
}
