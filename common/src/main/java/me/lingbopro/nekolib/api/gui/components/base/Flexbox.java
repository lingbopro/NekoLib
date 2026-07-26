package me.lingbopro.nekolib.api.gui.components.base;

import me.lingbopro.nekolib.api.gui.ComponentLike;
import me.lingbopro.nekolib.api.gui.ContainerWidget;
import me.lingbopro.nekolib.api.gui.NativeBuildContext;
import net.minecraft.client.gui.GuiGraphics;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A flexbox layout for GUIs.
 */
public class Flexbox extends ContainerWidget {
    public FlexDirection flexDirection = FlexDirection.ROW;
    public AlignItems alignItems = AlignItems.FLEX_START;
    public JustifyContent justifyContent = JustifyContent.FLEX_START;
    public int wrapLength = Integer.MAX_VALUE;
    public int minWidth = 0;
    public int minHeight = 0;
    public int lineGap = 0;
    private int contentWidth;
    private int contentHeight;
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

    public Flexbox alignItems(AlignItems alignItems) {
        this.alignItems = alignItems;
        return this;
    }

    public Flexbox justifyContent(JustifyContent justifyContent) {
        this.justifyContent = justifyContent;
        return this;
    }

    protected void relayout() {
        // M = main axis, C = cross axis
        int containerLengthM = 0;
        int containerLengthC;
        List<Integer> lineLengthsM = new ArrayList<>();
        List<Integer> lineLengthsC = new ArrayList<>();
        List<Integer> wrapAt = new ArrayList<>();
        // first loop: calculate container length and line lengths
        {
            int lineLengthM = 0;
            int lineLengthC = 0;
            int linesLengthTotalC = 0;
            for (int i = 0; i < children.size(); i++) {
                ComponentLike child = children.get(i);
                int childLengthM = flexDirection == FlexDirection.ROW ? child.getWidth() : child.getHeight();
                int childLengthC = flexDirection == FlexDirection.ROW ? child.getHeight() : child.getWidth();
                // line wrap (this child will wrap to next line)
                if (lineLengthM + childLengthM > wrapLength) {
                    // container length
                    containerLengthM = Math.max(containerLengthM, lineLengthM);
                    linesLengthTotalC += lineLengthC;
                    // record the length of previous line
                    lineLengthsM.add(lineLengthM);
                    lineLengthsC.add(lineLengthC);
                    // record the index of the first child in the next line
                    wrapAt.add(i);
                    // reset length for current line
                    lineLengthM = 0;
                    lineLengthC = 0;
                }
                lineLengthM += childLengthM;
                // lineLengthC += childLengthC;
                lineLengthC = Math.max(lineLengthC, childLengthC);
            }
            // add the last line
            lineLengthsM.add(lineLengthM);
            lineLengthsC.add(lineLengthC);
            containerLengthM = Math.max(containerLengthM, lineLengthM);
            linesLengthTotalC += lineLengthC;
            containerLengthC = linesLengthTotalC + (lineLengthsC.size() - 1) * lineGap;
            wrapAt.add(children.size());
        }
        // second loop: place children in their lines
        {
            // line position in cross axis relative to container
            int lineC = 0;
            // iterate over lines
            for (int lineIndex = 0; lineIndex < wrapAt.size(); lineIndex++) {
                int wrap = wrapAt.get(lineIndex);
                int prevWrap = lineIndex == 0 ? 0 : wrapAt.get(lineIndex - 1);
                int lineLengthM = lineLengthsM.get(lineIndex);
                int lineLengthC = lineLengthsC.get(lineIndex);

                int startM;
                switch (justifyContent) {
                    case FLEX_END -> startM = containerLengthM - lineLengthM;
                    case CENTER -> startM = (containerLengthM - lineLengthM) / 2;
                    default -> startM = 0;
                }

                int spaceBetweenGap = wrap - prevWrap > 1 ? (containerLengthM - lineLengthM) / (wrap - prevWrap - 1) : 0;

                int lineRelM = startM;
                for (int i = prevWrap; i < wrap; i++) {
                    ComponentLike child = children.get(i);
                    int childLengthM = flexDirection == FlexDirection.ROW ? child.getWidth() : child.getHeight();
                    int childLengthC = flexDirection == FlexDirection.ROW ? child.getHeight() : child.getWidth();
                    int childC;
                    switch (alignItems) {
                        case FLEX_END -> childC = lineC + lineLengthC - childLengthC;
                        case CENTER -> childC = lineC + (lineLengthC - childLengthC) / 2;
                        default -> childC = lineC;
                    }
                    if (flexDirection == FlexDirection.COLUMN) {
                        child.setX(x + childC);
                        child.setY(y + lineRelM);
                    }
                    else {
                        child.setX(x + lineRelM);
                        child.setY(y + childC);
                    }
                    lineRelM += childLengthM;
                    if (justifyContent == JustifyContent.SPACE_BETWEEN) lineRelM += spaceBetweenGap;
                }
                lineC += lineLengthC + lineGap;
            }
        }
        // expose the content size
        contentWidth = flexDirection == FlexDirection.ROW ? containerLengthM : containerLengthC;
        contentHeight = flexDirection == FlexDirection.ROW ? containerLengthC : containerLengthM;
    }

    protected void relayoutIfNeeded() {
        if (needsRelayout) relayout();
        needsRelayout = false;
    }

    public Flexbox wrap(int wrapLength) {
        this.wrapLength = wrapLength;
        return this;
    }

    public Flexbox minWidth(int minWidth) {
        this.minWidth = minWidth;
        return this;
    }
    public Flexbox minHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public Flexbox lineGap(int lineGap) {
        this.lineGap = lineGap;
        return this;
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
        return Math.max(contentWidth, minWidth);
    }

    @Override
    public int getHeight() {
        relayoutIfNeeded();
        return Math.max(contentHeight, minHeight);
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

    /**
     * The flex direction, similar to CSS {@code flex-direction} property.
     */
    public enum FlexDirection {
        ROW,
        COLUMN
    }

    /**
     * The alignment of children along the cross axis, similar to CSS {@code align-items} property.
     */
    public enum AlignItems {
        // STRETCH,
        CENTER,
        FLEX_START,
        FLEX_END
    }

    /**
     * The alignment of children along the main axis, similar to CSS {@code justify-content} property.
     */
    public enum JustifyContent {
        FLEX_START,
        FLEX_END,
        CENTER,
        SPACE_BETWEEN,
        // SPACE_AROUND,
        // SPACE_EVENLY
    }
}
