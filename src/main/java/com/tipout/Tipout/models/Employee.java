package com.tipout.Tipout.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/*
This class will be used to create a base employee,
different roles will inherit from this class.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Employee extends AbstractEntity implements Serializable {
    @NotNull
    @NotBlank
    @Expose
    private String firstName;
    @NotNull
    @NotBlank
    @Expose
    private String lastName;

// Employees are tied to employer
    @ManyToOne
    private Employer employer;
    @Expose
    private BigInteger percentOfTipOut;
//Quick way to get role name
@Expose
    private String roleDetail = this.getClass().getSimpleName();
//Field is used to filter out archived employees from active Employees
@Expose
    private boolean deleted = Boolean.FALSE;




    public Employee() {
    }


    public Employee(String firstName, String lastName, Employer employer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employer = employer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Employer getEmployer() {
        return employer;
    }


    public String getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }

    public BigInteger getPercentOfTipout() {
        return percentOfTipOut;
    }

    public void setPercentOfTipout(BigInteger percentOfTipOut) {
        this.percentOfTipOut = percentOfTipOut;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return firstName + ' ' +lastName;
    }
}
