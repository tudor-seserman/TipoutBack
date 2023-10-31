package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.*;
import com.tipout.Tipout.models.DTOs.CollectTipsEmployeeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tipout.Tipout.models.Employees.MoneyHandler;
import com.tipout.Tipout.models.Employees.NonMoneyHandler;
import com.tipout.Tipout.models.data.*;
import com.tipout.Tipout.service.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/*
This controller handles calculations and the display of those calculations for the app.
It takes employer input and displays how tips are to be distributed.

 */
@RestController
@RequestMapping(value="calculate")
public class TipoutController {
    AuthenticationController authenticationController;

    EmployeeRepository employeeRepository;
    MoneyHandlerRepository moneyHandlerRepository;
    NonMoneyHandlerRepository nonMoneyHandlerRepository;
    TipsCollectedRepository tipsCollectedRepository;
    AuthenticatedUser authenticatedUser;
    TipoutRepository tipoutRepository;

    @Autowired
    public TipoutController(AuthenticationController authenticationController, EmployeeRepository employeeRepository, MoneyHandlerRepository moneyHandlerRepository, NonMoneyHandlerRepository nonMoneyHandlerRepository, TipsCollectedRepository tipsCollectedRepository, AuthenticatedUser authenticatedUser, TipoutRepository tipoutRepository) {
        this.authenticationController = authenticationController;
        this.employeeRepository = employeeRepository;
        this.moneyHandlerRepository = moneyHandlerRepository;
        this.nonMoneyHandlerRepository = nonMoneyHandlerRepository;
        this.tipsCollectedRepository = tipsCollectedRepository;
        this.authenticatedUser = authenticatedUser;
        this.tipoutRepository = tipoutRepository;
    }


    /*
    This method is in charge of dealing with a simple tip pool. It provides two lists to the view:
    MoneyHandlers - Employees that collect tips tied to a particular Employer
    NonMoneyHandlers - Employees that do not collect tips tied to a particular Employer

    If value is set for a MoneyHandler they are included in the tippool, if no value is set they are not.
    For nonMoneyHandlers if they are selected to be in the tippol they will be included in the distribution.

    Data is collected by a TipsCollected objected and tabulated by a Tipout object
     */


    @GetMapping("MoneyHandler")
    public ResponseEntity<List<CollectTipsEmployeeDTO>> getMoneyHandler(){
        Employer employer = (Employer)authenticatedUser.getUser();
        List<MoneyHandler> moneyHandlerEmployees =moneyHandlerRepository.findAllByDeletedFalseAndEmployer_Id(employer.getId());
        List<CollectTipsEmployeeDTO> listToReturn = new ArrayList<>();
        moneyHandlerEmployees.forEach(x -> listToReturn.add(new CollectTipsEmployeeDTO(x)));
        return ResponseEntity.ok(listToReturn);
    }
    @GetMapping("NonMoneyHandler")
    public ResponseEntity<List<CollectTipsEmployeeDTO>> getNoNMoneyHandler(){
        Employer employer = (Employer)authenticatedUser.getUser();
        List<NonMoneyHandler> nonMoneyHandlerEmployees =nonMoneyHandlerRepository.findAllByDeletedFalseAndEmployer_Id(employer.getId());
        List<CollectTipsEmployeeDTO> listToReturn = new ArrayList<>();
        nonMoneyHandlerEmployees.forEach(x -> listToReturn.add(new CollectTipsEmployeeDTO(x)));
        return ResponseEntity.ok(listToReturn);
    }

