package me.lingbopro.nekolib;

import com.mojang.logging.LogUtils;
import dev.architectury.platform.Platform;
import org.slf4j.Logger;

public final class Nekolib {
    public static final String MOD_ID = "nekolib";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("Hello world! NekoLib initializing on {}!", Platform.getEnvironment());
    }
}
