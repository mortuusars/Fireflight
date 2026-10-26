package io.github.mortuusars.fireflight.torch_shooting;

import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.world.entity.RedstoneTorch;
import io.github.mortuusars.fireflight.world.entity.SoulTorch;
import io.github.mortuusars.fireflight.world.entity.Torch;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Predicate;

public class TorchShooting {
    public static Predicate<ItemStack> getSupportedHeldProjectiles(Player player, ItemStack weaponStack,
                                                                   ProjectileWeaponItem weaponItem, Predicate<ItemStack> original) {
        if (weaponStack.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES)) {
            return original;
        }

        return switch (Config.Server.OFFHAND_TORCH_SELECTION.get()) {
            case ALWAYS -> original.or(Fireflight::isShootableTorch);
            case WHEN_STANDING -> !player.isSecondaryUseActive()
                  ? original.or(Fireflight::isShootableTorch)
                  : original;
            case WHEN_SNEAKING -> player.isSecondaryUseActive()
                  ? original.or(Fireflight::isShootableTorch)
                  : original;
            case NEVER -> original;
        };
    }

    public static Predicate<ItemStack> getAllSupportedProjectiles(Player player, ItemStack weaponStack,
                                                                  ProjectileWeaponItem weaponItem, Predicate<ItemStack> original) {
        if (weaponStack.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES)) {
            return original;
        }

        InventoryTorchSelection selection = player.isSecondaryUseActive()
              ? Config.Server.INVENTORY_TORCH_SELECTION_WHEN_SNEAKING.get()
              : Config.Server.INVENTORY_TORCH_SELECTION_WHEN_STANDING.get();

        Predicate<ItemStack> predicate = applyTorchSelection(selection, player, weaponStack, weaponItem, original);

        // Fixes not being able to shoot when started using bow while sneaking and then stopped sneaking while no other ammo is in inventory
        if (!player.isSecondaryUseActive()
              && player.isUsingItem()
              && Config.Server.INVENTORY_TORCH_SELECTION_WHEN_STANDING.get() == InventoryTorchSelection.IGNORE_TORCHES
              && Config.Server.INVENTORY_TORCH_SELECTION_WHEN_SNEAKING.get() != InventoryTorchSelection.IGNORE_TORCHES
              && !player.getInventory().contains(original)
              && player.getInventory().contains(Fireflight::isShootableTorch)) {
            return predicate.or(Fireflight::isShootableTorch);
        }

        return predicate;
    }

    private static Predicate<ItemStack> applyTorchSelection(InventoryTorchSelection selection,
                                                            Player player, ItemStack weaponStack,
                                                            ProjectileWeaponItem weaponItem, Predicate<ItemStack> original) {
        return switch (selection) {
            case ONLY_TORCHES -> Fireflight::isShootableTorch;
            case PRIORITIZE_TORCHES -> player.getInventory().contains(Fireflight::isShootableTorch)
                      ? Fireflight::isShootableTorch
                      : original;
            case INCLUDE_TORCHES -> original.or(Fireflight::isShootableTorch);
            case IGNORE_TORCHES -> original;
        };
    }

    public static Optional<Projectile> createProjectile(ProjectileWeaponItem instance, Level level, LivingEntity shooter,
                                                        ItemStack weapon, ItemStack ammo, boolean isCrit) {
        return Optional.ofNullable(TorchType.getFromStack(ammo))
              .map(type -> {
                  ItemStack pickupStack = ammo.copyWithCount(1);
                  Torch projectile = switch (type) {
                      case TORCH -> new Torch(level, shooter, pickupStack);
                      case SOUL_TORCH -> new SoulTorch(level, shooter, pickupStack);
                      case REDSTONE_TORCH -> new RedstoneTorch(level, shooter, pickupStack);
                  };
                  projectile.setBaseDamage(type.getDamage());
                  return projectile;
              });
    }

    public static int getDurabilityCost(ProjectileWeaponItem item, ItemStack ammo, int original) {
        return TorchType.mapOrDefault(ammo, TorchType::getWeaponDurabilityCost, original);
    }
}
