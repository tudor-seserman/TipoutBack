package com.tipout.Tipout.models.Employees;

import com.tipout.Tipout.models.Employer;

import javax.persistence.Entity;
import java.math.BigDecimal;
/*
One of the roles Employees can occupy
 */
@Entity
public class Server extends MoneyHandler {
    private static String roleType = "Server";
    public Server() {}

    public Server(String firstName, String lastName, Employer employer) {
        super(firstName, lastName, employer);
        this.setPercentOfTipout(employer.getTipRates().getServerRate());
    }

    public static String getRoleType() {
        return roleType;
    }
}
