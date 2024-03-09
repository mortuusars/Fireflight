package io.github.mortuusars.fireflight.mixin;

import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.render.LayeredBakedModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Unique
    private static final ResourceLocation BOW_OVERLAY = Fireflight.resource("item/bow_overlay");
    @Unique
    private static final ResourceLocation CROSSBOW_OVERLAY = Fireflight.resource("item/crossbow_overlay");

    /**
     * Combines item model with overlay model.
     */
    @Inject(method = "getModel", at = @At("RETURN"), cancellable = true)
    private void onGetModel(ItemStack stack, Level level, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        if (stack.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES))
            return;

        ResourceLocation overlayModelLocation;
        if (stack.getItem() instanceof BowItem)
            overlayModelLocation = BOW_OVERLAY;
        else if (stack.getItem() instanceof CrossbowItem)
            overlayModelLocation = CROSSBOW_OVERLAY;
        else
            return;

        BakedModel overlayModel = Minecraft.getInstance().getModelManager().getModel(overlayModelLocation);
        ClientLevel lvl = level instanceof ClientLevel clientLevel ? clientLevel : null;
        BakedModel overridenModel = overlayModel.getOverrides().resolve(overlayModel, stack, lvl, entity, seed);
        if (overridenModel != null)
            overlayModel = overridenModel;

        BakedModel originalModel = cir.getReturnValue();
        LayeredBakedModel layeredModel = new LayeredBakedModel(List.of(originalModel, overlayModel));

        cir.setReturnValue(layeredModel);
    }
}