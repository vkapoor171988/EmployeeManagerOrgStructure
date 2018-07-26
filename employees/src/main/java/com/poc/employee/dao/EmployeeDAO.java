package com.poc.employee.dao;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.employee.constants.Constants;
import com.poc.employee.model.Employee;
import com.poc.employee.service.EmployeeService;
import com.poc.employee.utility.EmployeeList;

// class used for setting up the JSON for in memory Employee Data
// also for fetching the in memory data 
public class EmployeeDAO {

	ObjectMapper mapper = null;

	// dumping file in the specified path from List<Employee> records
	public void dumpFile() throws Exception {
		mapper = new ObjectMapper();
		mapper.writeValue(new File(new File(Constants.EMPTY).getAbsolutePath() + Constants.EMPLOYEERECORDS),
				EmployeeList.getEmployeesList());
	}

	// fetching the data from file in a List<Employee> variable
	public List<Employee> getEmployeeListFromFile() throws Exception {
		List<Employee> list = null;
		mapper = new ObjectMapper();
		list = mapper.readValue(new File(new File(Constants.EMPTY).getAbsolutePath() + Constants.EMPLOYEERECORDS),
				mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	// load MAP ds based on the list
	public void loadOrganisationStructure() throws Exception {
		if (EmployeeList.getEmployeesList() == null || EmployeeList.getEmployeesList().isEmpty()) {
			EmployeeList.setEmployeesList(getEmployeeListFromFile());
			EmployeeService.loadEmployeeData();
		}
	}
}
