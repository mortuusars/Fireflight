package io.github.mortuusars.fireflight.neoforge.event;

import io.github.mortuusars.fireflight.Fireflight;
import net.neoforged.fml.common.EventBusSubscriber;

//@EventBusSubscriber(modid = Fireflight.ID)
public class NeoForgeCommonEvents {
//    @SubscribeEvent
//    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
//        event.put(Envelope.EntityTypes.PIGEON.get(), Pigeon.createAttributes().build());
//        event.put(Envelope.EntityTypes.CHARRED_PIGEON.get(), CharredPigeon.createAttributes().build());
//        event.put(Envelope.EntityTypes.COURIER_BAT.get(), CourierBat.createAttributes().build());
//    }

//    @SubscribeEvent
//    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
//        event.register(Envelope.EntityTypes.PIGEON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
//              Pigeon::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
//        event.register(Envelope.EntityTypes.CHARRED_PIGEON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
//              CharredPigeon::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
//    }

//    @SubscribeEvent
//    public static void addDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
//        event.dataPackRegistry(Envelope.Registries.PIGEON_VARIANT, PigeonVariant.DIRECT_CODEC, PigeonVariant.DIRECT_CODEC);
//        event.dataPackRegistry(Envelope.Registries.SERVICE_ADDRESS_DEFINITION, ServiceAddressDefinition.DIRECT_CODEC, ServiceAddressDefinition.DIRECT_CODEC);
//        event.dataPackRegistry(Envelope.Registries.SEAL_MATERIAL, SealMaterial.DIRECT_CODEC, SealMaterial.DIRECT_CODEC);
//        event.dataPackRegistry(Envelope.Registries.SEAL_SYMBOL, SealSymbol.DIRECT_CODEC, SealSymbol.DIRECT_CODEC);
//    }

//    @SubscribeEvent
//    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
//        event.registerBlockEntity(
//              Capabilities.ItemHandler.BLOCK,
//              Envelope.BlockEntityTypes.MAILBOX.get(),
//              SidedInvWrapper::new);
//    }
//
//    @SubscribeEvent
//    public static void registerCommands(RegisterCommandsEvent event) {
//        EnvelopeCommand.register(event.getDispatcher(), event.getBuildContext());
//    }
//
//    @SubscribeEvent
//    public static void serverStarted(ServerStartedEvent event) {
//        ServerEvents.serverStarted(event.getServer());
//    }
//
//    @SubscribeEvent
//    public static void serverTick(ServerTickEvent.Post tick) {
//        ServerEvents.serverTick(tick.getServer());
//    }
//
//    @SubscribeEvent
//    public static void levelTick(LevelTickEvent.Post event) {
//        CommonEvents.levelTick(event.getLevel());
//    }
//
//    @SubscribeEvent
//    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
//        if (event.getEntity() instanceof ServerPlayer player) {
//            ServerEvents.playerLogin(player);
//        }
//    }
//
//    @SubscribeEvent
//    public static void entityLeaveLevel(EntityLeaveLevelEvent event) {
//        CommonEvents.entityLeaveLevel(event.getLevel(), event.getEntity());
//    }
//
//    @SubscribeEvent
//    public static void entityDeath(LivingDeathEvent event) {
//        CommonEvents.livingDeath(event.getEntity(), event.getSource());
//    }
//
//    @SubscribeEvent
//    public static void copyPlayer(PlayerEvent.Clone event) {
//        if (event.getOriginal() instanceof ServerPlayer oldPlayer && event.getEntity() instanceof ServerPlayer newPlayer) {
//            ServerEvents.playerCopy(oldPlayer, newPlayer, !event.isWasDeath());
//        }
//    }
//
//    @SubscribeEvent
//    public static void onLivingConvert(LivingConversionEvent.Post event) {
//        if (event.getOutcome() instanceof ZombieVillager zombieVillager && zombieVillager.level() instanceof ServerLevel level) {
//            Pigeon.trySpawnGhost(level, zombieVillager);
//        }
//    }
}