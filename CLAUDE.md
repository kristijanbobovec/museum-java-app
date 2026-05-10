# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run the application
./mvnw clean javafx:run

# Build JAR
./mvnw clean package

# Compile only
./mvnw clean compile

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Run a single test method
./mvnw test -Dtest=ClassName#methodName
```

> **Note:** `pom.xml` references `me.kristijanbobovec.museum.HelloApplication` as the main class, but the actual entry point is `Main`. Update the `<mainClass>` in `pom.xml` if `javafx:run` fails.

## Architecture

Museum management desktop app: JavaFX 22 + FXML frontend, SQL Server backend, HikariCP connection pool. No separate backend service — everything runs in-process.

### Startup sequence

`Main.init()` → `DatabaseInitialization.init()` checks if the database exists, then creates it, runs DDL, and installs stored procedures (all from `SQLQueries` string constants) — then `Main.start()` loads `home.fxml`.

### Configuration

`ApplicationProperties` loads `src/main/resources/application.properties` at startup and falls back to hardcoded defaults. DB credentials come exclusively from env vars `DB_USERNAME` and `DB_PASSWORD` (default: `"development"` if unset). Connection target: `localhost:1401`, database `museum`.

### Database layer

- **`DatabaseManager`** — singleton HikariCP pool; call `DatabaseManager.getConnection()` to get a `Connection`.
- **`DatabaseInitialization`** — runs once at startup; DDL and procedures are embedded as Java string constants in `SQLQueries`, split on `GO` and batch-executed.
- All data access uses stored procedures named `usp_<Entity>_<Operation>`. No ad-hoc SQL elsewhere.

### Domain model

Sealed class hierarchy: `Person` (abstract, sealed) → `Curator` (final) | `User` (final).

Location hierarchy: `Country` → `City` → `Museum`. Content: `Museum` → `Exhibit` → `ImagePath`; `Exhibit` optionally belongs to a `MuseumCollection`. Many-to-many: `CuratorCollection` links `Curator` ↔ `MuseumCollection`.

Model instances from the database carry an ID field (e.g. `idMuseum`); in-memory instances created before insertion do not.

### UI layer

FXML views live under `src/main/resources/me/kristijanbobovec/museum/`. Controllers are in the `controller` package and must be `open`ed to `javafx.fxml` in `module-info.java`. The `database` package is also opened to `javafx.fxml` (required for initialization triggered from FXML context).

### Module system

`module-info.java` declares all `requires` and `opens`. When adding a new package that is injected by FXML or reflection, add an `opens` directive there.
