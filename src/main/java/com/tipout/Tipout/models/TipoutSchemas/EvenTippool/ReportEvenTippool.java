package com.tipout.Tipout.models.TipoutSchemas.EvenTippool;

import com.google.gson.annotations.Expose;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs.CollectEvenTippoolTipsEmployeeDTO;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs.CollectTipsEvenTippoolMapDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.ReportWeightedByRoleEntry;
import com.tipout.Tipout.models.interfaces.Report;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class ReportEvenTippool implements Report<ReportEvenTippoolEntry, CollectTipsEvenTippoolMapDTO> {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Expose
    private UUID id;
    @OneToMany(cascade = CascadeType.PERSIST)
    List<ReportEvenTippoolEntry> moneyHandlersEntries= new ArrayList<>();
    @OneToMany(cascade = CascadeType.PERSIST)
    List<ReportEvenTippoolEntry> nonMoneyHandlersEntries= new ArrayList<>();

    BigDecimal totalTipsCollected = null;
    BigDecimal totalSalesCollected = null;


    @Override
    public void initializeReport(CollectTipsEvenTippoolMapDTO collectTipsEvenTippoolMapDTO) {
        for(CollectEvenTippoolTipsEmployeeDTO collectEvenTipsEmployeeDTO : collectTipsEvenTippoolMapDTO.getMoneyHandlers()){
            ReportEvenTippoolEntry entry = new ReportEvenTippoolEntry();
            entry.createEntry(collectEvenTipsEmployeeDTO);
            this.moneyHandlersEntries.add(entry);
        }
        for(CollectEvenTippoolTipsEmployeeDTO collectEvenTipsEmployeeDTO : collectTipsEvenTippoolMapDTO.getNonMoneyHandlers()){
            ReportEvenTippoolEntry entry = new ReportEvenTippoolEntry();
            entry.createEntry(collectEvenTipsEmployeeDTO);
            this.nonMoneyHandlersEntries.add(entry);
        }
    }
}


