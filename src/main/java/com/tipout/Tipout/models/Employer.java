package com.tipout.Tipout.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tipout.Tipout.models.Employees.*;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
One of two user classes. Employers handle Employee enrollment and setting Tipout schemes
 */

@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Employer extends UserEntity implements Serializable {

    @NotNull
    private String businessName;


//List of Employees tied to Employer
    @OneToMany(mappedBy = "employer", cascade=CascadeType.ALL)
    private List<Employee> employees= new ArrayList<>();

    //Eventually Employers will ba able to choose the employee types they want to have in their system
    @ElementCollection(targetClass = String.class)
    private List<String> employeesTypes = new ArrayList<>(Arrays.asList(Bartender.getRoleType(), BOH.getRoleType(), Busser.getRoleType(), Server.getRoleType()));

    //Tip rates for Employes for tippool schema
    @OneToOne(cascade=CascadeType.ALL)
    private EmployeeTipRates tipRates = new EmployeeTipRates();

    public Employer(String username, String password) {
        super(username, password);
    }

    public Employer(String username, String businessName, String pwHash) {
        super(username, pwHash);
        this.businessName = businessName;
    }


    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<String> getEmployeesTypes() {
        return employeesTypes;
    }

    public void setEmployeesTypes(List<String> employeesTypes) {
        this.employeesTypes = employeesTypes;
    }

    public EmployeeTipRates getTipRates() {
        return tipRates;
    }

    public void setTipRates(EmployeeTipRates tipRates) {
        this.tipRates = tipRates;
    }
}
