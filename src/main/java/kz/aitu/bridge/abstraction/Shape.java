package kz.aitu.bridge.abstraction;

import kz.aitu.bridge.implementor.Renderer;
import kz.aitu.bridge.exception.InvalidShapeParameterException;

public abstract class Shape {
    private Renderer renderer;

    protected Shape(Renderer renderer) {
        setRenderer(renderer);
    }

    public final void setRenderer(Renderer renderer) {
        if (renderer == null) {
            throw new InvalidShapeParameterException("Cannot set a null renderer to Shape");
        }
        this.renderer = renderer;
    }

    protected final Renderer getRenderer() {
        return renderer;
    }

    public abstract void draw();
}
