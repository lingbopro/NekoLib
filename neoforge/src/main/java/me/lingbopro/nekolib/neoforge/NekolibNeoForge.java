package me.lingbopro.nekolib.neoforge;

import me.lingbopro.nekolib.Nekolib;
import me.lingbopro.nekolib.internal.demo.GUIDemoScreen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(Nekolib.MOD_ID)
public final class NekolibNeoForge {
    public NekolibNeoForge(ModContainer container) {
        // Run our common setup.
        Nekolib.init();

        container.registerExtensionPoint(IConfigScreenFactory.class, (cont, screen) -> new GUIDemoScreen(screen));
    }
}
