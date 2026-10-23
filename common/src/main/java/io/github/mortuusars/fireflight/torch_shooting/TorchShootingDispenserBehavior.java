package io.github.mortuusars.fireflight.torch_shooting;

import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;

public class TorchShootingDispenserBehavior {
    public static final DispenseItemBehavior DISPENSER_BEHAVIOR = new DispenseItemBehavior() {
        @Override
        public ItemStack dispense(BlockSource blockSource, ItemStack itemStack) {
            //TODO Implement
            return ItemStack.EMPTY;
        }
//        @Override
//        protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position position, ItemStack stack) {
//            TorchArrow arrow;
//
//            if (stack.is(Fireflight.Tags.Items.SOUL_TORCH))
//                arrow = new SoulTorchArrow(level, position.x(), position.y(), position.z(), stack.copy().split(1));
//            else if (stack.is(Fireflight.Tags.Items.REDSTONE_TORCH))
//                arrow = new RedstoneTorchArrow(level, position.x(), position.y(), position.z(), stack.copy().split(1));
//            else
//                arrow = new TorchArrow(level, position.x(), position.y(), position.z(), stack.copy().split(1));
//
//            arrow.setBaseDamage(Config.Common.TORCH_DAMAGE.get());
//            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
//
//            return arrow;
//        }
    };
}
