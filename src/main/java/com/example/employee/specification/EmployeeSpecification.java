package com.example.employee.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.employee.dto.FilterDto;
import com.example.employee.entity.Employee;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EmployeeSpecification {
	public static Specification<Employee> columnEqual(List<FilterDto> filterDtoList){
		return new Specification<Employee>() {
			private static final long serialVersionUid = 1L;
			
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				filterDtoList.forEach(filter -> {
					Predicate predicate = criteriaBuilder.equal(root.get(filter.getColumnName()), filter.getColumnValue());
					predicates.add(predicate);
				});
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
}
}
