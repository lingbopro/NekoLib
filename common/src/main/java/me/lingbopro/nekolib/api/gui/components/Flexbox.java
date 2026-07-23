package me.lingbopro.nekolib.api.gui.components;

import me.lingbopro.nekolib.api.gui.ContainerComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

public class Flexbox extends ContainerComponent {
    public FlexDirection flexDirection = FlexDirection.ROW;

    public Flexbox(int x, int y, int width, int height, Component component) {
        super(x, y, width, height, component);
    }
    public Flexbox(int x, int y, int width, int height, Component component, AbstractWidget ...children) {
        super(x, y, width, height, component);
        for (AbstractWidget child : children) {
            addChild(child);
        }
    }

    public Flexbox setDirection(FlexDirection direction) {
        flexDirection = direction;
        return this;
    }

    @Override
    public void relayout() {
        if (flexDirection == FlexDirection.ROW) {
            rebuildRow();
        }
        else if (flexDirection == FlexDirection.COLUMN) {
            rebuildColumn();
        }
        else {
            throw new IllegalStateException("Invalid FlexDirection: " + flexDirection);
        }
    }

    protected void rebuildRow() {
        final int x = getX();
        int relX = 0;
        final int y = getY();
        int relY = 0;
        final int width = getWidth();
        int rowHeight = 0;
        for (ContainerChild child : this.children) {
            AbstractWidget widget = child.widget;
            if (child.getOuterHeight() > rowHeight) rowHeight = child.getOuterHeight();
            // line break
            if (relX + child.getOuterWidth() > width) {
                relY += rowHeight;
                relX = 0;
                if (this.height > rowHeight) this.height = rowHeight;
                rowHeight = 0;
            }
            widget.setX(x + relX + child.properties.marginLeft);
            widget.setY(y + relY + child.properties.marginTop);
            relX += child.getOuterWidth();
        }
    }
    protected void rebuildColumn() {
        final int x = getX();
        int relX = 0;
        final int y = getY();
        int relY = 0;
        final int height = getHeight();
        int colWidth = 0;
        for (ContainerChild child : this.children) {
            AbstractWidget widget = child.widget;
            if (child.getOuterWidth() > colWidth) colWidth = child.getOuterWidth();
            // line break
            if (relY + child.getOuterHeight() > height) {
                relX += colWidth;
                relY = 0;
            }
            widget.setX(x + relX + child.properties.marginLeft);
            widget.setY(y + relY + child.properties.marginTop);
            relY += child.getOuterHeight();
        }
    }

    public enum FlexDirection {
        ROW,
        COLUMN
    }
}
