# Bean Concepts Demo — Documentation

## Purpose
A compact, hands‑on exploration of Spring bean fundamentals. This module demonstrates how Spring discovers, instantiates, wires, and manages beans, and clarifies lifecycle callbacks, scopes, qualifiers, and conditional beans.

## Learning goals
After working through this module you will be able to:
- Identify how and when Spring creates bean instances (stereotype annotations and `@Bean` methods).
- Apply constructor-based dependency injection and prefer immutable wiring.
- Observe bean lifecycle callbacks and understand their execution order.
- Choose between common bean scopes (`singleton` vs `prototype`) and reason about stateful vs stateless design.
- Resolve injection ambiguity using `@Qualifier` or `@Primary`.
- Enable/disable beans at startup using conditional annotations (e.g., `@ConditionalOnProperty`).

## Key concepts (summary)

1. Bean creation
    - Stereotype annotations (`@Component`, `@Service`, `@Repository`) are discovered by component scanning.
    - `@Configuration` classes and `@Bean` methods provide explicit Java-based bean registration.
    - Web-layer stereotypes
        - `@Controller` — MVC controller detected by component scanning; typically returns view names or uses `@ResponseBody` on individual handler methods.
        - `@RestController` — convenience meta-annotation (`@Controller` + `@ResponseBody`) for REST endpoints; methods return objects serialized (e.g., JSON). Both are regular Spring beans (discovered via component scanning), participate in DI and lifecycle callbacks, and are singletons by default unless another scope is declared.

2. Dependency injection
    - Prefer constructor injection (explicit or `@Autowired` on constructors) for testability and immutability.
    - Spring resolves dependencies by type; use `@Qualifier` when multiple candidates exist.

3. Bean lifecycle
    - Typical singleton lifecycle: instantiate → inject dependencies → set properties → `@PostConstruct` / `afterPropertiesSet()` → ready → `@PreDestroy` / `destroy()` on shutdown.
    - Supported callbacks: `@PostConstruct` / `@PreDestroy`, `InitializingBean.afterPropertiesSet()`, `DisposableBean.destroy()`, and `init-method` / `destroy-method` on `@Bean`.
    - Note: prototype beans are created on demand and are not automatically destroyed by the container.

### Diagram — Bean lifecycle

        (Container Starts)
                  |
          Instantiate Bean
                  |
          Inject Dependencies
                  |
        -------------------------
        |   Initialization Phase  |
        |                         |
        |  • @PostConstruct       |
        |  • afterPropertiesSet() |
        |  • initMethod           |
        -------------------------
                  |
              Ready for Use
                  |
         (Container Shutdown)
                  |
        -------------------------
        |    Destruction Phase    |
        |                         |
        |  • @PreDestroy          |
        |  • destroy()            |
        |  • destroyMethod        |
        -------------------------

4. Bean scopes
    - `singleton` (default): one shared instance per `ApplicationContext`.
    - `prototype`: a new instance each request to the context.
    - Choose scopes with state and concurrency considerations in mind.

5. Qualifiers
    - Use `@Qualifier("beanName")` or `@Primary` to disambiguate multiple beans of the same type.
    - Apply qualifiers on injection points or bean declarations.

6. Conditional beans
    - Use `@ConditionalOnProperty` (or similar) to enable/disable beans based on external configuration (`application.properties` / `application.yml`).

## How to experiment
- Run the Spring Boot app and inspect logs for bean instantiation and lifecycle callbacks.
- Toggle conditional properties to observe which beans are created.
- Request prototype beans multiple times and compare identity/hashcodes.
- Introduce two beans of the same type to trigger a resolution error, then fix it with `@Qualifier`.

## Typical pitfalls & best practices
- Prefer constructor injection over field injection.
- Manage prototype-scoped resources manually if they require cleanup.
- Avoid expensive work in `@PostConstruct`; prefer lazy init or `ApplicationRunner`/`CommandLineRunner` for conditional or deferred work.
- Use conditionals to keep configuration flexible and environment-driven.

### Spring Framework vs Spring Boot — short comparison

- Purpose
    - Spring Framework: comprehensive programming and configuration model for Java apps (DI container, AOP, core libraries).
    - Spring Boot: opinionated layer to simplify creating standalone, production-ready Spring applications.

- Configuration & Setup
    - Spring Framework: more manual setup; explicit configuration (XML or @Configuration).
    - Spring Boot: auto-configuration, sensible defaults, “starter” dependencies to reduce boilerplate.

- Packaging & Execution
    - Spring Framework: typically deployed to an external servlet container.
    - Spring Boot: can package an embedded server (executable JAR) for easy deployment.

- Dependency management
    - Spring Framework: you manage versions and explicit dependencies.
    - Spring Boot: dependency management via starters and curated dependency versions.

- Production features
    - Spring Framework: provides core capabilities; add-ons available.
    - Spring Boot: built-in production-ready features (Actuator, metrics, health checks, externalized config).

- When to choose
    - Spring Framework: fine-grained control, library/module development, or when embedding into existing container-managed apps.
    - Spring Boot: ideal for quickly delivering microservices and standalone applications with minimal configuration.


### Output Sanpshot
    - Present in the path - /resources/output_snapshot.png