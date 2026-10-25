package io.github.mortuusars.fireflight.torch_shooting;

import io.github.mortuusars.fireflight.world.entity.RedstoneTorch;
import io.github.mortuusars.fireflight.world.entity.SoulTorch;
import io.github.mortuusars.fireflight.world.entity.Torch;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Pretty much a copy of ProjectileDispenseBehavior. We cannot use it directly because torch is not a ProjectileItem.
 */
public class TorchShootingDispenseBehavior extends DefaultDispenseItemBehavior {
    public static final TorchShootingDispenseBehavior INSTANCE = new TorchShootingDispenseBehavior();

    private final ProjectileItem projectileItem;

    private TorchShootingDispenseBehavior() {
        projectileItem = new ProjectileItem() {
            @Override
            public @NotNull Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
                return Optional.ofNullable(TorchType.getFromStack(stack))
                      .map(type -> {
                          ItemStack pickupStack = stack.copyWithCount(1);
                          Torch projectile = switch (type) {
                              case TORCH -> new Torch(level, pos.x(), pos.y(), pos.z(), pickupStack);
                              case SOUL_TORCH -> new SoulTorch(level, pos.x(), pos.y(), pos.z(), pickupStack);
                              case REDSTONE_TORCH -> new RedstoneTorch(level, pos.x(), pos.y(), pos.z(), pickupStack);
                          };
                          projectile.setBaseDamage(type.getDamage());
                          projectile.pickup = AbstractArrow.Pickup.ALLOWED;
                          return projectile;
                      })
                      .orElseGet(() -> {
                          ItemStack pickupStack = stack.copyWithCount(1);
                          Torch projectile = new Torch(level, pos.x(), pos.y(), pos.z(), pickupStack);
                          projectile.pickup = AbstractArrow.Pickup.ALLOWED;
                          return projectile;
                      });
            }
        };
    }

    public @NotNull ItemStack execute(BlockSource blockSource, ItemStack item) {
        Level level = blockSource.level();
        Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
        ProjectileItem.DispenseConfig config = projectileItem.createDispenseConfig();
        Position position = config.positionFunction().getDispensePosition(blockSource, direction);
        Projectile projectile = projectileItem.asProjectile(level, position, item, direction);
        projectile.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), config.power(), config.uncertainty());
        level.addFreshEntity(projectile);
        item.shrink(1);
        return item;
    }

    protected void playSound(BlockSource blockSource) {
        blockSource.level().levelEvent(projectileItem.createDispenseConfig().overrideDispenseEvent().orElse(1002), blockSource.pos(), 0);
    }
}
