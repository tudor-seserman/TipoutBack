package com.tipout.Tipout.models;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/*
Class for handling collected tips
 */
@Entity
@Data
@NoArgsConstructor
public class Tips{
    @Id
    @GeneratedValue(generator = "identity")
    @Expose
    private long id;
    private BigDecimal tips=null;
    private String displayTips;

    public Tips(BigDecimal tips) {
        this.tips = tips;
    }
    public Tips(Double numberTips){this.tips = new BigDecimal(numberTips);}
    public Tips(Integer numberTips){this.tips = new BigDecimal(numberTips);}


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
