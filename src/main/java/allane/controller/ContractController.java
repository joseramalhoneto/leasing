package allane.controller;

import allane.model.Contract;
import allane.model.ContractOverview;
import allane.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/contract")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/save")
    public ResponseEntity<Contract> saveContract(@RequestBody Contract Contract){
        Contract contract = contractService.saveContract(Contract);
        return new ResponseEntity<>(contract, HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Contract>> findAllContract(){
        List<Contract> allContracts = contractService.findAllContract();
        return new ResponseEntity<>(allContracts, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Contract> findContractById(@PathVariable("id") Long id){
        Contract contract = contractService.findContractById(id);
        return new ResponseEntity<>(contract, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Contract> updateContract(@RequestBody Contract Contract){
        Contract contract = contractService.updateContract(Contract);
        return new ResponseEntity<>(contract, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContractById(@PathVariable("id") Long id){
        contractService.deleteContractById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/overview")
    public ResponseEntity<List<ContractOverview>> getContractOverview(){
        List<ContractOverview> contractOverview = contractService.getContractOverview();
        return new ResponseEntity<>(contractOverview, HttpStatus.OK);
    }
}
