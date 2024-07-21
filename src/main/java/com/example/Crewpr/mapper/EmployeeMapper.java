package com.example.Crewpr.mapper;
import com.example.Crewpr.dto.EmployeeDto;
import com.example.Crewpr.entity.Employee;
public class EmployeeMapper {
    public static EmployeeDto toEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setRoles(employee.getRoles());
        return employeeDto;
    }
    public static Employee toEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setRoles(employeeDto.getRoles());
        return employee;
    }
   
}
