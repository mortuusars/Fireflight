package io.github.mortuusars.fireflight;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fireflight.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static class Common {
        public static final ForgeConfigSpec SPEC;

        public static final ForgeConfigSpec.EnumValue<TorchAmmoSelectionBehavior> AMMO_SELECTION_BEHAVIOR;
        public static final ForgeConfigSpec.IntValue SHOOTING_TORCHES_DURABILITY_COST;
        public static final ForgeConfigSpec.DoubleValue TORCH_DAMAGE;
        public static final ForgeConfigSpec.IntValue TORCH_IGNITE_SECONDS;
        public static final ForgeConfigSpec.IntValue SOUL_TORCH_WITHER_SECONDS;
        public static final ForgeConfigSpec.IntValue REDSTONE_TORCH_SLOWNESS_SECONDS;
        public static final ForgeConfigSpec.IntValue TORCH_IN_GROUND_ITEM_DROP_TIME;

        public static final ForgeConfigSpec.BooleanValue SHOOTING_TORCHES_DISPENSER;

        static {
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

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
                    .comment("Dispenser will shoot torches.")
                    .define("DispenserShooting", true);

            SPEC = builder.build();
        }
//
//    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
//
//    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
//            .comment("Whether to log the dirt block on common setup")
//            .define("logDirtBlock", true);
//
//    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
//            .comment("A magic number")
//            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
//
//    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");
//
//    // a list of strings that are treated as resource locations for items
//    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);
//
//    static final ForgeConfigSpec SPEC = BUILDER.build();
//
//    public static boolean logDirtBlock;
//    public static int magicNumber;
//    public static String magicNumberIntroduction;
//    public static Set<Item> items;
//
//    private static boolean validateItemName(final Object obj)
//    {
//        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
//    }
//
//    @SubscribeEvent
//    static void onLoad(final ModConfigEvent event)
//    {
//        logDirtBlock = LOG_DIRT_BLOCK.get();
//        magicNumber = MAGIC_NUMBER.get();
//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//
//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
//    }
    }
}
