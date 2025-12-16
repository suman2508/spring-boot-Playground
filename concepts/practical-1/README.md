# Cake Bakery – Spring Boot Dependency Injection Example

## 📌 Problem Statement

Create a Spring Boot application that simulates a cake baking system:

1. A `CakeBaker` class depends on `Frosting` and `Syrup`.
2. `Frosting` and `Syrup` are interfaces.
3. Each interface has two implementations:
   - Chocolate
   - Strawberry
4. Dependencies should be injected using Spring Dependency Injection.
5. The choice of frosting and syrup should be configurable using `application.yml`.

---

## 🧠 Concepts Implemented

### 1. Dependency Injection (DI)
- Spring injects dependencies automatically instead of manual object creation.

### 2. Interface-based Design
- `CakeBaker` depends on interfaces, not implementations.
- Enables loose coupling and easy extension.

### 3. Constructor Injection
- Preferred DI method in Spring.
- Makes dependencies immutable and test-friendly.

### 4. Configuration using `@Configuration`
- Bean creation logic moved to a configuration class.
- Avoids ambiguity when multiple implementations exist.

### 5. Externalized Configuration
- Behavior controlled via `application.yml`.
- No code change needed to switch cake flavors.

---

## ⚙️ Configuration

```yaml
cake:
  frosting: chocolate
  syrup: strawberry
