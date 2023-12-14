package com.tipout.Tipout.service;

import com.tipout.Tipout.models.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.DTOs.CollectTipsEmployeeMapDTO;
import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.EmployeeRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CollectTipsMapGenerator {

    public static CollectTipsEmployeeMapDTO generateCollectTipsEmployeeMapDTO(List<Employee> employeesGet) {

        CollectTipsEmployeeMapDTO collectTipsEmployeeMapDTO = new CollectTipsEmployeeMapDTO();

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

        collectTipsEmployeeMapDTO.setMoneyHandlers(moneyHandlerEmployeesLoad);
        collectTipsEmployeeMapDTO.setNonMoneyHandlers(nonMoneyHandlerEmployeesLoad);

        return collectTipsEmployeeMapDTO;
    }
}
