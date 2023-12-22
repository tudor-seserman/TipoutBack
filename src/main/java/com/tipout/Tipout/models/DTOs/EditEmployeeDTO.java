package com.tipout.Tipout.models.DTOs;

import com.tipout.Tipout.models.EmployeeRole;

import java.util.UUID;

public record EditEmployeeDTO(UUID idEdit, String firstName, String lastName, EmployeeRole[] employeeRole) {

}