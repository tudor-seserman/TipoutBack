package com.tipout.Tipout.models.data;

import com.tipout.Tipout.models.Employees.Server;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends CrudRepository<Server,Long> {
}
