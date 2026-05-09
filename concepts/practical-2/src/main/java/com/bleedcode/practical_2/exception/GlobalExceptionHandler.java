package com.bleedcode.practical_2.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.bleedcode.practical_2.dto.ApiResponse;
import com.bleedcode.practical_2.dto.ApiValidationError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleDepartmentNotFound(
			DepartmentNotFoundException exception,
			HttpServletRequest request) {
		return buildError(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
	}

	@ExceptionHandler(DuplicateDepartmentException.class)
	public ResponseEntity<ApiResponse<Void>> handleDuplicateDepartment(
			DuplicateDepartmentException exception,
			HttpServletRequest request) {
		return buildError(HttpStatus.CONFLICT, exception.getMessage(), request, null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationErrors(
			MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		List<ApiValidationError> validationErrors = new ArrayList<>();
		exception.getBindingResult().getFieldErrors()
				.forEach(error -> validationErrors.add(new ApiValidationError(error.getField(), error.getDefaultMessage())));

		return buildError(
				HttpStatus.BAD_REQUEST,
				"Request validation failed",
				request,
				validationErrors);
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiResponse<Void>> handleHandlerMethodValidationErrors(
			HandlerMethodValidationException exception,
			HttpServletRequest request) {
		List<ApiValidationError> validationErrors = new ArrayList<>();
		exception.getParameterValidationResults().forEach(result -> {
			String parameterName = result.getMethodParameter().getParameterName();
			String fieldName = parameterName == null ? "parameter" : parameterName;
			result.getResolvableErrors()
					.forEach(error -> validationErrors.add(new ApiValidationError(fieldName, error.getDefaultMessage())));
		});

		return buildError(
				HttpStatus.BAD_REQUEST,
				"Request parameter validation failed",
				request,
				validationErrors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleConstraintViolationErrors(
			ConstraintViolationException exception,
			HttpServletRequest request) {
		List<ApiValidationError> validationErrors = new ArrayList<>();
		exception.getConstraintViolations().forEach(violation -> validationErrors.add(new ApiValidationError(
				violation.getPropertyPath().toString(),
				violation.getMessage())));

		return buildError(
				HttpStatus.BAD_REQUEST,
				"Request parameter validation failed",
				request,
				validationErrors);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception exception, HttpServletRequest request) {
		return buildError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request, null);
	}

	private ResponseEntity<ApiResponse<Void>> buildError(
			HttpStatus status,
			String message,
			HttpServletRequest request,
			List<ApiValidationError> validationErrors) {
		List<ApiValidationError> errors = validationErrors;
		if (errors == null) {
			errors = List.of(new ApiValidationError(request.getRequestURI(), message));
		}
		return ResponseEntity.status(status).body(ApiResponse.failure(status, message, errors));
	}
}
