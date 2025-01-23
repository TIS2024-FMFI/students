# Students

Maintenance of personal data of former students of the university.

## Features
- **Student Data Management**: View personal student data and access historical records.
- **JavaFX Interface**: A clean and responsive GUI for managing student data.
- **PDF Generation**: Generate downloadable PDFs for student records.
- **SSH Connectivity**: Integration for secure remote operations through SSH.

## Requirements
Before running the application, make sure you have the following installed:

### Gradle
- Gradle is used as the build tool to manage dependencies and project tasks.

### Required Java Libraries
- `org.openjfx:javafx-controls:17.0.8`
- `org.openjfx:javafx-fxml:17.0.8`
- `org.openjfx:javafx-graphics:17.0.8`
- `jakarta.xml.bind:jakarta.xml.bind-api:3.0.1`
- `org.glassfish.jaxb:jaxb-runtime:3.0.1`
- `com.github.mwiede:jsch:0.2.16`
- `org.projectlombok:lombok:1.18.30`
- `com.itextpdf:kernel:9.0.0`
- `com.itextpdf:layout:9.0.0`

These dependencies will be automatically resolved when running the application through Gradle.

## Getting Started

### Prerequisites
Ensure that you have the following installed:
1. **Java 17 or higher**.
2. **Gradle** (You can use the Gradle wrapper for this project).

### Running the Application
Follow these steps to set up and run the application:

#### 1. Clone the Repository
Clone the repository or download the source code:

```bash
git clone https://github.com/yourusername/students.git
cd students
```

#### 2. Build the Project
You can build and run the project using the Gradle wrapper:

```bash
./gradlew build
```

#### 3. Run the Application
Once the project is built, you can run the application:

```bash
./gradlew run
```

This command will compile the code and start the JavaFX application.

## License
This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.
