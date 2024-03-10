package io.github.mortuusars.fireflight.torch_shooting;

import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.entity.RedstoneTorchArrow;
import io.github.mortuusars.fireflight.entity.SoulTorchArrow;
import io.github.mortuusars.fireflight.entity.TorchArrow;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TorchShootingDispenserBehavior {
    public static final DispenseItemBehavior DISPENSER_BEHAVIOR = new AbstractProjectileDispenseBehavior() {
        @Override
        protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position position, ItemStack stack) {
            TorchArrow arrow;

            if (stack.is(Fireflight.Tags.Items.SOUL_TORCH))
                arrow = new SoulTorchArrow(level, position.x(), position.y(), position.z(), stack.copy().split(1));
            else if (stack.is(Fireflight.Tags.Items.REDSTONE_TORCH))
                arrow = new RedstoneTorchArrow(level, position.x(), position.y(), position.z(), stack.copy().split(1));
            else
                arrow = new TorchArrow(level, position.x(), position.y(), position.z(), stack.copy().split(1));

            arrow.setBaseDamage(Config.Common.TORCH_DAMAGE.get());
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;

            return arrow;
        }
    };
}