    @PostMapping("report")
    public ResponseEntity<Map<String, String>> tipReport(@RequestBody List<CollectTipsEmployeeDTO> collectTipsEmployees){
        System.out.println(collectTipsEmployees);
        //Employees with different roles are fed into the same table,
        // any employees that are not included in the tippool are not included in the new table
        // if no tips are declared an error is thrown
//        try {
//            tipsCollected.mergeTables();
//        }catch (RuntimeException e){
//            attributes.addAttribute("error", e.getMessage());
//            return "redirect:/calculate";
//        }
        Map<Employee,Tips> employeesMap=new HashMap<>();

        for(CollectTipsEmployeeDTO collectTipsEmployeeDTO: collectTipsEmployees){
            if(collectTipsEmployeeDTO.getTips() != null){
            Optional<Employee> optionalEmployee = employeeRepository.findById(collectTipsEmployeeDTO.getId());
            if (optionalEmployee.isEmpty()){throw new RuntimeException();}
            Employee employee =  optionalEmployee.get();
            employeesMap.put(employee, collectTipsEmployeeDTO.getTips());}
        }
//        Map<Employee,Tips> employeesMap= tipsCollected.getEmployeeTipsMap();
//
        TipsCollected tipsCollected =new TipsCollected(employeesMap);
        tipsCollectedRepository.save(tipsCollected);
//
////        In order to calculate the distribution for the current schema, we need to use the
////        Tipout object which handles the calculation for the current schema we need to pass in three pieces of information:
        long id = tipsCollected.getId();
////        1) The total amount in the tippool
        BigDecimal totalTippool = tipsCollectedRepository.findTotalTippool(id);
////        2) The different types of employees in the tip pool
        Integer totalEmployeeTipRates = tipsCollectedRepository.findTotalEmployeeTipoutPercentInTippool(id);
////        3) The Employees in the tip pool
        List<Employee> employeesInTipPool = new ArrayList<>(employeesMap.keySet());
//
//
        Tipout tipout = new Tipout();
////        We call the calculateTippoolDistribution from the Tipout class which will return a list of Employees with money they are owed
        Map<String, String> employeeShareofTipoolMap = tipout.calculateTippoolDistribution(totalEmployeeTipRates, totalTippool, employeesInTipPool);
        tipoutRepository.save(tipout);
//
//        model.addAttribute("title","Calculated Tips");
//        model.addAttribute("tippool", totalTippool);
//        model.addAttribute("payouts", employeeShareofTipoolMap);

        return ResponseEntity.ok(employeeShareofTipoolMap);
    }


//    @GetMapping
//    public String enterTips(Model model,
//                            @RequestParam(required = false) String error,
//                            HttpServletRequest request){
//        HttpSession session = request.getSession();
//        Employer employer = authenticationController.getEmployerFromSession(session);
//
//        //moneyHandlers tied to a particular Employer
//        ArrayList<MoneyHandler> moneyHandlers = (ArrayList<MoneyHandler>) moneyHandlerRepository.findAllByDeletedFalseAndEmployer_Id(employer.getId());
//        //nonMoneyHandlers tied to a particular Employer
//        ArrayList<NonMoneyHandler> nonMoneyHandlers = (ArrayList<NonMoneyHandler>) nonMoneyHandlerRepository.findAllByDeletedFalseAndEmployer_Id(employer.getId());
//        //TipsCollected handles gathering the money and the employees that are in the tippool
//        TipsCollected collectTips = new TipsCollected();
//
//        //Employees are paired with a Tip object, which is to be set in the form
//        for(MoneyHandler moneyHandler : moneyHandlers){
//            collectTips.setMoneyHandlerTipsMap(moneyHandler, new Tips());
//        }
//        for(NonMoneyHandler nonMoneyHandler : nonMoneyHandlers){
//            collectTips.setNonMoneyHandlerTipsMap(nonMoneyHandler, new Tips());
//        }
//
//        model.addAttribute("title","Calculate Tips");
//        model.addAttribute("moneyHandlers", moneyHandlers);
//        model.addAttribute("nonMoneyHandlers", nonMoneyHandlers);
//        model.addAttribute("collectTips", collectTips);
//        model.addAttribute("error", error);
//        return "calculate/index";
//    }
//
//    @PostMapping("report")
//    public String tipReport(Model model,
//                            @ModelAttribute TipsCollected tipsCollected,
//                            RedirectAttributes attributes){
//        //Employees with different roles are fed into the same table,
//        // any employees that are not included in the tippool are not included in the new table
//        // if no tips are declared an error is thrown
//        try {
//            tipsCollected.mergeTables();
//        }catch (RuntimeException e){
//            attributes.addAttribute("error", e.getMessage());
//            return "redirect:/calculate";
//        }
//        Map<Employee,Tips> employeesMap= tipsCollected.getEmployeeTipsMap();
//
//        tipsCollectedRepository.save(tipsCollected);
//
////        In order to calculate the distribution for the current schema, we need to use the
////        Tipout object which handles the calculation for the current schema we need to pass in three pieces of information:
//        long id = tipsCollected.getId();
////        1) The total amount in the tippool
//        BigDecimal totalTippool = tipsCollectedRepository.findTotalTippool(id);
////        2) The different types of employees in the tip pool
//        Integer totalEmployeeTipRates = tipsCollectedRepository.findTotalEmployeeTipoutPercentInTippool(id);
////        3) The Employees in the tip pool
//        List<Employee> employeesInTipPool = new ArrayList<>(employeesMap.keySet());
//
//
//        Tipout tipout = new Tipout();
////        We call the calculateTippoolDistribution from the Tipout class which will return a list of Employees with money they are owed
//        Map<Employee, Tips> employeeShareofTipoolMap = tipout.calculateTippoolDistribution(totalEmployeeTipRates, totalTippool, employeesInTipPool);
//
//        model.addAttribute("title","Calculated Tips");
//        model.addAttribute("tippool", totalTippool);
//        model.addAttribute("payouts", employeeShareofTipoolMap);
//
//        return "calculate/report";
//    }



}
