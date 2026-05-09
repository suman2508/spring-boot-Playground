package com.bleedcode.practical_2.exception;

public class DuplicateDepartmentException extends RuntimeException {

	public DuplicateDepartmentException(Long id) {
		super("Department already exists with id: " + id);
	}
}
