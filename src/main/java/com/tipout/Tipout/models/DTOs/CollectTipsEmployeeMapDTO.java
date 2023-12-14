package com.tipout.Tipout.models.DTOs;

import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.Tips;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CollectTipsEmployeeMapDTO {
    List<CollectTipsEmployeeDTO> moneyHandlers = new ArrayList<>();
    List<CollectTipsEmployeeDTO> nonMoneyHandlers = new ArrayList<>();

}
