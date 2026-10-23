package io.github.mortuusars.fireflight.torch_shooting;

import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.entity.RedstoneTorch;
import io.github.mortuusars.fireflight.entity.SoulTorch;
import io.github.mortuusars.fireflight.entity.Torch;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class TorchShooting {
    public static void getProjectile(Player player) {

        if (Config.Common.SNEAKING_PREVENTS_TORCH_SHOOTING.get() && player.isSecondaryUseActive()) {
            return;
        }

        /*ItemStack weapon = event.getProjectileWeaponItemStack();
        ItemStack projectile = event.getProjectileItemStack();

        if (weapon.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES) ||
              !(weapon.getItem() instanceof ProjectileWeaponItem projectileWeaponItem))
            return;

        Predicate<ItemStack> supportedHeldProjectiles = projectileWeaponItem.getSupportedHeldProjectiles();

        ItemStack offhandStack = player.getOffhandItem();
        if (Fireflight.isShootableTorch(offhandStack)) {
            event.setProjectileItemStack(offhandStack);
            return;
        } else if (supportedHeldProjectiles.test(offhandStack))
            return;

        ItemStack mainhandItem = player.getMainHandItem();
        if (Fireflight.isShootableTorch(mainhandItem)) {
            event.setProjectileItemStack(mainhandItem);
            return;
        } else if (supportedHeldProjectiles.test(mainhandItem))
            return;


        TorchAmmoSelectionBehavior torchSelection = Config.Common.AMMO_SELECTION_BEHAVIOR.get();

        if (torchSelection == TorchAmmoSelectionBehavior.MAINHAND_OFFHAND)
            return;


        // Finding currently selected projectile in inventory to compare against later:
        int projectileSlot = -1;
        if (!projectile.isEmpty()) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (projectile.equals(stack, true)) {
                    projectileSlot = i;
                    break;
                }
            }
        }

        // Select the torch from any slot since we don't have other projectiles:
        if (projectileSlot == -1) {
            if (!player.isCreative() && weapon.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) == 0) {
                // Player probably has a quiver or something.
                return;
            }

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (Fireflight.isShootableTorch(stack)) {
                    event.setProjectileItemStack(stack);
                    return;
                }
            }

            return; // No shootable torches in inventory
        }

        if (torchSelection == TorchAmmoSelectionBehavior.HOTBAR_REVERSED) {
            for (int i = 8; i >= 0; i--) {
                if (projectileSlot == i)
                    return;

                ItemStack stack = player.getInventory().getItem(i);

                if (Fireflight.isShootableTorch(stack)) {
                    event.setProjectileItemStack(stack);
                    return;
                }
            }
        }

        int startIndex = torchSelection == TorchAmmoSelectionBehavior.REGULAR ? 0 : 9;

        for (int i = startIndex; i < Math.min(player.getInventory().getContainerSize(), projectileSlot + 1); i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if (Fireflight.isShootableTorch(stack)) {
                event.setProjectileItemStack(stack);
                return;
            }
        }*/
    }

    public static Predicate<ItemStack> getSupportedHeldProjectiles(Player player, ItemStack weaponStack,
                                                                   ProjectileWeaponItem weaponItem, Predicate<ItemStack> original) {
        if (weaponStack.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES)) {
            return original;
        }

        if (player.isSecondaryUseActive()) {
            return original.or(Fireflight::isShootableTorch);
        }

        return original;
    }

    public static Predicate<ItemStack> getAllSupportedProjectiles(Player player, ItemStack weaponStack,
                                                                  ProjectileWeaponItem weaponItem, Predicate<ItemStack> original) {
        if (weaponStack.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES)) {
            return original;
        }

        if (player.isSecondaryUseActive()) {
            return original.or(Fireflight::isShootableTorch);
        }

        return original;
    }

    public static Optional<Projectile> createProjectile(ProjectileWeaponItem instance, Level level, LivingEntity shooter,
                                                        ItemStack weapon, ItemStack ammo, boolean isCrit) {
        if (!Fireflight.isShootableTorch(ammo)) {
            return Optional.empty();
        }

        Torch torch;
        ItemStack torchStack = ammo.copyWithCount(1);

        if (torchStack.is(Fireflight.Tags.Items.SOUL_TORCH))
            torch = new SoulTorch(level, shooter, torchStack);
        else if (torchStack.is(Fireflight.Tags.Items.REDSTONE_TORCH))
            torch = new RedstoneTorch(level, shooter, torchStack);
        else {
            torch = new Torch(level, shooter, torchStack);
        }

        torch.setBaseDamage(Config.Common.TORCH_DAMAGE.get());

        return Optional.of(torch);
    }
}
