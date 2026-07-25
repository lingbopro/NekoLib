package me.lingbopro.nekolib.api.gui.components.base;

import me.lingbopro.nekolib.api.gui.ComponentLike;
import me.lingbopro.nekolib.api.gui.ContainerWidget;
import me.lingbopro.nekolib.api.gui.NativeBuildContext;
import net.minecraft.client.gui.GuiGraphics;
import org.jspecify.annotations.NonNull;

/**
 * A flexbox layout for GUIs.
 */
public class Flexbox extends ContainerWidget {
    public FlexDirection flexDirection = FlexDirection.ROW;
    private int width;
    private int height;
    private int x;
    private int y;
    protected boolean needsRelayout = true;

    public Flexbox() {
        super();
    }

    public Flexbox(FlexDirection direction) {
        super();
        flexDirection = direction;
    }

    public Flexbox(ComponentLike... children) {
        super();
        for (ComponentLike child : children) {
            addChild(child);
        }
    }

    /**
     * Set the flex direction.
     *
     * @return returns {@code this} for chaining.
     */
    public Flexbox setDirection(FlexDirection direction) {
        flexDirection = direction;
        return this;
    }

    protected void relayout() {
        if (flexDirection == FlexDirection.ROW) {
            int relX = 0;
            int containerHeight = 0;
            for (ComponentLike child : children) {
                child.setX(x + relX);
                child.setY(y);
                relX += child.getWidth();
                if (child.getHeight() > containerHeight) containerHeight = child.getHeight();
            }
            width = relX;
            height = containerHeight;
        } else if (flexDirection == FlexDirection.COLUMN) {
            int relY = 0;
            int containerWidth = 0;
            for (ComponentLike child : children) {
                child.setX(x);
                child.setY(y + relY);
                relY += child.getHeight();
                if (child.getWidth() > containerWidth) containerWidth = child.getWidth();
            }
            width = containerWidth;
            height = relY;
        } else throw new IllegalStateException("Invalid FlexDirection");
    }

    protected void relayoutIfNeeded() {
        if (needsRelayout) relayout();
        needsRelayout = false;
    }

    @Override
    public Flexbox addChild(ComponentLike child) {
        needsRelayout = true;
        return (Flexbox) super.addChild(child);
    }

    @Override
    public void buildNative(NativeBuildContext context) {
        relayoutIfNeeded();
        for (ComponentLike child : children) {
            child.buildNative(context);
        }
    }

    @Override
    public void renderNative(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (ComponentLike child : children) {
            child.renderNative(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    //region Size and Position
    @Override
    public int getWidth() {
        relayoutIfNeeded();
        return width;
    }

    @Override
    public int getHeight() {
        relayoutIfNeeded();
        return height;
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

    /**
     * The flex direction, similar to CSS {@code flex-direction} property.
     */
    public enum FlexDirection {
        ROW,
        COLUMN
    }
}
