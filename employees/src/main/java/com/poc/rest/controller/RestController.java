package com.poc.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import com.poc.employee.constants.Constants;
import com.poc.employee.model.Employee;
import com.poc.employee.service.EmployeeService;

@Path(Constants.ORGANISATION)
public class RestController {
	// for using java util logging
	Logger logger = Logger.getLogger(Constants.RESTCONTROLLER);

	/*
	 * rest post service to load employee data from the json empData is the json
	 * String coming from input as a request It reads the JSON and maps to the
	 * List<Employee> object setting MAP DS for the organisation structure
	 * having Employee and list of subordinates as the value and saving the json
	 * in the memory for future purposes local server with port 7001 will Expose
	 * URL as
	 * http://localhost:7001/employees-v1/resources/organisation/employees
	 * 
	 */
	@Path(Constants.EMPLOYEES)
	@POST
	@Consumes(Constants.APPLICATION_JSON)
	public Response loadEmployeeData(String empData) {
		logger.info("Entry:" + Constants.RESTCONTROLLER + ":" + Constants.LOADEMPLOYEEDATA);
		try {
			if (empData != null && empData.isEmpty()) {
				EmployeeService.loadEmployeeData(empData);
				// if the employee data is created successfully
				logger.info("Exit:" + Constants.RESTCONTROLLER + ":" + Constants.LOADEMPLOYEEDATA);
				return Response.status(Status.CREATED).entity(Constants.UPLOAD_SUCCESS).build();
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Constants.EMPLOYEE_UPLOAD_FAIL).build();
			}
		} catch (Exception e) {
			// Exception
			logger.info("Exception:" + Constants.RESTCONTROLLER + ":" + Constants.LOADEMPLOYEEDATA + ":Exception=" + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Constants.UPLOAD_FAIL).build();
		}
	}

	/*
	 * rest post service to change the manager ID of the employee Emp ID will be
	 * part of input and manager ID it will read from request JSON It Basically
	 * apply the changes on the MAP DS stored for organisation and dumps the new
	 * Employee JSON in Memory Will Expose Rest URL as
	 * http://localhost:7001/employees-0.0.1-SNAPSHOT/resources/organisation/
	 * changeManager/{employeeId}
	 * 
	 */
	@Path(Constants.MANAGER_CHANGE_URL)
	@POST
	@Consumes(Constants.APPLICATION_JSON)
	public Response changeEmployeeManager(String mData, @PathParam(Constants.ID) int empId) {
		logger.info("Entry:" + Constants.RESTCONTROLLER + ":" + Constants.MANAGER_CHANGE);
		try {
			logger.info("Exit:" + Constants.RESTCONTROLLER + ":" + Constants.MANAGER_CHANGE);
			HashMap statusMap = EmployeeService.changeEmployeeManager(mData, empId);
			return Response.status((StatusType) statusMap.get("key")).entity((String) statusMap.get("value")).build();
		} catch (Exception e) {
			logger.info("Exception:" + Constants.RESTCONTROLLER + ":" + Constants.MANAGER_CHANGE + "Exception=" + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Constants.ERROR_CHANGES).build();
		}
	}

	/*
	 * rest post service to calculate Employees with Maximum Subordinates below
	 * them Firstly, load the MAP DS, Sort it as per custom comparator and then
	 * Print with Descending order of MAx Subordinates in the structure
	 * http://localhost:7001/employees-0.0.1-SNAPSHOT/resources/organisation/
	 * employee/maxSubordinates
	 * 
	 */
	@Path(Constants.SUBORDINATE_URL)
	@GET
	public Response getMaximumEmployeeSubordinate() {
		logger.info("Entry:" + Constants.RESTCONTROLLER + ":" + Constants.MAX_SUBORDINATE_METHOD);
		try {
			List<Employee> listwithMax = EmployeeService.printEmployeeWithMaximumSubordinates();
			String result = EmployeeService.printMaximumSubordinates(listwithMax);
			logger.info("Exit:" + Constants.RESTCONTROLLER + ":" + Constants.MAX_SUBORDINATE_METHOD);
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			logger.info("Exception:" + Constants.RESTCONTROLLER + ":" + Constants.MAX_SUBORDINATE_METHOD + " Exception="
					+ e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Constants.DATA_ERROR).build();
		}
	}

	/*
	 * rest post service to find the salaries of direct total subordinates and
	 * add them altogether to provide total
	 * 
	 * Rest APiExposed is
	 * http://localhost:7001/employees-0.0.1-SNAPSHOT/resources/organisation/
	 * employee/salarySubordinate/{employeeid}
	 * 
	 */
	@Path(Constants.SALARY_URL)
	@GET
	public Response getTotalSubordinatesSalary(@PathParam(Constants.ID) int empId) {
		logger.info("Entry:" + Constants.RESTCONTROLLER + ":" + Constants.SALARY_METHOD);
		try {
			if (empId != 0) {
				String result = EmployeeService.printTotalDirectSubordinatesSalary(empId);
				logger.info("Exit:" + Constants.RESTCONTROLLER + ":" + Constants.SALARY_METHOD);
				return Response.status(Status.OK).entity(result).build();
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Constants.NO_EMPLOYEE_DATA).build();
			}
		} catch (Exception e) {
			logger.info("Exception:" + Constants.RESTCONTROLLER + ":" + Constants.SALARY_METHOD + " Exception=" + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Constants.DATA_ERROR).build();
		}

	}

	/*
	 * rest post service to print Employee Structure Top to Bottom in hierarchy
	 * 
	 * Rest API Exposed:
	 * http://localhost:7001/employees-0.0.1-SNAPSHOT/resources/organisation/
	 * getOrganisationStructure
	 * 
	 */
	@Path(Constants.STRUCTURE_URL)
	@GET
	public Response printOrganisationStructure() {
		logger.info("Entry:" + Constants.RESTCONTROLLER + ":" + Constants.STRUCTURE_METHOD);
		try {
			String result = EmployeeService.printOrganisationStructure();
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Constants.DATA_ERROR).build();
		}
	}

}
