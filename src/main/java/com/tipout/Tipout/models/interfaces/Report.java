package com.tipout.Tipout.models.interfaces;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface Report <T extends ReportEntry,K extends CollectEmployeeInfoMap>{
    List<ReportEntry> moneyHandlersEntries= new ArrayList<>();
    List<ReportEntry> nonMoneyHandlersEntries= new ArrayList<>();
    BigDecimal totalTipsCollected = null;
    BigDecimal totalSalesCollected = null;

    void initializeReport(K collectEmployeeInfoMap);

    BigDecimal getTotalTipsCollected();

    List<T> getMoneyHandlersEntries();

    List<T> getNonMoneyHandlersEntries();
}
