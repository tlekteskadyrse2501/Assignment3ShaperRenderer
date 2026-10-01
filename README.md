# Assignment 3: Design Patterns (Bridge)

## Purpose
This project is an interactive console application demonstrating the **Bridge structural design pattern**.
It separates the `Shape` abstraction hierarchy from the `Renderer` implementation hierarchy, allowing them to vary independently. The application proves this by letting users switch the renderer of any shape dynamically at runtime without modifying the shape's original instance or class.

## Project Structure
- `kz.aitu.bridge.Main`: Application entry point.
- `kz.aitu.bridge.abstraction`: Contains the high-level `Shape` abstraction and refined shapes (`Circle`, `Square`).
- `kz.aitu.bridge.implementor`: Contains the `Renderer` interface and concrete drawing modules (`VectorRenderer`, `RasterRenderer`).
- `kz.aitu.bridge.client`: Contains the interactive `ConsoleApplication` menu handling user input smoothly.
- `kz.aitu.bridge.exception`: Contains domain-specific exceptions, keeping logic clean.
- `kz.aitu.bridge.report`: Contains the `.docx` document generation tool.
- `docs/Bridge_Pattern_Report.docx`: The generated assignment report covering theory and Clean Code principles applied.

## How to Build and Run
1. **To build the project**:
   ```bash
   mvn clean compile
   ```
2. **To run the application via Maven**:
   ```bash
   mvn exec:java -Dexec.mainClass="kz.aitu.bridge.Main"
   ```
3. **To generate the specific Word document report**:
   ```bash
   mvn exec:java -Dexec.mainClass="kz.aitu.bridge.report.ReportGenerator"
   ```
Alternatively, open the project in IntelliJ IDEA, and run `kz.aitu.bridge.Main` using the standard IDE runner.

## Features and Menu Overview
- **1-2**: Create shapes by passing coordinates, size, and choosing the underlying renderer.
- **3**: View a list of created shapes.
- **4-5**: Delegate rendering to the selected implementation (demonstrating pattern bridge execution).
- **6**: Switch the renderer of an existing shape dynamically (demonstrating run-time decoupling).
- **7**: See a short hard-coded proof loop (demo) of the Bridge pattern in action.