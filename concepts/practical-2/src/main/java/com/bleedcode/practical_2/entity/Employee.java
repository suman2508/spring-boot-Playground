package com.bleedcode.practical_2.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.CreditCardNumber;
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
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@PositiveOrZero(message = "id must be zero or positive")
	private Long id;

	@NotNull(message = "departmentId is required")
	@Positive(message = "departmentId must be positive")
	private Long departmentId;

	@NotBlank(message = "name is required")
	@Size(min = 2, max = 80, message = "name must be between 2 and 80 characters")
	@Length(min = 2, max = 80, message = "name length must be between 2 and 80 characters")
	private String name;

	@NotEmpty(message = "email is required")
	@Email(message = "email must be valid")
	private String email;

	@Pattern(regexp = "^[0-9]{10}$", message = "phone must contain exactly 10 digits")
	private String phone;

	@DecimalMin(value = "1000.00", message = "salary must be at least 1000.00")
	@DecimalMax(value = "1000000.00", message = "salary cannot exceed 1000000.00")
	@Digits(integer = 7, fraction = 2, message = "salary can have up to 7 digits and 2 decimal places")
	private BigDecimal salary;

	@PositiveOrZero(message = "bonus must be zero or positive")
	private BigDecimal bonus;

	@Negative(message = "taxAdjustment must be negative")
	private BigDecimal taxAdjustment;

	@NegativeOrZero(message = "outstandingLoan must be zero or negative")
	private BigDecimal outstandingLoan;

	@Min(value = 1, message = "performanceScore must be at least 1")
	@Max(value = 10, message = "performanceScore cannot be more than 10")
	private Integer performanceScore;

	@Range(min = 18, max = 65, message = "age must be between 18 and 65")
	private Integer age;

	@Past(message = "dateOfBirth must be in the past")
	private LocalDate dateOfBirth;

	@PastOrPresent(message = "joiningDate cannot be in the future")
	private LocalDate joiningDate;

	@Future(message = "contractEndDate must be in the future")
	private LocalDate contractEndDate;

	@FutureOrPresent(message = "nextReviewDate must be today or in the future")
	private LocalDate nextReviewDate;

	@AssertTrue(message = "probationCompleted must be true")
	private Boolean probationCompleted;

	@AssertFalse(message = "terminated must be false")
	private Boolean terminated;

	@CreditCardNumber(message = "corporateCardNumber must be a valid credit card number")
	private String corporateCardNumber;

	@URL(message = "portfolioUrl must be a valid URL")
	private String portfolioUrl;
}
