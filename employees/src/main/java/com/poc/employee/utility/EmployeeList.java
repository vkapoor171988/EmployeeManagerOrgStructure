package com.poc.employee.utility;

import java.util.ArrayList;
import java.util.List;

import com.poc.employee.model.Employee;

// class having the instance of list 
// Which sets up during fetching Employee Data
// Also helpful in creating MAP DS Structure for Employee Organisation
public class EmployeeList {

	private static List<Employee> employeesList = null;

	// getting the instance of Employee List
	public static List<Employee> getEmployeesList() {
		return employeesList;
	}

	public static void setEmployeesList(List<Employee> empList) {
		employeesList = empList;
	}

	// if list is empty, instantiate the list and add employee in the same
	public static void addEmployee(Employee e) {
		if (employeesList == null) {
			employeesList = new ArrayList<Employee>();
		}
		employeesList.add(e);
	}
}
