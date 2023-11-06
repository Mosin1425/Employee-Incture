package com.example.employee.serviceImpl;

import com.example.employee.dto.FilterDto;
import com.example.employee.entity.Employee;
import com.example.employee.exceptionHandler.EmployeeNotFoundException;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.specification.EmployeeSpecification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
		if(employeeRepository.findById(id).isPresent()) {
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

	@Override
	public Page<Employee> paginationAndSorting(int page, int pageSize, String sortBy) {
		try {
            //int offset = (page - 1) * pageSize;
            Sort sort = Sort.by(sortBy);
            Pageable pageable = PageRequest.of(page, pageSize, sort);

            return employeeRepository.findAll(pageable);
        }
        catch (Exception e){
            System.out.println("Error occured while pagination!!!");
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public List<Employee> getEmployeeByFilter(List<FilterDto> filterDtoList) {
		List<Employee> list;
		try {
			list = employeeRepository.findAll(EmployeeSpecification.columnEqual(filterDtoList));
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	@Override
	public List<Employee> getEmployeeByFilterByJdbc(List<FilterDto> filterDtoList){
		StringBuilder sql = new StringBuilder("select * from employee_table as t1\r\n"
				+ "join employee_table_2 as t2 on t1.employee_id = t2.employee_id\r\n"
				+ "where 1=1");
		
		for(FilterDto filter : filterDtoList) {
			sql.append(" AND ").append(filter.getColumnName()).append(" = ?");
		}
		
		List<Object> params = new ArrayList<Object>();
		for(FilterDto filter : filterDtoList) {
			params.add(filter.getColumnValue());
		}
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(Employee.class));
	}

	@Override
	public Page<Employee> getEmployeeWithFilterAndPagination(List<FilterDto> filterDtoList, Pageable pageable) {
		StringBuilder sql = new StringBuilder("select * from employee_table as t1\r\n"
				+ "join employee_table_2 as t2 on t1.employee_id = t2.emp_id\r\n"
				+ "where 1=1");
		
		for(FilterDto filter : filterDtoList) {
			sql.append(" AND ").append(filter.getColumnName()).append(" = ?");
		}
		
		List<Object> params = new ArrayList<Object>();
		for(FilterDto filter : filterDtoList) {
			params.add(filter.getColumnValue());
		}
		
		sql.append(" LIMIT ? OFFSET ?");
		
		//Pagination
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		params.add(pageable.getPageSize());
	    params.add(offset);
	    
	    List<Employee> employees = jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(Employee.class));
	    int totalRecords = getTotalRecords(sql.toString(), params.toArray());
	    
	    return new PageImpl<>(employees, pageable, totalRecords);
	}

	private int getTotalRecords(String sql, Object[] params) {
	    String countSql = "SELECT COUNT(*) FROM (" + sql + ") AS countQuery";
	    return jdbcTemplate.queryForObject(countSql, params, Integer.class);
	}

}
//	@Override
//	public String deleteEmployee(int id) {
//	Below method is for handling exception using Response Entity
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