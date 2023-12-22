package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.Data;

import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.ReportWeightedByRoleEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportWeightedByRoleEntryRepository extends JpaRepository<ReportWeightedByRoleEntry, UUID> {
}
