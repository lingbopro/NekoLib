package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.gui.GuiGraphics;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A base class for any components that can contain other components.
 */
public abstract class ContainerWidget implements ComponentLike {
    protected final List<ComponentLike> children = new ArrayList<>();

    /**
     * Add a child to this container.
     *
     * @return returns {@code this} for chaining.
     */
    public ContainerWidget addChild(ComponentLike child) {
        children.add(child);
        return this;
    }

    @Override
    public abstract void buildNative(NativeBuildContext context);

    @Override
    public abstract void renderNative(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    @Override
    public abstract int getWidth();

    @Override
    public abstract int getHeight();

    @Override
    public abstract int getX();

    @Override
    public abstract int getY();

    @Override
    public abstract void setX(int x);

    @Override
    public abstract void setY(int y);
}
