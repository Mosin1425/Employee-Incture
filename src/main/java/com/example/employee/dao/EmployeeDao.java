package com.example.employee.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.employee.dto.FilterDto;
import com.example.employee.entity.Employee;

public class EmployeeDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Employee> getEmployeesByFilterJdbc(List<FilterDto> filterDtoList){
		StringBuilder sql = new StringBuilder("SELECT * FROM EMPLOYEE_TABLE WHERE 1=1");
		
		for(FilterDto filter : filterDtoList) {
			sql.append(" AND ").append(filter.getColumnName()).append(" = ?");
		}
		
		List<Object> params = new ArrayList();
		for(FilterDto filter : filterDtoList) {
			params.add(filter.getColumnValue());
		}
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(Employee.class));
	}
}
