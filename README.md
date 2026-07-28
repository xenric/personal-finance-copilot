# personal-finance-copilot
A personal project exploring AI-assisted personal finance reviews, portfolio tracking, and personalized investment insights for better financial decisions.

## Development Stack

This project will be implemented with Java.

Recommended MVP stack:

- Java 21
- Spring Boot
- Thymeleaf
- Gradle
- JUnit 5
- Jackson
- Jakarta Bean Validation

## UI Direction

The first MVP will use Spring Boot with Thymeleaf for server-rendered pages.

This keeps the initial project as a single Java application and avoids adding a separate frontend build pipeline too early. The UI should focus on the core workflow:

1. Create a financial profile and goals
2. Enter income, expenses, assets, liabilities, and holdings
3. Calculate financial metrics
4. Generate rule-based warnings
5. Create and compare monthly snapshots
6. Generate an AI-assisted review report

If the product later needs a richer frontend, the UI can be split into a React or TypeScript client after the core domain, storage, and analysis flows are stable.
