package com.poc.employee.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.employee.constants.Constants;
import com.poc.employee.dao.EmployeeDAO;
import com.poc.employee.model.Employee;
import com.poc.employee.utility.EmployeeList;
import com.poc.employee.utility.EmployeeSubordinateComparator;

/*
* Service methods for handling
* 1. load EmployeeData
* 2. Change Manager Of Employee
* 3. Find Maximum Subordinate Employee
* 4. Find Subordinates Salary
* 5. Print Organisation Structure
*
*/
public class EmployeeService {

	static Logger logger = Logger.getLogger(Constants.EMPLOYEESERVICE);
	private static HashMap<Integer, Employee> employeeData = null;
	static ObjectMapper mapper = null;
	static EmployeeDAO dao = new EmployeeDAO();

	private EmployeeService() {
	}

	public static HashMap<Integer, Employee> getEmployeeData() {
		if (employeeData == null) {
			employeeData = new HashMap<Integer, Employee>();
		}
		return employeeData;
	}

	/*
	 * loading the map and changing the manager ID of Employee removing the ID
	 * from Previous manager subordinate and adding to its new manager list
	 */
	public static HashMap changeEmployeeManager(String mData, int empId) throws Exception {
		logger.info("Entry:" + Constants.EMPLOYEESERVICE + ":changeEmployeeManager" + ":Manager ID=" + mData
				+ " Employee ID=" + empId);
		ObjectMapper mapper = new ObjectMapper();
		HashMap status = new HashMap();
		if (mData != null) {
			// load the data from in memory if List of Employee is null
			// and make a map ds out of it
			dao.loadOrganisationStructure();
			if (EmployeeList.getEmployeesList() == null) {
				status.put("key", Status.INTERNAL_SERVER_ERROR);
				status.put("value", Constants.NO_EMPLOYEE_DATA);
			} else {
				Employee e = EmployeeService.getEmployeeData().get(empId);
				if (e != null) {
					// get old manager Id and remove itself from his
					// subordinates
					Integer oldManagerId = e.getManagerId();
					HashMap map = mapper.readValue(mData, HashMap.class);
					// adds to the new manager
					Integer newManagerId = (Integer) map.get(Constants.MANAGER_ID);
					EmployeeService.getEmployeeData().get(empId).setManagerId(newManagerId);
					int indexOfObjcet = EmployeeService.getEmployeeData().get(oldManagerId).getSubordinatesList()
							.indexOf(empId);
					EmployeeService.getEmployeeData().get(oldManagerId).getSubordinatesList().remove(indexOfObjcet);
					EmployeeService.getEmployeeData().get(newManagerId).addEmployee(empId);
					List<Employee> employeeList = new ArrayList<Employee>(EmployeeService.getEmployeeData().values());
					EmployeeList.setEmployeesList(employeeList);
					EmployeeService.loadEmployeeData();
					status.put("key", Status.ACCEPTED);
					status.put("value", Constants.CHANGED);
				} else {
					status.put("key", Status.INTERNAL_SERVER_ERROR);
					status.put("value", Constants.NO_EMPLOYEE_DATA);
				}
			}
		} else {
			status.put("key", Status.INTERNAL_SERVER_ERROR);
			status.put("value", Constants.VALID_MANAGER);
		}
		logger.info("Exit:" + Constants.EMPLOYEESERVICE + ":changeEmployeeManager");
		return status;
	}

