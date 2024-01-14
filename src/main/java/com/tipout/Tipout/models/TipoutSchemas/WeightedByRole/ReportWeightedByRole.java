package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole;

import com.google.gson.annotations.Expose;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsWeightedByRoleMapDTO;
import com.tipout.Tipout.models.Tips;
import com.tipout.Tipout.models.interfaces.CollectEmployeeInfoMap;
import com.tipout.Tipout.models.interfaces.Report;
import com.tipout.Tipout.models.interfaces.ReportEntry;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class ReportWeightedByRole implements Report<ReportWeightedByRoleEntry, CollectTipsWeightedByRoleMapDTO> {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Expose
    private UUID id;
    @OneToMany(cascade = CascadeType.PERSIST)
    List<ReportWeightedByRoleEntry> moneyHandlersEntries= new ArrayList<>();
    @OneToMany(cascade = CascadeType.PERSIST)
    List<ReportWeightedByRoleEntry> nonMoneyHandlersEntries= new ArrayList<>();

    BigDecimal totalTipsCollected ;
    BigDecimal totalSalesCollected;
    LocalDateTime dateTime;
    String shift;

    @Override
    public void initializeReport(CollectTipsWeightedByRoleMapDTO collectTipsWeightedByRoleMapDTO) {
        for(CollectTipsEmployeeDTO collectTipsEmployeeDTO : collectTipsWeightedByRoleMapDTO.getMoneyHandlers()){
            ReportWeightedByRoleEntry entry = new ReportWeightedByRoleEntry();
            entry.createEntry(collectTipsEmployeeDTO);
            this.moneyHandlersEntries.add(entry);
        }
        for(CollectTipsEmployeeDTO collectTipsEmployeeDTO : collectTipsWeightedByRoleMapDTO.getNonMoneyHandlers()){
            ReportWeightedByRoleEntry entry = new ReportWeightedByRoleEntry();
            entry.createEntry(collectTipsEmployeeDTO);
            this.nonMoneyHandlersEntries.add(entry);
        }
        this.dateTime = collectTipsWeightedByRoleMapDTO.getDateTime();
        this.shift = collectTipsWeightedByRoleMapDTO.getShift();
    }
}
