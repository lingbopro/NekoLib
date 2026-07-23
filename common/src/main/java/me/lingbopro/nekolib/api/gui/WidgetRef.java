package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import org.jspecify.annotations.Nullable;

public class WidgetRef<T extends AbstractWidget> {
    @Nullable
    private T current = null;
    public WidgetRef() {}
    public WidgetRef(@Nullable T widget) {
        current = widget;
    }
    public T use(T widget) {
        current = widget;
        return widget;
    }
    public T current() {
        return current;
    }
}