	/*
	 * Service method to fetch input JSON for employee data loaded in request
	 * and converting the data of list into MAP DS for fetching of EMployee as
	 * well as its sub- ordinates and dumping all the data in memory JSON file
	 * for duture purposes
	 * 
	 */
	public static void loadEmployeeData(String empData) throws Exception {
		logger.info("Entry:" + Constants.EMPLOYEESERVICE + ":" + "loadEmployeeData: input:" + empData);
		mapper = new ObjectMapper();
		// loading from JSON to Employee list
		List<Employee> list = mapper.readValue(empData,
				mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
		// setting the employee list to dump for memory
		EmployeeList.setEmployeesList(list);
		// setting up map ds organisation structure
		EmployeeService.loadEmployeeData();
		logger.info("Exit:" + Constants.EMPLOYEESERVICE + ":" + "loadEmployeeData");
	}

	/*
	 * utility method for loading Employee Data It Basically sets the singleton
	 * instance of MAP DS for Employee Organisation Structure Iterating and
	 * setting the map data and second iteration to populate the list of each
	 * employee subordinates
	 */
	public static void loadEmployeeData() throws Exception {
		logger.info("Entry:" + Constants.EMPLOYEESERVICE + ":" + "loadEmployeeData");
		Employee empdata = null;
		if (EmployeeList.getEmployeesList() != null && !EmployeeList.getEmployeesList().isEmpty()) {
			// iterating list to add elemnt in MAP
			Iterator<Employee> empIterator = EmployeeList.getEmployeesList().iterator();
			while (empIterator.hasNext()) {
				empdata = empIterator.next();
				// so that when we reload then it takes the latest update of
				// Employee List
				empdata.getSubordinatesList().clear();
				// put employee in Map as Emp ID Key
				EmployeeService.getEmployeeData().put(empdata.getEmployeeID(), empdata);
			}

			// iterating and fetching the manager of employee and adding it to
			// the Subordinate List of Employees in Map
			Iterator<Employee> toAddManager = EmployeeList.getEmployeesList().iterator();
			while (toAddManager.hasNext()) {
				empdata = toAddManager.next();
				if (empdata.getManagerId() != 0
						&& EmployeeService.getEmployeeData().containsKey(empdata.getManagerId())) {
					Employee manager = EmployeeService.getEmployeeData().get(empdata.getManagerId());
					manager.addEmployee(empdata.getEmployeeID());
					EmployeeService.getEmployeeData().put(empdata.getManagerId(), manager);
				}
			}
		}
		// dumping MAP DS in JSON
		if (EmployeeList.getEmployeesList() != null && !EmployeeList.getEmployeesList().isEmpty()) {
			dao.dumpFile();
		}
		logger.info("Exit:" + Constants.EMPLOYEESERVICE + ":" + "loadEmployeeData");
	}

	/*
	 * Sorting the MAP DS with custom comparator in Employee Subordinate List
	 * Descending order Get the Max Subordinates in Structure and add all
	 * employees in that list
	 */
	public static List<Employee> printEmployeeWithMaximumSubordinates() throws Exception {
		logger.info("Entry:" + Constants.EMPLOYEESERVICE + ":" + "printEmployeeWithMaximumSubordinates");
		dao.loadOrganisationStructure();
		List<Employee> listOfMaximumSubordinates = new ArrayList<Employee>();
		int maxSubOrdinates = 0;
		if (EmployeeService.getEmployeeData() != null && !EmployeeService.getEmployeeData().isEmpty()) {
			List<Employee> employees = new ArrayList<Employee>(EmployeeService.getEmployeeData().values());
			// using custom comparator
			Collections.sort(employees, new EmployeeSubordinateComparator());
			for (int i = 0; i < employees.size(); i++) {
				Employee emp = employees.get(i);
				if (i == 0) {
					maxSubOrdinates = emp.getSubordinatesList().size();
				}
				if (maxSubOrdinates == emp.getSubordinatesList().size()) {
					listOfMaximumSubordinates.add(emp);
				} else if (maxSubOrdinates > emp.getSubordinatesList().size()) {
					break;
				}
			}
		}
		logger.info("Exit:" + Constants.EMPLOYEESERVICE + ":" + "printEmployeeWithMaximumSubordinates");
		return listOfMaximumSubordinates;
	}

	/*
	 * Utility to print all the Max Subordinates employee
	 */
	public static String printMaximumSubordinates(List<Employee> employee) {
		StringBuilder result = new StringBuilder();
		if (employee != null && !employee.isEmpty()) {
			result.append("The Maximum Subordinates Employees are:");
			result.append("\n");
			for (Employee data : employee) {
				result.append(
						data.getFirstName() + " " + data.getSecondName() + " " + "Employee ID:" + data.getEmployeeID());
				result.append("\n");
				result.append("Number of Direct Subordinates it is having:" + data.getSubordinatesList().size());
				result.append("\nIDs of the subordinates are following:");
				for (Integer i : data.getSubordinatesList()) {
					result.append("\n ID:" + i);
				}
				result.append("\n\n");
			}
		} else {
			result.append("Employees in the organisation does not have any subordinates");
		}
		return result.toString();
	}

	/*
	 * Fetching the Employee Info based on Employee ID Now iterating through
	 * subordinate list and summing up to total
	 */
	public static String printTotalDirectSubordinatesSalary(int empId) throws Exception {
		logger.info("Entry:" + Constants.EMPLOYEESERVICE + ":" + "printTotalDirectSubordinatesSalary:Input=" + empId);
		StringBuilder result = new StringBuilder();
		dao.loadOrganisationStructure();
		if (EmployeeService.getEmployeeData() != null && !EmployeeService.getEmployeeData().isEmpty()) {
			Employee empData = EmployeeService.getEmployeeData().get(empId);
			if (empData != null) {
				long totalSalaryOfSubordinates = 0;
				Iterator<Integer> i = empData.getSubordinatesList().iterator();
				while (i.hasNext()) {
					Employee e = EmployeeService.getEmployeeData().get(i.next());
					result.append("Subordinate Employee ID:" + e.getEmployeeID() + "\n");
					result.append("Salary:" + e.getSalary() + "\n\n");
					totalSalaryOfSubordinates = totalSalaryOfSubordinates + e.getSalary();
				}
				if (totalSalaryOfSubordinates != 0) {
					result.append("Total salary of the Direct subordinates for Employee ID:" + empId + " is "
							+ totalSalaryOfSubordinates);
				} else {
					result.append("There are no subordinates of the Employee");
				}
			} else {
				result.append("Employee is not valid");
			}
		}
		logger.info("Exit:" + Constants.EMPLOYEESERVICE + ":" + "printTotalDirectSubordinatesSalary");
		return result.toString();
	}

	/*
	 * to print the hierarchy of Employee organisation from top to bottom
	 * Fetching the employees with manager ID 0 for the start and going bottom
	 * with the subordinates list
	 */
	public static String printOrganisationStructure() throws Exception {
		logger.info("Entry:" + Constants.EMPLOYEESERVICE + ":" + "printOrganisationStructure");
		dao.loadOrganisationStructure();
		StringBuilder result = new StringBuilder();
		if (EmployeeService.getEmployeeData() != null && !EmployeeService.getEmployeeData().isEmpty()) {
			List<Employee> managers = new ArrayList<Employee>();
			for (Integer i : EmployeeService.getEmployeeData().keySet()) {
				Employee e = EmployeeService.getEmployeeData().get(i);
				if (e.getManagerId() == 0) {
					managers.add(e);
				}
			}
			for (Employee manager : managers) {
				result.append("\n");
				result.append(printStructure(manager));
			}
		}
		logger.info("Exit:" + Constants.EMPLOYEESERVICE + ":" + "printOrganisationStructure");
		return result.toString();
	}

	/*
	 * Utility function to print organisation structure with method of recursion
	 */
	public static StringBuilder printStructure(Employee e) {
		logger.info("Entry:" + Constants.EMPLOYEESERVICE + ":" + "printStructure");
		StringBuilder result = new StringBuilder();
		List<Employee> listForEmployees = new ArrayList<Employee>();
		if (e.getSubordinatesList() != null && !e.getSubordinatesList().isEmpty()) {
			result.append("\nManager------->" + e.getFirstName() + " " + e.getSecondName());
			for (int i : e.getSubordinatesList()) {
				Employee data = EmployeeService.getEmployeeData().get(i);
				result.append("\nUnder Manager " + e.getFirstName() + " , Employee------>" + data.getFirstName());
				listForEmployees.add(data);
			}
			for (Employee sub : listForEmployees) {
				result.append("\n");
				result.append(printStructure(sub));
			}
		} else {
			result.append("\nEmployee------->" + e.getFirstName() + " " + e.getSecondName());
		}
		logger.info("Exit:" + Constants.EMPLOYEESERVICE + ":" + "printStructure");
		return result;
	}
}
