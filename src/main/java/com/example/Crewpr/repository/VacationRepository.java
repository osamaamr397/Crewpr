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
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface VacationRepository extends CrudRepository<Vacation, Long> {
        
        List<VacationHistory> findVacationHistoryByEmployeeId(Long employeeId);
        Long VacationDays(Long employeeId);
        Long RemainingDays(Long employeeId);

        @Modifying
        @Transactional
        @Query("UPDATE Vacation v SET v.vacationDays = :vacationDays WHERE v.employee.id = :employeeId")
        void UpdateVacationDays(@Param("employeeId") Long employeeId, @Param("vacationDays") Long vacationDays);
        @Modifying
        @Transactional
        @Query("UPDATE Vacation v SET v.remainingDays = :remainingDays WHERE v.employee.id = :employeeId")
        void updateRemainingDays(@Param("employeeId") Long employeeId, @Param("remainingDays") Long remainingDays);
        @Query("SELECT v FROM Vacation v WHERE v.endDate = :endDate")
        Vacation findByEndDate(@Param("endDate") LocalDate endDate);

        @Query("SELECT SUM(v.remainingDays) FROM Vacation v WHERE v.employee.id = :employeeId")
        Long getRemainingDays(@Param("employeeId") Long employeeId);


}
