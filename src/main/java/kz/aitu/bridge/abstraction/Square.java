package kz.aitu.bridge.abstraction;

import kz.aitu.bridge.implementor.Renderer;
import kz.aitu.bridge.exception.InvalidShapeParameterException;

public final class Square extends Shape {
    private final double x;
    private final double y;
    private final double sideLength;

    public Square(double x, double y, double sideLength, Renderer renderer) {
        super(renderer);
        if (sideLength <= 0) {
            throw new InvalidShapeParameterException("Cannot create Square: side length must be positive but was " + sideLength);
        }
        this.x = x;
        this.y = y;
        this.sideLength = sideLength;
    }

    @Override
    public void draw() {
        getRenderer().renderSquare(x, y, sideLength);
    }

    @Override
    public String toString() {
        return "Square at (" + x + ", " + y + ") with side length " + sideLength;
    }
}
