package com.example.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.employee.dto.FilterDto;
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
	
	//Pagination and Sorting
	Page<Employee> paginationAndSorting(int page, int pageSize, String sortBy);
	
	//Filtering 
	List<Employee> getEmployeeByFilter(List<FilterDto> filterDtoList);
	
	//Filter By Jdbc Template
	List<Employee> getEmployeeByFilterByJdbc(List<FilterDto> filterDtoList);

	Page<Employee> getEmployeeWithFilterAndPagination(List<FilterDto> filterDtoList, Pageable pageable);
}
