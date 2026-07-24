package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.gui.GuiGraphics;
import org.jspecify.annotations.NonNull;

/**
 * The base interface for components using the NekoLib GUI System.
 */
public interface ComponentLike {
    /**
     * Build the component with native Minecraft GUI elements.
     *
     * @param context The render context
     */
    void buildNative(NativeBuildContext context);

    /**
     * Render the component to the native screen graphics.
     *
     * @see net.minecraft.client.gui.components.AbstractWidget#render(GuiGraphics, int, int, float)
     */
    void renderNative(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    /**
     * Get the width of the component, including paddings.
     */
    int getWidth();

    /**
     * Get the height of the component, including paddings.
     */
    int getHeight();

    /**
     * Get the x position of the component.
     */
    int getX();

    /**
     * Get the y position of the component.
     */
    int getY();

    /**
     * Set the x position of the component.
     */
    void setX(int x);

    /**
     * Set the y position of the component.
     */
    void setY(int y);
}
