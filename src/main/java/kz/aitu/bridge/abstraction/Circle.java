package kz.aitu.bridge.abstraction;

import kz.aitu.bridge.implementor.Renderer;
import kz.aitu.bridge.exception.InvalidShapeParameterException;

public final class Circle extends Shape {
    private double x;
    private double y;
    private final double radius;

    public Circle(double x, double y, double radius, Renderer renderer) {
        super(renderer);
        if (radius <= 0) {
            throw new InvalidShapeParameterException("Cannot create Circle: radius must be positive but was " + radius);
        }
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }    public double getRadius() { return radius; }

    @Override
    public void draw() {
        getRenderer().renderCircle(x, y, radius);
    }

    @Override
    public String toString() {
        return "Circle at (" + x + ", " + y + ") with radius " + radius;
    }
}