package org.example.repository;

import org.example.entity.Employee;
import org.example.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<EmployeeProjection> findAllProjectedBy();

	EmployeeProjection findProjectedById(Long id);

	List<EmployeeProjection> findByDepartmentId(Long departmentId);
}