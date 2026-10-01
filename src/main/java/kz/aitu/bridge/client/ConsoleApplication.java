package kz.aitu.bridge.client;

import kz.aitu.bridge.abstraction.Circle;
import kz.aitu.bridge.abstraction.Shape;
import kz.aitu.bridge.abstraction.Square;
import kz.aitu.bridge.implementor.RasterRenderer;
import kz.aitu.bridge.implementor.Renderer;
import kz.aitu.bridge.implementor.VectorRenderer;
import kz.aitu.bridge.exception.InvalidShapeParameterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ConsoleApplication {
    private final List<Shape> shapes = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    public void run() {
        while (running) {
            executeMenu();
        }
    }

    private void executeMenu() {
        try {
            printMenu();
            if (!scanner.hasNextLine()) {
                running = false;
                return;
            }
            processChoice(readInt("Choice: "));
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("\n--- Bridge Pattern Menu ---");
        System.out.println("1. Create Circle");
        System.out.println("2. Create Square");
        System.out.println("3. Show all shapes");
        System.out.println("4. Draw one shape");
        System.out.println("5. Draw all shapes");
        System.out.println("6. Switch shape renderer");
        System.out.println("7. Run automatic demo");
        System.out.println("0. Exit");
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1 -> createCircle();
            case 2 -> createSquare();
            case 3 -> showShapes();
            case 4 -> drawOneShape();
            case 5 -> drawAllShapes();
            case 6 -> switchRenderer();
            case 7 -> runDemo();
            case 0 -> running = false;
            default -> throw new InvalidShapeParameterException("Invalid menu option: " + choice);
        }
    }

    private void createCircle() {
        double x = readDouble("X coordinate: ");
        double y = readDouble("Y coordinate: ");
        double radius = readDouble("Radius: ");
        Renderer renderer = selectRenderer();
        shapes.add(new Circle(x, y, radius, renderer));
        System.out.println("Circle created.");
    }

    private void createSquare() {
        double x = readDouble("X coordinate: ");
        double y = readDouble("Y coordinate: ");
        double side = readDouble("Side length: ");
        Renderer renderer = selectRenderer();
        shapes.add(new Square(x, y, side, renderer));
        System.out.println("Square created.");
    }

    private void showShapes() {
        if (shapes.isEmpty()) {
            throw new InvalidShapeParameterException("No shapes available.");
        }
        for (int i = 0; i < shapes.size(); i++) {
            System.out.println(i + ": " + shapes.get(i).toString());
        }
    }

    private void drawOneShape() {
        Shape shape = selectShape();
        shape.draw();
    }

    private void drawAllShapes() {
        if (shapes.isEmpty()) {
            throw new InvalidShapeParameterException("No shapes available to draw.");
        }
        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    private void switchRenderer() {
        Shape shape = selectShape();
        Renderer newRenderer = selectRenderer();
        shape.setRenderer(newRenderer);
        System.out.println("Renderer switched successfully.");
        shape.draw();
    }

    private void runDemo() {
        Renderer vector = new VectorRenderer();
        Renderer raster = new RasterRenderer();
        Shape demoCircle = new Circle(10, 10, 5, vector);
        Shape demoSquare = new Square(20, 20, 8, raster);
        System.out.println("Initial draw:");
        demoCircle.draw();
        demoSquare.draw();
        System.out.println("Switching renderers at runtime:");
        demoCircle.setRenderer(raster);
        demoSquare.setRenderer(vector);
        demoCircle.draw();
        demoSquare.draw();
    }

    private Renderer selectRenderer() {
        System.out.println("Select Renderer (1 = Vector, 2 = Raster):");
        int choice = readInt("Choice: ");
        if (choice == 1) {
            return new VectorRenderer();
        }
        if (choice == 2) {
            return new RasterRenderer();
        }
        throw new InvalidShapeParameterException("Invalid renderer choice: " + choice);
    }

    private Shape selectShape() {
        if (shapes.isEmpty()) {
            throw new InvalidShapeParameterException("No shapes available.");
        }
        int index = readInt("Shape index: ");
        if (index < 0 || index >= shapes.size()) {
            throw new InvalidShapeParameterException("Invalid shape index: " + index);
        }
        return shapes.get(index);
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidShapeParameterException("Invalid number format for integer");
        }
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new InvalidShapeParameterException("Invalid number format for decimal");
        }
    }
}
