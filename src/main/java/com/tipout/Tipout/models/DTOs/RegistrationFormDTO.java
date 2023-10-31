package com.tipout.Tipout.models.DTOs;

import lombok.Data;

//Wrapper class to facilitate Employer creation
@Data
public class RegistrationFormDTO extends LoginFormDTO {
    private String verifyPassword;
    private String businessName;

}
