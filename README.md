# Library Management System — Layered Architecture Deep Dive

A library catalog and borrowing system built with **classic Spring MVC**, deliberately without Spring Boot's auto-configuration. I built this project to understand what Spring Boot abstracts away — manually wiring the servlet dispatcher, data source, and security configuration that Spring Boot normally configures automatically.

## Features

- Full CRUD management for Books, Authors, Users, and Borrow records.
- Manual Spring configuration: `DispatcherServlet` initialization, `DataSource`, `EntityManagerFactory`, and `TransactionManager` beans, registered without `web.xml` via `AbstractDispatcherServletInitializer`.
- **Three parallel implementations of the same repository contract**, to directly compare data-access strategies:
    - In-memory (`CopyOnWriteArrayList`) — for fast prototyping without a database.
    - Raw SQL via `NamedParameterJdbcTemplate`, including manual `JOIN` queries and `RowMapper`s.
    - Spring Data JPA — the standard, declarative approach.
- Thymeleaf-rendered CRUD views for all core entities.
- Role-based access control via manually configured Spring Security.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Classic Spring MVC (no Spring Boot) |
| Security | Spring Security |
| Persistence | JDBC (`NamedParameterJdbcTemplate`), Spring Data JPA |
| Database | PostgreSQL |
| Templating | Thymeleaf |
| Server | Apache Tomcat |
| Build Tool | Maven |

## Architecture

Unlike my Spring Boot projects, this application has no auto-configuration to fall back on. Every bean — the `DataSource`, the JPA `EntityManagerFactory`, the `DispatcherServlet`, and the security filter chain — is registered manually in Java config classes. This made it much clearer to me exactly what `spring-boot-starter-data-jpa` and `spring-boot-starter-web` automate under the hood.

The repository layer is split into three packages (`InMemory`, `DataBase`, and the standard Spring Data JPA repositories), all implementing the same interface — so the service layer never needs to know which strategy is backing it.

## Getting Started

### Prerequisites

- Java 17+
- Apache Tomcat (10.x recommended)
- PostgreSQL running locally
- Maven

### Setup

1. Clone the repository
```bash
   git clone https://github.com/BekzodJumabaev/Library_project_1.git
   cd Library_project_1
```

2. Create a PostgreSQL database named `lib` (or update the connection URL in `DataSourceConfig.java` to match your own database name).

3. Set your database password as an environment variable in your Tomcat run configuration:
4. Build the WAR file:
```bash
   mvn clean package
```

5. Deploy the generated `.war` file to Tomcat (or run it directly through your IDE's Tomcat integration) and open `http://localhost:8080`.

## What I Learned

This was the project where Spring Boot "clicked" for me — by manually configuring the `DataSource`, `EntityManagerFactory`, and dispatcher servlet that Spring Boot normally hides, I got a much clearer picture of what each starter dependency actually does. Implementing the same repository interface three different ways (in-memory, raw JDBC, and JPA) also reinforced how dependency inversion lets you swap a data-access strategy without ever touching the service layer.

## License

This project was built as part of my studies at PDP Academy (Java Backend track) and is available for educational reference.