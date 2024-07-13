package com.example.Crewpr.service;

import com.example.Crewpr.entity.Vacation;
import com.example.Crewpr.entity.VacationHistory;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VacationService {
    Vacation submitVacation(Vacation vacation);

    Long RemainingDays(Long employeeId);



    Long TotalVacationDays(Long employeeId);




    @Query("SELECT vh FROM VacationHistory vh WHERE vh.employee.id = :employeeId")
    List<VacationHistory> findVacationHistoryByEmployeeId(Long employeeId);


}
