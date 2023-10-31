package com.tipout.Tipout.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tipout.Tipout.models.DTOs.CreateEmployeeDTO;
import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.Employees.*;
import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.UserEntity;
import com.tipout.Tipout.models.data.*;
import com.tipout.Tipout.service.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
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
    BartenderRepository bartenderRepository;
    BOHRepository bohRepository;
    BusserRepository busserRepository;
    ServerRepository serverRepository;
    AuthenticationController authenticationController;
    EmployeeRepository employeeRepository;
    EmployerRepository employerRepository;
    AuthenticatedUser authenticatedUser;
    @Autowired
    public EmployeeController(BartenderRepository bartenderRepository, BOHRepository bohRepository, BusserRepository busserRepository, ServerRepository serverRepository, AuthenticationController authenticationController, EmployeeRepository employeeRepository, EmployerRepository employerRepository, AuthenticatedUser authenticatedUser) {
        this.bartenderRepository = bartenderRepository;
        this.bohRepository = bohRepository;
        this.busserRepository = busserRepository;
        this.serverRepository = serverRepository;
        this.authenticationController = authenticationController;
        this.employeeRepository = employeeRepository;
        this.employerRepository = employerRepository;
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping
    public Iterable<String> getAllEmployers(){
        Employer employer = (Employer)authenticatedUser.getUser();
        return employer.getEmployeesTypes();

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
            switch (employee.getEmployeeRole()) {
                case "Bartender":
                    Bartender newBartender = new Bartender(employee.getFirstName(), employee.getLastName(), employer);
                    bartenderRepository.save(newBartender);
                    break;
                case "BOH":
                    BOH newBOH = new BOH(employee.getFirstName(), employee.getLastName(), employer);
                    bohRepository.save(newBOH);
                    break;
                case "Busser":
                    Busser newBusser = new Busser(employee.getFirstName(), employee.getLastName(), employer);
                    busserRepository.save(newBusser);
                    break;
                case "Server":
                    Server newServer = new Server(employee.getFirstName(), employee.getLastName(), employer);
                    serverRepository.save(newServer);
                    break;
                default:
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
    }


//This method creates the form to add employees.
//I am currently passing to the view a list of empty employees, but aim to pass in an a list of Employer selected roles.
//This way an employer can customize what employee roles they want to create.
//
//    @GetMapping("add")
//    public String addNewEmployee(Model model,
//                                 HttpServletRequest request
//    ) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//        model.addAttribute("title", "Add Employee");
//        model.addAttribute("createEmployeeDTO", new CreateEmployeeDTO());
//        model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//        return "employees/add";
//    }

//
////Employee enrollment is processed by this form. It takes in the logedin Employer, the role name
////and creates the specific kind of Employee that the Employer selected.
////

//        @PostMapping("add")
//    public String processAddNewEmployee(@RequestBody @Valid CreateEmployeeDTO employee,
//                                        Errors errors) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//
//        if (errors.hasErrors()) {
//            model.addAttribute("title", "Add Employee");
//            model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//            return "employees/add";
//        }
//        try{
//            createEmployee(employer, employee);
//        }catch(RuntimeException e) {
//            model.addAttribute("error", "Something went wrong. Please try again or contact customer support if problem persists.");
//            model.addAttribute("title", "Add Employee");
//            model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//            return "employees/add";
//        }
//
//        model.addAttribute("title", "Add Employee");
//        model.addAttribute("createEmployeeDTO", new CreateEmployeeDTO());
//        model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//        model.addAttribute("newEmployee", employee.getFirstName());
//        return "employees/add";
//    }

//    @PostMapping("add")
//    public String processAddNewEmployee(Model model,
//                                        @ModelAttribute @Valid CreateEmployeeDTO employee,
//                                        Errors errors,
//                                        HttpServletRequest request
//    ) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//
//        if (errors.hasErrors()) {
//            model.addAttribute("title", "Add Employee");
//            model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//            return "employees/add";
//        }
//        try{
//            createEmployee(employer, employee);
//        }catch(RuntimeException e) {
//            model.addAttribute("error", "Something went wrong. Please try again or contact customer support if problem persists.");
//            model.addAttribute("title", "Add Employee");
//            model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//            return "employees/add";
//        }
//
//        model.addAttribute("title", "Add Employee");
//        model.addAttribute("createEmployeeDTO", new CreateEmployeeDTO());
//        model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//        model.addAttribute("newEmployee", employee.getFirstName());
//        return "employees/add";
//    }
//


////This method displays all active employees with the option to edit them.
    @GetMapping("current")
    public ResponseEntity<List<String>> allEmployee(){
        Employer employer = (Employer)authenticatedUser.getUser();
        List<Employee> employees = employeeRepository.findCurrentEmployees(employer.getId());
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();;
        List<String> JSONemplpyees= employees.stream().map(gson::toJson).collect(Collectors.toList());
        return ResponseEntity.ok(JSONemplpyees);
    }

//    @GetMapping("current")
//    public String allEmployee(Model model,
//                                HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//        model.addAttribute("title", "Current Employees");
//        model.addAttribute("currentEmployees", employeeRepository.findCurrentEmployees(employer.getId()));
//        return "employees/current";
//    }
//
////    This method displays an employee with the option to edit name, archive, or delete.
////    Eventually I hope to be able to allow for Employers to change Employee roles and allow for employees to have multiple roles
//    @GetMapping("edit/{employeeToEditId}")
//    public String editEmployeeForm(@PathVariable Long employeeToEditId, Model model,
//                                   HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//        Optional<Employee> optEmployeeToEdit = employeeRepository.findById(employeeToEditId);
//
////        Handling if employee is not in system
//        if (optEmployeeToEdit.isEmpty()) {
//            model.addAttribute("title", "Current Employees");
//            model.addAttribute("currentEmployees", employeeRepository.findCurrentEmployees(employer.getId()));
//            model.addAttribute("cannotFindEmployee","cannotFindEmployee");
//            return "employees/current";
//        }
//        Employee employeeToEdit = optEmployeeToEdit.get();
//
//        model.addAttribute("title",
//                "Edit "+employeeToEdit);
//        model.addAttribute("employeeToEdit", employeeToEdit);
//        model.addAttribute("employeeToEditId", employeeToEditId);
//        return "employees/edit";
//    }
//
////
//// This methode handles the employee edit form.
////    If the Employer selects to Archive an employee that is determined by the boolean field archive.
////    In the form the employee name is passed in, whatever is in the form when submitted is saved to the database as updated information
////    If nothing is changed, the same information is saved.
//
//
//    @PostMapping("edit/{employeeToEditId}")
//    public String editEmployeeProcessing(@PathVariable Long employeeToEditId,
//                                         Model model,
//                                         String firstName,
//                                         String lastName,
//                                         @RequestParam Boolean archive ,
//                                         HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//        Optional<Employee> optEmployeeToEdit = employeeRepository.findById(employeeToEditId);
//
//        if (optEmployeeToEdit.isEmpty()) {
//            model.addAttribute("title", "Current Employees");
//            model.addAttribute("currentEmployees", employeeRepository.findCurrentEmployees(employer.getId()));
//            model.addAttribute("cannotFindEmployee", "cannotFindEmployee");
//            return "redirect:/employees/current";
//        }
//
//        Employee employeeToEdit = optEmployeeToEdit.get();
//
//        if(archive) {
//            employeeToEdit.setDeleted(Boolean.TRUE);
//            model.addAttribute("archive","archive");
//        }else{
//            employeeToEdit.setFirstName(firstName);
//            employeeToEdit.setLastName(lastName);
//            model.addAttribute("success","success");
//        }
//
//        employeeRepository.save(employeeToEdit);
//
//
//        model.addAttribute("title",
//                "Edit "+employeeToEdit);
//        model.addAttribute("employeeToEdit", employeeToEdit);
//        model.addAttribute("employeeToEditId", employeeToEditId);
//        return "employees/edit";
//    }
//
////    This method brute forces an Employee delete.
////       Employers are asked to confirm and warned that all records associated with that employee will be deleted.
////       If they confirm, employees and all their data is deleted from the datebase.
////       This delete is intend mostly from employees created in error.
////
//    @PostMapping("delete/{employeeToDeleteId}")
//    public String deleteEmployeeProcessing(@PathVariable Long employeeToDeleteId,
//                                           Model model,
//                                           Boolean confirmation) {
//
//        Optional<Employee> optEmployeeToDelete = employeeRepository.findById(employeeToDeleteId);
//        if (optEmployeeToDelete.isEmpty()) {
//            model.addAttribute("title", "Current Employees");
//            model.addAttribute("currentEmployees", employeeRepository.findAll());
//            model.addAttribute("cannotFindEmployee", "cannotFindEmployee");
//            return "redirect:/employees/current";
//        }
//
//        Employee employeeToDelete = optEmployeeToDelete.get();
//
////      Employees are not deleted until Employer confirms this action
//        if(confirmation){
////            In order to delete Employees all their recorded tips have to also be deleted
//            employeeRepository.completelyDeleteEmployeeTipRecord(employeeToDeleteId);
//            if(employeeToDelete instanceof MoneyHandler){employeeRepository.completelyDeleteMoneyhandlerTipRecord(employeeToDeleteId);}
//            if(employeeToDelete instanceof NonMoneyHandler){employeeRepository.completelyDeleteNonMoneyhandlerTipRecord(employeeToDelete.getId());}
//            employeeRepository.deleteById(employeeToDeleteId);
//
//            model.addAttribute("title", "Current Employees");
//            model.addAttribute("currentEmployees", employeeRepository.findAll());
//            model.addAttribute("delete","delete");
//            return "redirect:/employees/current";
//        }
//
//        model.addAttribute("title", "Delete Employee");
//        model.addAttribute("employeeToDelete", employeeToDelete);
//        model.addAttribute("employeeToDeletedId", employeeToDeleteId);
//        return "employees/delete";
//    }
}
