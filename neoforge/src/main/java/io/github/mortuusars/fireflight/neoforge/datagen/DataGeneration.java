package io.github.mortuusars.fireflight.neoforge.datagen;

import io.github.mortuusars.fireflight.Fireflight;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Fireflight.ID)
public class DataGeneration {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();

        // -- Server

//        generator.addProvider(event.includeServer(), new RecipesDatagen(output, registries));
//        TagsDatagen.BlockTagsDatagen blockTags = new TagsDatagen.BlockTagsDatagen(output, registries, helper);
//        generator.addProvider(event.includeServer(), blockTags);
//        generator.addProvider(event.includeServer(), new TagsDatagen.ItemTags(output, registries, blockTags.contentsGetter(), helper));
//        generator.addProvider(event.includeServer(), new TagsDatagen.DamageTypeTags(output, registries, helper));
//        generator.addProvider(event.includeServer(), new TagsDatagen.EntityTypeTagsDatagen(output, registries, helper));
//        generator.addProvider(event.includeServer(), LootTablesDatagen.create(output, registries));
//
//        generator.addProvider(event.includeServer(), datapackEntries);
//        generator.addProvider(event.includeServer(), new TagsDatagen.SealImpressionTagsDatagen(output, registries, helper));
//        generator.addProvider(event.includeServer(), new TagsDatagen.ServiceAddressTagsDatagen(output, registries, helper));
//
//        generator.addProvider(event.includeServer(), new AdvancementsDatagen(output, registries, helper, List.of(
//              new AdvancementsDatagen.Generator()
//        )));


        // -- Client

//        generator.addProvider(event.includeClient(), new ModelsDatagen(output, helper));
    }
}
