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
    public BoxProperties properties = new BoxProperties();
    public int width = 0;
    public int height = 0;
    private int x;
    private int y;

    public ContentBox() {
        super();
    }

    public ContentBox(BoxProperties properties) {
        super();
        this.properties = properties;
    }

    public ContentBox(ComponentLike... children) {
        super();
        for (ComponentLike child : children) {
            addChild(child);
        }
    }

    @Override
    public ContentBox addChild(ComponentLike child) {
        return (ContentBox) super.addChild(child);
    }

    @Override
    public void buildNative(NativeBuildContext context) {
        int childMaxW = 0;
        int childMaxH = 0;
        for (ComponentLike child : children) {
            child.setX(properties.paddingLeft + x);
            child.setY(properties.paddingTop + y);
            child.buildNative(context);
            childMaxW = Math.max(childMaxW, child.getWidth());
            childMaxH = Math.max(childMaxH, child.getHeight());
        }

        int w = properties.paddingLeft + childMaxW + properties.paddingRight;
        int h = properties.paddingTop + childMaxH + properties.paddingBottom;
        width = Math.clamp(w, properties.minWidth, properties.maxWidth);
        height = Math.clamp(h, properties.minHeight, properties.maxHeight);
    }

    @Override
    public void renderNative(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        boolean scissorEnabled = properties.maxHeight < Integer.MAX_VALUE || properties.maxWidth < Integer.MAX_VALUE;
        int scissorWidth = properties.maxWidth < Integer.MAX_VALUE ? properties.maxWidth : width;
        int scissorHeight = properties.maxHeight < Integer.MAX_VALUE ? properties.maxHeight : height;
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
        return width;
    }

    @Override
    public int getHeight() {
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

    //region Properties setter
    public ContentBox padding(int paddingAll) {
        properties.setPadding(paddingAll);
        return this;
    }

    public ContentBox padding(int paddingVertical, int paddingHorizontal) {
        properties.setPadding(paddingVertical, paddingHorizontal);
        return this;
    }

    public ContentBox padding(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
        properties.setPadding(paddingTop, paddingRight, paddingBottom, paddingLeft);
        return this;
    }

    public ContentBox maxWidth(int maxWidth) {
        properties.setMaxWidth(maxWidth);
        return this;
    }

    public ContentBox maxHeight(int maxHeight) {
        properties.setMaxHeight(maxHeight);
        return this;
    }

    public ContentBox minWidth(int minWidth) {
        properties.setMinWidth(minWidth);
        return this;
    }

    public ContentBox minHeight(int minHeight) {
        properties.setMinHeight(minHeight);
        return this;
    }
    //endregion

    /**
     * <p>The layout properties of the ContentBox.</p>
     */
    public static class BoxProperties {
        public int paddingTop = 0;
        public int paddingRight = 0;
        public int paddingBottom = 0;
        public int paddingLeft = 0;
        public int maxWidth = Integer.MAX_VALUE;
        public int maxHeight = Integer.MAX_VALUE;
        public int minWidth = 0;
        public int minHeight = 0;

        //region Setters
        public BoxProperties setPadding(int paddingAll) {
            return setPadding(paddingAll, paddingAll, paddingAll, paddingAll);
        }

        public BoxProperties setPadding(int paddingVertical, int paddingHorizontal) {
            return setPadding(paddingVertical, paddingHorizontal, paddingVertical, paddingHorizontal);
        }

        public BoxProperties setPadding(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
            this.paddingTop = paddingTop;
            this.paddingRight = paddingRight;
            this.paddingBottom = paddingBottom;
            this.paddingLeft = paddingLeft;
            return this;
        }

        public BoxProperties setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public BoxProperties setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        public BoxProperties setMinWidth(int minWidth) {
            this.minWidth = minWidth;
            return this;
        }

        public BoxProperties setMinHeight(int minHeight) {
            this.minHeight = minHeight;
            return this;
        }
        //endregion
    }
}
