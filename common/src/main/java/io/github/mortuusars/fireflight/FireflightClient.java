package io.github.mortuusars.fireflight;

import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;

public class FireflightClient {
    public static void init() {
        ItemModelOverrides.register();
    }

    public static class Models {
        public static final ModelResourceLocation BOW_OVERLAY =
              new ModelResourceLocation(Fireflight.resource("item/bow_overlay"), "standalone");
        public static final ModelResourceLocation CROSSBOW_OVERLAY =
              new ModelResourceLocation(Fireflight.resource("item/crossbow_overlay"), "standalone");
    }

    public static class ItemModelOverrides {
        public static final ResourceLocation TORCH_TYPE = Fireflight.resource("torch_type");

        public static void register() {
            ItemProperties.registerGeneric(TORCH_TYPE, ItemModelOverrides::getTorchType);
        }

        /**
         * Ammo type. Torch - 0.1, Soul Torch - 0.2, Redstone Torch - 0.3. All other ammo - 0.0.
         */
        public static float getTorchType(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
            if (entity instanceof Player player && !stack.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES)) {
                if (stack.getItem() instanceof BowItem) {
                    if (player.isUsingItem()) {
                        return TorchType.getFromStack(player.getProjectile(stack)).getModelOverrideValue();
                    }
                    return 0f;
                }

                if (stack.get(DataComponents.CHARGED_PROJECTILES) instanceof ChargedProjectiles projectiles) {
                    for (ItemStack projectile : projectiles.getItems()) {
                        return TorchType.getFromStack(projectile).getModelOverrideValue();
                    }
                }
            }

            return 0f;
        }
    }
}
