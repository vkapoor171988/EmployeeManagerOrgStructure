Readme.txt
1. All the rest Services are written in RestController.java whose mapping to web app and url mapping is done in web.xml
2. Used Maven Project for easy handling of dependencies
3. maven clean and maven install will create war file which can be deployed on server for REST Architecture to work
4. Project Structure: Code of rest calls from RestController goes to EmployeeService(Service) which goes to DAO to retrieve data from JSON
5. Dumping of file and fetching of JSON file is mapped  in EmployeeDAO
6. Constants.java is used for adding constants
7. Single instance of Map Data Structure is used which contain all the organisation structure
8. Employee Class is POJO for Employee type instances
9. Project can be imported to IDE and just mvn install will help create you war files

For Localhost with port 7001:
URL's for rest services are

2 post calls

1. to load employee data
http://localhost:7001/employees-v1/resources/organisation/employees

Input Sample:
[	{
		"Employee ID": 1490,
		"City Name": "New Delhi",
		"Salary": 200000,
		"First Name": "Suresh",
		"Second Name" : "Prabhu",
		"Manager Emp Id" :3450
	},
	{
		"Employee ID": 3450,
		"City Name": "Bangalore",
		"Salary": 900000,
		"First Name": "Indira",
		"Second Name" : "Sharma",
		"Manager Emp Id" :0
	},
	{
		"Employee ID": 2657,
		"City Name": "New Delhi",
		"Salary": 100000,
		"First Name": "Harish",
		"Second Name" : "Prasad",
		"Manager Emp Id" :1490
	},
	{
		"Employee ID": 8490,
		"City Name": "Mysore",
		"Salary": 50000,
		"First Name": "Kamlesh",
		"Second Name" : "Kumar",
		"Manager Emp Id" :2657
	},
	{
		"Employee ID": 3867,
		"City Name": "New Delhi",
		"Salary": 100000,
		"First Name": "Pranav",
		"Second Name" : "Pandey",
		"Manager Emp Id" :1490
	},
	{
		"Employee ID": 1495,
		"City Name": "Chennai",
		"Salary": 50000,
		"First Name": "Latha",
		"Second Name" : "Prasad",
		"Manager Emp Id" :3867
	}
	
	
]



2. Change Manager of Employee
http://localhost:7001/employees-0.0.1-SNAPSHOT/resources/organisation/changeManager/{employeeId}

Sample Input:
{
"Manager ID":2657
}



3 get calls

3. For Max Subordinates
http://localhost:7001/employees-0.0.1-SNAPSHOT/resources/organisation/employee/maxSubordinates

4. For total subordinates salary
http://localhost:7001/employees-0.0.1-SNAPSHOT/resources/organisation/employee/salarySubordinate/{employeeid}

5. Print Organisation Structure

http://localhost:7001/employees-0.0.1-SNAPSHOT/resources/organisation/getOrganisationStructure