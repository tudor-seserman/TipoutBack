package com.tipout.Tipout.models.Employees;

import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.Tips;
import com.tipout.Tipout.models.TipsCollected;

import javax.persistence.*;
/*
One of two main Employee class.
Employees in this class handle money. They collect payments from guests.
Usually will have to declare tips at the end of the shift.
 */
@Entity
public class MoneyHandler extends Employee {

    @OneToOne
    private Tips tips;

    public MoneyHandler() {
    }

    public MoneyHandler(String firstName, String lastName, Employer employer) {
        super(firstName, lastName, employer);
    }


    public Tips getTips() {
        return tips;
    }

    public void setTips(Tips tips) {
        this.tips = tips;
    }
}
