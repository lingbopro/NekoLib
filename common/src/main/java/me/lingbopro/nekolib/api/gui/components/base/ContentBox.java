package me.lingbopro.nekolib.api.gui.components.base;

import me.lingbopro.nekolib.api.gui.ComponentLike;
import me.lingbopro.nekolib.api.gui.ContainerWidget;
import me.lingbopro.nekolib.api.gui.NativeBuildContext;
import net.minecraft.client.gui.GuiGraphics;
import org.jspecify.annotations.NonNull;

/**
 * <p>The ContentBox is a simple container component that has custom padding (or also margin as it do not have any styles) and size constraints.</p>
 * <p>It can be used to create custom margin and size constraints for its children.</p>
 */
public class ContentBox extends ContainerWidget {
    public int width = 0;
    public int height = 0;
    private int x;
    private int y;
    protected boolean needsRelayout = true;

    public int paddingTop = 0;
    public int paddingRight = 0;
    public int paddingBottom = 0;
    public int paddingLeft = 0;
    public int maxWidth = Integer.MAX_VALUE;
    public int maxHeight = Integer.MAX_VALUE;
    public int minWidth = 0;
    public int minHeight = 0;

    public ContentBox() {
        super();
    }

    public ContentBox(ComponentLike... children) {
        super();
        for (ComponentLike child : children) {
            addChild(child);
        }
    }

    protected void relayout() {
        int childMaxW = 0;
        int childMaxH = 0;
        for (ComponentLike child : children) {
            child.setX(paddingLeft + x);
            child.setY(paddingTop + y);
            childMaxW = Math.max(childMaxW, child.getWidth());
            childMaxH = Math.max(childMaxH, child.getHeight());
        }

        int w = paddingLeft + childMaxW + paddingRight;
        int h = paddingTop + childMaxH + paddingBottom;
        width = Math.clamp(w, minWidth, maxWidth);
        height = Math.clamp(h, minHeight, maxHeight);
    }

    protected void relayoutIfNeeded() {
        if (needsRelayout) relayout();
        needsRelayout = false;
    }

    @Override
    public ContentBox addChild(ComponentLike child) {
        needsRelayout = true;
        return (ContentBox) super.addChild(child);
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
        boolean scissorEnabled = maxHeight < Integer.MAX_VALUE || maxWidth < Integer.MAX_VALUE;
        int scissorWidth = maxWidth < Integer.MAX_VALUE ? maxWidth : width;
        int scissorHeight = maxHeight < Integer.MAX_VALUE ? maxHeight : height;
        if (scissorEnabled) {
            guiGraphics.enableScissor(x, y, x + scissorWidth, y + scissorHeight);
        }
        for (ComponentLike child : children) {
            child.renderNative(guiGraphics, mouseX, mouseY, partialTick);
        }
        if (scissorEnabled) {
            guiGraphics.disableScissor();
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
        needsRelayout = true;
    }

    @Override
    public void setY(int y) {
        this.y = y;
        needsRelayout = true;
    }
    //endregion

    //region Properties setter
    public ContentBox padding(int paddingAll) {
        return padding(paddingAll, paddingAll, paddingAll, paddingAll);
    }

    public ContentBox padding(int paddingVertical, int paddingHorizontal) {
        return padding(paddingVertical, paddingHorizontal, paddingVertical, paddingHorizontal);
    }

    public ContentBox padding(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
        this.paddingTop = paddingTop;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;
        this.paddingLeft = paddingLeft;
        return this;
    }

    public ContentBox maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public ContentBox maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public ContentBox minWidth(int minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public ContentBox minHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }
    //endregion
}
