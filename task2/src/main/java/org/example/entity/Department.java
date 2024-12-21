package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("department")
	private List<Employee> employees = new ArrayList<>();

	public Department(String name) {
		this.name = name;
	}

	public Department(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}