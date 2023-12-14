package com.tipout.Tipout.models.DTOs;

import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.EmployeeRole;
import com.tipout.Tipout.models.Tips;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CollectTipsEmployeeDTO {
    UUID id;
    UUID roleID;
    String roleName;
    String name;
    Tips tips;

    public CollectTipsEmployeeDTO(Employee employee, EmployeeRole role) {
        this.id = employee.getId();
        this.name = employee.toString();
        this.roleID=role.getId();
        this.roleName=role.getName();
    }
}
