package io.github.mortuusars.fireflight.client.render;

import com.google.common.base.Preconditions;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LayeredBakedModel implements BakedModel {
    private final List<BakedModel> layers;

    public LayeredBakedModel(List<BakedModel> layers) {
        Preconditions.checkArgument(!layers.isEmpty(), "Should have at least one model.");
        this.layers = layers;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, @NotNull RandomSource random) {
        List<BakedQuad> quads = new ArrayList<>();

        for (BakedModel layer : layers) {
            List<BakedQuad> layerQuads = layer.getQuads(state, direction, random);
            quads.addAll(layerQuads);
        }

        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return layers.getFirst().useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return layers.getFirst().isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return layers.getFirst().usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return layers.getFirst().isCustomRenderer();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return layers.getFirst().getParticleIcon();
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return layers.getFirst().getOverrides();
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return layers.getFirst().getTransforms();
    }
}