package com.tipout.Tipout.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.Expose;
import lombok.Data;

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
//@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Employee implements Serializable {

    @Id
    @GeneratedValue
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

//Quick way to get role name
//    @Expose
//    private String roleDetail = this.getClass().getSimpleName();
    @Expose
    @OneToMany
    private List<EmployeeRole> employeeRole = new ArrayList<>();

    //Field is used to filter out archived employees from active Employees
    @Expose
    private boolean deleted = Boolean.FALSE;

    public Employee(String firstName, String lastName, Employer employer, EmployeeRole employeeRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employer = employer;
        this.employeeRole.add(employeeRole);
    }

    @Override
    public String toString() {
        return firstName + ' ' +lastName;
    }
}
