package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import org.jspecify.annotations.Nullable;

/**
 * <p>A simple reference class, just like React Refs.</p>
 * <p>Can be used to access the widget after it has been rendered to the screen.</p>
 *
 * @param <T> Type of the reference data
 */
public class Ref<T extends AbstractWidget> {
    @Nullable
    private T current = null;

    public Ref() {
    }

    public Ref(@Nullable T widget) {
        current = widget;
    }

    /**
     * <p>Store the reference of the object and return the object itself.</p>
     * <p>Can be used to wrap a widget to store its reference.</p>
     */
    public T use(T widget) {
        current = widget;
        return widget;
    }

    /**
     * <p>Get the reference data.</p>
     * <p>If the reference is not set, default value is {@code null}.</p>
     */
    public T current() {
        return current;
    }
}
