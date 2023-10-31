package com.tipout.Tipout.models.Employees;

import com.tipout.Tipout.models.Employer;

import javax.persistence.Entity;
/*
One of the roles Employees can occupy
 */
@Entity
public class Bartender extends MoneyHandler {
    private static String roleType = "Bartender";
    public Bartender() {}

    public Bartender(String firstName, String lastName, Employer employer) {
        super(firstName, lastName, employer);
        this.setPercentOfTipout(employer.getTipRates().getBartenderRate());
    }

    public static String getRoleType() {
        return roleType;
    }
}
