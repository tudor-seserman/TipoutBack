package com.tipout.Tipout.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.math.BigInteger;

/*
Employee tip rates tied to Employer
Fields in this class should follow the naming convention [capital case role]Rate
The tip distribution template access fields using this naming convention
 */
@Entity
@Data
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class EmployeeRole{
    @ManyToOne
    @Expose
    private Employer employer;

    @Expose
    private String name;
    @Expose
    @NotNull
    @DecimalMax(value = "100", message = "Number must be less than 100")
    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
    private BigInteger rate;
    @Expose
    private boolean moneyHandler;


//    @NotNull
//    @DecimalMax(value = "100", message = "Number must be less than 100")
//    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
//    private BigInteger BartenderRate = BigInteger.valueOf(10);
//    @NotNull
//    @DecimalMax(value = "100", message = "Number must be less than 100")
//    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
//    private BigInteger BOHRate = BigInteger.valueOf(2);
//    @NotNull
//    @DecimalMax(value = "100", message = "Number must be less than 100")
//    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
//    private BigInteger BusserRate = BigInteger.valueOf(3);
//    @NotNull
//    @DecimalMax(value = "100", message = "Number must be less than 100")
//    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
//    private BigInteger ServerRate = BigInteger.valueOf(85);

    public EmployeeRole(Employer employer, String name, BigInteger rate, boolean moneyHandler) {
        this.employer = employer;
        this.name = name;
        this.rate = rate;
        this.moneyHandler = moneyHandler;
    }
//
//    public Employer getEmployer() {
//        return employer;
//    }
//
//    public void setEmployer(Employer employer) {
//        this.employer = employer;
//    }
//
//    public BigInteger getBartenderRate() {
//        return BartenderRate;
//    }
//
//    public void setBartenderRate(BigInteger bartenderRate) {
//        this.BartenderRate = bartenderRate;
//    }
//
//    public BigInteger getBOHRate() {
//        return BOHRate;
//    }
//
//    public void setBOHRate(BigInteger BOHRate) {
//        this.BOHRate = BOHRate;
//    }
//
//    public BigInteger getBusserRate() {
//        return BusserRate;
//    }
//
//    public void setBusserRate(BigInteger busserRate) {
//        this.BusserRate = busserRate;
//    }
//
//    public BigInteger getServerRate() {
//        return ServerRate;
//    }
//
//    public void setServerRate(BigInteger serverRate) {
//        this.ServerRate = serverRate;
//    }
////Method used to retrieve role tipout rate by taking in Employee
//    public BigInteger getTipoutByRole(Employee employee){
//        employee.getEmployeeRole();
//    }
//
//    public BigInteger getTipoutByRoleName(String roleName){
//        if(roleName.equals("Bartender")) {
//            return this.BartenderRate;
//        } else if (roleName.equals("BOH")) {
//            return this.BOHRate;
//        } else if (roleName.equals("Server")) {
//            return this.ServerRate;
//        } else if (roleName.equals("Busser")) {
//            return this.BusserRate;
//        }else{
////            throw some error message
//            throw new Error();
//        }
//    }
//
//    public EmployeeRole editTipRates(List<TipRatesDTO> ratesToEdit){
//        for(TipRatesDTO rateDTO : ratesToEdit){
//            try{
//            BigInteger currentTipRate = this.getTipoutByRoleName(rateDTO.roleName());
//            BigInteger submittedTipRate = rateDTO.tipRate();
//            if(currentTipRate != submittedTipRate){
//                this.editTipRateByRole(rateDTO);
//            }
//        }catch(Error e){
//            throw new RuntimeException("Role not found!");}
//        }
//
//        return this;
//    }
//
//    public void editTipRateByRole(TipRatesDTO tipRateDTOToEdit){
//        if(tipRateDTOToEdit.roleName().equals("Bartender")) {
//            this.setBartenderRate(tipRateDTOToEdit.tipRate());
//        } else if (tipRateDTOToEdit.roleName().equals("BOH")) {
//            this.setBOHRate(tipRateDTOToEdit.tipRate());
//        } else if (tipRateDTOToEdit.roleName().equals("Server")) {
//            this.setServerRate(tipRateDTOToEdit.tipRate());
//        } else if (tipRateDTOToEdit.roleName().equals("Busser")) {
//            this.setBusserRate(tipRateDTOToEdit.tipRate());
//        }else{
////            throw some error message
//            throw new Error();
//        }
//    }
//
//    public List<TipRatesDTO> getAllEmployerTipRates(){
//        return List.of(new TipRatesDTO("Bartender", this.BartenderRate),new TipRatesDTO("BOH", this.BOHRate), new TipRatesDTO("Busser", this.BusserRate), new TipRatesDTO("Server", this.ServerRate));
//    }
}
