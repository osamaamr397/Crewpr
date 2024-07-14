package com.example.Crewpr.Controller;
import com.example.Crewpr.entity.Employee;
import com.example.Crewpr.entity.Vacation;
import com.example.Crewpr.service.EmployeeService;
import com.example.Crewpr.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/test")
public class CrewperController {

    @Autowired
    private VacationService vacationService;
    @Autowired
    private EmployeeService employeeService;


    @Autowired
    CrewperController(VacationService vacationService){
        super();
        this.vacationService=vacationService;
    }
    @PostMapping("/ExtractEmployeeFromJWTAndSaveToDB")
    public ResponseEntity<?> ExtractEmployeeFromJWTAndSaveToDB(@RequestHeader Jwt jwt){
        return ResponseEntity.ok(employeeService.ExtractEmployeeFromJWTAndSaveToDB(jwt));
    }
    @PostMapping("/SubmitVacation")
     public ResponseEntity<?> submitVacation(@RequestBody Vacation vacation){
        return ResponseEntity.ok(vacationService.submitVacation(vacation));

    }

    @GetMapping("/Total-balance/{empId}")
    public ResponseEntity<?>TotalVacationDays(@Param("empId")Long empId){
        return ResponseEntity.ok(vacationService.TotalVacationDays(empId));
    }

    @GetMapping("/Total-remain/{empId}")
    public ResponseEntity<?>RemainingDays(@Param("empId")Long empId){
        return ResponseEntity.ok(vacationService.RemainingDays(empId));
    }

    @GetMapping("/vacation-history/{empId}")
     public ResponseEntity<?>findVacationHistoryByEmployeeId(@Param("empId")Long empId){
        return ResponseEntity.ok(vacationService.findVacationHistoryByEmployeeId(empId));
    }
}