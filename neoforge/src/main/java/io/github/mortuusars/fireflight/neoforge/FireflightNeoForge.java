package io.github.mortuusars.fireflight.neoforge;

import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Fireflight.ID)
public class FireflightNeoForge {
    public FireflightNeoForge(ModContainer container) {
        Fireflight.init();

        container.registerConfig(ModConfig.Type.COMMON, Config.Common.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, Config.Client.SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            FireflightNeoForgeClient.init(container);
        }
    }
}