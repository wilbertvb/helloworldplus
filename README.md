# HelloWorldPlus

This is a Java 21 Maven project demonstrating version control with Git.

## Features
- Hello World application with Maven 3.9.9
- Logging capability with timestamp formatting
- JUnit 5 tests with parameterized test support
- OWASP security scanning with dependency-check
- Multiple Maven plugins for comprehensive build management

## Building

```bash
mvn clean install
```

## Running

```bash
java -cp target/classes com.example.HelloWorldPlus
```

## Project Structure

```
helloworldplus/
├── pom.xml
├── src/
│   ├── main/java/com/example/
│   │   └── HelloWorldPlus.java
│   └── test/java/com/example/
│       └── HelloWorldPlusTest.java
└── README.md
```

## Git Merge Scenarios

This project demonstrates real-world Git merge scenarios:
- Fast-forward merges (hotfixes)
- Three-way merges (long-running features)
- Non-fast-forward merges (explicit merge commits)

See `git_merge_scenario.md` for comprehensive examples.

@Autor: Wilbert Valverde Barrantes
