package com.example.Crewpr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.Crewpr.entity.Employee;
import com.example.Crewpr.repository.EmployeeRepository;

import java.util.HashSet;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken; // Add this import statement
import org.springframework.security.core.Authentication;
import java.util.Set;
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getCurrentEmployeeAndSave() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        String email=null;
        String password=null;
        String role = null; // Changed from Set<String> to String to store only one role
        if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
            username = principal.getName(); // Or any other method that gives you a unique identifier

            // Extract role
            AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();
            if (!accessToken.getRealmAccess().getRoles().isEmpty()) {
                role = accessToken.getRealmAccess().getRoles().iterator().next(); // Get the first role
            }
            email = accessToken.getEmail(); // Directly access the email property
            // Extract other properties as necessary
            password = accessToken.getOtherClaims().get("password").toString();

        }

        if (username != null) {
            Employee employee = employeeRepository.findByUsername(username);
            if (employee == null) {
                employee = new Employee();
                employee.setUsername(username);
                employee.setEmail(email);
                employee.setPassword(password);
                // Set other employee details as necessary
            }

            // Set role and other properties
            employee.setRole(role); // Assuming Employee has a String role field
            // employee.setEmail(email);
            // employee.setFirstName(firstName);
            // employee.setLastName(lastName);

            employeeRepository.save(employee);
            return employee;
        }
        return null; // Or handle
    }
   

}
