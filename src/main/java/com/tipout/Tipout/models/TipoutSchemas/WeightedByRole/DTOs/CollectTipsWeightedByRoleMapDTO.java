package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs;

import com.tipout.Tipout.models.interfaces.CollectEmployeeInfoMap;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CollectTipsWeightedByRoleMapDTO implements CollectEmployeeInfoMap<CollectTipsEmployeeDTO> {
    List<CollectTipsEmployeeDTO> moneyHandlers = new ArrayList<>();
    List<CollectTipsEmployeeDTO> nonMoneyHandlers = new ArrayList<>();
    LocalDateTime dateTime;
    String shift;

//    @Override
//    public void setNonMoneyHandlers(List<CollectEvenTippoolTipsEmployeeDTO> nonMoneyHandlers) {
//        this.nonMoneyHandlers=nonMoneyHandlers;
//    }
//
//    @Override
//    public void setMoneyHandlers(List<CollectEvenTippoolTipsEmployeeDTO> moneyHandlers) {
//        this.moneyHandlers=moneyHandlers;
//    }
//
//    @Override
//    public List<CollectEvenTippoolTipsEmployeeDTO> getMoneyHandlers() {
//        return moneyHandlers;
//    }
//
//    @Override
//    public List<CollectEvenTippoolTipsEmployeeDTO> getNonMoneyHandlers() {
//        return nonMoneyHandlers;
//    }
}
