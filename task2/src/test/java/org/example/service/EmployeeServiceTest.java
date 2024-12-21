package org.example.service;

import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.projection.EmployeeProjection;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	private Employee employee;
	private Department department;
	private EmployeeProjection employeeProjection;

	@BeforeEach
	void setUp() {
		department = new Department(1L, "IT");
		employee = new Employee(1L, "John", "Doe", "Developer",
				new BigDecimal("75000"), department);

		employeeProjection = new EmployeeProjection() {
			@Override
			public String getFullName() {
				return "John Doe";
			}

			@Override
			public String getPosition() {
				return "Developer";
			}

			@Override
			public String getDepartmentName() {
				return "IT";
			}
		};
	}

	@Test
	void getAllEmployees() {
		when(employeeRepository.findAll()).thenReturn(List.of(employee));

		List<Employee> employees = employeeService.getAllEmployees();

		assertFalse(employees.isEmpty());
		assertEquals(1, employees.size());
		verify(employeeRepository).findAll();
	}

	@Test
	void getAllEmployeeProjections() {
		when(employeeRepository.findAllProjectedBy()).thenReturn(List.of(employeeProjection));

		List<EmployeeProjection> projections = employeeService.getAllEmployeeProjections();

		assertFalse(projections.isEmpty());
		assertEquals(1, projections.size());
		verify(employeeRepository).findAllProjectedBy();
	}

	@Test
	void getEmployeeById() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

		Employee found = employeeService.getEmployeeById(1L);

		assertNotNull(found);
		assertEquals(employee.getFirstName(), found.getFirstName());
		verify(employeeRepository).findById(1L);
	}

	@Test
	void getEmployeeProjectionById() {
		when(employeeRepository.findProjectedById(1L)).thenReturn(employeeProjection);

		EmployeeProjection projection = employeeService.getEmployeeProjectionById(1L);

		assertNotNull(projection);
		assertEquals("John Doe", projection.getFullName());
		verify(employeeRepository).findProjectedById(1L);
	}

	@Test
	void getEmployeesByDepartment() {
		when(employeeRepository.findByDepartmentId(1L)).thenReturn(List.of(employeeProjection));

		List<EmployeeProjection> projections = employeeService.getEmployeesByDepartment(1L);

		assertFalse(projections.isEmpty());
		assertEquals(1, projections.size());
		verify(employeeRepository).findByDepartmentId(1L);
	}

	@Test
	void createEmployee() {
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		Employee created = employeeService.createEmployee(new Employee(null, "John", "Doe",
				"Developer", new BigDecimal("75000"), department));

		assertNotNull(created);
		assertEquals(employee.getFirstName(), created.getFirstName());
		verify(employeeRepository).save(any(Employee.class));
	}

	@Test
	void updateEmployee() {
		Employee updatedEmployee = new Employee(1L, "John", "Smith", "Senior Developer",
				new BigDecimal("85000"), department);
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

		Employee result = employeeService.updateEmployee(1L, updatedEmployee);

		assertNotNull(result);
		assertEquals(updatedEmployee.getLastName(), result.getLastName());
		verify(employeeRepository).save(any(Employee.class));
	}

	@Test
	void deleteEmployee() {
		employeeService.deleteEmployee(1L);
		verify(employeeRepository).deleteById(1L);
	}
}