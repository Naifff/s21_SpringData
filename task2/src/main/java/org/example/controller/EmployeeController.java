package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Employee;
import org.example.projection.EmployeeProjection;
import org.example.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin
public class EmployeeController {
	private final EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	@GetMapping("/projections")
	public ResponseEntity<List<EmployeeProjection>> getAllEmployeeProjections() {
		return ResponseEntity.ok(employeeService.getAllEmployeeProjections());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}

	@GetMapping("/projections/{id}")
	public ResponseEntity<EmployeeProjection> getEmployeeProjectionById(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.getEmployeeProjectionById(id));
	}

	@GetMapping("/department/{departmentId}")
	public ResponseEntity<List<EmployeeProjection>> getEmployeesByDepartment(@PathVariable Long departmentId) {
		return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
	}

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		return ResponseEntity.ok(employeeService.createEmployee(employee));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.ok().build();
	}
}