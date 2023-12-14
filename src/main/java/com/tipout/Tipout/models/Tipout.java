package com.tipout.Tipout.models;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
/*
This class handles calculations for different tip schemas it will be paired with methods from TipCollector
 */
@Entity
@Data
@NoArgsConstructor
public class Tipout{

    @Id
    @GeneratedValue
    @Expose
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL)
    private final Map<Employee, Tips> tipPoolDistribution = new HashMap<>();
    @ElementCollection
//    @CollectionTable(name = "order_item_mapping",
//            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "employee")
    @Column(name = "employee_tips")
    private Map<String, String> displayMap = new HashMap<>();


/*
This is the first tipout schema I have instantiated.
Employee rates are best understood as the number of employee shares of the tippool at calculation:
employee tipout rate / all employee rates in tipout pool.
The more employees in the tip pool the more those shares are diluted.

Employees payout is expressed as their fractional share,
 employee tipout rate / all employee rates in pool tipout ,multiplied by the total money in the tip pool.

Mathematically employee distribution is described as their rate divided by the total rates in this tippool multiplied by the money in the tippool.

In order to calculate the tip out:
1. All the employee rates are in the tip pool are added together to determine the
the the size of the tippool.
2. The money that is to be distributed is divided by the total rates in the tippool to determine the monetary value of each share.
3. Individual tipouts are calculated by multiplying the number of shares an employee has of the tip pool by the value of each of those shares.
4. This information is pushed to the tipPoolDistribution Map.
 */
    public Map<String, String> calculateWeightedTippoolByRole(Integer totalTippoolRates, BigDecimal totalTippool, List<Employee> employeesInTippool){

//        This method receives three pieces of information from the TipoutController, total tips, total rates in tip pool, employees that need to be tipped out

//        We divide the total money by all the rates of the employees in the tip pool
//        shareOfTippool is the monatery value of each employee share of the tippool
        BigDecimal shareOfTippool = totalTippool.divide(BigDecimal.valueOf(totalTippoolRates), 4, RoundingMode.HALF_UP);


        for(Employee employeeInTippool: employeesInTippool){
            BigDecimal portionOfTippool = shareOfTippool.multiply(new BigDecimal(employeeInTippool.getEmployeeRoles().g));
            Tips tip = new Tips(portionOfTippool);
            tipPoolDistribution.put(employeeInTippool, tip);
            displayMap.put(employeeInTippool.toString(), tip.getDisplayTips());
        }
        return displayMap;
    }

//    public Map<String, String> calculateEvenTippool(BigDecimal totalTippool, List<Employee> employeesInTippool){
//
////        This method receives three pieces of information from the TipoutController, total tips, total rates in tip pool, employees that need to be tipped out
//
////        We divide the total money by all the rates of the employees in the tip pool
////        shareOfTippool is the monatery value of each employee share of the tippool
//        BigDecimal shareOfTippool = totalTippool.divide(BigDecimal.valueOf(employeesInTippool.size()), 4, RoundingMode.HALF_UP);
//        System.out.println(shareOfTippool);
//
//
//        for(Employee employeeInTippool: employeesInTippool){
//            Tips tip = new Tips(shareOfTippool);
//            tipPoolDistribution.put(employeeInTippool, tip);
//            displayMap.put(employeeInTippool.toString(), tip.getDisplayTips());
//        }
//        return displayMap;
//    }

}
