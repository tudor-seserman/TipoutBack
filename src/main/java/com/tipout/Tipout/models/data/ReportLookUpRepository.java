package com.tipout.Tipout.models.data;

import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.interfaces.Report;
import com.tipout.Tipout.models.utilities.ReportLookUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportLookUpRepository extends JpaRepository<ReportLookUp, UUID> {
    Optional<List<ReportLookUp>> findByEmployer(Employer employer);
}
