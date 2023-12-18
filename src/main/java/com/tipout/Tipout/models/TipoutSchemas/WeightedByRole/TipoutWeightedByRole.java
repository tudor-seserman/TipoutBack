package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole;

import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsEmployeeDTO;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs.CollectTipsWeightedByRoleMapDTO;
import com.tipout.Tipout.models.Tips;
import com.tipout.Tipout.models.interfaces.Tipout;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TipoutWeightedByRole implements Tipout<CollectTipsWeightedByRoleMapDTO, ReportWeightedByRole, CollectTipsEmployeeDTO> {


    @Override
    public ReportWeightedByRole generateReport(CollectTipsWeightedByRoleMapDTO collectEmployeeInfoMap) {
        CollectTipsWeightedByRoleMapDTO cleanedMap = this.clean(collectEmployeeInfoMap);
        ReportWeightedByRole report = this.calculate(cleanedMap);

        return report;
    }

    @Override
    public ReportWeightedByRole calculate(CollectTipsWeightedByRoleMapDTO CollectEmployeeInfoMap) {
        ReportWeightedByRole reportWeightedByRole = new ReportWeightedByRole();
        reportWeightedByRole.initializeReport(CollectEmployeeInfoMap);

        BigDecimal totalTipsInTippool= this.calculateTotalTips(CollectEmployeeInfoMap.getMoneyHandlers());
        reportWeightedByRole.setTotalTipsCollected(totalTipsInTippool);

        BigInteger totalTippoolRates = this.calculateTotalTipoutRates(CollectEmployeeInfoMap);

        BigDecimal shareOfTippoolForEachRole = totalTipsInTippool.divide(new BigDecimal(totalTippoolRates), 4, RoundingMode.HALF_UP);

        for (ReportWeightedByRoleEntry nonMoneyHandler: reportWeightedByRole.getNonMoneyHandlersEntries()){
            nonMoneyHandler.setTipsOwed(new Tips(shareOfTippoolForEachRole.multiply(new BigDecimal(nonMoneyHandler.getRole().getRate()))));
        }
        for (ReportWeightedByRoleEntry moneyHandler: reportWeightedByRole.getMoneyHandlersEntries()){
            moneyHandler.setTipsOwed(new Tips(shareOfTippoolForEachRole.multiply(new BigDecimal(moneyHandler.getRole().getRate()))));
        }

        return reportWeightedByRole;
    }

    BigInteger calculateTotalTipoutRates(CollectTipsWeightedByRoleMapDTO collectTipsWeightedByRoleMapDTO){
        BigInteger totalRates = BigInteger.valueOf(0);

        for(CollectTipsEmployeeDTO employeeDTO:collectTipsWeightedByRoleMapDTO.getMoneyHandlers()){
            totalRates = totalRates.add(employeeDTO.getRole().getRate());
        }
        for(CollectTipsEmployeeDTO employeeDTO:collectTipsWeightedByRoleMapDTO.getNonMoneyHandlers()){
            totalRates = totalRates.add(employeeDTO.getRole().getRate());
        }



        return totalRates;
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
