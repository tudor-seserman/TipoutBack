package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.tipout.Tipout.models.interfaces.CollectEmployeeInfoMap;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CollectTipsWeightedByRoleMapDTO implements CollectEmployeeInfoMap<CollectTipsEmployeeDTO> {
    List<CollectTipsEmployeeDTO> moneyHandlers = new ArrayList<>();
    List<CollectTipsEmployeeDTO> nonMoneyHandlers = new ArrayList<>();
    LocalDateTime dateTime;
    String shift;
}
