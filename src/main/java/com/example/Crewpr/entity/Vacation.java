package com.example.Crewpr.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vacation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;
    
    @ManyToOne
    @JoinColumn(name = "employee_id") // Ensure this matches the column name in your database
    private Employee employee;

    @Column(name = "vacationDays", nullable = false)
    private int vacationDays ;
    
    @Column(name = "remainingDays", nullable = false)
    private int remainingDays =30;

    @Column(name = "days_used")
    private int daysUsed;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL)
    private List<VacationHistory> vacationHistories;


    
}