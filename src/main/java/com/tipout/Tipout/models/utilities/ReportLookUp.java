package com.tipout.Tipout.models.utilities;

import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.ReportEvenTippool;
import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.ReportWeightedByRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class ReportLookUp {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @OneToOne
    Employer employer;
    @OneToOne
    ReportEvenTippool reportEvenTippool;
    @OneToOne
    ReportWeightedByRole reportWeightedByRole;

    public ReportLookUp(Employer employer) {
        this.employer = employer;
    }
}
