package com.tipout.Tipout.models.data;

import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.Employees.BOH;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BOHRepository extends CrudRepository<BOH,Long> {
}
