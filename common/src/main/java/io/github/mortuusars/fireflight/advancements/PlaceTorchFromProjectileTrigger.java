package io.github.mortuusars.fireflight.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import io.github.mortuusars.fireflight.world.entity.Torch;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlaceTorchFromProjectileTrigger extends SimpleCriterionTrigger<PlaceTorchFromProjectileTrigger.TriggerInstance> {
    public @NotNull Codec<PlaceTorchFromProjectileTrigger.TriggerInstance> codec() {
        return PlaceTorchFromProjectileTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Torch torch) {
        this.trigger(player, (triggerInstance) -> triggerInstance.matches(player, torch));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player,
                                  Optional<LocationPredicate> placeLocation,
                                  Optional<TorchType> torchType,
                                  Optional<DistancePredicate> distance) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
              EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
              LocationPredicate.CODEC.optionalFieldOf("place_location").forGetter(TriggerInstance::placeLocation),
              TorchType.CODEC.optionalFieldOf("torch_type").forGetter(TriggerInstance::torchType),
              DistancePredicate.CODEC.optionalFieldOf("distance").forGetter(TriggerInstance::distance)
        ).apply(i, TriggerInstance::new));

        public boolean matches(ServerPlayer player, Torch torch) {
            Vec3 pos = torch.position();
            return (placeLocation.isEmpty() || placeLocation.get().matches(player.serverLevel(), pos.x(), pos.y(), pos.z()))
                  && (torchType.isEmpty() || torchType.get().equals(torch.getTorchType()))
                  && (this.distance.isEmpty() || (torch.getShootPosition() instanceof Vec3 shootPos
                                                    && this.distance.get().matches(pos.x(), pos.y(), pos.z(), shootPos.x(), shootPos.y(), shootPos.z())));
        }
    }
}
