package com.tipout.Tipout.models.data;

import com.tipout.Tipout.models.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, UUID> {
    Optional<EmployeeRole> findByNameAndEmployer_id(String name, UUID Employer);
}
