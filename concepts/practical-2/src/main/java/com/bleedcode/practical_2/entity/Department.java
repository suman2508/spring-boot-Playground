package com.bleedcode.practical_2.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@PositiveOrZero(message = "id must be zero or positive")
	private Long id;

	@NotBlank(message = "title is required")
	@Size(min = 2, max = 60, message = "title must be between 2 and 60 characters")
	@Length(min = 2, max = 60, message = "title length must be between 2 and 60 characters")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "title can contain only letters and spaces")
	private String title;

	@NotNull(message = "isActive is required")
	private Boolean isActive;

	@PastOrPresent(message = "createdAt cannot be in the future")
	private LocalDateTime createdAt;

	@AssertTrue(message = "acceptingEmployees must be true")
	private Boolean acceptingEmployees;

	@AssertFalse(message = "deleted must be false")
	private Boolean deleted;

	@DecimalMin(value = "0.00", inclusive = true, message = "budget cannot be negative")
	@DecimalMax(value = "10000000.00", message = "budget cannot exceed 10000000.00")
	@Digits(integer = 8, fraction = 2, message = "budget can have up to 8 digits and 2 decimal places")
	private BigDecimal budget;

	@Min(value = 1, message = "rating must be at least 1")
	@Max(value = 5, message = "rating cannot be more than 5")
	private Integer rating;

	@NegativeOrZero(message = "basementFloor must be zero or negative")
	private Integer basementFloor;

	@NotEmpty(message = "contactEmail is required")
	@Email(message = "contactEmail must be valid")
	private String contactEmail;

	@URL(message = "website must be a valid URL")
	private String website;

	@FutureOrPresent(message = "nextHiringDate must be today or in the future")
	private LocalDate nextHiringDate;

	@Range(min = 1, max = 5000, message = "employeeCapacity must be between 1 and 5000")
	private Integer employeeCapacity;

	@Null(message = "archivedReason must be null for active departments")
	private String archivedReason;
}
