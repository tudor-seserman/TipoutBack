package com.tipout.Tipout.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tipout.Tipout.models.DTOs.CreateEmployeeDTO;
import com.tipout.Tipout.models.DTOs.DeleteEmployeeDTO;
import com.tipout.Tipout.models.DTOs.EditEmployeeDTO;
import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.EmployeeRole;
import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.data.*;
import com.tipout.Tipout.service.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
This controller is responsible for creating, editing, archiving, and deleting employees.
The employee archive is managed by the SettingsController.
 */

@RestController
@RequestMapping(value="employees")
public class EmployeeController {

    AuthenticationController authenticationController;
    EmployeeRepository employeeRepository;
    EmployeeRoleRepository employeeRoleRepository;
    EmployerRepository employerRepository;
    AuthenticatedUser authenticatedUser;
    @Autowired
    public EmployeeController(AuthenticationController authenticationController, EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository, EmployerRepository employerRepository, AuthenticatedUser authenticatedUser) {
        this.authenticationController = authenticationController;
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.employerRepository = employerRepository;
        this.authenticatedUser = authenticatedUser;
    }



//
//    Creates a specific kind of employee depending on the Employer passed in and information from the CreateEmployeeDTO
//    The main vehicle by which Employees are created by Employer
//    This method might be better placed in its own Service class
//
//
    @PostMapping
    public ResponseEntity createEmployee(@RequestBody CreateEmployeeDTO employee){
            Employer employer = (Employer) authenticatedUser.getUser();
            Optional<EmployeeRole> employeeRoleOptional = employeeRoleRepository.findByNameAndEmployer_id(employee.getEmployeeRole(), employer.getId());
            if (employeeRoleOptional.isEmpty()){new ResponseEntity<>(HttpStatus.BAD_REQUEST);};
            EmployeeRole employeeRole = employeeRoleOptional.get();

            Employee newEmployee = new Employee(employee.getFirstName(), employee.getLastName(), employer, employeeRole);
            employeeRepository.save(newEmployee);

            return new ResponseEntity<>(HttpStatus.OK);
    }



////This method displays all active employees with the option to edit them.
    @GetMapping("current")
    public ResponseEntity<List<String>> allEmployee(){
        Employer employer = (Employer)authenticatedUser.getUser();
        Optional<List<Employee>> optionalEmployees = employeeRepository.findByEmployerAndDeletedFalse(employer);
        if (optionalEmployees.isEmpty()) {
//            Placeholder until I can implement @ControllerAdvice
            return ResponseEntity.ok(List.of("Not Found"));
        }
        List<Employee> employees = optionalEmployees.get();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<String> JSONemplpyees= employees.stream().map(gson::toJson).collect(Collectors.toList());
        return ResponseEntity.ok(JSONemplpyees);
    }

    @PostMapping("edit")
    public ResponseEntity editEmployee(@RequestBody EditEmployeeDTO employee){

        Optional<Employee> optEmployeeToEdit = employeeRepository.findById(employee.idEdit());
        if(optEmployeeToEdit.isEmpty()){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        Employee employeeToEdit = optEmployeeToEdit.get();
        employeeToEdit.setFirstName(employee.firstName());
        employeeToEdit.setLastName(employee.lastName());
        employeeRepository.save(employeeToEdit);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("delete")
    public HttpStatus deleteEmployeeProcessing(@RequestBody DeleteEmployeeDTO employeeToDelete){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeToDelete.employeeToDelete());
        if(optionalEmployee.isEmpty()){return HttpStatus.NOT_FOUND;}
        Employee employee = optionalEmployee.get();
        employee.setDeleted(!employee.isDeleted());
        employeeRepository.save(employee);
        return HttpStatus.OK;
    }

}
