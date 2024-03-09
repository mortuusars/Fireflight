package io.github.mortuusars.fireflight;

import com.google.common.base.Preconditions;
import io.github.mortuusars.fireflight.entity.TorchArrow;
import io.github.mortuusars.fireflight.event.ClientEvents;
import io.github.mortuusars.fireflight.event.CommonEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

@Mod(Fireflight.ID)
public class Fireflight
{
    public static final String ID = "fireflight";

    public Fireflight()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.Common.SPEC);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Entities.ENTITIES.register(modEventBus);

        modEventBus.register(ClientEvents.class);

        MinecraftForge.EVENT_BUS.register(CommonEvents.Forge.class);
    }

    public static ResourceLocation resource(String path) {
        return new ResourceLocation(ID, path);
    }

    public static boolean isShootableTorch(ItemStack stack) {
        return TorchType.isShootableTorch(stack);
    }

    public static AbstractArrow createArrowFromItem(ItemStack ammoStack, Level level, LivingEntity shooter) {
        Preconditions.checkArgument(Fireflight.isShootableTorch(ammoStack));

        TorchArrow torchArrow;

//            if (ammoStack.is(Fireflight.Tags.Items.SOUL_TORCH))
//                torchArrow = new SoulTorchArrow(level, shooter);
//            else if (ammoStack.is(Fireflight.Tags.Items.REDSTONE_TORCH))
//                torchArrow = new RedstoneTorchArrow(level, shooter);
//            else
        torchArrow = new TorchArrow(level, shooter, ammoStack.copy().split(1));

        torchArrow.setBaseDamage(Config.Common.TORCH_DAMAGE.get());

        return torchArrow;
    }

    public static class Entities {
        public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Fireflight.ID);

        public static final RegistryObject<EntityType<TorchArrow>> TORCH_ARROW = ENTITIES.register("torch_arrow",
                () -> EntityType.Builder.<TorchArrow>of(TorchArrow::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .fireImmune()
                        .build("torch_arrow"));

//        public static final RegistryObject<EntityType<SoulTorchArrow>> SOUL_TORCH_ARROW = ENTITIES.register("soul_torch_arrow",
//                () -> EntityType.Builder.<SoulTorchArrow>of(SoulTorchArrow::new, MobCategory.MISC)
//                        .sized(0.5F, 0.5F)
//                        .fireImmune()
//                        .build("soul_torch_arrow"));
//
//        public static final RegistryObject<EntityType<RedstoneTorchArrow>> REDSTONE_TORCH_ARROW = ENTITIES.register("redstone_torch_arrow",
//                () -> EntityType.Builder.<RedstoneTorchArrow>of(RedstoneTorchArrow::new, MobCategory.MISC)
//                        .sized(0.5F, 0.5F)
//                        .fireImmune()
//                        .build("redstone_torch_arrow"));

        public static void register(IEventBus bus) {
            ENTITIES.register(bus);
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
