package com.tipout.Tipout.models.interfaces;

import java.math.BigDecimal;
import java.util.List;

public interface Tipout<T extends CollectEmployeeInfoMap, K extends Report, S extends CollectEmployeeInfo> {

     K generateReport(T collectEmployeeInfoMap);
     K calculate(T collectEmployeeInfoMap);

     BigDecimal calculateTotalTips(List<S> collectEmployeeInfoMoneyHandlers);

     T clean(T collectEmployeeInfoMap);

}
