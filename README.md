# 🚀 Spring Boot Playground — Microservices & Backend Development

Welcome to my **Spring Boot Playground**, a personal repository where I explore, practice, and document various backend development concepts.  
This repo will contain **small demo projects, microservices examples, hands-on experiments, and concept implementations** across Spring Boot, Microservices, Docker, Kubernetes, and more.

---

## 📘 About This Repository

This project is meant to serve as:

- A **practice ground** for learning backend engineering  
- A **collection of demos** for different Spring Boot & microservices concepts  
- A **reference repo** for understanding architecture patterns  
- A **place to store experiments** with Docker, Kubernetes, CI/CD, messaging, and cloud tools  

I will keep updating this repository as I learn new things.

---

## 🧩 Topics I Will Be Covering

### ✅ Core Spring Boot
- REST API development  
- Controllers, Services, Repositories  
- Exception handling  
- Validation  
- Configuration & profiles  

### ☁️ Microservices Architecture
- Service-to-service communication  
- Feign clients / WebClient  
- Config Server  
- Service Discovery (Eureka)  
- API Gateway (Spring Cloud Gateway)  
- Load balancing (Spring Cloud LoadBalancer)  
- Circuit Breakers (Resilience4j)  

### 🛢️ Databases & Persistence
- Spring Data JPA  
- Hibernate  
- MySQL / PostgreSQL  
- MongoDB  
- Query optimization  

### 📦 Messaging & Event-Driven Systems
- Kafka  
- RabbitMQ  
- JMS  
- Asynchronous communication  

### 🐳 Docker & Kubernetes
- Dockerizing Spring Boot apps  
- Docker Compose setups  
- Minikube  
- Deployments, Services, Ingress  
- ConfigMaps & Secrets  

### 🧪 Testing
- Unit testing (JUnit, Mockito)  
- Integration testing  
- Testcontainers  

---

## 📁 Repository Structure

```
spring-boot-Playground/
├── concepts/
│   ├── practical-1/
│   ├── spring-boot-starter-demo/
│   └── bean-concepts-demo/
│
├── kafka-implementation/
│   ├── user-service/
│   ├── notification-service/
│   └── KAFKA_IMPLEMENTATION.md
│
└── README.md
```

---

## 📦 Kafka Implementation

The `kafka-implementation` folder contains a basic Spring Boot Kafka demo with two services:

- `user-service`: exposes an HTTP endpoint and publishes messages to Kafka using `KafkaTemplate`.
- `notification-service`: consumes messages from Kafka using `@KafkaListener`.

The current Kafka flow is:

```text
HTTP request -> user-service -> Kafka topic -> notification-service -> log output
```

The demo uses a topic named `user-random-topic`, configured with three partitions. The user service sends keyed string messages to Kafka, and the notification service consumes them as part of the `notification-service` consumer group.

For detailed implementation notes, see:

[kafka-implementation/KAFKA_IMPLEMENTATION.md](kafka-implementation/KAFKA_IMPLEMENTATION.md)

### Running The Kafka Demo

Kafka must be running locally at:

```text
localhost:9092
```

Start the services separately:

```bash
cd kafka-implementation/user-service
./mvnw spring-boot:run
```

```bash
cd kafka-implementation/notification-service
./mvnw spring-boot:run
```

Then publish a test message:

```bash
curl -X POST http://localhost:9050/users/test
```

The notification service should receive and log the Kafka messages.
