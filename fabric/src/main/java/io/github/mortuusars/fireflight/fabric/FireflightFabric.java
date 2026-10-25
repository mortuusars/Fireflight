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

//        CommonEvents.commonSetup();
//
//        DynamicRegistries.registerSynced(Envelope.Registries.PIGEON_VARIANT, PigeonVariant.DIRECT_CODEC, PigeonVariant.DIRECT_CODEC);
//        DynamicRegistries.registerSynced(Envelope.Registries.SERVICE_ADDRESS_DEFINITION, ServiceAddressDefinition.DIRECT_CODEC, ServiceAddressDefinition.DIRECT_CODEC);
//        DynamicRegistries.registerSynced(Envelope.Registries.SEAL_MATERIAL, SealMaterial.DIRECT_CODEC, SealMaterial.DIRECT_CODEC);
//        DynamicRegistries.registerSynced(Envelope.Registries.SEAL_SYMBOL, SealSymbol.DIRECT_CODEC, SealSymbol.DIRECT_CODEC);
//
//        FabricDefaultAttributeRegistry.register(Envelope.EntityTypes.PIGEON.get(), Pigeon.createAttributes().build());
//        FabricDefaultAttributeRegistry.register(Envelope.EntityTypes.CHARRED_PIGEON.get(), CharredPigeon.createAttributes().build());
//        FabricDefaultAttributeRegistry.register(Envelope.EntityTypes.COURIER_BAT.get(), CourierBat.createAttributes().build());
//
//        ServerTickEvents.END_SERVER_TICK.register(ServerEvents::serverTick);
//
//        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
//            ServerEvents.playerLogin(handler.getPlayer());
//        });
//
//        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
//            EnvelopeCommand.register(dispatcher, registryAccess);
//        });
//
//        BiomeModifications.addSpawn(biomeSelector -> biomeSelector.hasTag(Envelope.Tags.Biomes.ALLOWS_PIGEON_SPAWNS),
//                MobCategory.CREATURE, Envelope.EntityTypes.PIGEON.get(), 2, 3, 6);
//        BiomeModifications.addSpawn(biomeSelector -> biomeSelector.hasTag(Envelope.Tags.Biomes.ALLOWS_CHARRED_PIGEON_SPAWNS),
//              MobCategory.MONSTER, Envelope.EntityTypes.CHARRED_PIGEON.get(), 1, 1, 1);
//        BiomeModifications.create(Envelope.resource("charred_pigeon_spawn_cost"))
//              .add(ModificationPhase.ADDITIONS, biomeSelector -> biomeSelector.hasTag(Envelope.Tags.Biomes.ALLOWS_CHARRED_PIGEON_SPAWNS),
//                    context -> context.getSpawnSettings().setSpawnCost(Envelope.EntityTypes.CHARRED_PIGEON.get(), 0.7, 0.15));
//
//        SpawnPlacements.register(Envelope.EntityTypes.PIGEON.get(), SpawnPlacementTypes.ON_GROUND,
//                Heightmap.Types.MOTION_BLOCKING, Pigeon::checkSpawnRules);
//        SpawnPlacements.register(Envelope.EntityTypes.CHARRED_PIGEON.get(), SpawnPlacementTypes.ON_GROUND,
//              Heightmap.Types.MOTION_BLOCKING, CharredPigeon::checkSpawnRules);
//
//        FlammableBlockRegistry.getDefaultInstance().add(Envelope.Blocks.PAPER_BOX.get(), 50, 15);
//        FlammableBlockRegistry.getDefaultInstance().add(Envelope.Blocks.PACKAGE.get(), 50, 15);
//        FlammableBlockRegistry.getDefaultInstance().add(Envelope.Blocks.LETTER.get(), 200, 10);
//        Envelope.Blocks.PIGEONHOLES.forEach((id, block) -> {
//            FlammableBlockRegistry.getDefaultInstance().add(block.get(), 20, 15);
//        });
//
//        ServerLivingEntityEvents.AFTER_DEATH.register(CommonEvents::livingDeath);
//
//        ServerPlayerEvents.COPY_FROM.register(ServerEvents::playerCopy);
    }
}
