package com.bleedcode.practical_2.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bleedcode.practical_2.entity.Department;
import com.bleedcode.practical_2.exception.DepartmentNotFoundException;
import com.bleedcode.practical_2.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentService {

	private final DepartmentRepository departmentRepository;

	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Transactional(readOnly = true)
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException(id));
	}

	public Department createDepartment(Department department) {
		department.setId(null);
		if (department.getCreatedAt() == null) {
			department.setCreatedAt(LocalDateTime.now());
		}

		return departmentRepository.save(department);
	}

	public Department updateDepartment(Department department) {
		Long id = department.getId();
		if (id == null || !departmentRepository.existsById(id)) {
			throw new DepartmentNotFoundException(id);
		}

		if (department.getCreatedAt() == null) {
			department.setCreatedAt(getDepartmentById(id).getCreatedAt());
		}

		return departmentRepository.save(department);
	}

	public void deleteDepartment(Long id) {
		if (!departmentRepository.existsById(id)) {
			throw new DepartmentNotFoundException(id);
		}
		departmentRepository.deleteById(id);
	}
}
