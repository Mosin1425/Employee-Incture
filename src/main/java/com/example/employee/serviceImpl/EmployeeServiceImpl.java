package com.example.employee.serviceImpl;

import com.example.employee.entity.Employee;
import com.example.employee.exceptionHandler.EmployeeNotFoundException;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    public EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

	@Override
	public List<Employee> allEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public String updateSalary(int id, int updatedSalary) {
		Employee em = employeeRepository.findById(id).get();
		em.setSalary(updatedSalary);
		employeeRepository.save(em);
        return "The salary has been updated to " + updatedSalary + " for employee " + em.getEmployeeName();
	}

	@Override
	public void deleteEmployee(int id) {
		if(employeeRepository.findById(id).isEmpty()) {
			throw new EmployeeNotFoundException("Requested Employee not found in database");
		}
		else {
			employeeRepository.deleteById(id);
		}}

	@Override
	public String updateDesignation(int id, String updatedDesignation) {
		Employee em = employeeRepository.findById(id).get();
		em.setDesignation(updatedDesignation);
		employeeRepository.save(em);
		return "The designation has been changed to " + updatedDesignation + " for employee " + em.getEmployeeName();
	}

	@Override
	public String updateAddress(int	 id, String updatedAddress) {
		Employee em = employeeRepository.findById(id).get();
		em.setAddress(updatedAddress);
		employeeRepository.save(em);
		return "The address has been changed to " + updatedAddress + " for employee " + em.getEmployeeName();
	}

	@Override
	public Employee findEmployeeById(int id) {
		return employeeRepository.findById(id).get();
	}
}	
//	@Override
//	public String deleteEmployee(int id) {
//	Below method is for handling exception using Reponse Entity
//	Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//	if(!optionalEmployee.isPresent()){
//		return "Not_Found";
//	}
//	else {
//		employeeRepository.deleteById(id);
//		return "Deleted";
//	}
//	employeeRepository.deleteById(id);
//	return "Employee with id " + id + " is deleted successfully....";
//	}