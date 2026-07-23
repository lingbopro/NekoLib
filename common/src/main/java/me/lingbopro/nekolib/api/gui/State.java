package me.lingbopro.nekolib.api.gui;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class State<T> {
    @Nullable private T value;
    private final Runnable onChange;
    public State(@Nullable T value, Runnable onChange)
    {
        this.value = value;
        this.onChange = onChange;
    }
    public T get() {
        return value;
    }
    public void set(@Nullable T value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            onChange.run();
        }
    }
}
