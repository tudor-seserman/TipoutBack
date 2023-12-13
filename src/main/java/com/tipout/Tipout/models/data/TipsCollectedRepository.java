package com.tipout.Tipout.models.data;
//
//import com.tipout.Tipout.models.TipsCollected;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Repository
//public interface TipsCollectedRepository extends CrudRepository<TipsCollected,Long> {
////Query to  calculate to money in the tippool for submission
//    @Query(value="SELECT Sum(Tips.tips)\n" +
//            "FROM TipsCollected_employeeTipsMap\n" +
//            "Join Tips on Tips.id=employeeTipsMap_id\n" +
//            "Where TipsCollected_employeeTipsMap.TipsCollected_id = ?1",
//                nativeQuery = true)
//    BigDecimal findTotalTippool(long id);
//
////Query returns all the Employee tipout rates
//    @Query(value="Select SUM(Employee.percentOfTipout) \n" +
//            "From TipsCollected_employeeTipsMap\n" +
//            "Join Employee on Employee.id=employeeTipsMap_KEY\n" +
//            "Where TipsCollected_employeeTipsMap.TipsCollected_id = ?1", nativeQuery = true)
//    Integer findTotalEmployeeTipoutPercentInTippool(long id);
//
////    Query returns all the Employees included in the tip pool
//    @Query(value="Select Employee.id \n" +
//            "From TipsCollected_employeeTipsMap\n" +
//            "Join Employee on Employee.id=employeeTipsMap_KEY\n" +
//            "Where TipsCollected_employeeTipsMap.TipsCollected_id = ?1", nativeQuery = true)
//    List<Integer> findEmployeesByIdInTippool(Long id);
//
//}
