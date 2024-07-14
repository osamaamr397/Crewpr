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
    
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Vacation> vacations;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<VacationHistory> vacationHistories;

   private Set<String> roles;
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    public Set<String> getRoles() {
        return roles;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setVacations(List<Vacation> vacations) {
        this.vacations = vacations;
    }
    public List<Vacation> getVacations() {
        return vacations;
    }
    public void setVacationHistories(List<VacationHistory> vacationHistories) {
        this.vacationHistories = vacationHistories;
    }
    public List<VacationHistory> getVacationHistories() {
        return vacationHistories;
    }

}
