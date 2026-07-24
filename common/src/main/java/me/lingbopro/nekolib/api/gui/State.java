package me.lingbopro.nekolib.api.gui;

import java.util.Objects;

/**
 * <p>A simple state class, just like React States.</p>
 * <p>
 * You shouldn't construct it directly in components.
 * Use the {@link CustomComponent#useState(Object)} wrapper method instead.
 * </p>
 *
 * @param <T> Type of the state data
 */
public class State<T> {
    private T value;
    private final Runnable onChange;

    public State(T value, Runnable onChange) {
        this.value = value;
        this.onChange = onChange;
    }

    /**
     * <p>Get the state data.</p>
     */
    public T get() {
        return value;
    }

    /**
     * <p>Set the state data (and trigger the onChange callback).</p>
     *
     * @param value New state data
     */
    public void set(T value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            onChange.run();
        }
    }
}
