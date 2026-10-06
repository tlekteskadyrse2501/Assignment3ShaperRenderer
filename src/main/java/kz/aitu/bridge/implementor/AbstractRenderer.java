package kz.aitu.bridge.implementor;

public abstract class AbstractRenderer implements Renderer {
    private final String rendererType;

    protected AbstractRenderer(String rendererType) {
        this.rendererType = rendererType;
    }

    protected final void printShape(String shapeName, double x, double y, String dimensions) {
        System.out.println("[" + rendererType + "] " + shapeName + " " + getFormatDescription() + " at (" + x + ", " + y + ") with " + dimensions);
    }

    protected abstract String getFormatDescription();

    @Override
    public String getType() {
        return rendererType;
    }
}