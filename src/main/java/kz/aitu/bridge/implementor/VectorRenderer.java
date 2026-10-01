package kz.aitu.bridge.implementor;

public final class VectorRenderer extends AbstractRenderer {
    public VectorRenderer() {
        super("VECTOR");
    }

    @Override
    protected String getFormatDescription() {
        return "path";
    }

    @Override
    public void renderCircle(double x, double y, double radius) {
        printShape("Circle", x, y, "radius " + radius);
    }

    @Override
    public void renderSquare(double x, double y, double side) {
        printShape("Square", x, y, "side length " + side);
    }
}
