# VMetrix Query Manager POC

Dynamic metadata-driven SQL query engine built with Spring Boot.

---

# Overview

This project implements a dynamic query engine capable of:

* Generating SQL dynamically from JSON requests
* Resolving entities and joins using metadata definitions
* Supporting recursive filter groups (AND / OR)
* Applying comparator strategies dynamically
* Producing parameterized SQL queries
* Validating requests before SQL generation
* Executing generated queries using JDBC

The solution was designed with a layered and extensible architecture focused on separation of responsibilities and maintainability.

---

# Tech Stack

* Java 17
* Spring Boot 2.7.x
* Spring JDBC
* H2 Database
* Maven
* Lombok
* Swagger / OpenAPI
* JUnit 5
* MockMvc

---

# Project Structure

```text
src/main/java/com/vmetrix/querymanager
│
├── api
├── config
├── execution
├── metadata
├── query
├── shared
├── sql
└── validation
```

---

# Architecture Flow

```text
HTTP Request
    ↓
Controller
    ↓
Query Engine
    ↓
Parser
    ↓
Validation
    ↓
Resolver
    ↓
SQL Builder
    ↓
SQL Generator
    ↓
Execution Layer
    ↓
Response Mapper
```

---

# Main Features

## Metadata-Driven Querying

Entities, fields, relationships, and comparators are loaded dynamically from JSON metadata files.

Example:

```text
src/main/resources/metadata
```

---

## Dynamic Join Resolution

Joins are automatically resolved based on entity relationships.

Example:

```sql
LEFT JOIN PARTY p
ON t.COUNTERPARTY_ID = p.PARTY_ID
```

---

## Recursive Filters

Supports nested filter groups:

```json
{
  "operator": "AND",
  "conditions": [
    {
      "entity": "transaction",
      "field": "amount",
      "comparator": "greaterThan",
      "value": 1000
    },
    {
      "operator": "OR",
      "conditions": []
    }
  ]
}
```

---

## Comparator Strategy Pattern

Comparators are implemented using strategy pattern.

Examples:

* equals
* greaterThan
* lessThan
* like
* between
* in
* isNull
* isNotNull

---

## Parameterized SQL

Generated SQL uses named parameters to prevent SQL injection.

Example:

```sql
WHERE t.AMOUNT > :p1
```

---

# Running the Application

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---

# Swagger UI

Swagger documentation:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# H2 Console

H2 console:

```text
http://localhost:8080/h2-console
```

Connection:

```text
JDBC URL: jdbc:h2:mem:vmetrixdb
Username: sa
Password:
```

---

# Example Request

Endpoint:

```http
POST /api/query/build
```

Request body:

```json
{
  "select": [
    {
      "entity": "transaction",
      "field": "amount"
    },
    {
      "entity": "party",
      "field": "partyName"
    }
  ],
  "filters": {
    "operator": "AND",
    "conditions": [
      {
        "entity": "transaction",
        "field": "amount",
        "comparator": "greaterThan",
        "value": 1000
      },
      {
        "entity": "party",
        "field": "country",
        "comparator": "equals",
        "value": "CL"
      }
    ]
  },
  "sorting": [
    {
      "entity": "transaction",
      "field": "txnDate",
      "direction": "DESC"
    }
  ],
  "maxResults": 10
}
```

---

# Example Response

```json
{
  "sql": "SELECT t.AMOUNT, p.PARTY_NAME FROM TX_TRANSACTION t LEFT JOIN PARTY p ON t.COUNTERPARTY_ID = p.PARTY_ID WHERE (t.AMOUNT > :p1 AND p.COUNTRY = :p2) ORDER BY t.TXN_DATE DESC LIMIT 10",
  "parameters": {
    "p1": 1000,
    "p2": "CL"
  },
  "resolvedTables": [
    "transaction",
    "party"
  ],
  "resolvedJoins": [
    "party"
  ]
}
```

---

# Testing

The project includes:

* Unit tests
* Integration tests
* Controller endpoint tests
* SQL generation validation tests

Run tests:

```bash
mvn test
```

---

# Design Decisions

## Why Spring JDBC instead of JPA?

This challenge requires dynamic SQL generation at runtime.

Spring JDBC provides:

* Better control over generated SQL
* Simpler dynamic query handling
* Explicit parameter binding
* Lower complexity for metadata-driven queries

---

## Why Metadata-Based Architecture?

Metadata-driven querying allows:

* Dynamic entity resolution
* Configurable joins
* Flexible field definitions
* Easier extensibility

without hardcoding SQL logic.

---

## Why Comparator Strategy Pattern?

Comparator strategies isolate SQL comparison behavior and make the engine extensible.

New comparators can be added without modifying existing builders.

---

# AI Usage in Development

## Tool used

**Claude Code** (Anthropic) was used throughout the development of this PoC as the primary AI-assisted development tool, running directly in the terminal via the CLI.

## Tasks where AI helped

* **Architecture design**: initial layer breakdown (parser → validator → resolver → builder → generator → executor), identification of applicable patterns (Strategy for comparators, Builder for SQL clauses, Facade for query orchestration).
* **Boilerplate acceleration**: generating repetitive but structurally consistent classes such as comparator implementations, request/response DTOs, and Spring `@Component` wrappers.
* **Metadata model design**: defining the JSON structure for entity, field, relationship, and comparator configuration, and aligning it with the requirement that zero Java code changes are needed to add a new field or entity.
* **Test scaffolding**: generating the initial structure for integration tests using `@SpringBootTest` + `MockMvc` and unit tests for SQL builders.
* **Documentation**: README sections and inline Swagger annotations.

## Cases where AI output was corrected or discarded

* **JOIN resolution logic**: the first AI-generated version resolved joins by scanning all declared relationships for any entity appearing in the query. This was incorrect — it could include unneeded joins when the same physical table (PARTY) is reachable via multiple aliases (counterparty, issuer). The logic was rewritten to resolve joins only for the specific aliases explicitly referenced in the select and filter clauses.
* **WHERE clause recursion**: the initial generated implementation flattened nested AND/OR groups into a single level. It was discarded and replaced with a recursive tree traversal that properly parenthesizes nested groups, supporting arbitrary nesting depth as required.
* **Comparator factory**: the first suggestion used a `HashMap` with manually registered entries, which would require code changes to add a new comparator. It was replaced with Spring's dependency injection collecting all `SqlComparator` beans automatically — zero registration code needed.
* **Test assertions**: several AI-generated test assertions were too brittle (exact SQL string matching with hardcoded spacing). These were replaced with `containsIgnoringCase` checks on SQL clauses and structural assertions on the parameters map.

## What I learned about effective AI usage in development

* **Specificity drives quality**: vague prompts like "generate a query builder" produced generic code. Prompts that described the exact contract — inputs, outputs, and constraints — produced code that required far fewer corrections.
* **AI is strong at structure, weak at domain invariants**: the generated architecture was solid, but subtle domain rules (e.g., PARTY reached via two different FK paths) required human judgment. AI accelerated the scaffolding; the business logic correctness required manual review every time.
* **Iterative correction is faster than regeneration**: when AI output was partially wrong, describing the specific failure and asking for a targeted fix was more efficient than starting over with a new prompt.
* **Tests are the best AI guardrail**: writing the test first and then asking the AI to implement the code to pass it produced more reliable results than generating both together.

---

# Future Improvements

Potential next steps:

* Pagination support
* Multi-root query resolution
* Dialect abstraction
* Query execution caching
* Metadata persistence
* Query optimization strategies
* Authentication / authorization
* Advanced aggregation support

---

# Author

Juan Araos
