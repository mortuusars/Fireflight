package io.github.mortuusars.fireflight.mixin.client;

import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.FireflightClient;
import io.github.mortuusars.fireflight.client.render.LayeredBakedModel;
import io.github.mortuusars.mortaar.Platform;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    /**
     * Combines item model with overlay model.
     */
    @Inject(method = "getModel", at = @At("RETURN"), cancellable = true)
    private void onGetModel(ItemStack stack, Level level, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        if (!Config.Client.RENDER_TORCH.get() || !(entity instanceof Player) || stack.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES))
            return;

        ModelResourceLocation modelLocation;
        if (stack.getItem() instanceof BowItem) {
            if (!Fireflight.isShootableTorch(entity.getProjectile(stack))) {
                return;
            }

            if (!Config.Client.RENDER_TORCH_AS_OVERLAY.get()) {
                cir.setReturnValue(Platform.Client.getModelWithOverrides(FireflightClient.Models.BOW_FULL, stack, level, entity, seed));
                return;
            }

            modelLocation = FireflightClient.Models.BOW_OVERLAY;
        } else if (stack.getItem() instanceof CrossbowItem) {
            if (!(stack.get(DataComponents.CHARGED_PROJECTILES) instanceof ChargedProjectiles projectiles)
                  || projectiles.isEmpty()
                  || !Fireflight.isShootableTorch(projectiles.getItems().getFirst())) {
                return;
            }

            modelLocation = FireflightClient.Models.CROSSBOW_OVERLAY;
        } else {
            return;
        }

        BakedModel model = Platform.Client.getModelWithOverrides(modelLocation, stack, level, entity, seed);

        BakedModel originalModel = cir.getReturnValue();
        LayeredBakedModel layeredModel = new LayeredBakedModel(List.of(originalModel, model));

        cir.setReturnValue(layeredModel);
    }
}