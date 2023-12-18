package com.tipout.Tipout.models.TipoutSchemas.EvenTippool;

import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs.CollectEvenTippoolTipsEmployeeDTO;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs.CollectTipsEvenTippoolMapDTO;
import com.tipout.Tipout.models.Tips;
import com.tipout.Tipout.models.interfaces.Tipout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class TipoutEvenTippool implements Tipout<CollectTipsEvenTippoolMapDTO, ReportEvenTippool, CollectEvenTippoolTipsEmployeeDTO> {


    @Override
    public ReportEvenTippool generateReport(CollectTipsEvenTippoolMapDTO collectEmployeeInfoMap) {
        CollectTipsEvenTippoolMapDTO cleanedMap = this.clean(collectEmployeeInfoMap);
        ReportEvenTippool report = this.calculate(cleanedMap);

        return report;
    }

    @Override
    public ReportEvenTippool calculate(CollectTipsEvenTippoolMapDTO collectTipsEvenTippoolMapDTO) {
        ReportEvenTippool reportEvenTippool = new ReportEvenTippool();
        reportEvenTippool.initializeReport(collectTipsEvenTippoolMapDTO);

        BigDecimal totalTipsInTippool = this.calculateTotalTips(collectTipsEvenTippoolMapDTO.getMoneyHandlers());
        reportEvenTippool.setTotalTipsCollected(totalTipsInTippool);

        Integer sizeOfTippol = reportEvenTippool.getNonMoneyHandlersEntries().size()+reportEvenTippool.getMoneyHandlersEntries().size();

        BigDecimal shareOfTippoolForEachRole = totalTipsInTippool.divide(new BigDecimal(sizeOfTippol), 4, RoundingMode.HALF_UP);

        for (ReportEvenTippoolEntry nonMoneyHandler : reportEvenTippool.getNonMoneyHandlersEntries()) {
            nonMoneyHandler.setTipsOwed(new Tips(shareOfTippoolForEachRole));
        }
        for (ReportEvenTippoolEntry moneyHandler : reportEvenTippool.getMoneyHandlersEntries()) {
            moneyHandler.setTipsOwed(new Tips(shareOfTippoolForEachRole));
        }

        return reportEvenTippool;
    }


    @Override
    public BigDecimal calculateTotalTips(List<CollectEvenTippoolTipsEmployeeDTO> collectEmployeeInfoMoneyHandlers) {
        BigDecimal totalTips = BigDecimal.valueOf(0);

        for (CollectEvenTippoolTipsEmployeeDTO collectTipsEmployeeDTO : collectEmployeeInfoMoneyHandlers) {
            totalTips = totalTips.add(collectTipsEmployeeDTO.getTips());
        }

        return totalTips;
    }

    @Override
    public CollectTipsEvenTippoolMapDTO clean(CollectTipsEvenTippoolMapDTO collectTipsWeightedByRoleMapDTO) {

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
