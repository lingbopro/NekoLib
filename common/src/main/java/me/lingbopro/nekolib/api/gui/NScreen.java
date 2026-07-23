package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

public abstract class NScreen extends Screen {
    private boolean rendering = false;
    private AbstractWidget root;
    @Nullable public Screen parent = null;
    public NScreen(Component title) {
        super(title);
    }
    public NScreen(Component title, @Nullable Screen parent) {
        super(title);
        this.parent = parent;
    }
    public NScreen(@Nullable Screen parent) {
        super(Component.empty());
        this.parent = parent;
    }

    protected <T> State<T> createState(T initial) {
        return new State<>(initial, this::requestRender);
    }

    protected abstract AbstractWidget build();

    protected void requestRender() {
        if (rendering) return;
        if (Minecraft.getInstance().screen == this) {
            rebuild();
        }
    }

    protected void rebuild() {
        rendering = true;
        clearWidgets();
        root = build();
        if (Minecraft.getInstance().screen == this && root != null) {
            root.setX(0);
            root.setY(0);
            root.setWidth(width);
            if (root instanceof UpdatableComponent) {
                ((UpdatableComponent) root).setScreenUpdateDispatcher(this::requestRender);
            }
            addRenderableWidget(root);
        }
        rendering = false;
    }

    public void back() {
        this.minecraft.setScreen(parent);
    }

    @Override
    protected void init() {
        super.init();
        rebuild();
    }
}
