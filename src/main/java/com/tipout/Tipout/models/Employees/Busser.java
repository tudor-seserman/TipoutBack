package com.tipout.Tipout.models.Employees;

import com.tipout.Tipout.models.Employer;

import javax.persistence.Entity;
import java.math.BigDecimal;
/*
One of the roles Employees can occupy
 */
@Entity
public class Busser extends NonMoneyHandler {
    private static String roleType = "Busser";
    public Busser() {}

    public Busser(String firstName, String lastName, Employer employer) {
        super(firstName, lastName, employer);
        this.setPercentOfTipout(employer.getTipRates().getBusserRate());
    }

    public static String getRoleType() {
        return roleType;
    }
}
