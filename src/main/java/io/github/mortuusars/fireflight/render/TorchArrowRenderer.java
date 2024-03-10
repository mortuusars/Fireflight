package io.github.mortuusars.fireflight.render;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import io.github.mortuusars.fireflight.entity.TorchArrow;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TorchArrowRenderer extends ArrowRenderer<TorchArrow> {
    public static final ResourceLocation TORCH_ARROW_LOCATION = Fireflight.resource("textures/entity/projectile/torch.png");
    public static final ResourceLocation SOUL_TORCH_ARROW_LOCATION = Fireflight.resource("textures/entity/projectile/soul_torch.png");
    public static final ResourceLocation REDSTONE_TORCH_ARROW_LOCATION = Fireflight.resource("textures/entity/projectile/redstone_torch.png");

    private final TorchType type;

    public TorchArrowRenderer(EntityRendererProvider.Context context, TorchType type) {
        super(context);
        this.type = type;
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull TorchArrow entity) {
        return switch (type) {
            case NONE, TORCH -> TORCH_ARROW_LOCATION;
            case SOUL -> SOUL_TORCH_ARROW_LOCATION;
            case REDSTONE -> REDSTONE_TORCH_ARROW_LOCATION;
        };
    }

    @Override
    public void render(@NotNull TorchArrow entity, float entityYaw, float partialTicks,
                       @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}