package io.github.mortuusars.fireflight.mixin.shoot_sound;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.mortuusars.fireflight.Fireflight;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public class BowItemMixin {
    @SuppressWarnings({"LocalMayUseName", "NameDoesntMatchTargetClass"})
    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void wrapPlaySound(Level instance, @Nullable Player player, double x, double y, double z,
                               SoundEvent sound, SoundSource category, float volume, float pitch, Operation<Void> original,
                               @Local(ordinal = 1) ItemStack projectile) {
        if (Fireflight.isShootableTorch(projectile)) {
            pitch /= 2f;
        }

        if (projectile.is(Fireflight.Tags.Items.SOUL_TORCH)) {
            instance.playSound(null, x, y, z, SoundEvents.SOUL_ESCAPE, category, volume, pitch);
        }

        original.call(instance, player, x, y, z, sound, category, volume, pitch);
    }
}
