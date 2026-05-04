# Kafka Implementation

This folder contains a basic event-driven setup using Spring Boot and Apache Kafka. The implementation is split into two services:

- `user-service`: exposes an HTTP API and publishes messages to Kafka.
- `notification-service`: listens to the Kafka topic and logs received messages.

The goal of this project is to demonstrate how one Spring Boot service can produce events and another service can consume those events asynchronously through Kafka.

## Project Structure

```text
kafka-implementation/
  user-service/
    src/main/java/com/example/kafka/user_service/
      controller/UserController.java
      config/kafkaTopicConfig.java
      UserServiceApplication.java
    src/main/resources/application.yaml

  notification-service/
    src/main/java/com/example/kafka/notification_service/
      consumer/UserKafkaConsumer.java
      NotificationServiceApplication.java
    src/main/resources/application.yaml
```

## Dependencies

Both services include `spring-boot-starter-kafka` in their `pom.xml` files.

This starter provides Spring Kafka support, including:

- `KafkaTemplate` for producing messages.
- `@KafkaListener` for consuming messages.
- Kafka auto-configuration based on `application.yaml`.
- Kafka test support through `spring-boot-starter-kafka-test`.

Both services also include web, JPA, PostgreSQL, and Lombok dependencies, although the Kafka demo flow mainly uses the web and Kafka pieces.

## Kafka Configuration

Both services connect to the same Kafka broker:

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
```

This means Kafka must be running locally on port `9092`.

Both services also define the same custom topic property:

```yaml
kafka:
  topic:
    user-random-topic: user-random-topic
```

Using a shared property keeps the topic name consistent between producer and consumer.

## Topic Creation

The `user-service` creates the Kafka topic through `kafkaTopicConfig.java`:

```java
@Bean
public NewTopic userRandomTopic() {
    return new NewTopic(KAFKA_RANDOM_USER_TOPIC, 3, (short) 1);
}
```

This creates a topic named `user-random-topic` with:

- `3` partitions.
- Replication factor `1`.

The replication factor is `1` because this is a local/demo setup. In a production Kafka cluster, the replication factor is usually greater than `1` for fault tolerance.

The topic has three partitions, which allows multiple consumers in the same consumer group to share the work.

## Producer Flow

The producer is implemented in `user-service/src/main/java/com/example/kafka/user_service/controller/UserController.java`.

The controller exposes this endpoint:

```text
POST /users/{message}
```

When the endpoint is called, it sends 1000 messages to Kafka:

```java
for(int i = 0; i < 1000; i++)
{
    kafkaTemplate.send(KAFKA_RANDOM_USER_TOPIC, "" + i % 3, message + i);
}
```

The important parts are:

- `KafkaTemplate<String, String>` is used to publish messages.
- The topic name comes from `kafka.topic.user-random-topic`.
- The key is `"" + i % 3`, so messages are sent with one of three keys: `0`, `1`, or `2`.
- The value is the path variable message plus the loop index.

Kafka uses the message key to decide which partition receives the message. Because this code cycles through three keys and the topic has three partitions, it demonstrates partition-based message distribution.

Example request:

```bash
curl -X POST http://localhost:9050/users/hello
```

The response is:

```text
Message queued
```

## Consumer Flow

The consumer is implemented in `notification-service/src/main/java/com/example/kafka/notification_service/consumer/UserKafkaConsumer.java`.

It has three listener methods:

```java
@KafkaListener(topics = "${kafka.topic.user-random-topic}")
public void hanldeUserRandomTopic1(String message) {
    log.info("message recieved hanldeUserRandomTopic1: {}", message);
}
```

The other two methods listen to the same topic in the same way.

The notification service configures a consumer group:

```yaml
spring:
  kafka:
    consumer:
      group-id: notification-service
```

Because all three listeners belong to the same application and use the same consumer group, Kafka distributes partitions among them. Since the topic has three partitions and there are three listener containers, each listener can be assigned one partition.

This means a message is normally handled by one listener, not all three listeners. Kafka consumer groups are designed for load sharing.

## End-to-End Flow

1. Kafka runs locally on `localhost:9092`.
2. `user-service` starts on port `9050`.
3. `user-service` creates the topic `user-random-topic` with three partitions.
4. `notification-service` starts on port `9060`.
5. `notification-service` subscribes to `user-random-topic`.
6. A client calls `POST /users/{message}` on the user service.
7. The user service publishes 1000 string messages to Kafka.
8. Kafka stores those messages in topic partitions.
9. The notification service consumes the messages and logs them.

## How To Run

Start Kafka locally first. The exact command depends on how Kafka is installed, but the broker must be available at:

```text
localhost:9092
```

Then start the services in separate terminals.

From `user-service`:

```bash
./mvnw spring-boot:run
```

From `notification-service`:

```bash
./mvnw spring-boot:run
```

Then send a test message:

```bash
curl -X POST http://localhost:9050/users/test
```

Check the `notification-service` logs. You should see log entries similar to:

```text
message recieved hanldeUserRandomTopic1: test0
message recieved hanldeUserRandomTopic2: test1
message recieved hanldeUserRandomTopic3: test2
```

The exact listener that receives each message depends on Kafka partition assignment.

## Important Concepts Demonstrated

### Producer

The producer is the service that sends data to Kafka. In this project, `user-service` is the producer.

It uses:

```java
KafkaTemplate<String, String>
```

### Topic

A Kafka topic is a named stream of messages. This project uses:

```text
user-random-topic
```

### Partition

The topic has three partitions. Partitions allow Kafka to scale message processing across multiple consumers.

### Message Key

The producer sends each message with a key:

```java
"" + i % 3
```

Kafka uses this key to choose a partition. Messages with the same key are sent to the same partition, preserving order for that key.

### Consumer

The consumer is the service that reads data from Kafka. In this project, `notification-service` is the consumer.

It uses:

```java
@KafkaListener
```

### Consumer Group

The notification service uses this consumer group:

```text
notification-service
```

Consumers in the same group divide partitions between themselves. This allows multiple consumers to process messages in parallel without every consumer receiving every message.

## Current Limitations

This implementation is intentionally simple. A more complete implementation could add:

- DTO/event classes instead of plain string messages.
- JSON serialization and deserialization.
- Error handling for producer failures.
- Consumer retry and dead-letter topic handling.
- Explicit listener container concurrency configuration.
- Integration tests using embedded Kafka or Testcontainers.
- A Docker Compose file for Kafka, PostgreSQL, and both services.

## Quick Summary

This project demonstrates the core Kafka pattern:

```text
HTTP request -> user-service -> Kafka topic -> notification-service -> log output
```

The `user-service` publishes messages to a Kafka topic using `KafkaTemplate`, and the `notification-service` consumes those messages using `@KafkaListener`.
