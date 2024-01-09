package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.Data.ReportEvenTippoolRepository;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.Data.ReportWeightedByRoleRepository;
import com.tipout.Tipout.models.data.EmployeeRepository;
import com.tipout.Tipout.models.data.EmployeeRoleRepository;
import com.tipout.Tipout.models.data.ReportLookUpRepository;
import com.tipout.Tipout.models.interfaces.Report;
import com.tipout.Tipout.service.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("reports")
public class ReportsController {

    EmployeeRepository employeeRepository;
    EmployeeRoleRepository employeeRoleRepository;
    AuthenticatedUser authenticatedUser;
    ReportLookUpRepository reportLookUpRepository;
    ReportWeightedByRoleRepository reportWeightedByRoleRepository;
    ReportEvenTippoolRepository reportEvenTippoolRepository;

    @Autowired
    public ReportsController(EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository, AuthenticatedUser authenticatedUser, ReportLookUpRepository reportLookUpRepository, ReportWeightedByRoleRepository reportWeightedByRoleRepository, ReportEvenTippoolRepository reportEvenTippoolRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.authenticatedUser = authenticatedUser;
        this.reportLookUpRepository = reportLookUpRepository;
        this.reportWeightedByRoleRepository = reportWeightedByRoleRepository;
        this.reportEvenTippoolRepository = reportEvenTippoolRepository;
    }

    @GetMapping("all")
    public ResponseEntity<List<Report>> getAllReports(){
        Employer employer = (Employer)authenticatedUser.getUser();
        Optional<List<Report>> optionalReports= reportLookUpRepository.findByEmployer(employer);
        List<Report> reports = optionalReports.get();
        System.out.println(reports);

        return ResponseEntity.ok(reports);
    }
}
