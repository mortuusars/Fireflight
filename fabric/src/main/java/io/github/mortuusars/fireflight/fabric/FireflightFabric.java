package io.github.mortuusars.fireflight.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;

public class FireflightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Fireflight.init();

        NeoForgeConfigRegistry.INSTANCE.register(Fireflight.ID, ModConfig.Type.SERVER, Config.Server.SPEC);
        NeoForgeConfigRegistry.INSTANCE.register(Fireflight.ID, ModConfig.Type.CLIENT, Config.Client.SPEC);
    }
}
