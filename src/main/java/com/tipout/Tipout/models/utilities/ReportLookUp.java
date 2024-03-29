package com.tipout.Tipout.models.utilities;

import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.ReportEvenTippool;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.ReportWeightedByRole;
import com.tipout.Tipout.models.interfaces.CollectEmployeeInfoMap;
import com.tipout.Tipout.models.interfaces.Report;
import com.tipout.Tipout.models.interfaces.ReportEntry;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class ReportLookUp<T extends ReportEntry, K extends CollectEmployeeInfoMap> {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @OneToOne
    Employer employer;
    @OneToOne
    ReportEvenTippool reportEvenTippool;
    @OneToOne
    ReportWeightedByRole reportWeightedByRole;
    LocalDateTime dateTime;
    String shift;

    public ReportLookUp(Employer employer) {
        this.employer = employer;
    }

    public void setReportEvenTippool(ReportEvenTippool reportEvenTippool) {
        this.shift = reportEvenTippool.getShift();
        this.dateTime = reportEvenTippool.getDateTime();
        this.reportEvenTippool = reportEvenTippool;
    }

    public void setReportWeightedByRole(ReportWeightedByRole reportWeightedByRole) {
        this.shift = reportWeightedByRole.getShift();
        this.dateTime = reportWeightedByRole.getDateTime();
        this.reportWeightedByRole = reportWeightedByRole;
    }

    public Report<T,K> getReport(){
        if (reportEvenTippool != null) return (Report<T, K>) reportEvenTippool;
        if (reportWeightedByRole != null) return (Report<T, K>) reportWeightedByRole;
        return null;
    }
}
