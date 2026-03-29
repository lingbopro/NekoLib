package me.lingbopro.nekolib.fabric;

import me.lingbopro.nekolib.Nekolib;
import net.fabricmc.api.ModInitializer;

public final class NekolibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Nekolib.init();
    }
}
