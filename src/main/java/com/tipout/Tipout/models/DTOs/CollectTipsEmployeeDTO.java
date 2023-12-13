package com.tipout.Tipout.models.DTOs;

import com.tipout.Tipout.models.Employee;
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
    String name;
    Tips tips;

    public CollectTipsEmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.toString();
    }
}
