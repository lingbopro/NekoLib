package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerComponent extends AbstractWidget implements UpdatableComponent {
    protected final List<ContainerChild> children = new ArrayList<>();
    protected boolean needsRelayout = true;
    private Runnable screenUpdateDispatcher;

    public ContainerComponent(int x, int y, int width, int height, Component component) {
        super(x, y, width, height, component);
    }

    public ContainerComponent addChild(ContainerChild child) {
        children.add(child);
        markDirty();
        return this;
    }
    public ContainerComponent addChild(AbstractWidget widget, LayoutProperties properties) {
        return addChild(new ContainerChild(widget, properties));
    }
    public ContainerComponent addChild(AbstractWidget widget) {
        return addChild(new ContainerChild(widget, new LayoutProperties()));
    }

    public void markDirty() {
        needsRelayout = true;
    }
    public abstract void relayout();

    public void setScreenUpdateDispatcher(Runnable dispatcher) {
        screenUpdateDispatcher = dispatcher;
    }

    public void renderWidget(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (needsRelayout) relayout();
        for (ContainerChild child : children) {
            AbstractWidget widget = child.widget;
            if (widget instanceof UpdatableComponent) {
                ((UpdatableComponent) widget).setScreenUpdateDispatcher(screenUpdateDispatcher);
            }
            widget.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        needsRelayout = false;
    }

//    public abstract int getWidth();
//    public abstract int getHeight();

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput narrationElementOutput) {}

    public static class ContainerChild {
        public final AbstractWidget widget;
        public final LayoutProperties properties;
        public ContainerChild(AbstractWidget widget, LayoutProperties properties) {
            this.widget = widget;
            this.properties = properties;
        }
        public int getOuterWidth() {
            return widget.getWidth() + properties.marginLeft + properties.marginRight;
        }
        public int getOuterHeight() {
            return widget.getHeight() + properties.marginTop + properties.marginBottom;
        }
    }
}
