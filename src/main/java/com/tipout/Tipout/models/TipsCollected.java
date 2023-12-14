package com.tipout.Tipout.models;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/*
This class is used to aggregate collected tips from employees as well
as the Employees that will receive money from the tip pool.
 */
@Entity
@Data
@NoArgsConstructor
public class TipsCollected{

    @Id
    @GeneratedValue
    @Expose
    private UUID id;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Map<Employee, Tips> employeeTipsMap = new LinkedHashMap<>();




//    This method filters through the other two maps, excluding anyone who does not have a set value.
//    If no money is added to the tippool, an error is thrown.
    public void eliminateEmployeesNotInTippool(){
//        this.moneyHandlerTipsMap.forEach((k,v)->{ if(v.getTips() !=null)this.employeeTipsMap.put(k,v);});
//
//  If no money is collected an error is thrown.
//  Check to see if there is a nonzero value, if there is one, tables are merged and the method ends.
//  If there are no dollar amounts an error is thrown.
        for(Tips tips : employeeTipsMap.values()){
           if((tips.getTips()).compareTo(BigDecimal.ZERO) >= 0){
               this.employeeTipsMap.forEach((k,v)->{if(v.getTips() == null)this.employeeTipsMap.remove(k);});
           return;
           }
//             If there are no Employees that collect tips an error is thrown
            if(employeeTipsMap.isEmpty()){throw new RuntimeException("No tips have been declared.");}
        }
   throw new RuntimeException("No tips have been declared.");
    }
}
