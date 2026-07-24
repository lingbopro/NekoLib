package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.gui.components.AbstractWidget;

import java.util.function.Function;

public class NativeBuildContext {
    /**
     * The {@code addRenderableWidget} function of the screen, used to add a native widget.
     *
     * @see net.minecraft.client.gui.screens.Screen
     */
    public final Function<AbstractWidget, AbstractWidget> addRenderableWidget;
    /**
     * A function that requests the screen to be rebuilt.
     * Most of the time it will be {@link NScreen#requestRebuild()}.
     */
    public final Runnable requestRebuild;

    public NativeBuildContext(Function<AbstractWidget, AbstractWidget> addRenderableWidget, Runnable requestRebuild) {
        this.addRenderableWidget = addRenderableWidget;
        this.requestRebuild = requestRebuild;
    }
}
