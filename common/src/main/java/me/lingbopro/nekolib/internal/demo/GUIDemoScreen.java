package me.lingbopro.nekolib.internal.demo;

import me.lingbopro.nekolib.api.gui.ComponentLike;
import me.lingbopro.nekolib.api.gui.NScreen;
import me.lingbopro.nekolib.api.gui.State;
import me.lingbopro.nekolib.api.gui.components.base.ContentBox;
import me.lingbopro.nekolib.api.gui.components.base.Flexbox;
import me.lingbopro.nekolib.api.gui.components.base.NativeWrap;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

public class GUIDemoScreen extends NScreen {
    public GUIDemoScreen(@Nullable Screen parent) {
        super(parent);
    }

    private final State<Boolean> clamp = useState(false);
    private final State<Flexbox.FlexDirection> direction = useState(Flexbox.FlexDirection.ROW);
    private final State<Integer> count = useState(0);

    @Override
    protected ComponentLike build() {
        return new Flexbox(Flexbox.FlexDirection.COLUMN)
                .addChild(
                        new Flexbox()
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Toggle Clamp"), button -> {
                                            clamp.set(!clamp.get());
                                        }).build()
                                ))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Toggle Flex Direction"), button -> {
                                            direction.set(direction.get() == Flexbox.FlexDirection.ROW ? Flexbox.FlexDirection.COLUMN : Flexbox.FlexDirection.ROW);
                                        }).build()
                                ))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Count: " + count.get()), button -> {
                                            count.set(count.get() + 1);
                                        }).build()
                                ))
                )
                .addChild(new ContentBox()
                        .padding(10)
                        .maxWidth(clamp.get() ? 250 : Integer.MAX_VALUE)
                        .addChild(new Flexbox(direction.get())
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 1"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 2"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 3"), button -> {
                                        }).build()))
                        ))
                .addChild(new ContentBox()
                        .padding(10)
                        .maxHeight(clamp.get() ? 20 : Integer.MAX_VALUE)
                        .addChild(new Flexbox()
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 4"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 5"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 6"), button -> {
                                        }).build()))
                        ));
    }

    @Override
    public void onClose() {
        back();
    }
}
