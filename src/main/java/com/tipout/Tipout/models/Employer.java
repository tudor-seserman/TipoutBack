package com.tipout.Tipout.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Employer extends UserEntity implements Serializable {

    @NotNull
    private String businessName;

//List of Employees tied to Employer
    @OneToMany(mappedBy = "employer", cascade=CascadeType.ALL)
    private List<Employee> employees= new ArrayList<>();

    @OneToMany(mappedBy = "employer", cascade=CascadeType.ALL)
    private List<EmployeeRole> employeesRoleTypes=new ArrayList<>();


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
                new EmployeeRole(this,"Busser", BigInteger.valueOf(15),false));
    }
}
