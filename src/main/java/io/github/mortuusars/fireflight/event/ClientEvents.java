package io.github.mortuusars.fireflight.event;

import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.TorchType;
import io.github.mortuusars.fireflight.render.TorchArrowRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            EntityRenderers.register(Fireflight.Entities.TORCH_ARROW.get(), context -> new TorchArrowRenderer(context, TorchType.TORCH));

            ItemProperties.register(Items.BOW, new ResourceLocation("torch"), ClientEvents::getBowTorchOverrideValue);
            ItemProperties.register(Items.BOW, new ResourceLocation("torch_pulled_animation"), ClientEvents::getTorchPulledValue);

            ItemProperties.register(Items.CROSSBOW, new ResourceLocation("torch"), ClientEvents::getCrossbowTorchOverrideValue);
            ItemProperties.register(Items.CROSSBOW, new ResourceLocation("torch_animation"), ClientEvents::getTorchPulledValue);
        });
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(Fireflight.resource("item/bow_overlay"));
    }

    /**
     * Ammo type. Torch - 0.1, Soul Torch - 0.2, Redstone Torch - 0.3. All other ammo - 0.0.
     */
    public static float getBowTorchOverrideValue(ItemStack stack, ClientLevel level, LivingEntity livingEntity, int seed) {
        return livingEntity != null && livingEntity.isUsingItem() ? TorchType.getFromStack(livingEntity.getProjectile(stack)).getFloatValue() : 0F;
    }

    /**
     * Same as for bow, but from a crossbow stack 'ChargedProjectiles' tag.
     */
    public static float getCrossbowTorchOverrideValue(ItemStack stack, ClientLevel level, LivingEntity livingEntity, int seed) {
        for (ItemStack projectileStack : CrossbowItem.getChargedProjectiles(stack)) {
            return TorchType.getFromStack(projectileStack).getFloatValue();
        }
        return 0F;
    }

    /**
     * Value for a looping animation. 0.05 per tick.
     */
    public static float getTorchPulledValue(ItemStack stack, ClientLevel level, LivingEntity livingEntity, int seed) {
        return level != null ? level.getGameTime() % 20 / 20F : 0F;
    }
}
