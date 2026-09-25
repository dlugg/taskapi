# Task REST API

A REST API for the tasks of my [Telegram To-Do Bot](https://github.com/dlugg/telegram-bot), built with Spring Boot.

The API works on the bot's PostgreSQL database, so a task created through the API shows up in the bot and the other way round. For the same data this is the third storage implementation: the bot started with in-memory collections, then moved to plain JDBC, and this project uses Spring Data JPA.

## Tech stack

- Java 21
- Spring Boot 4.1: Web MVC, Data JPA
- PostgreSQL
- JUnit 5, MockMvc
- Maven

## Endpoints

Users are identified by their Telegram chat ID. The service resolves it to the internal user ID from the `users` table, so clients never need to know database IDs.

| Method | Path | Result |
|---|---|---|
| `POST` | `/tasks/{chatId}` | creates a task from `{"taskText": "..."}`, returns `201 Created` |
| `GET` | `/tasks/{chatId}` | returns the user's tasks in creation order |
| `DELETE` | `/tasks/{chatId}/{position}` | deletes the task at a 1-based position in that list, returns `204 No Content` |
| `GET` | `/ping` | returns `pong`, a quick check that the app is up |

An unknown chat ID returns `404 Not Found` with an error message. \
A position outside the list returns `400 Bad Request` with an error message.

```bash
curl -i -X POST http://localhost:8080/tasks/12345 \
  -H "Content-Type: application/json" \
  -d '{"taskText":"buy bread"}'

curl http://localhost:8080/tasks/12345

curl -i -X DELETE http://localhost:8080/tasks/12345/1
```

## Running locally

The API does not create tables. `spring.jpa.hibernate.ddl-auto=validate` only checks that the entities match the existing schema, and the schema belongs to the bot: [`schema.sql`](https://github.com/dlugg/telegram-bot/blob/main/src/main/resources/schema.sql).

1. Create a PostgreSQL database `javabot` and apply `schema.sql` from the bot repository.
2. Set the database password: `export DATABASE_PASSWORD=...`
3. Start the app: `mvn spring-boot:run`. It listens on port 8080.

The connection URL and user are in `src/main/resources/application.properties`. The password is read from the environment and never committed.

## Tests

```bash
mvn test
```

Tests use a separate database `javabot_test` with the same schema, configured in `src/test/resources/application.properties`, so they never touch the main database. Every test runs inside a transaction that is rolled back afterwards, which keeps the test database empty between runs.

- `TaskServiceTests` covers the service logic: creating tasks, unknown users, deletion by position including out-of-range positions.
- `TaskControllerTests` covers the HTTP layer through MockMvc: status codes and JSON responses.
