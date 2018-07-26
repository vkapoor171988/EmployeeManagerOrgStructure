package com.poc.employee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee implements Serializable {
	
	@JsonProperty("Employee ID")
	private int employeeID;

	@JsonProperty("City Name")
	private String cityName;

	@JsonProperty("Salary")
	private long salary;

	@JsonProperty("First Name")
	private String firstName;

	@JsonProperty("Second Name")
	private String secondName;

	@JsonProperty("Manager Emp Id")
	private int managerId;

	@JsonIgnore
	private List<Integer> subOrdinateEmployees = new ArrayList<Integer>();

	Employee(int employeeID, String cityName, long salary, String firstName, String secondName, int managerId) {
		this.employeeID = employeeID;
		this.cityName = cityName;
		this.salary = salary;
		this.firstName = firstName;
		this.secondName = secondName;
		this.managerId = managerId;
	}

	Employee() {

	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public List<Integer> getSubordinatesList() {
		return subOrdinateEmployees;
	}

	public void addEmployee(int id) {
		subOrdinateEmployees.add(id);
	}

	@Override
	public boolean equals(Object obj) {
		Employee e = (Employee) obj;
		if (this.cityName != null && e.cityName != null && this.cityName.equalsIgnoreCase(e.cityName)
				&& this.firstName != null && e.firstName != null && this.firstName.equalsIgnoreCase(e.firstName)
				&& this.secondName != null && e.secondName != null && this.secondName.equalsIgnoreCase(e.secondName)
				&& this.employeeID == e.employeeID && this.salary == e.salary) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		StringBuilder build = new StringBuilder();
		build.append(this.firstName).append(this.secondName).append(this.cityName);
		hashcode = build.toString().hashCode() + this.employeeID;
		return hashcode;
	}

	@Override
	public String toString() {
		return "First Name=" + this.firstName + "\nSecond Name=" + this.secondName + "\nEmployeeID=" + this.employeeID;
	}

}
