package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.*;
import com.tipout.Tipout.models.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.DTOs.CollectTipsEmployeeMapDTO;
import com.tipout.Tipout.service.CollectTipsMapGenerator;
import org.springframework.http.ResponseEntity;
import com.tipout.Tipout.models.data.*;
import com.tipout.Tipout.service.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/*
This controller handles calculations and the display of those calculations for the app.
It takes employer input and displays how tips are to be distributed.

 */
@RestController
@RequestMapping(value="calculate")
public class TipoutController {
    AuthenticationController authenticationController;
    EmployeeRepository employeeRepository;
    EmployeeRoleRepository employeeRoleRepository;
    TipsCollectedRepository tipsCollectedRepository;
    AuthenticatedUser authenticatedUser;
    TipoutRepository tipoutRepository;

    @Autowired
    public TipoutController(AuthenticationController authenticationController, EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository, TipsCollectedRepository tipsCollectedRepository, AuthenticatedUser authenticatedUser, TipoutRepository tipoutRepository) {
        this.authenticationController = authenticationController;
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.tipsCollectedRepository = tipsCollectedRepository;
        this.authenticatedUser = authenticatedUser;
        this.tipoutRepository = tipoutRepository;
    }



    /*
    This method is in charge of dealing with a simple tip pool. It provides two lists to the view:
    MoneyHandlers - Employees that collect tips tied to a particular Employer
    NonMoneyHandlers - Employees that do not collect tips tied to a particular Employer

    If value is set for a MoneyHandler they are included in the tippool, if no value is set they are not.
    For nonMoneyHandlers if they are selected to be in the tippol they will be included in the distribution.

    Data is collected by a TipsCollected objected and tabulated by a Tipout object
     */


    @GetMapping("EmployeeTipMap")
    public ResponseEntity<CollectTipsEmployeeMapDTO> employeeTipMapController(){
        Employer employer = (Employer)authenticatedUser.getUser();
        List<Employee> employees = employeeRepository.findAllByDeletedFalseAndEmployer_Id(employer.getId());

        CollectTipsEmployeeMapDTO collectTipsEmployeeMapDTO = CollectTipsMapGenerator.generateCollectTipsEmployeeMapDTO(employees);

        return ResponseEntity.ok(collectTipsEmployeeMapDTO);
    }

    @PostMapping("WeightedTippoolByRole")
    public ResponseEntity<Map<String, String>> WeightedTippoolByRoleReport(@RequestBody List<CollectTipsEmployeeDTO> collectTipsEmployees){
        Map<Employee,Tips> employeesMap=new HashMap<>();

        for(CollectTipsEmployeeDTO collectTipsEmployeeDTO: collectTipsEmployees){
            if(collectTipsEmployeeDTO.getTips() != null){
            Optional<Employee> optionalEmployee = employeeRepository.findById(collectTipsEmployeeDTO.getId());
            if (optionalEmployee.isEmpty()){throw new RuntimeException();}
            Employee employee =  optionalEmployee.get();
            employeesMap.put(employee, collectTipsEmployeeDTO.getTips());}
        }
//
        TipsCollected tipsCollected =new TipsCollected(employeesMap);
        tipsCollectedRepository.save(tipsCollected);
//
////        In order to calculate the distribution for the current schema, we need to use the
////        Tipout object which handles the calculation for the current schema we need to pass in three pieces of information:
        long id = tipsCollected.getId();
////        1) The total amount in the tippool
        BigDecimal totalTippool = tipsCollectedRepository.findTotalTippool(id);
////        2) The different types of employees in the tip pool
        Integer totalEmployeeTipRates = tipsCollectedRepository.findTotalEmployeeTipoutPercentInTippool(id);
////        3) The Employees in the tip pool
        List<Employee> employeesInTipPool = new ArrayList<>(employeesMap.keySet());
//
//
        Tipout tipout = new Tipout();
////        We call the calculateTippoolDistribution from the Tipout class which will return a list of Employees with money they are owed
        Map<String, String> employeeShareofTipoolMap = tipout.calculateWeightedTippoolByRole(totalEmployeeTipRates, totalTippool, employeesInTipPool);
        tipoutRepository.save(tipout);

        return ResponseEntity.ok(employeeShareofTipoolMap);
    }

    @PostMapping("EvenTippool")
    public ResponseEntity<Map<String, String>> EvenTippoolReport(@RequestBody List<CollectTipsEmployeeDTO> collectTipsEmployees){
        Map<Employee,Tips> employeesMap=new HashMap<>();

        for(CollectTipsEmployeeDTO collectTipsEmployeeDTO: collectTipsEmployees){
            if(collectTipsEmployeeDTO.getTips() != null){
                Optional<Employee> optionalEmployee = employeeRepository.findById(collectTipsEmployeeDTO.getId());
                if (optionalEmployee.isEmpty()){throw new RuntimeException();}
                Employee employee =  optionalEmployee.get();
                employeesMap.put(employee, collectTipsEmployeeDTO.getTips());}
        }
//
        TipsCollected tipsCollected =new TipsCollected(employeesMap);
        tipsCollectedRepository.save(tipsCollected);
//
////        In order to calculate the distribution for the current schema, we need to use the
////        Tipout object which handles the calculation for the current schema we need to pass in three pieces of information:
        long id = tipsCollected.getId();
////        1) The total amount in the tippool
        BigDecimal totalTippool = tipsCollectedRepository.findTotalTippool(id);
////        2) The Employees in the tip pool
        List<Employee> employeesInTipPool = new ArrayList<>(employeesMap.keySet());
//
//
        Tipout tipout = new Tipout();
////        We call the calculateTippoolDistribution from the Tipout class which will return a list of Employees with money they are owed
        Map<String, String> employeeShareofTipoolMap = tipout.calculateEvenTippool(totalTippool, employeesInTipPool);
        tipoutRepository.save(tipout);

        return ResponseEntity.ok(employeeShareofTipoolMap);
    }





}
