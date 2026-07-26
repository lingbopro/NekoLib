package me.lingbopro.nekolib.internal.demo;

import me.lingbopro.nekolib.api.gui.ComponentLike;
import me.lingbopro.nekolib.api.gui.NScreen;
import me.lingbopro.nekolib.api.gui.State;
import me.lingbopro.nekolib.api.gui.components.base.ContentBox;
import me.lingbopro.nekolib.api.gui.components.base.Flexbox;
import me.lingbopro.nekolib.api.gui.components.base.Label;
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
    private final State<Flexbox.AlignItems> alignItems = useState(Flexbox.AlignItems.FLEX_START);
    private final State<Flexbox.JustifyContent> justifyContent = useState(Flexbox.JustifyContent.FLEX_START);
    private final State<Integer> count = useState(0);

    @Override
    protected ComponentLike build() {
        return new Flexbox(Flexbox.FlexDirection.COLUMN)
                .addChild(
                        new Flexbox()
                                .wrap(800)
                                .addChild(new ContentBox()
                                        .padding(10)
                                        .addChild(new Label("NekoLib GUI API Demo")))
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
                                        Button.builder(Component.literal("Toggle AlignItems"), button -> {
                                            switch (alignItems.get()) {
                                                case FLEX_START -> alignItems.set(Flexbox.AlignItems.CENTER);
                                                case CENTER -> alignItems.set(Flexbox.AlignItems.FLEX_END);
                                                case FLEX_END -> alignItems.set(Flexbox.AlignItems.FLEX_START);
                                            }
                                        }).build()
                                ))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Toggle JustifyContent"), button -> {
                                            switch (justifyContent.get()) {
                                                case FLEX_START -> justifyContent.set(Flexbox.JustifyContent.CENTER);
                                                case CENTER -> justifyContent.set(Flexbox.JustifyContent.FLEX_END);
                                                case FLEX_END -> justifyContent.set(Flexbox.JustifyContent.SPACE_BETWEEN);
                                                case SPACE_BETWEEN -> justifyContent.set(Flexbox.JustifyContent.FLEX_START);
                                            }
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
                                .alignItems(alignItems.get())
                                .justifyContent(justifyContent.get())
                                .wrap(500)
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 1"), button -> {
                                        }).size(100, 40).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 2"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 3"), button -> {
                                        }).size(80, 60).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 4"), button -> {
                                        }).size(100, 40).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 5"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 6"), button -> {
                                        }).size(80, 60).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 7"), button -> {
                                        }).size(100, 40).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 8"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 9"), button -> {
                                        }).size(80, 60).build()))
                        ))
                .addChild(new ContentBox()
                        .padding(10)
                        .maxHeight(clamp.get() ? 20 : Integer.MAX_VALUE)
                        .addChild(new Flexbox()
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 10"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 11"), button -> {
                                        }).build()))
                                .addChild(new NativeWrap<>(
                                        Button.builder(Component.literal("Button 12"), button -> {
                                        }).build()))
                        ));
    }

    @Override
    public void onClose() {
        back();
    }
}
