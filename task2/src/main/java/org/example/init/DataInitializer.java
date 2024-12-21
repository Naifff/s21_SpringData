package org.example.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;

	@PostConstruct
	@Transactional
	public void init() {
		// Create departments
		Department itDepartment = new Department("IT");
		Department hrDepartment = new Department("HR");
		Department financeDepartment = new Department("Finance");
		Department marketingDepartment = new Department("Marketing");
		Department salesDepartment = new Department("Sales");

		List<Department> departments = departmentRepository.saveAll(Arrays.asList(
				itDepartment, hrDepartment, financeDepartment, marketingDepartment, salesDepartment
		));

		// Create IT Department employees
		Employee itManager = new Employee(null, "John", "Smith", "IT Manager",
				new BigDecimal("95000"), itDepartment);
		Employee seniorDeveloper = new Employee(null, "Michael", "Johnson", "Senior Developer",
				new BigDecimal("85000"), itDepartment);
		Employee javaDeveloper = new Employee(null, "Sarah", "Davis", "Java Developer",
				new BigDecimal("75000"), itDepartment);
		Employee qaEngineer = new Employee(null, "David", "Wilson", "QA Engineer",
				new BigDecimal("70000"), itDepartment);

		// Create HR Department employees
		Employee hrManager = new Employee(null, "Emily", "Brown", "HR Manager",
				new BigDecimal("90000"), hrDepartment);
		Employee recruiter = new Employee(null, "Jessica", "Taylor", "Senior Recruiter",
				new BigDecimal("65000"), hrDepartment);
		Employee hrSpecialist = new Employee(null, "Daniel", "Anderson", "HR Specialist",
				new BigDecimal("60000"), hrDepartment);

		// Create Finance Department employees
		Employee financeDirector = new Employee(null, "William", "Clark", "Finance Director",
				new BigDecimal("120000"), financeDepartment);
		Employee accountant = new Employee(null, "Oliver", "White", "Senior Accountant",
				new BigDecimal("80000"), financeDepartment);
		Employee financialAnalyst = new Employee(null, "Sophie", "Lee", "Financial Analyst",
				new BigDecimal("75000"), financeDepartment);

		// Create Marketing Department employees
		Employee marketingManager = new Employee(null, "Emma", "Martinez", "Marketing Manager",
				new BigDecimal("92000"), marketingDepartment);
		Employee contentManager = new Employee(null, "Lucas", "Garcia", "Content Manager",
				new BigDecimal("70000"), marketingDepartment);
		Employee marketingSpecialist = new Employee(null, "Isabella", "Rodriguez", "Marketing Specialist",
				new BigDecimal("65000"), marketingDepartment);

		// Create Sales Department employees
		Employee salesDirector = new Employee(null, "James", "Miller", "Sales Director",
				new BigDecimal("110000"), salesDepartment);
		Employee salesManager = new Employee(null, "Sophia", "Moore", "Sales Manager",
				new BigDecimal("85000"), salesDepartment);
		Employee salesRepresentative = new Employee(null, "Benjamin", "Thompson", "Sales Representative",
				new BigDecimal("65000"), salesDepartment);

		// Save all employees
		employeeRepository.saveAll(Arrays.asList(
				itManager, seniorDeveloper, javaDeveloper, qaEngineer,
				hrManager, recruiter, hrSpecialist,
				financeDirector, accountant, financialAnalyst,
				marketingManager, contentManager, marketingSpecialist,
				salesDirector, salesManager, salesRepresentative
		));
	}
}