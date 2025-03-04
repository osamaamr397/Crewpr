package com.example.Crewpr.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.example.Crewpr.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    Employee findByname(String username);
    Employee findByEmail(String email);

    Optional<Employee> findByName(String name);
}
