package me.lingbopro.nekolib.api.gui.components.base;

import me.lingbopro.nekolib.api.gui.ComponentLike;
import me.lingbopro.nekolib.api.gui.NativeBuildContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class Label implements ComponentLike {
    public Font font = Minecraft.getInstance().font;
    public Component text;
    public int color = 0xFFFFFFFF;
    public boolean drawShadow = false;
    private int x;
    private int y;
    public int lineWrap = 0;

    public Label(Component text) {
        this.text = text;
    }

    public Label(String text) {
        this.text = Component.literal(text);
    }

    /**
     * Set the font to use.
     *
     * @return this
     */
    public Label font(Font font) {
        this.font = font;
        return this;
    }

    /**
     * Set the text color (in ARGB format).
     *
     * @return this
     * @see net.minecraft.util.ARGB#opaque(int)
     */
    public Label color(int color) {
        this.color = color;
        return this;
    }

    /**
     * Enable shadow under the text.
     *
     * @return this
     */
    public Label drawShadow() {
        this.drawShadow = true;
        return this;
    }

    /**
     * Enable or disable shadow under the text.
     *
     * @param drawShadow Whether to draw shadow
     * @return this
     */
    public Label drawShadow(boolean drawShadow) {
        this.drawShadow = drawShadow;
        return this;
    }

    @Override
    public void buildNative(NativeBuildContext context) {

    }

    @Override
    public void renderNative(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawString(font, text, x, y, color, drawShadow);
    }

    /**
     * Get the actual rendered width of the text.
     */
    @Override
    public int getWidth() {
        return lineWrap < Integer.MAX_VALUE && lineWrap > 0 ? Math.min(lineWrap, font.width(text)) : font.width(text);
    }

    /**
     * Get the actual rendered height of the text.
     */
    @Override
    public int getHeight() {
        return lineWrap < Integer.MAX_VALUE && lineWrap > 0 ? font.wordWrapHeight(text, lineWrap) : font.lineHeight;
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
}
