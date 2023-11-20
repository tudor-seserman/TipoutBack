package com.tipout.Tipout.models;

import com.tipout.Tipout.models.DTOs.TipRatesDTO;
import com.tipout.Tipout.models.Employees.BOH;
import com.tipout.Tipout.models.Employees.Bartender;
import com.tipout.Tipout.models.Employees.Busser;
import com.tipout.Tipout.models.Employees.Server;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.util.List;

/*
Employee tip rates tied to Employer
Fields in this class should follow the naming convention [capital case role]Rate
The tip distribution template access fields using this naming convention
 */
@Entity
public class EmployeeTipRates extends AbstractEntity{
    @OneToOne(mappedBy = "tipRates")
    private Employer employer;
    @NotNull
    @DecimalMax(value = "100", message = "Number must be less than 100")
    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
    private BigInteger BartenderRate = BigInteger.valueOf(10);
    @NotNull
    @DecimalMax(value = "100", message = "Number must be less than 100")
    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
    private BigInteger BOHRate = BigInteger.valueOf(2);
    @NotNull
    @DecimalMax(value = "100", message = "Number must be less than 100")
    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
    private BigInteger BusserRate = BigInteger.valueOf(3);
    @NotNull
    @DecimalMax(value = "100", message = "Number must be less than 100")
    @DecimalMin(value="0", message = "Number must be 0 or less than 100")
    private BigInteger ServerRate = BigInteger.valueOf(85);

    public EmployeeTipRates() {
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public BigInteger getBartenderRate() {
        return BartenderRate;
    }

    public void setBartenderRate(BigInteger bartenderRate) {
        this.BartenderRate = bartenderRate;
    }

    public BigInteger getBOHRate() {
        return BOHRate;
    }

    public void setBOHRate(BigInteger BOHRate) {
        this.BOHRate = BOHRate;
    }

    public BigInteger getBusserRate() {
        return BusserRate;
    }

    public void setBusserRate(BigInteger busserRate) {
        this.BusserRate = busserRate;
    }

    public BigInteger getServerRate() {
        return ServerRate;
    }

    public void setServerRate(BigInteger serverRate) {
        this.ServerRate = serverRate;
    }
//Method used to retrieve role tipout rate by taking in Employee
    public BigInteger getTipoutByRole(Employee employee){
        if(employee instanceof Bartender) {
            return getBartenderRate();
        } else if (employee instanceof BOH) {
            return getBOHRate();
        } else if (employee instanceof Server) {
            return getServerRate();
        } else if (employee instanceof Busser) {
            return getBusserRate();
        }else{
//            throw some error message
            return null;
        }
    }

    public BigInteger getTipoutByRoleName(String roleName){
        if(roleName.equals("Bartender")) {
            return this.BartenderRate;
        } else if (roleName.equals("BOH")) {
            return this.BOHRate;
        } else if (roleName.equals("Server")) {
            return this.ServerRate;
        } else if (roleName.equals("Busser")) {
            return this.BusserRate;
        }else{
//            throw some error message
            throw new Error();
        }
    }

    public EmployeeTipRates editTipRates(List<TipRatesDTO> ratesToEdit){
        for(TipRatesDTO rateDTO : ratesToEdit){
            try{
            BigInteger currentTipRate = this.getTipoutByRoleName(rateDTO.roleName());
            BigInteger submittedTipRate = rateDTO.tipRate();
            if(currentTipRate != submittedTipRate){
                this.editTipRateByRole(rateDTO);
            }
        }catch(Error e){
            throw new RuntimeException("Role not found!");}
        }

        return this;
    }

    public void editTipRateByRole(TipRatesDTO tipRateDTOToEdit){
        if(tipRateDTOToEdit.roleName().equals("Bartender")) {
            this.setBartenderRate(tipRateDTOToEdit.tipRate());
        } else if (tipRateDTOToEdit.roleName().equals("BOH")) {
            this.setBOHRate(tipRateDTOToEdit.tipRate());
        } else if (tipRateDTOToEdit.roleName().equals("Server")) {
            this.setServerRate(tipRateDTOToEdit.tipRate());
        } else if (tipRateDTOToEdit.roleName().equals("Busser")) {
            this.setBusserRate(tipRateDTOToEdit.tipRate());
        }else{
//            throw some error message
            throw new Error();
        }
    }

    public List<TipRatesDTO> getAllEmployerTipRates(){
        return List.of(new TipRatesDTO("Bartender", this.BartenderRate),new TipRatesDTO("BOH", this.BOHRate), new TipRatesDTO("Busser", this.BusserRate), new TipRatesDTO("Server", this.ServerRate));
    }
}
