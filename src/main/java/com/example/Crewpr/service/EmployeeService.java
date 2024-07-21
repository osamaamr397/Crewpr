package com.example.Crewpr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Service;
import com.example.Crewpr.entity.Employee;
import com.example.Crewpr.repository.EmployeeRepository;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.Jwt;


@Service
public class EmployeeService  {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

   

    @Autowired
    private EmployeeRepository employeeRepository;

    String roles=null;
    List<SimpleGrantedAuthority> resourceRoles = new ArrayList<>();


    

    public Employee processUserDetails_Employee(Object principal) {
        Employee employee = new Employee();
    
        String email;
        String name;
        List<String> roles = new ArrayList<>();

        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            email = jwt.getClaimAsString("email");
            name = jwt.getClaimAsString("given_name");
            List<String> roleStrings = resourceRoles.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            roles.addAll(roleStrings);
        } else {
            throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
        }
        return employee;
    }

    public Employee processUserDetails(JwtAuthenticationToken principal) {
        System.out.println(principal);
        String name = principal.getName();
        System.out.println(name);
        String email = principal.getToken().getClaim("email");

        // Check if an employee with the given unique name already exists
        Optional<Employee> existingEmployee = employeeRepository.findByName(name);
        if (existingEmployee.isPresent()) {
            // Handle the case where the employee already exists
            // For example, you could update the existing employee's details or simply return the existing employee
            Employee employee = existingEmployee.get();
            employee.setEmail(email);
            Map<String, Object> resourceAccessMap = (Map<String, Object>) principal.getToken().getClaim("resource_access");
            Map<String, Object> crewperMap = (Map<String, Object>) resourceAccessMap.get("crewper");
            List<String> roles = (List<String>) crewperMap.get("roles");
            employee.setRoles(roles);
            // Update other fields if necessary
            employeeRepository.save(employee);
            return employee;
        } else {
            // If there's no employee with the given unique name, proceed to create a new one
            Map<String, Object> resourceAccessMap = (Map<String, Object>) principal.getToken().getClaim("resource_access");
            Map<String, Object> crewperMap = (Map<String, Object>) resourceAccessMap.get("crewper");
            List<String> roles = (List<String>) crewperMap.get("roles");

            Employee employee = new Employee();
            employee.setEmail(email);
            employee.setName(name);
            employee.setRoles(roles);
            employeeRepository.save(employee);
            return employee;
        }
    }
}
