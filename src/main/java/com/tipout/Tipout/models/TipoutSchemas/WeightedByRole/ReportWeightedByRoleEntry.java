package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole;

import com.google.gson.annotations.Expose;
import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.EmployeeRole;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.Tips;
import com.tipout.Tipout.models.interfaces.CollectEmployeeInfo;
import com.tipout.Tipout.models.interfaces.Report;
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
public class ReportWeightedByRoleEntry implements ReportEntry<CollectTipsEmployeeDTO> {
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
    public void createEntry(CollectTipsEmployeeDTO collectTipsEmployeeDTO) {
        this.role = collectTipsEmployeeDTO.getRole();
        this.roleName = collectTipsEmployeeDTO.getRoleName();
        this.name = collectTipsEmployeeDTO.getName();
        this.tips = collectTipsEmployeeDTO.getTips();
    }
}
