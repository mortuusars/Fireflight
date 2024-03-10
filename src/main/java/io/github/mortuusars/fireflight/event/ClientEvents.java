package io.github.mortuusars.fireflight.event;

import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import io.github.mortuusars.fireflight.render.TorchArrowRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            EntityRenderers.register(Fireflight.Entities.TORCH_ARROW.get(), context -> new TorchArrowRenderer(context, TorchType.TORCH));
            EntityRenderers.register(Fireflight.Entities.SOUL_TORCH_ARROW.get(), context -> new TorchArrowRenderer(context, TorchType.SOUL));
            EntityRenderers.register(Fireflight.Entities.REDSTONE_TORCH_ARROW.get(), context -> new TorchArrowRenderer(context, TorchType.REDSTONE));

            ItemProperties.registerGeneric(Fireflight.resource("torch_type"), ClientEvents::getTorchType);
        });
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(Fireflight.resource("item/bow_overlay"));
        event.register(Fireflight.resource("item/crossbow_overlay"));
    }

    /**
     * Ammo type. Torch - 0.1, Soul Torch - 0.2, Redstone Torch - 0.3. All other ammo - 0.0.
     */
    public static float getTorchType(ItemStack stack, ClientLevel level, LivingEntity livingEntity, int seed) {
        if (!(livingEntity instanceof Player) || stack.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES))
            return 0f;

        if (stack.getItem() instanceof BowItem) {
            if (livingEntity.isUsingItem())
                return TorchType.getFromStack(livingEntity.getProjectile(stack)).getFloatValue();
            return 0f;
        }

        if (stack.getItem() instanceof CrossbowItem) {
            for (ItemStack projectileStack : CrossbowItem.getChargedProjectiles(stack)) {
                return TorchType.getFromStack(projectileStack).getFloatValue();
            }
            return 0F;
        }

        return 0f;
    }
}
