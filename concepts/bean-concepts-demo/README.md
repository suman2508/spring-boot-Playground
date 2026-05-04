# Bean Concepts Demo

This project demonstrates basic Spring Bean concepts using a small Spring Boot application. It focuses on how Spring creates beans, injects dependencies, chooses between multiple implementations, manages bean scopes, and runs lifecycle callbacks.

## What This Demo Covers

- Component scanning with `@Component` and `@Service`
- Dependency injection through constructors
- Multiple implementations of one interface
- Bean selection using `@ConditionalOnProperty`
- Singleton and prototype bean scopes
- Bean lifecycle callbacks with `@PostConstruct`, `@PreDestroy`, `InitializingBean`, and `DisposableBean`
- Accessing beans manually through `ApplicationContext`

## Project Structure

```text
bean-concepts-demo/
  src/main/java/com/bleedcode/bean_concepts_demo/
    BeanConceptsDemoApplication.java
    components/
      LifeCycleBean.java
      PaymentProcessor.java
      PrototypeBean.java
      SimpleComponent.java
      SingletonBean.java
    config/
      ScopeTester.java
    services/
      PaymentService.java
      CardPaymentService.java
      UpiPaymentService.java
  src/main/resources/
    application.yaml
    Bean-Lifecycle.md
    output_snapshot.png
```

## Main Application Flow

The application starts from `BeanConceptsDemoApplication`.

After Spring Boot starts the application context, the main method manually fetches two beans:

```java
PaymentProcessor processor = context.getBean(PaymentProcessor.class);
processor.processPayment(1500.00);

ScopeTester tester = context.getBean(ScopeTester.class);
tester.testScopes();
```

This makes the demo easy to observe from the console because the application prints:

- Which payment service was selected
- Whether singleton beans return the same object
- Whether prototype beans return different objects
- Bean lifecycle callback messages

## Payment Service Bean Selection

The project defines one interface:

```java
public interface PaymentService {
    void pay(double amount);
}
```

There are two implementations:

- `CardPaymentService`
- `UpiPaymentService`

Both are Spring services, but only one is loaded based on this property in `application.yaml`:

```yaml
payment:
  enabled: card
```

`CardPaymentService` is enabled when:

```java
@ConditionalOnProperty(prefix = "payment", name = "enabled", havingValue = "card")
```

`UpiPaymentService` is enabled when:

```java
@ConditionalOnProperty(prefix = "payment", name = "enabled", havingValue = "upi")
```

So with the current configuration, Spring creates `CardPaymentService` and injects it into `PaymentProcessor`.

Expected payment output:

```text
Paid 1500.0 using Card Payment Service
```

To switch the implementation, update `application.yaml`:

```yaml
payment:
  enabled: upi
```

Then restart the application.

## Dependency Injection

`PaymentProcessor` depends on the `PaymentService` interface:

```java
private final PaymentService paymentService;

@Autowired
public PaymentProcessor(PaymentService paymentService) {
    this.paymentService = paymentService;
}
```

This demonstrates programming to an interface instead of depending directly on a concrete class.

Because only one `PaymentService` bean is active at runtime, Spring can inject it automatically.

There is also commented code showing how `@Qualifier` could be used when multiple beans of the same interface are available at the same time.

## Bean Scopes

The project demonstrates two Spring bean scopes.

### Singleton Scope

`SingletonBean` is marked as:

```java
@Component
@Scope("singleton")
public class SingletonBean {
}
```

Singleton is the default Spring scope. Spring creates one object and returns the same instance every time it is requested.

`ScopeTester` checks this by asking the context for the bean twice:

```java
SingletonBean singletonBean1 = context.getBean(SingletonBean.class);
SingletonBean singletonBean2 = context.getBean(SingletonBean.class);
```

Expected result:

```text
Singleton Beans are the same: true
```

### Prototype Scope

`PrototypeBean` is marked as:

```java
@Component
@Scope("prototype")
public class PrototypeBean {
}
```

Prototype scope means Spring creates a new object every time the bean is requested.

`ScopeTester` requests the prototype bean twice:

```java
Object prototypeBean1 = context.getBean(PrototypeBean.class);
Object prototypeBean2 = context.getBean(PrototypeBean.class);
```

Expected result:

```text
Prototype Beans are the same: false
```

## Bean Lifecycle

`LifeCycleBean` demonstrates different lifecycle hooks:

```java
@PostConstruct
public void postConstruct() {
    System.out.println("LifeCycleBean: @PostConstruct method called");
}
```

It also implements:

- `InitializingBean`
- `DisposableBean`

The lifecycle order shown by this demo is:

1. Constructor is called
2. Dependencies are injected
3. `@PostConstruct` method is called
4. `afterPropertiesSet()` is called
5. Bean is ready to use
6. On shutdown, `@PreDestroy` is called
7. `destroy()` is called

There is also a detailed lifecycle note in:

```text
src/main/resources/Bean-Lifecycle.md
```

## How To Run

From this project folder:

```bash
cd concepts/bean-concepts-demo
./mvnw spring-boot:run
```

Or run with Maven:

```bash
mvn spring-boot:run
```

## How To Test

```bash
./mvnw test
```

## Expected Console Output

The exact object addresses will be different each time, but the important output should show:

```text
Paid 1500.0 using Card Payment Service

---------Singleton Scope Test---------
Singleton Beans are the same: true

---------Prototype Scope Test---------
Prototype Beans are the same: false
```

You should also see lifecycle logs from `LifeCycleBean` during startup and shutdown.

## Quick Summary

This project is a compact demo of how Spring manages beans:

```text
Spring container creates beans -> injects dependencies -> applies scopes -> runs lifecycle hooks
```

It is useful for understanding what happens behind the scenes when Spring Boot starts an application and wires components together.
