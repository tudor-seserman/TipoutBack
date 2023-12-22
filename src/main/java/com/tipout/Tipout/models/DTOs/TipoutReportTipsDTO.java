package com.tipout.Tipout.models.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class TipoutReportTipsDTO {
    private Map<Integer, String[]> employeesAndTipsOwed = new HashMap<>();
    private BigDecimal totalTips = new BigDecimal(0);

    public void addEmployeesAndTipsOwed(Integer index, String name,String tips){
        String[] entry = new String[]{name, tips};
        this.employeesAndTipsOwed.put(index, entry);
    }

}
