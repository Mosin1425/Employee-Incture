package com.example.employee.dto;

public class FilterDto {
	private String columnName;
	private Object columnValue;
	
	public FilterDto(String columnName, Object columnValue) {
		super();
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	public FilterDto() {
		super();
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}
	
	
	
}
