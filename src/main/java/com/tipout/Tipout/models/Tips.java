package com.tipout.Tipout.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/*
Class for handling collected tips
 */
@Entity
public class Tips extends AbstractEntity{
    private BigDecimal tips=null;
    private String displayTips;

    public Tips() {
    }

    public Tips(BigDecimal tips) {
        this.tips = tips;
    }
    public Tips(Double numberTips){this.tips = new BigDecimal(numberTips);}
    public Tips(Integer numberTips){this.tips = new BigDecimal(numberTips);}

    public BigDecimal getTips() {
        return tips;
    }

    public void setTips(BigDecimal tips) {
        this.tips = tips;
    }


//Formats Tips to dollar amount rounded to two decimal places
    public String getDisplayTips() {
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
        usdCostFormat.setMinimumFractionDigits( 2 );
        usdCostFormat.setMaximumFractionDigits( 2 );
        return usdCostFormat.format(tips);
    }

    @Override
    public String toString() {
        return (tips == null)? "Not in tippool" :tips.toString();
    }
}
