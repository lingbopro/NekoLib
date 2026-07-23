package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public abstract class NComponent extends AbstractWidget implements UpdatableComponent {
    private AbstractWidget root;
    private Runnable screenUpdateDispatcher;

    public NComponent(int x, int y, int width, int height, Component component) {
        super(x, y, width, height, component);
    }

    protected <T> State<T> createState(T initial) {
        return new State<>(initial, screenUpdateDispatcher);
    }

    protected abstract AbstractWidget build();

    public void setScreenUpdateDispatcher(Runnable dispatcher) {
        screenUpdateDispatcher = dispatcher;
    }

    @Override
    protected void renderWidget(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        root = build();
        if (root instanceof UpdatableComponent) {
            ((UpdatableComponent) root).setScreenUpdateDispatcher(screenUpdateDispatcher);
        }
        root.setWidth(width);
        root.setHeight(height);
        root.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput narrationElementOutput) {}
}
