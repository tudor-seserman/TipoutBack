package com.tipout.Tipout.models.TipoutSchemas.EvenTippool;

import com.google.gson.annotations.Expose;
import com.tipout.Tipout.models.EmployeeRole;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs.CollectEvenTippoolTipsEmployeeDTO;
import com.tipout.Tipout.models.Tips;
import com.tipout.Tipout.models.interfaces.ReportEntry;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class ReportEvenTippoolEntry implements ReportEntry<CollectEvenTippoolTipsEmployeeDTO> {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Expose
    private UUID id;
    @OneToOne
    private EmployeeRole role;
    private String roleName;
    private String name;
    private BigDecimal tips;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Tips tipsOwed;
    private BigDecimal sales;

    @Override
    public void createEntry(CollectEvenTippoolTipsEmployeeDTO collectEvenTippoolTipsEmployeeDTO) {
        this.role = collectEvenTippoolTipsEmployeeDTO.getRole();
        this.roleName = collectEvenTippoolTipsEmployeeDTO.getRoleName();
        this.name = collectEvenTippoolTipsEmployeeDTO.getName();
        this.tips = collectEvenTippoolTipsEmployeeDTO.getTips();
    }
}