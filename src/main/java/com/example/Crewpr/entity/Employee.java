package com.example.Crewpr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;
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
    @Column(name = "name")
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable =true)
    private String password;
    @Column(name = "role", nullable = false)
    private String role;
    
    private Set<String> roles;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Vacation> vacations;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<VacationHistory> vacationHistories;

    public void setRoles(Set<String> roles) {

        this.roles = roles;
    
    }

}
