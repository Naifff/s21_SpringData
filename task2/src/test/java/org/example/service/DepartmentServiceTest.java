package org.example.service;

import org.example.entity.Department;
import org.example.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private DepartmentService departmentService;

	private Department department;

	@BeforeEach
	void setUp() {
		department = new Department(1L, "IT");
	}

	@Test
	void getAllDepartments() {
		when(departmentRepository.findAll()).thenReturn(List.of(department));

		List<Department> departments = departmentService.getAllDepartments();

		assertFalse(departments.isEmpty());
		assertEquals(1, departments.size());
		verify(departmentRepository).findAll();
	}

	@Test
	void getDepartmentById() {
		when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

		Department found = departmentService.getDepartmentById(1L);

		assertNotNull(found);
		assertEquals(department.getName(), found.getName());
		verify(departmentRepository).findById(1L);
	}

	@Test
	void createDepartment() {
		when(departmentRepository.save(any(Department.class))).thenReturn(department);

		Department created = departmentService.createDepartment(new Department(null, "IT"));

		assertNotNull(created);
		assertEquals(department.getName(), created.getName());
		verify(departmentRepository).save(any(Department.class));
	}

	@Test
	void updateDepartment() {
		Department updatedDepartment = new Department(1L, "Updated IT");
		when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
		when(departmentRepository.save(any(Department.class))).thenReturn(updatedDepartment);

		Department result = departmentService.updateDepartment(1L, updatedDepartment);

		assertNotNull(result);
		assertEquals(updatedDepartment.getName(), result.getName());
		verify(departmentRepository).save(any(Department.class));
	}

	@Test
	void deleteDepartment() {
		departmentService.deleteDepartment(1L);
		verify(departmentRepository).deleteById(1L);
	}
}