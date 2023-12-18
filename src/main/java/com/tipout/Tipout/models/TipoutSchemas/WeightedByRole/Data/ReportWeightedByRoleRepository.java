package com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.Data;

import com.tipout.Tipout.models.TipoutSchemas.WeightedByRole.ReportWeightedByRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportWeightedByRoleRepository extends JpaRepository<ReportWeightedByRole, UUID> {
}
