package io.github.mortuusars.fireflight;

import io.github.mortuusars.fireflight.advancements.PlaceTorchFromProjectileTrigger;
import io.github.mortuusars.fireflight.world.entity.RedstoneTorch;
import io.github.mortuusars.fireflight.world.entity.SoulTorch;
import io.github.mortuusars.fireflight.world.entity.Torch;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import io.github.mortuusars.mortaar.Register;
import io.github.mortuusars.mortaar.Registrar;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class Fireflight {
    public static final String ID = "fireflight";

    public static final Registrar REGISTRAR = Register.registrar(ID);

    public static void init() {
        EntityTypes.init();
        CriteriaTriggers.init();
    }

    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    public static boolean isShootableTorch(ItemStack stack) {
        return TorchType.isShootableTorch(stack);
    }

    public static class EntityTypes {
        public static final Supplier<EntityType<Torch>> TORCH = REGISTRAR.entityType("torch",
              Torch::new, MobCategory.MISC, true, builder -> builder
                    .sized(0.5F, 0.5F)
                    .fireImmune());

        public static final Supplier<EntityType<SoulTorch>> SOUL_TORCH = REGISTRAR.entityType("soul_torch",
              SoulTorch::new, MobCategory.MISC, true, builder -> builder
                    .sized(0.5F, 0.5F)
                    .fireImmune());

        public static final Supplier<EntityType<RedstoneTorch>> REDSTONE_TORCH = REGISTRAR.entityType("redstone_torch",
              RedstoneTorch::new, MobCategory.MISC, true, builder -> builder
                    .sized(0.5F, 0.5F)
                    .fireImmune());

        public static void init() {
        }
    }

    public static class CriteriaTriggers {
        public static Supplier<PlaceTorchFromProjectileTrigger> PLACE_TORCH_FROM_PROJECTILE =
              REGISTRAR.criterionTrigger("place_torch_from_projectile", PlaceTorchFromProjectileTrigger::new);

        public static void init() {
        }
    }

    public static class Tags {
        public static class Items {
            public static final TagKey<Item> TORCH = TagKey.create(Registries.ITEM, Fireflight.resource("torches"));
            public static final TagKey<Item> SOUL_TORCH = TagKey.create(Registries.ITEM, Fireflight.resource("soul_torches"));
            public static final TagKey<Item> REDSTONE_TORCH = TagKey.create(Registries.ITEM, Fireflight.resource("redstone_torches"));

            public static final TagKey<Item> CANNOT_SHOOT_TORCHES = TagKey.create(Registries.ITEM, Fireflight.resource("cannot_shoot_torches"));
        }
    }
}
