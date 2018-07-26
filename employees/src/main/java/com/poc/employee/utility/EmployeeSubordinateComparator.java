package com.poc.employee.utility;

import java.util.Comparator;

import com.poc.employee.model.Employee;

/*
* Custom Comparator
* comparing 2 employees based on there subordinates
* for sorting in descending order
**/
public class EmployeeSubordinateComparator implements Comparator<Employee> {

	// method to compare 2 employees
	public int compare(Employee emp1, Employee emp2) {
		if (emp1 != null && emp2 != null && emp1.getSubordinatesList().size() < emp2.getSubordinatesList().size()) {
			return 1;
		} else if (emp1 != null && emp2 != null
				&& emp1.getSubordinatesList().size() > emp2.getSubordinatesList().size()) {
			return -1;
		} else {
			return 0;
		}
	}

}
