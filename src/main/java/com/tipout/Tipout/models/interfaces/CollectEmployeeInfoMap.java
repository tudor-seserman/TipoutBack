package com.tipout.Tipout.models.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface CollectEmployeeInfoMap<T extends CollectEmployeeInfo> {
    List<CollectEmployeeInfo> moneyHandlers = new ArrayList<>();
    List<CollectEmployeeInfo> nonMoneyHandlers = new ArrayList<>();

    List<T> getMoneyHandlers();

    List<T> getNonMoneyHandlers();

     void setNonMoneyHandlers(List<T> nonMoneyHandlers);

    void setMoneyHandlers(List<T> moneyHandlers);
}
