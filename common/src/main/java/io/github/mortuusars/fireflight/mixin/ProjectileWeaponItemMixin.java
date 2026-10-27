package io.github.mortuusars.fireflight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.torch_shooting.InventoryTorchSelection;
import io.github.mortuusars.fireflight.torch_shooting.TorchShooting;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(value = ProjectileWeaponItem.class, priority = 999) // Slightly higher priority than default
public class ProjectileWeaponItemMixin {
    @WrapOperation(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;createProjectile(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/entity/projectile/Projectile;"))
    private Projectile wrapCreateProjectile(ProjectileWeaponItem instance, Level level, LivingEntity shooter,
                                            ItemStack weapon, ItemStack ammo, boolean isCrit, Operation<Projectile> original) {
        return TorchShooting.createProjectile(instance, level, shooter, weapon, ammo, isCrit)
              .orElseGet(() -> original.call(instance, level, shooter, weapon, ammo, isCrit));
    }

    @WrapOperation(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;getDurabilityUse(Lnet/minecraft/world/item/ItemStack;)I"))
    private int wrapCreateProjectile(ProjectileWeaponItem instance, ItemStack stack, Operation<Integer> original) {
        return TorchShooting.getDurabilityCost(instance, stack, original.call(instance, stack));
    }

    @SuppressWarnings("NameDoesntMatchTargetClass")
    @WrapOperation(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;useAmmo(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Z)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack wrapDraw(ItemStack weapon, ItemStack ammo, LivingEntity shooter, boolean intangible,
                                      Operation<ItemStack> original, @Local(argsOnly = true, ordinal = 1) ItemStack originalAmmo) {
        // This matters when shooting from Crossbow enchanted with Multishot.
        // 2/3 Multishot projectiles do not consume ammo which is problematic as placed torches always drop the item when broken.
        if (Fireflight.isShootableTorch(ammo)) {
            ammo = originalAmmo; // Pass original stack instead of a copy, to properly shrink it
            intangible = false; // Torches are not intangible
        }

        return original.call(weapon, ammo, shooter, intangible);
    }

    @Inject(method = "getHeldProjectile", at = @At("HEAD"), cancellable = true)
    private static void onGetHeldProjectile(LivingEntity shooter, Predicate<ItemStack> isAmmo, CallbackInfoReturnable<ItemStack> cir) {
        if (shooter instanceof Player player
              && Config.Server.BLOCK_GET_HELD_PROJECTILE_CHANGES.get()
              && ((Config.Server.INVENTORY_TORCH_SELECTION_WHEN_STANDING.get() == InventoryTorchSelection.PRIORITIZE_TORCHES && !player.isSecondaryUseActive() && player.getInventory().contains(Fireflight::isShootableTorch))
                || (Config.Server.INVENTORY_TORCH_SELECTION_WHEN_SNEAKING.get() == InventoryTorchSelection.PRIORITIZE_TORCHES && player.isSecondaryUseActive() && player.getInventory().contains(Fireflight::isShootableTorch)))
              && !isAmmo.test(shooter.getOffhandItem())
              && !isAmmo.test(shooter.getMainHandItem())) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }
}
