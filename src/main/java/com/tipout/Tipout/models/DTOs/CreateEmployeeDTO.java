package com.tipout.Tipout.models.DTOs;

import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.Employees.BOH;
import com.tipout.Tipout.models.Employees.Bartender;
import com.tipout.Tipout.models.Employees.Busser;
import com.tipout.Tipout.models.Employees.Server;
import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.data.BOHRepository;
import com.tipout.Tipout.models.data.BartenderRepository;
import com.tipout.Tipout.models.data.BusserRepository;
import com.tipout.Tipout.models.data.ServerRepository;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
