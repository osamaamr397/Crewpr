package com.example.Crewpr.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacationHistory {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
    
        @Column(name = "StartDate", nullable = false)
        private LocalDate startDate;
    
        @Column(name = "endDate",nullable = false)
        private LocalDate endDate;
        
        @ManyToOne
        @JoinColumn(name = "employee_id")
        private Employee employee;
    
        @ManyToOne
        @JoinColumn(name = "vacation_id")
         private Vacation vacation;

}
