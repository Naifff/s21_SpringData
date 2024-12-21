package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Employee;
import org.example.projection.EmployeeProjection;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
	private final EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public List<EmployeeProjection> getAllEmployeeProjections() {
		return employeeRepository.findAllProjectedBy();
	}

	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
	}

	public EmployeeProjection getEmployeeProjectionById(Long id) {
		EmployeeProjection projection = employeeRepository.findProjectedById(id);
		if (projection == null) {
			throw new RuntimeException("Employee not found with id: " + id);
		}
		return projection;
	}

	public List<EmployeeProjection> getEmployeesByDepartment(Long departmentId) {
		return employeeRepository.findByDepartmentId(departmentId);
	}

	@Transactional
	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Transactional
	public Employee updateEmployee(Long id, Employee employee) {
		Employee existingEmployee = getEmployeeById(id);
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setPosition(employee.getPosition());
		existingEmployee.setSalary(employee.getSalary());
		existingEmployee.setDepartment(employee.getDepartment());
		return employeeRepository.save(existingEmployee);
	}

	@Transactional
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
}