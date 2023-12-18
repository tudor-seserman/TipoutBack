package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole;

import com.tipout.Tipout.models.EmployeeRole;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsWeightedByRoleMapDTO;
import com.tipout.Tipout.models.Tips;
import com.tipout.Tipout.models.interfaces.CollectEmployeeInfo;
import com.tipout.Tipout.models.interfaces.Tipout;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TipoutWeightedByRole implements Tipout<CollectTipsWeightedByRoleMapDTO, ReportWeightedByRole, CollectTipsEmployeeDTO> {


    @Override
    public ReportWeightedByRole calculate(CollectTipsWeightedByRoleMapDTO CollectEmployeeInfoMap) {
        ReportWeightedByRole reportWeightedByRole = new ReportWeightedByRole();
        reportWeightedByRole.intializeReport(CollectEmployeeInfoMap);

        BigDecimal totalTipsInTippool= this.calculateTotalTips(CollectEmployeeInfoMap.getMoneyHandlers());
        reportWeightedByRole.setTotalTipsCollected(totalTipsInTippool);

        BigInteger totalTippoolRates = this.calculateTotalUniqueTipoutRates(CollectEmployeeInfoMap);

        BigDecimal shareOfTippool = totalTipsInTippool.divide(new BigDecimal(totalTippoolRates), 4, RoundingMode.HALF_UP);

        for (ReportWeightedByRoleEntry nonMoneyHandler: reportWeightedByRole.getNonMoneyHandlersEntries()){
            nonMoneyHandler.setTipsOwed(new Tips(shareOfTippool.multiply(new BigDecimal(nonMoneyHandler.getRole().getRate()))));
        }
        for (ReportWeightedByRoleEntry moneyHandler: reportWeightedByRole.getMoneyHandlersEntries()){
            moneyHandler.setTipsOwed(new Tips(shareOfTippool.multiply(new BigDecimal(moneyHandler.getRole().getRate()))));
        }

        System.out.println(reportWeightedByRole);
        return reportWeightedByRole;
    }

    BigInteger calculateTotalUniqueTipoutRates(CollectTipsWeightedByRoleMapDTO collectTipsWeightedByRoleMapDTO){
        BigInteger totalUniqueRates = BigInteger.valueOf(0);
        Set<EmployeeRole> roles= new HashSet<>();

        for(CollectTipsEmployeeDTO employeeDTO:collectTipsWeightedByRoleMapDTO.getMoneyHandlers()){
            roles.add(employeeDTO.getRole());
        }
        for(CollectTipsEmployeeDTO employeeDTO:collectTipsWeightedByRoleMapDTO.getNonMoneyHandlers()){
            roles.add(employeeDTO.getRole());
        }

        for(EmployeeRole role: roles){
            totalUniqueRates = totalUniqueRates.add(role.getRate());
        }

        return totalUniqueRates;
    }

    @Override
    public BigDecimal calculateTotalTips(List<CollectTipsEmployeeDTO> collectEmployeeInfoMoneyHandlers) {
        BigDecimal totalTips = BigDecimal.valueOf(0);

        for (CollectTipsEmployeeDTO collectTipsEmployeeDTO:collectEmployeeInfoMoneyHandlers){
            totalTips = totalTips.add(collectTipsEmployeeDTO.getTips());
        }

        return totalTips;
    }

    @Override
    public CollectTipsWeightedByRoleMapDTO clean(CollectTipsWeightedByRoleMapDTO collectTipsWeightedByRoleMapDTO) {

        collectTipsWeightedByRoleMapDTO.setNonMoneyHandlers(
                collectTipsWeightedByRoleMapDTO
                        .getNonMoneyHandlers()
                        .stream()
                        .filter((nonMoneyHandler) ->
                                nonMoneyHandler.getTips() != null)
                        .collect(Collectors.toList()));

        collectTipsWeightedByRoleMapDTO.setMoneyHandlers(
                collectTipsWeightedByRoleMapDTO
                        .getMoneyHandlers()
                        .stream()
                        .filter((moneyHandler) ->
                                moneyHandler.getTips() != null)
                        .collect(Collectors.toList()));


        return collectTipsWeightedByRoleMapDTO;
    }
}
