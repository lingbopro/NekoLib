package me.lingbopro.nekolib.api.gui.components.base;

import me.lingbopro.nekolib.api.gui.ComponentLike;
import me.lingbopro.nekolib.api.gui.NativeBuildContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import org.jspecify.annotations.NonNull;

/**
 * A wrapper for native minecraft components to be used in NekoLib GUIs.
 */
public class NativeWrap<T extends AbstractWidget> implements ComponentLike {
    public final T widget;
    public int x;
    public int y;

    public NativeWrap(T widget) {
        this.widget = widget;
    }

    @Override
    public void buildNative(NativeBuildContext context) {
        widget.setX(x);
        widget.setY(y);
        context.addRenderableWidget.apply(widget);
    }

    @Override
    public void renderNative(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        widget.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    //region Size and Position
    @Override
    public int getWidth() {
        return widget.getWidth();
    }

    @Override
    public int getHeight() {
        return widget.getHeight();
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
    //endregion
}
