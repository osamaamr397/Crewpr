package com.example.Crewpr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.Crewpr.entity.Vacation;
import com.example.Crewpr.entity.VacationHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
@Repository
public interface VacationRepository extends CrudRepository<Vacation, Integer> {

        List<VacationHistory> findVacationHistoryByEmployeeId(int employeeId);

        @Query("SELECT v.vacationDays FROM Vacation v WHERE v.employee.id = :employeeId")
        Integer VacationDays(int employeeId);

        @Query("SELECT v.remainingDays FROM Vacation v WHERE v.employee.id = :employeeId")
        int RemainingDays(int employeeId);

        @Modifying
        @Transactional
        @Query("UPDATE Vacation v SET v.vacationDays = :vacationDays WHERE v.employee.id = :employeeId")
        void UpdateVacationDays(@Param("employeeId") int employeeId, @Param("vacationDays") int vacationDays);


        @Query("SELECT MAX(v.id) FROM Vacation v WHERE v.employee.id = :employeeId")
        int findMaxVacationIdByEmployeeId(@Param("employeeId") int employeeId);



        @Modifying
        @Transactional
       
        @Query("UPDATE Vacation v SET v.remainingDays = :remainingDays WHERE v.employee.id = :employeeId AND v.id = (SELECT MAX(v2.id) FROM Vacation v2 WHERE v2.employee.id = :employeeId)")
        void updateLastRemainingDays(@Param("employeeId") int employeeId, @Param("remainingDays") int remainingDays);

        @Query("SELECT v FROM Vacation v WHERE v.endDate = :endDate")
        Vacation findByEndDate(@Param("endDate") LocalDate endDate);

        @Query("SELECT v.remainingDays FROM Vacation v WHERE v.employee.id = :employeeId ORDER BY v.id DESC")
        Optional<Integer> getLastRemainingDays(@Param("employeeId") int employeeId);

        @Query("SELECT v.vacationDays FROM Vacation v WHERE v.employee.id = :employeeId")
        List<Integer> findVacationDays(int employeeId);

        @Modifying
        @Transactional
        @Query("UPDATE Vacation v SET v.remainingDays = :remainingDays WHERE v.employee.id = :employeeId AND v.id = (SELECT MAX(v2.id) FROM Vacation v2 WHERE v2.employee.id = :employeeId)")
        void  updateRemainingDays(@Param("employeeId") int employeeId, @Param("remainingDays") int remainingDays);

       // @Query("SELECT v FROM Vacation v WHERE v.employee.id = :employeeId")
        boolean existsByEmployeeId(int employeeId);
}
