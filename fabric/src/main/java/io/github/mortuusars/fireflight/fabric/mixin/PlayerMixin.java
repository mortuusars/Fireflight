package io.github.mortuusars.fireflight.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.mortuusars.fireflight.torch_shooting.TorchShooting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

/**
 * NeoForge and Fabric calls differ.
 * NeoForge passes 'ItemStack weaponStack' into ProjectileWeaponItem#getXXXProjectiles, and Fabric does not.
 */
@Mixin(Player.class)
public class PlayerMixin {
    @WrapOperation(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;getSupportedHeldProjectiles()Ljava/util/function/Predicate;"))
    private Predicate<ItemStack> wrapGetSupportedHeldProjectiles(ProjectileWeaponItem instance, Operation<Predicate<ItemStack>> original,
                                                                 @Local(argsOnly = true) ItemStack weaponStack) {
        return TorchShooting.getSupportedHeldProjectiles((Player) (Object) this, weaponStack, instance, original.call(instance));
    }

    @WrapOperation(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;getAllSupportedProjectiles()Ljava/util/function/Predicate;"))
    private Predicate<ItemStack> wrapGetAllSupportedProjectiles(ProjectileWeaponItem instance, Operation<Predicate<ItemStack>> original,
                                                                @Local(argsOnly = true) ItemStack weaponStack) {
        return TorchShooting.getAllSupportedProjectiles((Player) (Object) this, weaponStack, instance, original.call(instance));
    }
}
