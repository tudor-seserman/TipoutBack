package com.tipout.Tipout.controllers;

//import com.tipout.Tipout.models.Employee;
//import com.tipout.Tipout.models.EmployeeTipRates;
//import com.tipout.Tipout.models.Employer;
//import com.tipout.Tipout.models.data.EmployeeRepository;
//import com.tipout.Tipout.models.data.EmployerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import java.util.Optional;
///*
//THis controller allows for the Employer to customize settings for their use
// */
//@Controller
//@RequestMapping(value="settings")
//public class SettingsController {
//
//    @Autowired
//    AuthenticationController authenticationController;
//    @Autowired
//    EmployeeRepository employeeRepository;
//    @Autowired
//    EmployerRepository employerRepository;
////Landing page with the option to select the settings they would like to adjust
//    @GetMapping
//    public String returnIndex(Model model){
//        model.addAttribute("title", "Settings");
//        return "settings/index";
//    }
//
////    Displays Employer's Employee archive. Employees are archived if their deletd field is set to true
//    @GetMapping("archive")
//    public String allArchivedEmployee(Model model,
//                                      HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//        model.addAttribute("title", "Archived Employees");
//        model.addAttribute("archivedEmployees", employeeRepository.findArhievedEmployees(employer.getId()));
//        return "settings/archive";
//    }
////If an Employer reinstates an Employee this form sets the Employees delete field to false
//    @PostMapping("archive")
//    public String unArchiveEmployee(@RequestParam Long employeeToUnArchiveId, Model model,
//                                    HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//        Optional<Employee> optEmployeeToUnArchive = employeeRepository.findById(employeeToUnArchiveId);
//        if (optEmployeeToUnArchive.isEmpty()) {
//            model.addAttribute("title", "Archived Employees");
//            model.addAttribute("cannotFindEmployee", "cannotFindEmployee");
//            model.addAttribute("archivedEmployees", employeeRepository.findArhievedEmployees(employer.getId()));
//            return "settings/archive";
//        }
//
//        Employee employeeToUnArchive = optEmployeeToUnArchive.get();
//        employeeToUnArchive.setDeleted(Boolean.FALSE);
//        employeeRepository.save(employeeToUnArchive);
//
//        model.addAttribute("title", "Archived Employees");
//        model.addAttribute("archivedEmployees", employeeRepository.findArhievedEmployees(employer.getId()));
//        model.addAttribute("employeeToUnArchive", employeeToUnArchive);
//        return "settings/archive";
//    }
//
////    Employer adjust tip rates for Employee roles
//    @GetMapping("tipDistribution")
//    public String returnTipDistribution(Model model,
//                                        HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//        EmployeeTipRates employeeTipRates = employer.getTipRates();
//        model.addAttribute("title", "Tip Distribution");
//        model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//        model.addAttribute("employeeTipRates", employeeTipRates);
//        return "settings/tipDistribution";
//    }
//
//    @PostMapping("tipDistribution")
//    public String processTipDistributionForm(Model model,
//                                             @ModelAttribute @Valid EmployeeTipRates employeeTipRates,
//                                             Errors errors,
//                                             HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//
////         Checking for validation issues
//        if(errors.hasErrors()){
//            model.addAttribute("title", "Tip Distribution");
//            model.addAttribute("employeeTipRates", employeeTipRates);
//            model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//            return "settings/tipDistribution";
//        }
//
////         Saves the passed in EmployeeTipRated rates for the current Employer
//        employer.setTipRates(employeeTipRates);
//        employerRepository.save(employer);
//
////        Changes the tip rates for current employees to the new rate
//        for(Employee employee: employer.getEmployees()){
//            employee.setPercentOfTipout(employeeTipRates.getTipoutByRole(employee));
//            employeeRepository.save(employee);
//        }
//
//
//        EmployeeTipRates newEmployeeTipRates = employer.getTipRates();
//        model.addAttribute("title", "Tip Distribution");
//        model.addAttribute("success", "success");
//        model.addAttribute("employeeTypes", employer.getEmployeesTypes());
//        model.addAttribute("employeeTipRates", newEmployeeTipRates);
//        return "settings/tipDistribution";
//    }
//
//}
