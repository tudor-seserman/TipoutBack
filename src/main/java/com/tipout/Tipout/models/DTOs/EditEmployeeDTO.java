package com.tipout.Tipout.models.DTOs;

import java.util.UUID;

public record EditEmployeeDTO(UUID idEdit, String firstName, String lastName, String employeeRole) {

}