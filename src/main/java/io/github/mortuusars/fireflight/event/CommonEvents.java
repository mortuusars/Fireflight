package io.github.mortuusars.fireflight.event;

import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.TorchAmmoSelectionBehavior;
import io.github.mortuusars.fireflight.TorchType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingGetProjectileEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.function.Predicate;

public class CommonEvents {
//    public static class Mod {
//        @SubscribeEvent
//        public static void commonSetup(FMLCommonSetupEvent event) {
//            event.enqueueWork(() -> {
//
//            })
//        }
//    }

    public static class Forge {
        @SubscribeEvent
        public static void onGetProjectile(LivingGetProjectileEvent event) {
            if (!(event.getEntity() instanceof Player player))
                return;

            ItemStack weapon = event.getProjectileWeaponItemStack();
            ItemStack projectile = event.getProjectileItemStack();

            if (weapon.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES) ||
                    !(weapon.getItem() instanceof ProjectileWeaponItem projectileWeaponItem))
                return;

            Predicate<ItemStack> supportedHeldProjectiles = projectileWeaponItem.getSupportedHeldProjectiles();

            ItemStack offhandStack = player.getOffhandItem();
            if (Fireflight.isShootableTorch(offhandStack)) {
                event.setProjectileItemStack(offhandStack);
                return;
            }
            else if (supportedHeldProjectiles.test(offhandStack))
                return;

            ItemStack mainhandItem = player.getMainHandItem();
            if (Fireflight.isShootableTorch(mainhandItem)) {
                event.setProjectileItemStack(mainhandItem);
                return;
            }
            else if (supportedHeldProjectiles.test(mainhandItem))
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
            }
        }

        @SubscribeEvent
        public static void onArrowLoose(ArrowLooseEvent event) {
            Player player = event.getEntity();
            Level level = event.getLevel();
            ItemStack weapon = event.getBow();

            if (weapon.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES))
                return;

            // Crossbow is damaged by 1 for each shoot by default - this handles increased durability cost:
            if (!level.isClientSide && weapon.getItem() instanceof CrossbowItem && Config.Common.SHOOTING_TORCHES_DURABILITY_COST.get() > 1) {
                handleCrossbowAdditionalDurabilityCost(weapon, player);
                return;
            }

            // Only handling bows here. Crossbows are handled by CrossbowItemMixin
            if (weapon.getItem() instanceof BowItem && !weapon.is(Fireflight.Tags.Items.CANNOT_SHOOT_TORCHES)) {
                ItemStack projectile = player.getProjectile(weapon);

                if (TorchType.getFromStack(projectile) == TorchType.NONE)
                    return;

                handleBowShooting(event, projectile, level, player, weapon);
            }
        }

        private static void handleBowShooting(ArrowLooseEvent event, ItemStack projectile, Level level, Player player, ItemStack weapon) {
            float power = BowItem.getPowerForTime(event.getCharge());

            if (power >= 0.1F) {
                AbstractArrow arrow = Fireflight.createArrowFromItem(projectile, level, player);
                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0F, power * 3.0F, 1F);

                if (power == 1.0F) {
                    arrow.setCritArrow(true);
                }

                int powerEnchantmentLevel = weapon.getEnchantmentLevel(Enchantments.POWER_ARROWS);
                if (powerEnchantmentLevel > 0) {
                    arrow.setBaseDamage(arrow.getBaseDamage() + (double)powerEnchantmentLevel * 0.5D + 0.5D);
                }

                int punchEnchantmentLevel = weapon.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
                if (punchEnchantmentLevel > 0) {
                    arrow.setKnockback(punchEnchantmentLevel);
                }

                level.addFreshEntity(arrow);

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1F, 0.6F + (power * 0.6F) + (level.random.nextFloat() * 0.2F));

                if (!level.isClientSide) {
                    weapon.hurtAndBreak(Config.Common.SHOOTING_TORCHES_DURABILITY_COST.get(), player,
                            pl -> pl.broadcastBreakEvent(player.getUsedItemHand()));
                }

                if (!player.isCreative()) {
                    projectile.shrink(1);
                    if (projectile.isEmpty()) {
                        player.getInventory().removeItem(projectile);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(weapon.getItem()));
                event.setCanceled(true);
            }
        }

        private static void handleCrossbowAdditionalDurabilityCost(ItemStack weapon, Player player) {
            List<ItemStack> chargedProjectiles = CrossbowItem.getChargedProjectiles(weapon);

            for (ItemStack chargedProjectileStack : chargedProjectiles) {
                if (Fireflight.isShootableTorch(chargedProjectileStack) && !weapon.isEmpty()) {
                    weapon.hurtAndBreak(Config.Common.SHOOTING_TORCHES_DURABILITY_COST.get() - 1, player,
                            pl -> pl.broadcastBreakEvent(player.getUsedItemHand()));
                }
            }
        }
    }
}
