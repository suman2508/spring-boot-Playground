package com.bleedcode.practical_2.exception;

public class DepartmentNotFoundException extends RuntimeException {

	public DepartmentNotFoundException(Long id) {
		super("Department not found with id: " + id);
	}
}
