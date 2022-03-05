package allane.service;

import allane.exception.ContractException;
import allane.exception.ResourceNotFoundException;
import allane.model.Contract;
import allane.model.ContractOverview;
import allane.model.Vehicle;
import allane.repository.ContractRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Contract saveContract(Contract contract){
        Long vehicleById = contractRepository.getVehicleById(contract.getVehicle().getVehicleId());

        if(vehicleById == null || vehicleById == 0){
            return contractRepository.save(contract);
        }else{
            throw new ContractException("The vehicle has already booked. Try another vehicle.");
        }
    }

    public List<Contract> findAllContract(){
        return contractRepository.findAll();
    }

    public Contract findContractById(Long id){
        return contractRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
    }

    public Contract updateContract(Contract contract){
        Long vehicleById = contractRepository.getVehicleById(contract.getVehicle().getVehicleId());

        if(vehicleById == null){
            return contractRepository.save(contract);
        }else{
            throw new ContractException("The vehicle has already booked. Try another vehicle.");
        }
    }

    public void deleteContractById(Long id){
        contractRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        contractRepository.deleteById(id);
    }

    public List<ContractOverview> getContractOverview(){
        List<ContractOverview> contracts = new ArrayList<>();
        List<Contract> all = contractRepository.findAll();
        for (Contract contract: all) {
            ContractOverview contractOverview = new ContractOverview(
                contract.getContractNumber(),
                contract.getCustomer().getFirstName(),
                contract.getCustomer().getLastName(),
                contract.getVehicle().getBrand(),
                contract.getVehicle().getModel(),
                contract.getVehicle().getYear(),
                contract.getVehicle().getVin(),
                contract.getMonthlyRate(),
                contract.getVehicle().getPrice()
            );
            contracts.add(contractOverview);
        }
        return contracts;
    }

}
