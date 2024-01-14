package com.tipout.Tipout.models.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ReportDTO {
    UUID id;
    private Map<Integer, String[]> employeesAndTipsOwed = new HashMap<>();
    private BigDecimal totalTips = new BigDecimal(0);
}
