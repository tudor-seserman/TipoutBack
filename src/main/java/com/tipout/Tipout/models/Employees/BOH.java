package com.tipout.Tipout.models.Employees;

import com.tipout.Tipout.models.Employer;

import javax.persistence.*;
/*
One of the roles Employees can occupy
 */
@Entity
public class BOH extends NonMoneyHandler {
    private static String roleType = "BOH";
    public BOH() {}

    public BOH(String firstName, String lastName, Employer employer) {
        super(firstName, lastName, employer);
        this.setPercentOfTipout(employer.getTipRates().getBOHRate());
    }

    public static String getRoleType() {
        return roleType;
    }
}
