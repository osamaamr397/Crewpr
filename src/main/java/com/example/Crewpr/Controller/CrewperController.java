package com.example.Crewpr.Controller;
import com.example.Crewpr.entity.Employee;
import com.example.Crewpr.entity.Vacation;
import com.example.Crewpr.security.JwtAuthConverterProperties;
import com.example.Crewpr.service.EmployeeService;
import com.example.Crewpr.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class CrewperController {

    @Autowired
    private VacationService vacationService;

    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/SubmitVacation")

    public ResponseEntity<?> submitVacation(@RequestBody Vacation vacation){
        return ResponseEntity.ok(vacationService.submitVacation(vacation));

    }

    @Autowired
    CrewperController(VacationService vacationService){
        super();
        this.vacationService=vacationService;
    }
    @PostMapping("/ExtractEmployeeFromJWTAndSaveToDB")
    public ResponseEntity<?>  processUserDetails(JwtAuthenticationToken jwtAuthenticationToken){
        return ResponseEntity.ok(employeeService.processUserDetails(jwtAuthenticationToken));
    }


    @GetMapping("/Total-balance/{empId}")

    public ResponseEntity<?>TotalVacationDays(@Param("empId")int empId){
        return ResponseEntity.ok(vacationService.TotalVacationDays(empId));
    }

    @GetMapping("/Total-remain/{empId}")

    public ResponseEntity<?>RemainingDays(@Param("empId")int empId){
        return ResponseEntity.ok(vacationService.RemainingDays(empId));
    }

    @GetMapping("/vacation-history/{empId}")
     public ResponseEntity<?>findVacationHistoryByEmployeeId(@Param("empId")int empId){
        return ResponseEntity.ok(vacationService.findVacationHistoryByEmployeeId(empId));
    }
}