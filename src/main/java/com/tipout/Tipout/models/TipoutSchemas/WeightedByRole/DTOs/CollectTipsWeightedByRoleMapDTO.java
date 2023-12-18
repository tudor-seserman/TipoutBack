package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs;

import com.tipout.Tipout.models.interfaces.CollectEmployeeInfoMap;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CollectTipsWeightedByRoleMapDTO implements CollectEmployeeInfoMap<CollectTipsEmployeeDTO> {
    List<CollectTipsEmployeeDTO> moneyHandlers = new ArrayList<>();
    List<CollectTipsEmployeeDTO> nonMoneyHandlers = new ArrayList<>();

//    @Override
//    public void setNonMoneyHandlers(List<CollectTipsEmployeeDTO> nonMoneyHandlers) {
//        this.nonMoneyHandlers=nonMoneyHandlers;
//    }
//
//    @Override
//    public void setMoneyHandlers(List<CollectTipsEmployeeDTO> moneyHandlers) {
//        this.moneyHandlers=moneyHandlers;
//    }
//
//    @Override
//    public List<CollectTipsEmployeeDTO> getMoneyHandlers() {
//        return moneyHandlers;
//    }
//
//    @Override
//    public List<CollectTipsEmployeeDTO> getNonMoneyHandlers() {
//        return nonMoneyHandlers;
//    }
}
