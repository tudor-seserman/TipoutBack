package com.tipout.Tipout.models.data;

import com.tipout.Tipout.models.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long>{
//    Deletes Employee from a TipCollected tables in order to be able to delete Employee
    @Transactional
    @Modifying
    @Query(value="DElETE FROM tipout.TipsCollected_employeeTipsMap Where employeeTipsMap_KEY = ?1", nativeQuery = true)
    void completelyDeleteEmployeeTipRecord(Long id);

//    Deletes MoneyHandler from a TipCollected join table in order to be able to delete Employee
    @Transactional
    @Modifying
    @Query(value="DELETE FROM tipscollected_moneyhandlertipsmap Where moneyHandlerTipsMap_KEY = ?1", nativeQuery = true)
    void completelyDeleteMoneyhandlerTipRecord(Long id);

//    Deletes NonMoneyHandler from a TipCollected join table in order to be able to delete Employee
    @Transactional
    @Modifying
    @Query(value="DELETE FROM tipscollected_nonmoneyhandlertipsmap Where nonmoneyHandlerTipsMap_KEY = ?1", nativeQuery = true)
    void completelyDeleteNonMoneyhandlerTipRecord(long id);

//    Find all current active Employees that have the same Employer
    @Query(value="SELECT *, 0 AS clazz_ FROM tipout.EMPLOYEE Where deleted = false AND employer_id = ?1", nativeQuery = true)
    List<Employee> findCurrentEmployees(long id);

//    Find all archived Employees that have the same Employer
    @Query(value="SELECT *, 0 AS clazz_ FROM tipout.EMPLOYEE Where deleted = true AND employer_id = ?1", nativeQuery = true)
    List<Employee> findArhievedEmployees(long id);

}
