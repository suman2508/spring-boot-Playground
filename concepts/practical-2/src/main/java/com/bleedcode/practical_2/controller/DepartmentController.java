package com.bleedcode.practical_2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bleedcode.practical_2.dto.ApiResponse;
import com.bleedcode.practical_2.entity.Department;
import com.bleedcode.practical_2.service.DepartmentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/departments")
@Validated
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Department>>> getDepartments() {
		List<Department> departments = departmentService.getAllDepartments();
		return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Departments fetched successfully", departments));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Department>> createDepartment(@Valid @RequestBody Department department) {
		Department savedDepartment = departmentService.createDepartment(department);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success(HttpStatus.CREATED, "Department created successfully", savedDepartment));
	}

	@PutMapping
	public ResponseEntity<ApiResponse<Department>> updateDepartment(@Valid @RequestBody Department department) {
		Department updatedDepartment = departmentService.updateDepartment(department);
		return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Department updated successfully", updatedDepartment));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteDepartment(@RequestParam @Positive Long id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Department deleted successfully", null));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Department>> getDepartment(@PathVariable @Positive Long id) {
		Department department = departmentService.getDepartmentById(id);
		return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Department fetched successfully", department));
	}
}
