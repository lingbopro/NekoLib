package me.lingbopro.nekolib.internal.demo;

import me.lingbopro.nekolib.api.gui.LayoutProperties;
import me.lingbopro.nekolib.api.gui.NScreen;
import me.lingbopro.nekolib.api.gui.components.Flexbox;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

public class GUIDemoScreen extends NScreen {
    public GUIDemoScreen(@Nullable Screen parent) {
        super(parent);
    }

    @Override
    protected AbstractWidget build() {
        return new Flexbox(0, 0, 0, 0, Component.empty())
                .setDirection(Flexbox.FlexDirection.ROW)
                .addChild(new Flexbox(0, 0, 0, 0, Component.empty())
                                .setDirection(Flexbox.FlexDirection.COLUMN)
                                .addChild(Button.builder(Component.literal("Button"), button -> {
                                        }).build(),
                                        new LayoutProperties().setMargin(10))
                                .addChild(Button.builder(Component.literal("Button 2"), button -> {
                                        }).size(50, 20).build(),
                                        new LayoutProperties().setMargin(10)),
                        new LayoutProperties().setMargin(10))
                .addChild(new Flexbox(0, 0, 0, 0, Component.empty())
                        .addChild(Button.builder(Component.literal("Button 3"), button -> {
                                }).build()));
    }

    private void emptyFn() {
    }

    @Override
    public void onClose() {
        back();
    }
}
