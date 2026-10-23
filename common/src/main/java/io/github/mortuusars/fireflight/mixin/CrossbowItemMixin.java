package io.github.mortuusars.fireflight.mixin;

import io.github.mortuusars.fireflight.Fireflight;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends ProjectileWeaponItem {
    public CrossbowItemMixin(Properties pProperties) {
        super(pProperties);
    }

//    @Inject(method = "getArrow", at = @At("HEAD"), cancellable = true)
//    private static void getArrow(Level level, LivingEntity livingEntity, ItemStack crossbowStack, ItemStack ammoStack, CallbackInfoReturnable<AbstractArrow> cir) {
//        if (Fireflight.isShootableTorch(ammoStack)) {
//            AbstractArrow arrow = Fireflight.createArrowFromItem(ammoStack, level, livingEntity);
//            if (livingEntity instanceof Player) {
//                arrow.setCritArrow(true);
//            }
//            arrow.setShotFromCrossbow(true);
//            int pierceLevel = crossbowStack.getEnchantmentLevel(Enchantments.PIERCING);
//            if (pierceLevel > 0) {
//                arrow.setPierceLevel((byte)pierceLevel);
//            }
//            cir.setReturnValue(arrow);
//            cir.cancel();
//        }
//    }
//
//    @Inject(method = "tryLoadProjectiles", at = @At("HEAD"), cancellable = true)
//    private static void tryLoadProjectiles(LivingEntity shooter, ItemStack crossbowStack, CallbackInfoReturnable<Boolean> cir) {
//        boolean isInCreative = shooter instanceof Player && ((Player)shooter).getAbilities().instabuild;
//        boolean hasMultishot = crossbowStack.getEnchantmentLevel(Enchantments.MULTISHOT) > 0;
//
//        // Mixin to consume more than one torch when loading 2-3 torch arrows.
//        // Crossbow consumes only 1 arrow when loading 3 with multishot,
//
//        if (!isInCreative && hasMultishot) {
//            ItemStack ammoStack = shooter.getProjectile(crossbowStack);
//            if (Fireflight.isShootableTorch(ammoStack)) {
//                for (int i = 0; i < 3; i++) {
//                    if (loadProjectile(shooter, crossbowStack, ammoStack, false, false)) {
//                        cir.setReturnValue(false);
//                        cir.cancel();
//                    }
//                }
//
//                cir.setReturnValue(true);
//                cir.cancel();
//            }
//        }
//    }
//
//    @Shadow // Placeholder. Appropriate method in CrossbowItem will be called.
//    private static boolean loadProjectile(LivingEntity pShooter, ItemStack pCrossbowStack, ItemStack pAmmoStack, boolean pHasAmmo, boolean pIsCreative) {
//        return false;
//    }
}