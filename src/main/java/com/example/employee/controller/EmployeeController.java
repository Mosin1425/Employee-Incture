package com.example.employee.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.employee.dto.FilterDto;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	public EmployeeService employeeService;
	
    public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

    @GetMapping("/test")
    public String homePage(){
        return "This is a test url";
    }
    
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getEmployees(){
    	return ResponseEntity.ok(employeeService.allEmployees());
    }
    
    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
    	return ResponseEntity.ok(employeeService.addEmployee(employee));
    }
    
    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
    	return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @PutMapping("/updateSalary/{id}/{newSalary}")
    public ResponseEntity<String> updateSalary(@PathVariable int id,@PathVariable int newSalary){
    	employeeService.updateSalary(id,newSalary);
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("NameAB", "ValueAB");
    	
    	return ResponseEntity
    			.status(HttpStatus.OK)
    			.headers(headers)
    			.body("Salary updated successfully");
    }
    
    @PutMapping("/updateDesignation/{id}/{newDesignation}")
    public ResponseEntity<String> updateDesignation(@PathVariable int id, @PathVariable String newDesignation) {
    	return ResponseEntity.ok(employeeService.updateDesignation(id, newDesignation));
    }
    
    @PutMapping("/updateAddress/{id}/{newAddress}")
    public ResponseEntity<String> updateAddress(@PathVariable int id, @PathVariable String newAddress) {
    	return ResponseEntity.ok(employeeService.updateAddress(id, newAddress));
    }
    
    @PatchMapping("/updateName/{id}")
    public String updateName(@PathVariable int id, @RequestBody Map<String,String> updateName) {
    	Employee existingEmployee = employeeService.findEmployeeById(id);
    	
    	if(existingEmployee==null) {
    		return "Employee with id " + id + " does not exist";
    	}
    	
    	existingEmployee.setEmployeeName(updateName.get("employeeName"));
    	employeeService.addEmployee(existingEmployee);
    	
		return "Employee name updated successfully...";
    }
    
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
    	employeeService.deleteEmployee(id);
    	return ResponseEntity
    			.status(HttpStatus.OK)
    			.body("Employee data deleted successfully");
    }
    
    //Pagination and Sorting
	@GetMapping("/pagination")
	public List<Employee> findAllEmployees(@RequestParam(defaultValue = "1") int pageNumber,
			@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "employeeName") String sortBy) {
		
		Page<Employee> employeePage = employeeService.paginationAndSorting(pageNumber, pageSize, sortBy);
		return employeePage.getContent();
	}
	
	//Filter API
	@PostMapping("/filter")
	public ResponseEntity<List<Employee>> getEmployeeByFilter(@RequestBody List<FilterDto> filterDtoList){
		return ResponseEntity.ok().body(employeeService.getEmployeeByFilter(filterDtoList));
	}
	
	// Filter API with JDBC
	@PostMapping("/filterByJdbc")
	public ResponseEntity<List<Employee>> getEmployeeByFilterByJdbc(@RequestBody List<FilterDto> filterDtoList){
		return ResponseEntity.ok().body(employeeService.getEmployeeByFilterByJdbc(filterDtoList));
	}
	
	// Filter API with pagination 
	@PostMapping("/filterAndPagination")
	public ResponseEntity<List<Employee>> getEmployeeWithFilterAndPagination(@RequestBody List<FilterDto> filterDtoList, 
			@RequestParam int pageNumber, @RequestParam int pageSize){
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return ResponseEntity.ok().body(employeeService.getEmployeeWithFilterAndPagination(filterDtoList, pageable).getContent());
	}
    //Below method is handling exceptions using ResponseEntity
//  @DeleteMapping("/deleteEmployee/{id}")
//  public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable int id) {
//  	String result = employeeService.deleteEmployee(id);
//  	
//  	if("Deleted".equals(result)) {
//  		return ResponseEntity.status(HttpStatus.OK).build();
//  	}
//  	else if("Not_Found".equals(result)) {
//  		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//  	}
//  	else {
//  		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//  	}
//  }
}
