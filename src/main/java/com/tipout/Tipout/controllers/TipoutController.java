package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.*;
import com.tipout.Tipout.models.DTOs.TipoutReportTipsDTO;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs.CollectTipsEvenTippoolMapDTO;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.Data.ReportEvenTippoolRepository;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.ReportEvenTippool;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.TipoutEvenTippool;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsWeightedByRoleMapDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.ReportWeightedByRole;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.TipoutWeightedByRole;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.Data.ReportWeightedByRoleRepository;
import com.tipout.Tipout.models.utilities.ReportLookUp;
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
    ReportLookUpRepository reportLookUpRepository;
    ReportWeightedByRoleRepository reportWeightedByRoleRepository;
    ReportEvenTippoolRepository reportEvenTippoolRepository;

    @Autowired
    public TipoutController(AuthenticationController authenticationController, EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository, AuthenticatedUser authenticatedUser, ReportLookUpRepository reportLookUpRepository, ReportWeightedByRoleRepository reportWeightedByRoleRepository, ReportEvenTippoolRepository reportEvenTippoolRepository) {
        this.authenticationController = authenticationController;
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.authenticatedUser = authenticatedUser;
        this.reportLookUpRepository = reportLookUpRepository;
        this.reportWeightedByRoleRepository = reportWeightedByRoleRepository;
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
        Employer employer = (Employer)authenticatedUser.getUser();
        TipoutWeightedByRole tipoutWeightedByRole = new TipoutWeightedByRole();
        ReportWeightedByRole report = tipoutWeightedByRole.generateReport(collectTipsWeightedByRoleMapDTO);

        ReportLookUp reportLookUp= new ReportLookUp(employer);
        reportLookUp.setReportWeightedByRole(report);

        reportWeightedByRoleRepository.save(report);
        reportLookUpRepository.save(reportLookUp);
        TipoutReportTipsDTO simpleReport = GenerateTipoutReportTipsDTO.generate(report);

        return ResponseEntity.ok(simpleReport);
    }

    @PostMapping("EvenTippool")
    public ResponseEntity<TipoutReportTipsDTO> EvenTippoolReport(@RequestBody CollectTipsEvenTippoolMapDTO collectTipsEvenTippoolMapDTO) {
        Employer employer = (Employer)authenticatedUser.getUser();
        TipoutEvenTippool tipoutEvenTippool = new TipoutEvenTippool();
        ReportEvenTippool report = tipoutEvenTippool.generateReport(collectTipsEvenTippoolMapDTO);
        ReportLookUp reportLookUp= new ReportLookUp(employer);
        reportLookUp.setReportEvenTippool(report);

        reportEvenTippoolRepository.save(report);
        reportLookUpRepository.save(reportLookUp);
        TipoutReportTipsDTO simpleReport = GenerateTipoutReportTipsDTO.generate(report);

        return ResponseEntity.ok(simpleReport);
    }

}
