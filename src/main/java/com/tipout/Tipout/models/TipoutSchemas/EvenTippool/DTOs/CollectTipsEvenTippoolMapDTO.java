package com.tipout.Tipout.models.TipoutSchemas.EvenTippool.DTOs;

import com.tipout.Tipout.models.interfaces.CollectEmployeeInfoMap;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CollectTipsEvenTippoolMapDTO implements CollectEmployeeInfoMap<CollectEvenTippoolTipsEmployeeDTO> {
    List<CollectEvenTippoolTipsEmployeeDTO> moneyHandlers = new ArrayList<>();
    List<CollectEvenTippoolTipsEmployeeDTO> nonMoneyHandlers = new ArrayList<>();
    LocalDateTime dateTime;
    String shift;
}
