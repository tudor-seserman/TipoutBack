package com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs;

import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.EmployeeRole;
import com.tipout.Tipout.models.interfaces.CollectEmployeeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CollectEvenTippoolTipsEmployeeDTO implements CollectEmployeeInfo{
    UUID id;
    EmployeeRole role;
    String roleName;
    String name;
    BigDecimal tips;

    public CollectEvenTippoolTipsEmployeeDTO(Employee employee, EmployeeRole role) {
        this.id = employee.getId();
        this.name = employee.getFullName();
        this.role=role;
        this.roleName=role.getName();
    }
}
