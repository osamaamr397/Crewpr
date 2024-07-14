package com.example.Crewpr.service;

import com.example.Crewpr.security.JwtAuthConverterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Service;
import com.example.Crewpr.entity.Employee;
import com.example.Crewpr.repository.EmployeeRepository;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken; // Add this import statement

@Service
public class EmployeeService  {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

   

    @Autowired
    private EmployeeRepository employeeRepository;
    public Employee createOrUpdateEmployee(String email, String name, Set<String> roles) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            employee = new Employee();
            employee.setEmail(email);
        }
        employee.setName(name);
        // Assuming the Employee entity supports multiple roles and has a setter for them
        employee.setRoles(roles); // Make sure this method exists and correctly updates the entity's role field
        return employeeRepository.save(employee);
    }
    String roles=null;
    List<SimpleGrantedAuthority> resourceRoles = new ArrayList<>();
    

    public Employee getEmployee() {
        List<SimpleGrantedAuthority> resourceRoles = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        String email;
        String name;
        Set<String> roles = new HashSet<>(); // Initialize roles as an empty HashSet

         if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            
            email = jwt.getClaimAsString("email");
            name = jwt.getClaimAsString("given_name");
            // Assuming resourceRoles is initialized and populated correctly before this snippet
            List<String> roleStrings = resourceRoles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
            roles.addAll(roleStrings);
            System.out.println("Roles: " + roles);
        } else {
            throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
        }
        createOrUpdateEmployee(email, name, roles);
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            employee = new Employee();
            employee.setEmail(email);
            employee.setName(name);
        }
        employee.setRoles(roles);
            employeeRepository.save(employee);
        

        return employee;
    }
   

}
