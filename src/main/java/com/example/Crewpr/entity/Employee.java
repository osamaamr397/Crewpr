package com.example.Crewpr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private String role;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Vacation> vacations;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<VacationHistory> vacationHistories;

}
