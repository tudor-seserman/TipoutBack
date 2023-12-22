package com.tipout.Tipout.models.DTOs;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/*
Object to help gather information for the creation of new employees.
 */
@Data
public class CreateEmployeeDTO {

    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @NotBlank
    private String employeeRole;
}
