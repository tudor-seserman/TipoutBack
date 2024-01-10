package com.tipout.Tipout.service;

import com.tipout.Tipout.models.DTOs.ReportDTO;
import com.tipout.Tipout.models.DTOs.ReportsSummaryDTO;
import com.tipout.Tipout.models.interfaces.Report;
import com.tipout.Tipout.models.utilities.ReportLookUp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    public List<ReportsSummaryDTO> generateReportsSummaryDTO(List<ReportLookUp> reports){
        return reports.stream().map(report -> new ReportsSummaryDTO(report)).collect(Collectors.toList());
    }
}
