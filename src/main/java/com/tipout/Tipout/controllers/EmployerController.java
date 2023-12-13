package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.DTOs.TipRatesDTO;
import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.EmployeeRole;
import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.data.*;
import com.tipout.Tipout.service.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="employer")
public class EmployerController {


    AuthenticationController authenticationController;
    EmployeeRepository employeeRepository;

    EmployeeRoleRepository employeeRoleRepository;
    EmployerRepository employerRepository;
    AuthenticatedUser authenticatedUser;

    @Autowired
    public EmployerController(AuthenticationController authenticationController, EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository, EmployerRepository employerRepository, AuthenticatedUser authenticatedUser) {
        this.authenticationController = authenticationController;
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.employerRepository = employerRepository;
        this.authenticatedUser = authenticatedUser;
    }


    @GetMapping("roles")
    public Iterable<String> getAllEmployeesRoles() {
        Employer employer = (Employer) authenticatedUser.getUser();
        Iterable<String> employeeRoleNames;
        employeeRoleNames = employer.getEmployeesRoleTypes().stream().map(EmployeeRole::getName).collect(Collectors.toList());
        return employeeRoleNames;
    }


        @GetMapping("rates")
    public ResponseEntity<List<TipRatesDTO>> getEmployerTipRates(){
        Employer employer = (Employer)authenticatedUser.getUser();
            List<TipRatesDTO> employeeRoleRates;
            employeeRoleRates = employer.getEmployeesRoleTypes().stream().map(employeeRole->new TipRatesDTO(employeeRole.getName(), employeeRole.getRate())).collect(Collectors.toList());
        return ResponseEntity.ok(employeeRoleRates);
    }

    @PostMapping("editRates")
    public HttpStatus editEmployerTipRates(@RequestBody List<TipRatesDTO> ratesToEdit){
        Employer employer = (Employer)authenticatedUser.getUser();
        List<EmployeeRole> employeeRoleTipRatesToEdit = new ArrayList<>();
        for (TipRatesDTO tipRateToEdit : ratesToEdit){
            Optional<EmployeeRole> employeeRoleOptional = employeeRoleRepository.findByNameAndEmployer_id(tipRateToEdit.roleName(), employer.getId());
            EmployeeRole employeeRole = employeeRoleOptional.get();
            employeeRole.setRate(tipRateToEdit.tipRate());
            employeeRoleTipRatesToEdit.add(employeeRole);
        }
        employeeRoleRepository.saveAll(employeeRoleTipRatesToEdit);


        return HttpStatus.OK;
    }
}
