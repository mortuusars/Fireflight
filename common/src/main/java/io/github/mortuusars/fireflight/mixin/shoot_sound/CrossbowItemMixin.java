package io.github.mortuusars.fireflight.mixin.shoot_sound;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.mortuusars.fireflight.world.entity.Torch;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @WrapOperation(method = "shootProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;getShotPitch(Lnet/minecraft/util/RandomSource;I)F"))
    private float wrapPlaySound(RandomSource random, int index, Operation<Float> original, @Local(argsOnly = true) Projectile projectile) {
        Float pitch = original.call(random, index);

        if (projectile instanceof Torch) {
            pitch *= 0.7f;
        }

        return pitch;
    }
}
