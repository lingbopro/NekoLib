package me.lingbopro.nekolib.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * <p>A base class for custom screens, just like components (but for screens).</p>
 */
public abstract class NScreen extends Screen {
    private boolean rendering = false;
    private ComponentLike root;
    /**
     * The parent screen, if any.
     */
    @Nullable
    public Screen parent = null;

    public NScreen(Component title) {
        super(title);
    }

    public NScreen(Component title, @Nullable Screen parent) {
        super(title);
        this.parent = parent;
    }

    public NScreen(@Nullable Screen parent) {
        super(Component.empty());
        this.parent = parent;
    }

    /**
     * <p>Create a new state variable.</p>
     *
     * @see State
     */
    protected <T> State<T> useState(T initial) {
        return new State<>(initial, this::requestRebuild);
    }

    /**
     * <p>Your custom screen logic goes here.</p>
     * <p>It should return a component that will be the root of the screen.</p>
     */
    protected abstract ComponentLike build();

    /**
     * <p>Request the screen to rebuild itself.</p>
     * <p>Usually called internally when a state is changed.</p>
     */
    protected void requestRebuild() {
        if (rendering) return;
        if (Minecraft.getInstance().screen == this) {
            rebuild();
        }
    }

    /**
     * <p>Rebuild the screen.</p>
     * <p>Called when the screen is opened or when {@link #requestRebuild()} is called.</p>
     */
    protected void rebuild() {
        rendering = true;
        clearWidgets();
        root = build();
        if (Minecraft.getInstance().screen == this && root != null) {
            NativeBuildContext context = new NativeBuildContext(this::addRenderableWidget, this::requestRebuild);
            root.setX(0);
            root.setY(0);
            root.buildNative(context);
        }
        rendering = false;
    }

    /**
     * <p>Go back to the previous screen, if any.</p>
     * <p>Can be called from the {@link #onClose()} hook.</p>
     */
    public void back() {
        this.minecraft.setScreen(parent);
    }

    @Override
    protected void init() {
        super.init();
        rebuild();
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        root.renderNative(guiGraphics, mouseX, mouseY, partialTick);
    }
}
