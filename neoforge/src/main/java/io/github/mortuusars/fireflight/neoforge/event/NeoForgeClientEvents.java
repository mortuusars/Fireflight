package io.github.mortuusars.fireflight.neoforge.event;

import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.FireflightClient;
import io.github.mortuusars.fireflight.client.render.TorchArrowRenderer;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(modid = Fireflight.ID, value = Dist.CLIENT)
public class NeoForgeClientEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(FireflightClient::init);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Fireflight.EntityTypes.TORCH.get(), context -> new TorchArrowRenderer(context, TorchType.TORCH));
        event.registerEntityRenderer(Fireflight.EntityTypes.SOUL_TORCH.get(), context -> new TorchArrowRenderer(context, TorchType.SOUL_TORCH));
        event.registerEntityRenderer(Fireflight.EntityTypes.REDSTONE_TORCH.get(), context -> new TorchArrowRenderer(context, TorchType.REDSTONE_TORCH));
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(FireflightClient.Models.BOW_OVERLAY);
        event.register(FireflightClient.Models.CROSSBOW_OVERLAY);
    }
}