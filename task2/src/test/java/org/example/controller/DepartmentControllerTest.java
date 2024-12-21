package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Department;
import org.example.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DepartmentService departmentService;

	private Department department;

	@BeforeEach
	void setUp() {
		department = new Department(1L, "IT");
	}

	@Test
	void getAllDepartments() throws Exception {
		when(departmentService.getAllDepartments()).thenReturn(List.of(department));

		mockMvc.perform(get("/api/departments"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("IT"));
	}

	@Test
	void getDepartmentById() throws Exception {
		when(departmentService.getDepartmentById(1L)).thenReturn(department);

		mockMvc.perform(get("/api/departments/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("IT"));
	}

	@Test
	void createDepartment() throws Exception {
		when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

		mockMvc.perform(post("/api/departments")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(department)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("IT"));
	}

	@Test
	void updateDepartment() throws Exception {
		when(departmentService.updateDepartment(eq(1L), any(Department.class))).thenReturn(department);

		mockMvc.perform(put("/api/departments/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(department)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("IT"));
	}

	@Test
	void deleteDepartment() throws Exception {
		mockMvc.perform(delete("/api/departments/1"))
				.andExpect(status().isOk());
	}
}