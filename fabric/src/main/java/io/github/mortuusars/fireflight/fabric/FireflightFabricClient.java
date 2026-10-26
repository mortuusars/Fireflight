package io.github.mortuusars.fireflight.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.client.ConfigScreenFactoryRegistry;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.FireflightClient;
import io.github.mortuusars.fireflight.client.render.TorchArrowRenderer;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public class FireflightFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FireflightClient.init();

        ConfigScreenFactoryRegistry.INSTANCE.register(Fireflight.ID, ConfigurationScreen::new);

        EntityRendererRegistry.register(Fireflight.EntityTypes.TORCH.get(), context -> new TorchArrowRenderer(context, TorchType.TORCH));
        EntityRendererRegistry.register(Fireflight.EntityTypes.SOUL_TORCH.get(), context -> new TorchArrowRenderer(context, TorchType.SOUL_TORCH));
        EntityRendererRegistry.register(Fireflight.EntityTypes.REDSTONE_TORCH.get(), context -> new TorchArrowRenderer(context, TorchType.REDSTONE_TORCH));

//        ModelLoadingPlugin.register(context -> context.addModels(
//              FireflightClient.Models.BOW_FULL.id(),
//              FireflightClient.Models.BOW_OVERLAY.id(),
//              FireflightClient.Models.CROSSBOW_OVERLAY.id()
//        ));
    }
}
