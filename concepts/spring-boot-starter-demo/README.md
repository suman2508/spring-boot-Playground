# 🌱 Spring Boot Basics Demo

This project introduces the foundational concepts of **Spring Boot**, including:
- Project structure
- Starting point (`main` class)
- Controllers
- Services
- Models
- POM file structure
- Configuration using `application.yml`

It is part of the **Spring Boot Playground → Concepts** section.

## 📁 Project Structure

```
spring-boot-basics-demo/
├── pom.xml
├── src/
│ ├── main/
│ │ ├── java/com/bleedcode/demo/
│ │ │ ├── DemoApplication.java # Spring Boot entry point
│ │ │ ├── controller/ # REST Controllers
│ │ │ ├── service/ # Business logic
│ │ │ └── model/ # Domain objects
│ │ └── resources/
│ │ ├── application.yml # Central configuration file
│ │ └── static/ # Images, JS, CSS (optional)
│ └── test/java/com/bleedcode/demo/ # Unit tests
```
## 📌 Importance of `pom.xml`

The `pom.xml` (Maven Project Object Model) is the **brain of the project**.

### It defines:
- Project metadata (groupId, artifactId, version)
- Dependencies (Spring Boot Starter Web, JPA, Lombok, etc.)
- Build plugins
- Java version
- Packaging type (JAR)
- Parent Spring Boot version

### Why it matters:
- Controls **which Spring Boot version** you run
- Controls **dependency versions**
- Ensures reusable & stable builds
- Enables features like Spring Boot autoconfiguration

---

## 📌 Importance of `application.yml`

The `application.yml` file is your **central configuration hub**.

### Used to configure:
- Server port
- DB connections
- Logging
- Spring Profiles
- Application-level properties
- Third-party integrations

### Why YML is preferred:
- Cleaner than `.properties`
- Hierarchical structure
- Easy for microservices

### Example:

```yaml
server:
  port: 8080

spring:
  application:
    name: basics-demo
```

---

##Running the Application

```mvn spring-boot:run```

Or build and run:

```
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

🎯 Learning Goals

- By completing this project, you will understand:
- How Spring Boot apps are structured
- How the autoconfiguration works
- How to build APIs
- How to organize packages
- The meaning of the POM
- How application.yml drives configuration