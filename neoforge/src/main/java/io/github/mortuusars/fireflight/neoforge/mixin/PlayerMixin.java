package io.github.mortuusars.fireflight.neoforge.mixin;

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
    @WrapOperation(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;getSupportedHeldProjectiles(Lnet/minecraft/world/item/ItemStack;)Ljava/util/function/Predicate;"))
    private Predicate<ItemStack> wrapGetSupportedHeldProjectiles(ProjectileWeaponItem instance, ItemStack stack,
                                                                 Operation<Predicate<ItemStack>> original) {
        return TorchShooting.getSupportedHeldProjectiles((Player) (Object) this, stack, instance, original.call(instance, stack));
    }

    @WrapOperation(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;getAllSupportedProjectiles(Lnet/minecraft/world/item/ItemStack;)Ljava/util/function/Predicate;"))
    private Predicate<ItemStack> wrapGetAllSupportedProjectiles(ProjectileWeaponItem instance, ItemStack stack,
                                                                Operation<Predicate<ItemStack>> original) {
        return TorchShooting.getAllSupportedProjectiles((Player) (Object) this, stack, instance, original.call(instance, stack));
    }
}
