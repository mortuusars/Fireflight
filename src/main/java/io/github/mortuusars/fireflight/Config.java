package io.github.mortuusars.fireflight;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.codehaus.plexus.util.dag.DAG;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Fireflight.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static class Common {
        public static final ForgeConfigSpec SPEC;

        public static final ForgeConfigSpec.DoubleValue TORCH_DAMAGE;
        public static final ForgeConfigSpec.IntValue TORCH_IGNITE_SECONDS;
        public static final ForgeConfigSpec.IntValue SHOOTING_TORCHES_DURABILITY_COST;
        public static final ForgeConfigSpec.EnumValue<TorchAmmoSelectionBehavior> AMMO_SELECTION_BEHAVIOR;

//        public static final ForgeConfigSpec.BooleanValue SHOOTING_TORCHES_DISPENSER;
//        public static final ForgeConfigSpec.IntValue SHOOTING_TORCHES_WITHER_SECONDS;
//        public static final ForgeConfigSpec.IntValue SHOOTING_TORCHES_SLOWNESS_SECONDS;
//        public static final ForgeConfigSpec.BooleanValue SHOOTING_TORCHES_IGNORE_HOTBAR;

        static {
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

            int igniteDurationSeconds = 5;
            double baseTorchDamage = 0.5;
            int durabilityCost = 2;

            TORCH_DAMAGE = builder
                    .comment("Base damage of the torch projectile. Default: " + baseTorchDamage)
                    .defineInRange("TorchDamage", baseTorchDamage, 0.01, 99);

            TORCH_IGNITE_SECONDS = builder
                    .comment("How long entity that has been hit by torch will burn. In Seconds. Set to 0 to disable. " +
                            "Default: " + igniteDurationSeconds)
                    .defineInRange("IgniteDuration", igniteDurationSeconds, 0, Integer.MAX_VALUE);

            SHOOTING_TORCHES_DURABILITY_COST = builder
                    .comment("Amount of durability loss per shot. Default: " + durabilityCost)
                    .defineInRange("DurabilityCost", durabilityCost, 1, Integer.MAX_VALUE);

            AMMO_SELECTION_BEHAVIOR = builder
                    .comment("Controls how torch would be considered as an ammo for a bow/crossbow. Default: HOTBAR_REVERSED.",
                            "MAINHAND_OFFHAND: Only when holding torch in one of the hands.",
                            "HOTBAR_IGNORED: Torches in hotbar will not be used as ammo.",
                            "HOTBAR_REVERSED: ie: Arrow in slot 9 will be chosen over a Torch in slot 1-8. And vise-versa. Useful when you carry torches in one of the first slots and don't want them to take priority when shooting.",
                            "REGULAR: Ammo will be chosen from left to right like usual. Hotbar, then 1-3 inventory rows.")
                    .defineEnum("TorchAmmoBehavior", TorchAmmoSelectionBehavior.HOTBAR_REVERSED);

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
