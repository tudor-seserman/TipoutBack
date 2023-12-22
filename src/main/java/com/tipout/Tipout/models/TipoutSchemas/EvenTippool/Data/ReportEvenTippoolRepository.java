package com.tipout.Tipout.models.TipoutSchemas.EvenTippool.Data;

import com.tipout.Tipout.models.TipoutSchemas.EvenTippool.ReportEvenTippool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportEvenTippoolRepository extends JpaRepository<ReportEvenTippool, UUID> {
}
