package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.*;
import com.tipout.Tipout.models.DTOs.TipoutReportTipsDTO;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs.CollectTipsEvenTippoolMapDTO;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.Data.ReportEvenTippoolRepository;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.ReportEvenTippool;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.ReportEvenTippoolEntry;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.TipoutEvenTippool;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsWeightedByRoleMapDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.Data.ReportWeightedByRoleEntryRepository;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.ReportWeightedByRole;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.TipoutWeightedByRole;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.Data.ReportWeightedByRoleRepository;
import com.tipout.Tipout.models.interfaces.Report;
import com.tipout.Tipout.service.GenerateCollectEmployeeInfoMap;
import com.tipout.Tipout.service.GenerateTipoutReportTipsDTO;
import org.springframework.http.ResponseEntity;
import com.tipout.Tipout.models.data.*;
import com.tipout.Tipout.service.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    AuthenticatedUser authenticatedUser;
    ReportWeightedByRoleRepository reportWeightedByRoleRepository;
    ReportWeightedByRoleEntryRepository reportWeightedByRoleEntryRepository;
    ReportEvenTippoolRepository reportEvenTippoolRepository;

    @Autowired
    public TipoutController(AuthenticationController authenticationController, EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository, AuthenticatedUser authenticatedUser, ReportWeightedByRoleRepository reportWeightedByRoleRepository, ReportWeightedByRoleEntryRepository reportWeightedByRoleEntryRepository, ReportEvenTippoolRepository reportEvenTippoolRepository) {
        this.authenticationController = authenticationController;
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.authenticatedUser = authenticatedUser;
        this.reportWeightedByRoleRepository = reportWeightedByRoleRepository;
        this.reportWeightedByRoleEntryRepository = reportWeightedByRoleEntryRepository;
        this.reportEvenTippoolRepository = reportEvenTippoolRepository;
    }
    
    @GetMapping("EmployeeTipMap")
    public ResponseEntity<CollectTipsWeightedByRoleMapDTO> employeeTipMapController(){
        Employer employer = (Employer)authenticatedUser.getUser();
        List<Employee> employees = employeeRepository.findAllByDeletedFalseAndEmployer_Id(employer.getId());

        CollectTipsWeightedByRoleMapDTO collectTipsWeightedByRoleMapDTO = GenerateCollectEmployeeInfoMap.generateCollectTipsEmployeeMapDTO(employees);

        return ResponseEntity.ok(collectTipsWeightedByRoleMapDTO);
    }

    @PostMapping("WeightedTippoolByRole")
    public ResponseEntity<TipoutReportTipsDTO> WeightedTippoolByRoleReport(@RequestBody CollectTipsWeightedByRoleMapDTO collectTipsWeightedByRoleMapDTO){
        TipoutWeightedByRole tipoutWeightedByRole = new TipoutWeightedByRole();
        ReportWeightedByRole report = tipoutWeightedByRole.generateReport(collectTipsWeightedByRoleMapDTO);

        reportWeightedByRoleRepository.save(report);
        TipoutReportTipsDTO simpleReport = GenerateTipoutReportTipsDTO.generate(report);

        return ResponseEntity.ok(simpleReport);
    }

    @PostMapping("EvenTippool")
    public ResponseEntity<TipoutReportTipsDTO> EvenTippoolReport(@RequestBody CollectTipsEvenTippoolMapDTO collectTipsEvenTippoolMapDTO) {
        TipoutEvenTippool tipoutEvenTippool = new TipoutEvenTippool();
        ReportEvenTippool report = tipoutEvenTippool.generateReport(collectTipsEvenTippoolMapDTO);

        reportEvenTippoolRepository.save(report);
        TipoutReportTipsDTO simpleReport = GenerateTipoutReportTipsDTO.generate(report);

        return ResponseEntity.ok(simpleReport);
    }

}
