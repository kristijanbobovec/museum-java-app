# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Run Commands

### Build
```bash
./mvnw clean package
```
Compiles the project and packages it into a JAR file in the `target/` directory.

### Run the Application
```bash
./mvnw clean javafx:run
```
Compiles and runs the JavaFX application. The main entry point is `me.kristijanbobovec.museum.Main`.

### Compile Only
```bash
./mvnw clean compile
```
Compiles the source code without packaging.

### Install Dependencies
```bash
./mvnw clean install
```
Installs all Maven dependencies defined in pom.xml.

## Project Structure

### Technology Stack
- **UI Framework**: JavaFX 22.0.1 with FXML for UI layouts
- **Validation**: ValidatorFX 0.6.1 for form validation
- **Testing**: JUnit 5 (Jupiter 5.12.1) for unit tests
- **Java Version**: Java 22 (source and target compatibility)
- **Database**: SQL Server with stored procedures for all data access
- **Build System**: Maven 3.8.5

### Source Layout
```
src/main/
  java/
    me.kristijanbobovec.museum/
      Main.java                         # JavaFX application entry point
      module-info.java                  # Module declarations
      model/                            # Domain model classes
      controller/                       # JavaFX controllers
  resources/
    me/kristijanbobovec/museum/view/
      hello-world.fxml                 # JavaFX UI layout

sql/
  CreateDatabases.sql                  # Database schema (SQL Server)
  Procedures.sql                       # All stored procedures
  Reset.sql                            # Database reset script
```

## Architecture Overview

This is a museum management desktop application with a hierarchical data model and role-based user access control.

### Domain Model

**Location Hierarchy**
- `Country` - Contains cities
- `City` - Contains museums
- `Museum` - Core entity, has exhibits and curators

**Content Management**
- `Exhibit` - Museum exhibits belonging to collections
- `MuseumCollection` - Named collections of exhibits
- `ImagePath` - Image files associated with exhibits

**User & Access Control**
- `Person` (sealed abstract class) - Base for Curator and User
  - `Curator` (final) - Museum staff managing collections
  - `User` (final) - System users with roles
- `Role` - Defines role type (User or Administrator)
- `RoleType` (enum) - USER, ADMINISTRATOR

**Many-to-Many Relationship**
- `CuratorCollection` - Junction table linking curators to collections

### Data Access Layer

The database uses SQL Server with stored procedures following a naming convention: `usp_<TableName>_<Operation>`. Each entity has standard CRUD procedures:
- `usp_<Entity>_GetAll` - Retrieve all records
- `usp_<Entity>_GetByID` - Retrieve single record
- `usp_<Entity>_Insert` - Create new record
- `usp_<Entity>_Update` - Modify record
- `usp_<Entity>_Delete` - Remove record

Some entities have additional specialized procedures (e.g., `usp_Museum_GetByCity`, `usp_Exhibit_GetByMuseum`).

### UI Layer

- **FXML-based Views**: Views are defined in XML format (hello-world.fxml)
- **Controllers**: Bind to FXML views and handle user interactions (HelloWorldController)
- **Module System**: Uses Java module system (module-info.java) with javafx.fxml and javafx.controls exports

### Model Implementation Details

All model classes implement:
- `equals()` and `hashCode()` based on ID fields
- Constructors for creating instances with and without database IDs
- Getters and setters for all properties
- `toString()` for debugging

Instances from the database include an ID (e.g., `idMuseum`, `idUser`), while new instances created in memory before database insertion do not.

## Key Files to Understand

- **pom.xml**: Maven configuration, dependencies, and build plugins (JavaFX Maven plugin)
- **module-info.java**: Java module declarations required by JavaFX
- **Main.java**: Application bootstrap, loads hello-world.fxml
- **Model classes** (in `src/main/java/me/kristijanbobovec/museum/model/`): Domain entities
- **Procedures.sql**: Complete database contract and data access logic

## Development Notes

- The application uses Java 22 features including sealed classes and records (if present)
- ValidatorFX is used for form validation in the UI
- The project is structured as a monolithic desktop application with no separate backend/frontend
- No test files currently exist; unit tests should be added under `src/test/java/`
