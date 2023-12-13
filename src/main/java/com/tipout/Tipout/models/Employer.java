package com.tipout.Tipout.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/*
One of two user classes. Employers handle Employee enrollment and setting Tipout schemes
 */

@Entity
@Data
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Employer extends UserEntity implements Serializable {

    @NotNull
    private String businessName;

//List of Employees tied to Employer
    @OneToMany(mappedBy = "employer", cascade=CascadeType.ALL)
    private List<Employee> employees= new ArrayList<>();

    @OneToMany(mappedBy = "employer", cascade=CascadeType.ALL)
    private List<EmployeeRole> employeesRoleTypes=new ArrayList<>();

    //Eventually Employers will ba able to choose the employee types they want to have in their system
//    @ElementCollection(targetClass = String.class)
//    private List<String> employeesTypes = new ArrayList<>(Arrays.asList(Bartender.getRoleType(), BOH.getRoleType(), Busser.getRoleType(), Server.getRoleType()));
//
//    //Tip rates for Employes for tippool schema
//    @OneToOne(cascade=CascadeType.ALL)
//    private EmployeeRole tipRates = new EmployeeRole();

    public Employer(String username, String password) {
        super(username, password);
    }

    public Employer(String username, String businessName, String pwHash) {
        super(username, pwHash);
        this.businessName = businessName;
        this.employeesRoleTypes = List.of(
                new EmployeeRole(this,"Bartender", BigInteger.valueOf(40),true),
                new EmployeeRole(this,"Server", BigInteger.valueOf(40),true),
                new EmployeeRole(this,"BOH", BigInteger.valueOf(5),false),
                new EmployeeRole(this,"Busser", BigInteger.valueOf(115),false));
    }
}
