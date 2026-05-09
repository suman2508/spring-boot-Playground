# Practical 2 - Department REST API

Spring Boot project for Department CRUD endpoints, validation annotations, global response DTOs, exception handling, and persistence using Spring Data JPA with H2.

## REST APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/departments` | Get all departments |
| POST | `/departments` | Create a department |
| PUT | `/departments` | Update a department using the `id` in the JSON body |
| DELETE | `/departments?id={id}` | Delete a department |
| GET | `/departments/{id}` | Get one department by id |

## Department fields

- `id`
- `title`
- `isActive`
- `createdAt`

Additional Department and Employee fields are included to demonstrate these validation annotations:

`@Null`, `@NotNull`, `@AssertTrue`, `@AssertFalse`, `@Min`, `@Max`, `@DecimalMin`, `@DecimalMax`, `@Negative`, `@NegativeOrZero`, `@Positive`, `@PositiveOrZero`, `@Size`, `@Digits`, `@Past`, `@PastOrPresent`, `@Future`, `@FutureOrPresent`, `@Pattern`, `@Email`, `@NotEmpty`, `@NotBlank`, `@Length`, `@Range`, `@CreditCardNumber`, `@URL`.

## Persistence

- `Department` and `Employee` are JPA entities.
- `DepartmentRepository` and `EmployeeRepository` extend `JpaRepository`.
- H2 in-memory database is configured at `jdbc:h2:mem:practical2db`.
- H2 console is available at `/h2-console`.

## Response format

Successful responses are wrapped with `ApiResponse`:

```json
{
  "timestamp": "2026-05-10T00:00:00",
  "status": 200,
  "message": "Departments fetched successfully",
  "data": []
}
```

Validation and exception responses use the same wrapper with an `errors` list:

```json
{
  "timestamp": "2026-05-10T00:00:00",
  "status": 400,
  "message": "Request validation failed",
  "errors": [
    {
      "field": "title",
      "message": "title is required"
    }
  ]
}
```

## Run

```bash
./mvnw spring-boot:run
```

## Example request

```bash
curl -X POST http://localhost:8080/departments \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Engineering",
    "isActive": true,
    "acceptingEmployees": true,
    "deleted": false,
    "budget": 500000.00,
    "rating": 5,
    "basementFloor": 0,
    "contactEmail": "engineering@example.com",
    "website": "https://example.com/engineering",
    "nextHiringDate": "2026-06-01",
    "employeeCapacity": 100
  }'
```
