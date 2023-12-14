package com.tipout.Tipout.models.data;

import com.tipout.Tipout.models.Employee;
import com.tipout.Tipout.models.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
//    Deletes Employee from a TipCollected tables in order to be able to delete Employee
    @Transactional
    @Modifying
    @Query(value="DElETE FROM tipout.TipsCollected_employeeTipsMap Where employeeTipsMap_KEY = ?1", nativeQuery = true)
    void completelyDeleteEmployeeTipRecord(UUID id);

//    Deletes MoneyHandler from a TipCollected join table in order to be able to delete Employee
    @Transactional
    @Modifying
    @Query(value="DELETE FROM tipscollected_moneyhandlertipsmap Where moneyHandlerTipsMap_KEY = ?1", nativeQuery = true)
    void completelyDeleteMoneyhandlerTipRecord(Long id);

//    Deletes NonMoneyHandler from a TipCollected join table in order to be able to delete Employee
    @Transactional
    @Modifying
    @Query(value="DELETE FROM tipscollected_nonmoneyhandlertipsmap Where nonmoneyHandlerTipsMap_KEY = ?1", nativeQuery = true)
    void completelyDeleteNonMoneyhandlerTipRecord(UUID id);

//    Find all current active Employees that have the same Employer
    @Query(value="SELECT *, 0 AS clazz_ FROM tipout.EMPLOYEE Where deleted = false AND employer_id = ?1", nativeQuery = true)
    List<Employee> findCurrentEmployees(UUID id);
    @Query
    Optional<List<Employee>> findByEmployerAndDeletedFalse(Employer employer);

//    Find all archived Employees that have the same Employer
    @Query(value="SELECT *, 0 AS clazz_ FROM tipout.EMPLOYEE Where deleted = true AND employer_id = ?1", nativeQuery = true)
    List<Employee> findArhievedEmployees(UUID id);

    List<Employee> findAllByDeletedFalseAndEmployer_Id(UUID Employer_Id);

}
