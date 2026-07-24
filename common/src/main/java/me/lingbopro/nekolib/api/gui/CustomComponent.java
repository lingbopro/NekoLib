package me.lingbopro.nekolib.api.gui;

import org.jspecify.annotations.Nullable;

/**
 * <p>A base class for custom components (like React class components).</p>
 */
public abstract class CustomComponent implements ComponentLike {
    private ComponentLike root;
    @Nullable
    protected NativeBuildContext nativeBuildContext;
    private int x;
    private int y;

    /**
     * <p>Create a new state variable.</p>
     *
     * @see State
     */
    protected <T> State<T> useState(T initial) {
        return new State<>(initial, () -> {
            if (nativeBuildContext != null) nativeBuildContext.requestRebuild.run();
        });
    }

    /**
     * <p>The "render" function that will be called when the component is rendered (or re-rendered).</p>
     * <p>You should implement this in your custom component class.</p>
     */
    protected abstract ComponentLike build(NativeBuildContext context);

    @Override
    public void buildNative(NativeBuildContext context) {
        nativeBuildContext = context;
        root = build(context);
        root.setX(x);
        root.setY(y);
        root.buildNative(context);
    }

    @Override
    public int getWidth() {
        return root.getWidth();
    }

    @Override
    public int getHeight() {
        return root.getHeight();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
