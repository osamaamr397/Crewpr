package com.example.Crewpr.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<String> roles;

}
