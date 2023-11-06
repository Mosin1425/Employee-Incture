package com.example.employee.entity;

import jakarta.persistence.*;

@Entity
@Table(name="employee_table")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    private String employeeName;
    private int salary;
    private String address;
    private String designation;
    private String primarySkill;
    
    @Transient
    private String gender;

    

    public Employee(String employeeName, int salary, String address, String designation,
			String primarySkill, String gender) {
		this.employeeName = employeeName;
		this.salary = salary;
		this.address = address;
		this.designation = designation;
		this.primarySkill = primarySkill;
		this.gender = gender;
	}

	public Employee() {
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPrimarySkill() {
        return primarySkill;
    }

    public void setPrimarySkill(String primarySkill) {
        this.primarySkill = primarySkill;
    }
    
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", salary=" + salary
				+ ", address=" + address + ", designation=" + designation + ", primarySkill=" + primarySkill + "]";
	}   
}
