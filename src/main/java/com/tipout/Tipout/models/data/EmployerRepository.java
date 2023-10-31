package com.tipout.Tipout.models.data;

import com.tipout.Tipout.models.Employer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends CrudRepository<Employer, Long> {
    Optional<Employer> findByUsername(String username);
}
