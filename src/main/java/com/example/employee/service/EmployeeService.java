package com.example.employee.service;

import java.util.List;

import com.example.employee.entity.Employee;
public interface EmployeeService {
	//Get All Employees
	List<Employee> allEmployees();
	
    //Add Employee
    Employee addEmployee(Employee employee);
    
    //Delete Employee
    void deleteEmployee(int id);
    
    //Update Salary
    String updateSalary(int id, int salary);
    
    //Update Designation
    String updateDesignation(int id, String updatedDesignation);
    
    //Update Address
    String updateAddress(int id, String updatedAddress);

    //Find by id
	Employee findEmployeeById(int id);
}
