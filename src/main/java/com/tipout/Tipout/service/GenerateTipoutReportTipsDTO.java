package com.tipout.Tipout.service;

import com.tipout.Tipout.models.DTOs.TipoutReportTipsDTO;
import com.tipout.Tipout.models.interfaces.CollectEmployeeInfoMap;
import com.tipout.Tipout.models.interfaces.Report;
import com.tipout.Tipout.models.interfaces.ReportEntry;
import org.springframework.stereotype.Service;

@Service
public class GenerateTipoutReportTipsDTO {
    public static <T extends ReportEntry,K extends CollectEmployeeInfoMap> TipoutReportTipsDTO generate(Report<T, K> report){
        TipoutReportTipsDTO dto = new TipoutReportTipsDTO();
        dto.setTotalTips(report.getTotalTipsCollected());
//        Used integer to allow for people having the same name or the same person getting tipped out with two different roles on the Tipreport
        Integer index = 0;

        for(ReportEntry employee: report.getMoneyHandlersEntries()){
            dto.addEmployeesAndTipsOwed(index, employee.getName() ,employee.getTipsOwed().getDisplayTips());
            index++;
        }
        for(ReportEntry employee: report.getNonMoneyHandlersEntries()){
            dto.addEmployeesAndTipsOwed(index, employee.getName() ,employee.getTipsOwed().getDisplayTips());
            index++;
        }

        return dto;
    }
}
