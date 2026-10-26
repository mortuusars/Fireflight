package io.github.mortuusars.fireflight.neoforge.datagen.server;

import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.advancements.PlaceTorchFromProjectileTrigger;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementsDatagen extends AdvancementProvider {
    public AdvancementsDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries,
                               ExistingFileHelper existingFileHelper, List<AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, subProviders);
    }

    public static class Generator implements AdvancementGenerator {
        @SuppressWarnings("removal")
        @Override
        public void generate(HolderLookup.@NotNull Provider registries,
                             @NotNull Consumer<AdvancementHolder> consumer,
                             @NotNull ExistingFileHelper existingFileHelper) {
            AdvancementHolder shootAndPlaceTorch = Advancement.Builder.advancement()
                  .parent(ResourceLocation.parse("minecraft:adventure/shoot_arrow"))
                  .display(
                        new ItemStack(Items.TORCH),
                        Component.translatable("advancement.fireflight.shoot_torch.title"),
                        Component.translatable("advancement.fireflight.shoot_torch.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                  )
                  .requirements(AdvancementRequirements.Strategy.OR)
                  .addCriterion("shoot_torch", Fireflight.CriteriaTriggers.PLACE_TORCH_FROM_PROJECTILE.get().createCriterion(
                        new PlaceTorchFromProjectileTrigger.TriggerInstance(
                              Optional.empty(),
                              Optional.of(LocationPredicate.Builder.location().setLight(
                                    LightPredicate.Builder.light().setComposite(MinMaxBounds.Ints.atMost(4))).build()),
                              Optional.of(TorchType.TORCH),
                              Optional.of(DistancePredicate.absolute(MinMaxBounds.Doubles.atLeast(50)))))
                  )
                  .save(consumer, Fireflight.resource("adventure/shoot_torch"), existingFileHelper);
        }
    }
}
