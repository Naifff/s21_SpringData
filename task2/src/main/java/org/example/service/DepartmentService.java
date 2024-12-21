package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Department;
import org.example.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
	private final DepartmentRepository departmentRepository;

	@Transactional(readOnly = true)
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found"));
	}

	@Transactional
	public Department createDepartment(Department department) {
		return departmentRepository.save(department);
	}

	@Transactional
	public Department updateDepartment(Long id, Department department) {
		Department existingDepartment = getDepartmentById(id);
		existingDepartment.setName(department.getName());
		return departmentRepository.save(existingDepartment);
	}

	@Transactional
	public void deleteDepartment(Long id) {
		departmentRepository.deleteById(id);
	}
}