package com.example.Crewpr.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable =true)
    private String password;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Vacation> vacations;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<VacationHistory> vacationHistories;
    @Column(name = "role", nullable =true)
   private List<String> roles;

    public Employee(int id) {
        this.id = id;
    }

    @JsonCreator // This tells Jackson to use this constructor for deserialization
    public static Employee fromId(@JsonProperty("id") int id) {
        return new Employee(id);
    }


    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    public List<String> getRoles() {
        return roles;
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
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
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
