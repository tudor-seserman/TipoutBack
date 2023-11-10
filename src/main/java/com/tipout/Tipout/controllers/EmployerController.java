package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.DTOs.TipRatesDTO;
import com.tipout.Tipout.models.EmployeeTipRates;
import com.tipout.Tipout.models.Employees.MoneyHandler;
import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.data.*;
import com.tipout.Tipout.service.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="employer")
public class EmployerController {

    BartenderRepository bartenderRepository;
    BOHRepository bohRepository;
    BusserRepository busserRepository;
    ServerRepository serverRepository;
    AuthenticationController authenticationController;
    EmployeeRepository employeeRepository;
    EmployerRepository employerRepository;
    AuthenticatedUser authenticatedUser;
    @Autowired
    public EmployerController(BartenderRepository bartenderRepository, BOHRepository bohRepository, BusserRepository busserRepository, ServerRepository serverRepository, AuthenticationController authenticationController, EmployeeRepository employeeRepository, EmployerRepository employerRepository, AuthenticatedUser authenticatedUser) {
        this.bartenderRepository = bartenderRepository;
        this.bohRepository = bohRepository;
        this.busserRepository = busserRepository;
        this.serverRepository = serverRepository;
        this.authenticationController = authenticationController;
        this.employeeRepository = employeeRepository;
        this.employerRepository = employerRepository;
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping("rates")
    public ResponseEntity<List<TipRatesDTO>> getEmployerTipRates(){
        Employer employer = (Employer)authenticatedUser.getUser();
        return ResponseEntity.ok(employer.getTipRates().getAllEmployerTipRates());
    }
}
