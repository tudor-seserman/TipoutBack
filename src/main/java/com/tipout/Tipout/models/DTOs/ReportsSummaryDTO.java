package com.tipout.Tipout.models.DTOs;

import com.tipout.Tipout.models.utilities.ReportLookUp;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@NoArgsConstructor
public class ReportsSummaryDTO {
    UUID id;
    LocalDateTime dateTime;
    String shift;

    public ReportsSummaryDTO(ReportLookUp reportLookUp) {
        this.id = reportLookUp.getId();
        this.dateTime = reportLookUp.getDateTime();
        this.shift = reportLookUp.getShift();
    }
}
