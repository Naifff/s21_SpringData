package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.projection.EmployeeProjection;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EmployeeService employeeService;

	private Employee employee;
	private EmployeeProjection employeeProjection;

	@BeforeEach
	void setUp() {
		Department department = new Department(1L, "IT");
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
	void getAllEmployees() throws Exception {
		when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

		mockMvc.perform(get("/api/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].firstName").value("John"))
				.andExpect(jsonPath("$[0].lastName").value("Doe"));
	}

	@Test
	void getAllEmployeeProjections() throws Exception {
		when(employeeService.getAllEmployeeProjections()).thenReturn(List.of(employeeProjection));

		mockMvc.perform(get("/api/employees/projections"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].fullName").value("John Doe"))
				.andExpect(jsonPath("$[0].position").value("Developer"))
				.andExpect(jsonPath("$[0].departmentName").value("IT"));
	}

	@Test
	void getEmployeeById() throws Exception {
		when(employeeService.getEmployeeById(1L)).thenReturn(employee);

		mockMvc.perform(get("/api/employees/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Doe"));
	}

	@Test
	void getEmployeeProjectionById() throws Exception {
		when(employeeService.getEmployeeProjectionById(1L)).thenReturn(employeeProjection);

		mockMvc.perform(get("/api/employees/projections/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.fullName").value("John Doe"))
				.andExpect(jsonPath("$.position").value("Developer"))
				.andExpect(jsonPath("$.departmentName").value("IT"));
	}

	@Test
	void getEmployeesByDepartment() throws Exception {
		when(employeeService.getEmployeesByDepartment(1L)).thenReturn(List.of(employeeProjection));

		mockMvc.perform(get("/api/employees/department/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].fullName").value("John Doe"))
				.andExpect(jsonPath("$[0].position").value("Developer"))
				.andExpect(jsonPath("$[0].departmentName").value("IT"));
	}

	@Test
	void createEmployee() throws Exception {
		when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

		mockMvc.perform(post("/api/employees")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Doe"));
	}

	@Test
	void updateEmployee() throws Exception {
		when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(employee);

		mockMvc.perform(put("/api/employees/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Doe"));
	}

	@Test
	void deleteEmployee() throws Exception {
		mockMvc.perform(delete("/api/employees/1"))
				.andExpect(status().isOk());
	}
}