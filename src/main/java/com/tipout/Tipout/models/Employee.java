package com.tipout.Tipout.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
This class will be used to create a base employee,
different roles will inherit from this class.
 */
@Entity
@Data
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Expose
    private UUID id;
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
    @ManyToMany
    private List<EmployeeRole> employeeRoles = new ArrayList<>();

    //Field is used to filter out archived employees from active Employees
    @Expose
    private boolean deleted = Boolean.FALSE;

    public Employee(String firstName, String lastName, Employer employer, EmployeeRole employeeRoles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employer = employer;
        this.employeeRoles.add(employeeRoles);
    }

    public void deleteRole(EmployeeRole employeeRole){
        if(employeeRoles.contains(employeeRole)){
            employeeRoles.remove(employeeRole);}
    }

    public void addRole(EmployeeRole employeeRole){
        if(!employeeRoles.contains(employeeRole)){
            employeeRoles.add(employeeRole);}
    }


    public String getFullName() {
        return this.firstName + ' ' +this.lastName;
    }
}
