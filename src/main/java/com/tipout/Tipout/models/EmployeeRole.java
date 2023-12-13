package com.tipout.Tipout.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.util.UUID;

/*
Employee tip rates tied to Employer
Fields in this class should follow the naming convention [capital case role]Rate
The tip distribution template access fields using this naming convention
 */
@Entity
@Data
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class EmployeeRole{

    @Id
    @GeneratedValue
    @Expose
    private UUID id;
    @ManyToOne
    @Expose
    private Employer employer;

    @Expose
    private String name;
    @Expose
    @NotNull
    @DecimalMax(value = "100", message = "Number must be less than 100")
    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
    private BigInteger rate;
    @Expose
    private boolean moneyHandler;

    public EmployeeRole(Employer employer, String name, BigInteger rate, boolean moneyHandler) {
        this.employer = employer;
        this.name = name;
        this.rate = rate;
        this.moneyHandler = moneyHandler;
    }

}
