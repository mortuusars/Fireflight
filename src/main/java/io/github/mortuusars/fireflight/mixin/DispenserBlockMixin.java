package io.github.mortuusars.fireflight.mixin;

import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.torch_shooting.TorchShootingDispenserBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {
    /**
     * Using 'DispenserBlock#registerBehavior' does not really work for us, because it's hardcoded to an item, and we use tags for torches.
     */
    @Inject(method = "getDispenseMethod", at = @At("HEAD"), cancellable = true)
    private void onGetDispenseMethod(ItemStack stack, CallbackInfoReturnable<DispenseItemBehavior> cir) {
        if (Config.Common.SHOOTING_TORCHES_DISPENSER.get() && Fireflight.isShootableTorch(stack)) {
            cir.setReturnValue(TorchShootingDispenserBehavior.DISPENSER_BEHAVIOR);
        }
    }
}
