# Residential Energy Rating Desktop Tool

A Java Swing desktop application that simulates a residential energy rating workflow.  
This project is designed as a practical Java desktop application portfolio project, inspired by compliance-style
residential energy assessment software.

## Important Disclaimer

This is a simplified software engineering practice project. It is **not** an official NatHERS or FirstRate5 calculation
tool.

## Features

- Java Swing desktop UI
- Residential project data entry form
- Building input validation
- Simplified energy rating calculation
- Certificate-style result preview
- SQLite local persistence
- Project list, edit, save and delete
- JUnit 5 unit tests
- Maven build
- SwingWorker background calculation to keep UI responsive

## Technology Stack

- Java 17
- Java Swing
- Maven
- SQLite
- JUnit 5
- SLF4J + Logback

## Architecture

The project uses a simple layered structure:

```text
UI Layer       -> Swing panels and frames
Service Layer  -> validation, project management and rating calculation
Repository     -> SQLite persistence
Model          -> Project and RatingResult domain objects
```

This keeps the business logic testable without depending on the Swing UI.

## How to Run

Make sure Java 17 and Maven are installed.

```bash
mvn clean test
mvn exec:java
```

Or open the project in IntelliJ IDEA and run:

```text
com.energyrating.App
```

## Suggested Interview Explanation

I built this project to practise Java desktop development with Swing in a compliance-style business domain. The
application separates UI, business logic, validation and persistence layers, making the rating rules testable and
maintainable. I also used SwingWorker to avoid blocking the Event Dispatch Thread during the assessment workflow and
added JUnit tests and GitHub Actions CI to improve delivery confidence.
