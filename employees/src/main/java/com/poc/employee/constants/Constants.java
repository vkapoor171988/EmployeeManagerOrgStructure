package com.poc.employee.constants;

public interface Constants {

	// constants of Web App
	public static final String ORGANISATION = "/organisation";
	public static final String RESTCONTROLLER = "RestController";
	public static final String EMPLOYEES = "/employees";
	public static final String APPLICATION_JSON = "application/json";
	public static final String UPLOAD_SUCCESS = "Uploaded Successfully";
	public static final String UPLOAD_FAIL = "Upload Failure";
	public static final String EMPLOYEE_UPLOAD_FAIL = "Employee Upload Failure";
	public static final String MANAGER_ID = "Manager ID";
	public static final String NO_EMPLOYEE_DATA = "Employee Data Not Found";
	public static final String ID = "employeeId";
	public static final String MANAGER_CHANGE_URL = "/changeManager/{employeeId}";
	public static final String ERROR_CHANGES = "Error while changing the manager";
	public static final String LOADEMPLOYEEDATA = "loadEmployeeData";
	public static final String EMPLOYEESERVICE = "EmployeeService";
	public static final String EMPLOYEERECORDS = "//EmployeeRecords.json";
	public static final String EMPTY = "";
	public static final String MANAGER_CHANGE = "changeEmployeeManager";
	public static final String CHANGED = "Changed Successfully";
	public static final String VALID_MANAGER = "Please enter valid Manager ID";
	public static final String SUBORDINATE_URL = "/employee/maxSubordinates";
	public static final String DATA_ERROR = "Data Error";
	public static final String MAX_SUBORDINATE_METHOD = "getMaximumEmployeeSubordinate";
	public static final String SALARY_URL = "/employee/salarySubordinate/{employeeId}";
	public static final String SALARY_METHOD = "getTotalSubordinatesSalary";
	public static final String STRUCTURE_METHOD = "printOrganisationStructure";
	public static final String STRUCTURE_URL = "/getOrganisationStructure";

}
