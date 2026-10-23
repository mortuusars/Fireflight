package io.github.mortuusars.fireflight;

import io.github.mortuusars.fireflight.torch_shooting.TorchAmmoSelectionBehavior;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    public static class Client {
        public static final ModConfigSpec SPEC;

        public static final ModConfigSpec.BooleanValue RENDER_TORCH_OVERLAY;
        public static final ModConfigSpec.BooleanValue TORCHES_TOOLTIP;

        static {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

            RENDER_TORCH_OVERLAY = builder
                    .comment("Render torch projectile on a Bow/Crossbow. It will look like a regular arrow otherwise. Default: true")
                    .define("RenderTorchOnWeapon", true);

            TORCHES_TOOLTIP = builder
                    .comment("Shootable torches will have a tooltip showing that they can be fired from Bow/Crossbow. Default: false")
                    .define("TorchesTooltip", false);

            SPEC = builder.build();
        }
    }

    public static class Common {
        public static final ModConfigSpec SPEC;

        public static final ModConfigSpec.EnumValue<TorchAmmoSelectionBehavior> AMMO_SELECTION_BEHAVIOR;
        public static final ModConfigSpec.BooleanValue SNEAKING_PREVENTS_TORCH_SHOOTING;
        public static final ModConfigSpec.IntValue SHOOTING_TORCHES_DURABILITY_COST;
        public static final ModConfigSpec.DoubleValue TORCH_DAMAGE;
        public static final ModConfigSpec.IntValue TORCH_IGNITE_SECONDS;
        public static final ModConfigSpec.IntValue SOUL_TORCH_WITHER_SECONDS;
        public static final ModConfigSpec.IntValue REDSTONE_TORCH_SLOWNESS_SECONDS;
        public static final ModConfigSpec.IntValue TORCH_IN_GROUND_ITEM_DROP_TIME;

        public static final ModConfigSpec.BooleanValue SHOOTING_TORCHES_DISPENSER;

        static {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

            TorchAmmoSelectionBehavior ammoSelectionBehavior = TorchAmmoSelectionBehavior.HOTBAR_REVERSED;
            int durabilityCost = 2;
            double baseTorchDamage = 0.5;
            int igniteDurationSeconds = 5;
            int witherDurationSeconds = 8;
            int slownessDurationSeconds = 20;
            int inGroundItemDropTime = 400;

            AMMO_SELECTION_BEHAVIOR = builder
                    .comment("Controls how torch would be considered as an ammo for a bow/crossbow. Default: " + ammoSelectionBehavior,
                            "MAINHAND_OFFHAND: Only when holding torch in one of the hands.",
                            "HOTBAR_IGNORED: Torches in hotbar will not be used as ammo.",
                            "HOTBAR_REVERSED: ie: Arrow in slot 9 will be chosen over a Torch in slot 1-8. And vise-versa. Useful when you carry torches in one of the first slots and don't want them to take priority when shooting.",
                            "REGULAR: Ammo will be chosen from left to right like usual. Hotbar, then 1-3 inventory rows.")
                    .defineEnum("TorchAmmoBehavior", ammoSelectionBehavior);

            SNEAKING_PREVENTS_TORCH_SHOOTING = builder
                    .comment("Sneaking will prevent selecting a torch as ammo. Default: true")
                    .define("SneakingPreventsTorchShooting", true);

            SHOOTING_TORCHES_DURABILITY_COST = builder
                    .comment("Amount of durability loss per shot. Default: " + durabilityCost)
                    .defineInRange("DurabilityCost", durabilityCost, 1, Integer.MAX_VALUE);

            TORCH_DAMAGE = builder
                    .comment("Base damage of the torch projectile. Default: " + baseTorchDamage)
                    .defineInRange("TorchDamage", baseTorchDamage, 0.01, 99);

            TORCH_IGNITE_SECONDS = builder
                    .comment("How long entity that has been hit by torch will burn. " +
                            "In Seconds. Set to 0 to disable. Default: " + igniteDurationSeconds)
                    .defineInRange("TorchIgniteDuration", igniteDurationSeconds, 0, Integer.MAX_VALUE);

            SOUL_TORCH_WITHER_SECONDS = builder
                    .comment("How long entity that has been hit by soul torch will have the wither debuff. " +
                            "In Seconds. Set to 0 to disable. Default: " + witherDurationSeconds)
                    .defineInRange("SoulWitherDuration", witherDurationSeconds, 0, Integer.MAX_VALUE);

            REDSTONE_TORCH_SLOWNESS_SECONDS = builder
                    .comment("How long entity that has been hit by redstone torch will have the slowness debuff. " +
                            "In Seconds. Set to 0 to disable. Default: " + slownessDurationSeconds)
                    .defineInRange("RedstoneSlownessDuration", slownessDurationSeconds, 0, Integer.MAX_VALUE);

            TORCH_IN_GROUND_ITEM_DROP_TIME = builder
                    .comment("How many ticks torch projectile will wait before dropping as an item ",
                            "when stuck in a face of a block (torch wasn't placed). Default: " + inGroundItemDropTime)
                    .defineInRange("TorchProjectileStuckTime", inGroundItemDropTime, 0, Integer.MAX_VALUE);

            SHOOTING_TORCHES_DISPENSER = builder
                    .comment("Dispenser will shoot torches. Default: true")
                    .define("DispenserShooting", true);

            SPEC = builder.build();
        }
    }
}
