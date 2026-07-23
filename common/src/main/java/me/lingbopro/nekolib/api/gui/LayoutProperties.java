package me.lingbopro.nekolib.api.gui;

public class LayoutProperties {
    public int marginTop = 0;
    public int marginRight = 0;
    public int marginBottom = 0;
    public int marginLeft = 0;
    public int maxWidth = Integer.MAX_VALUE;
    public int maxHeight = Integer.MAX_VALUE;
    public int minWidth = 0;
    public int minHeight = 0;

    public LayoutProperties setMargin(int marginAll) {
        return setMargin(marginAll, marginAll,  marginAll, marginAll);
    }
    public LayoutProperties setMargin(int marginVertical, int marginHorizontal) {
        return setMargin(marginVertical, marginHorizontal, marginVertical, marginHorizontal);
    }
    public LayoutProperties setMargin(int marginTop, int marginRight, int marginBottom, int marginLeft) {
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        this.marginLeft = marginLeft;
        return this;
    }

    public LayoutProperties setMaxSize(int maxWidth, int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        return this;
    }
    public LayoutProperties setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }
    public LayoutProperties setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }
    public LayoutProperties setMinSize(int minWidth, int minHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        return this;
    }
    public LayoutProperties setMinWidth(int minWidth) {
        this.minWidth = minWidth;
        return this;
    }
    public LayoutProperties setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }
}
