package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.DTOs.ReportDTO;
import com.tipout.Tipout.models.DTOs.ReportsSummaryDTO;
import com.tipout.Tipout.models.DTOs.TipoutReportTipsDTO;
import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.Data.ReportEvenTippoolRepository;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsWeightedByRoleMapDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.Data.ReportWeightedByRoleRepository;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.ReportWeightedByRole;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.TipoutWeightedByRole;
import com.tipout.Tipout.models.data.EmployeeRepository;
import com.tipout.Tipout.models.data.EmployeeRoleRepository;
import com.tipout.Tipout.models.data.ReportLookUpRepository;
import com.tipout.Tipout.models.interfaces.CollectEmployeeInfoMap;
import com.tipout.Tipout.models.interfaces.Report;
import com.tipout.Tipout.models.interfaces.ReportEntry;
import com.tipout.Tipout.models.utilities.ReportLookUp;
import com.tipout.Tipout.service.AuthenticatedUser;
import com.tipout.Tipout.service.GenerateTipoutReportTipsDTO;
import com.tipout.Tipout.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("reports")
public class ReportsController {

    EmployeeRepository employeeRepository;
    EmployeeRoleRepository employeeRoleRepository;
    AuthenticatedUser authenticatedUser;
    ReportLookUpRepository reportLookUpRepository;
    ReportWeightedByRoleRepository reportWeightedByRoleRepository;
    ReportEvenTippoolRepository reportEvenTippoolRepository;
    ReportService reportService;

    @Autowired
    public ReportsController(EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository, AuthenticatedUser authenticatedUser, ReportLookUpRepository reportLookUpRepository, ReportWeightedByRoleRepository reportWeightedByRoleRepository, ReportEvenTippoolRepository reportEvenTippoolRepository, ReportService reportService) {
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.authenticatedUser = authenticatedUser;
        this.reportLookUpRepository = reportLookUpRepository;
        this.reportWeightedByRoleRepository = reportWeightedByRoleRepository;
        this.reportEvenTippoolRepository = reportEvenTippoolRepository;
        this.reportService = reportService;
    }



    @GetMapping("all")
    public ResponseEntity<List<ReportsSummaryDTO>> getAllReports(){
        Employer employer = (Employer)authenticatedUser.getUser();
        Optional<List<ReportLookUp>> optionalReports= reportLookUpRepository.findByEmployer(employer);
        if(optionalReports.isEmpty()){return ResponseEntity.badRequest().body(null);}

        List<ReportLookUp> reports = optionalReports.get();

        List<ReportsSummaryDTO> reportsSummaryDTOS = reportService.generateReportsSummaryDTO(reports);
        System.out.println(reportsSummaryDTOS);

        return ResponseEntity.ok(reportsSummaryDTOS);
    }

    @GetMapping("{id}")
    public <T extends ReportEntry,K extends CollectEmployeeInfoMap> ResponseEntity<TipoutReportTipsDTO> handleReport(@PathVariable UUID id){
        ReportLookUp reportLookUp= reportLookUpRepository.getReferenceById(id);
        Report<T,K> report = reportLookUp.getReport();

        TipoutReportTipsDTO simpleReport = GenerateTipoutReportTipsDTO.generate(report);

        return ResponseEntity.ok(simpleReport);
    }
}
