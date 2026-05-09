package com.bleedcode.practical_2.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
		LocalDateTime timestamp,
		int status,
		String message,
		T data,
		List<ApiValidationError> errors) {

	public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
		return new ApiResponse<>(LocalDateTime.now(), status.value(), message, data, null);
	}

	public static ApiResponse<Void> failure(HttpStatus status, String message, List<ApiValidationError> errors) {
		return new ApiResponse<>(LocalDateTime.now(), status.value(), message, null, errors);
	}
}
