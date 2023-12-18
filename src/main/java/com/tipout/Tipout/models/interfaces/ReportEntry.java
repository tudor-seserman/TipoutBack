package com.tipout.Tipout.models.interfaces;

import com.tipout.Tipout.models.Tips;

import java.math.BigDecimal;
import java.util.UUID;

public interface ReportEntry<T> {
    UUID id = null;
    UUID roleID = null;
    String roleName = null;
    String name = null;
    BigDecimal tips = null;
    Tips tipsOwed = null;
    BigDecimal sales = null;

    void createEntry(T collectEmployeeInfo);
    Tips getTipsOwed();
    String getName();
}
