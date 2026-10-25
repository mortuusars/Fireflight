package io.github.mortuusars.fireflight.client.render;

import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import io.github.mortuusars.fireflight.world.entity.Torch;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TorchArrowRenderer extends ArrowRenderer<Torch> {
    public static final ResourceLocation TORCH_ARROW_LOCATION = Fireflight.resource("textures/entity/projectile/torch.png");
    public static final ResourceLocation SOUL_TORCH_ARROW_LOCATION = Fireflight.resource("textures/entity/projectile/soul_torch.png");
    public static final ResourceLocation REDSTONE_TORCH_ARROW_LOCATION = Fireflight.resource("textures/entity/projectile/redstone_torch.png");

    private final TorchType type;

    public TorchArrowRenderer(EntityRendererProvider.Context context, TorchType type) {
        super(context);
        this.type = type;
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull Torch entity) {
        return switch (type) {
            case TORCH -> TORCH_ARROW_LOCATION;
            case SOUL_TORCH -> SOUL_TORCH_ARROW_LOCATION;
            case REDSTONE_TORCH -> REDSTONE_TORCH_ARROW_LOCATION;
        };
    }
}