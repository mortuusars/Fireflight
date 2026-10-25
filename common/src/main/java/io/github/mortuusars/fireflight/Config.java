package io.github.mortuusars.fireflight;

import io.github.mortuusars.fireflight.torch_shooting.OffhandTorchSelection;
import io.github.mortuusars.fireflight.torch_shooting.InventoryTorchSelection;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static class Server {
        public static final ModConfigSpec SPEC;

        public static final ModConfigSpec.EnumValue<OffhandTorchSelection> OFFHAND_TORCH_SELECTION;
        public static final ModConfigSpec.EnumValue<InventoryTorchSelection> INVENTORY_TORCH_SELECTION_WHEN_STANDING;
        public static final ModConfigSpec.EnumValue<InventoryTorchSelection> INVENTORY_TORCH_SELECTION_WHEN_SNEAKING;

        public static final ModConfigSpec.IntValue TORCH_PROJECTILE_STUCK_DROP_TIME;
        public static final ModConfigSpec.BooleanValue DISPENSER_SHOOTS_TORCHES;

        // Torch
        public static final ModConfigSpec.DoubleValue TORCH_VELOCITY_MULTIPLIER;
        public static final ModConfigSpec.DoubleValue TORCH_DAMAGE;
        public static final ModConfigSpec.IntValue TORCH_IGNITE_EFFECT_DURATION;
        public static final ModConfigSpec.IntValue TORCH_WEAPON_DURABILITY_COST;

        // Soul Torch
        public static final ModConfigSpec.DoubleValue SOUL_TORCH_VELOCITY_MULTIPLIER;
        public static final ModConfigSpec.DoubleValue SOUL_TORCH_DAMAGE;
        public static final ModConfigSpec.IntValue SOUL_TORCH_WITHER_EFFECT_DURATION;
        public static final ModConfigSpec.IntValue SOUL_TORCH_WEAPON_DURABILITY_COST;

        // Redstone Torch
        public static final ModConfigSpec.DoubleValue REDSTONE_TORCH_VELOCITY_MULTIPLIER;
        public static final ModConfigSpec.DoubleValue REDSTONE_TORCH_DAMAGE;
        public static final ModConfigSpec.IntValue REDSTONE_TORCH_SLOWNESS_EFFECT_DURATION;
        public static final ModConfigSpec.IntValue REDSTONE_TORCH_WEAPON_DURABILITY_COST;

        static {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

            OFFHAND_TORCH_SELECTION = builder
                  .comment("Controls when torch would be chosen as ammo, when held in offhand.",
                        "ALWAYS: If held - shoot",
                        "WHEN_STANDING: When not sneaking - shoot, if sneaking - search for other ammo in inventory.",
                        "WHEN_SNEAKING: When sneaking - shoot, if not - search for other ammo in inventory.",
                        "NEVER: Ignore torches in offhand.",
                        "Default: ONLY_WHEN_SNEAKING")
                  .defineEnum("offhand_torch_selection", OffhandTorchSelection.WHEN_SNEAKING);

            INVENTORY_TORCH_SELECTION_WHEN_STANDING = builder
                  .comment("Controls when and how torch would be chosen, when offhand does not have a valid ammo and the player is not sneaking.",
                        "ONLY_TORCHES: Only use torches, do not shoot arrows altogether (weird, if you ask me, but could be useful if you only want to shoot with arrows if they are in offhand).",
                        "PRIORITIZE_TORCHES: If torch is in inventory - it will be used regardless of the slot it's in.",
                        "INCLUDE_TORCHES: Torches would be included in the regular ammo selection (first item wins).",
                        "IGNORE_TORCHES: Vanilla behavior.",
                        "Default: PRIORITIZE_TORCHES")
                  .defineEnum("inventory_torch_selection_when_standing", InventoryTorchSelection.IGNORE_TORCHES);

            INVENTORY_TORCH_SELECTION_WHEN_SNEAKING = builder
                  .comment("Controls when and how torch would be chosen, when offhand does not have a valid ammo and the player is not sneaking.",
                        "ONLY_TORCHES: Only use torches, do not shoot arrows altogether (weird, if you ask me, but could be useful if you only want to shoot with arrows if they are in offhand).",
                        "PRIORITIZE_TORCHES: If torch is in inventory - it will be used regardless of the slot it's in.",
                        "INCLUDE_TORCHES: Torches would be included in the regular ammo selection (first item wins).",
                        "IGNORE_TORCHES: Vanilla behavior.",
                        "Default: PRIORITIZE_TORCHES")
                  .defineEnum("inventory_torch_selection_when_sneaking", InventoryTorchSelection.PRIORITIZE_TORCHES);

            TORCH_PROJECTILE_STUCK_DROP_TIME = builder
                  .comment("How many ticks torch projectile will wait before dropping as an item ",
                        "when stuck in a face of a block (when torch wasn't placed).")
                  .defineInRange("torch_projectile_stuck_drop_time", 400, 0, Integer.MAX_VALUE);

            DISPENSER_SHOOTS_TORCHES = builder
                  .comment("Dispenser will shoot torches. Default: true")
                  .define("dispenser_shoots_torches", true);

            {
                builder.push("torch");

                TORCH_VELOCITY_MULTIPLIER = builder
                      .comment("Velocity multiplier of the torch projectile. 1.0 - same as arrow.")
                      .defineInRange("velocity_multiplier", 0.5, 0.01, 10.0);

                TORCH_DAMAGE = builder
                      .comment("Base damage of the torch projectile.")
                      .defineInRange("damage", 0.5, 0.0, 99);

                TORCH_IGNITE_EFFECT_DURATION = builder
                      .comment("How long (in ticks) entity that has been hit by torch will burn." +
                            "Set to 0 to disable.")
                      .defineInRange("ignite_effect_duration", 100, 0, Integer.MAX_VALUE);

                TORCH_WEAPON_DURABILITY_COST = builder
                      .comment("Amount of durability Bow/Crossbow loses per torch shot.")
                      .defineInRange("weapon_durability_cost", 3, 1, Integer.MAX_VALUE);

                builder.pop();
            }

            {
                builder.push("soul_torch");

                SOUL_TORCH_VELOCITY_MULTIPLIER = builder
                      .comment("Velocity multiplier of the soul torch projectile. 1.0 - same as arrow.")
                      .defineInRange("velocity_multiplier", 0.5, 0.01, 10.0);

                SOUL_TORCH_DAMAGE = builder
                      .comment("Base damage of the soul torch projectile.")
                      .defineInRange("torch_damage", 1.0, 0.01, 99);

                SOUL_TORCH_WITHER_EFFECT_DURATION = builder
                      .comment("How long (in ticks) entity that has been hit by soul torch will have the wither debuff." +
                            "Set to 0 to disable.")
                      .defineInRange("wither_effect_duration", 160, 0, Integer.MAX_VALUE);

                SOUL_TORCH_WEAPON_DURABILITY_COST = builder
                      .comment("Amount of durability Bow/Crossbow loses per torch shot.")
                      .defineInRange("weapon_durability_cost", 5, 1, Integer.MAX_VALUE);

                builder.pop();
            }

            {
                builder.push("redstone_torch");

                REDSTONE_TORCH_VELOCITY_MULTIPLIER = builder
                      .comment("Velocity multiplier of the redstone torch projectile. 1.0 - same as arrow.")
                      .defineInRange("velocity_multiplier", 0.7, 0.01, 10.0);

                REDSTONE_TORCH_DAMAGE = builder
                      .comment("Base damage of the redstone torch projectile.")
                      .defineInRange("torch_damage", 1.0, 0.01, 99);

                REDSTONE_TORCH_SLOWNESS_EFFECT_DURATION = builder
                      .comment("How long (in ticks) entity that has been hit by redstone torch will have the slowness debuff." +
                            "Set to 0 to disable.")
                      .defineInRange("slowness_effect_duration", 300, 0, Integer.MAX_VALUE);

                REDSTONE_TORCH_WEAPON_DURABILITY_COST = builder
                      .comment("Amount of durability Bow/Crossbow loses per torch shot.")
                      .defineInRange("weapon_durability_cost", 3, 1, Integer.MAX_VALUE);

                builder.pop();
            }

            SPEC = builder.build();
        }
    }

    public static class Client {
        public static final ModConfigSpec SPEC;

        public static final ModConfigSpec.BooleanValue RENDER_TORCH_OVERLAY;

        static {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

            RENDER_TORCH_OVERLAY = builder
                  .comment("Render torch projectile on a Bow/Crossbow. It will look like a regular arrow otherwise. Default: true")
                  .define("render_torch_on_weapon", true);

            SPEC = builder.build();
        }
    }
}
