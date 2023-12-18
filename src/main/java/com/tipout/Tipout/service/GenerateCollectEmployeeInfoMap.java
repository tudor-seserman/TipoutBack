package com.tipout.Tipout.service;

import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsWeightedByRoleMapDTO;
import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.EmployeeRole;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GenerateCollectEmployeeInfoMap {

    public static CollectTipsWeightedByRoleMapDTO generateCollectTipsEmployeeMapDTO(List<Employee> employeesGet) {

        CollectTipsWeightedByRoleMapDTO collectTipsWeightedByRoleMapDTO = new CollectTipsWeightedByRoleMapDTO();

        List<CollectTipsEmployeeDTO> moneyHandlerEmployeesLoad = new ArrayList<>();
        List<CollectTipsEmployeeDTO> nonMoneyHandlerEmployeesLoad = new ArrayList<>();

        for(
            Employee employee:employeesGet) {
                for (EmployeeRole role : employee.getEmployeeRoles()) {
                    if (role.isMoneyHandler()) {
                        moneyHandlerEmployeesLoad.add(new CollectTipsEmployeeDTO(employee, role));
                    } else {
                        nonMoneyHandlerEmployeesLoad.add(new CollectTipsEmployeeDTO(employee, role));
                    }
                }
            }

        collectTipsWeightedByRoleMapDTO.setMoneyHandlers(moneyHandlerEmployeesLoad);
        collectTipsWeightedByRoleMapDTO.setNonMoneyHandlers(nonMoneyHandlerEmployeesLoad);

        return collectTipsWeightedByRoleMapDTO;
    }
}
