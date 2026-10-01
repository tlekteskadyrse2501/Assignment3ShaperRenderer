package kz.aitu.bridge.implementor;

public final class RasterRenderer extends AbstractRenderer {
    public RasterRenderer() {
        super("RASTER");
    }

    @Override
    protected String getFormatDescription() {
        return "pixels";
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
