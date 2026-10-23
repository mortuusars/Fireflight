package io.github.mortuusars.fireflight.entity;

import com.mojang.logging.LogUtils;
import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.naming.OperationNotSupportedException;

public class Torch extends AbstractArrow {
    protected boolean torchWasPlaced;
    protected int inGroundItemDropTimer = Config.Common.TORCH_IN_GROUND_ITEM_DROP_TIME.get();

    public Torch(EntityType<? extends Torch> entityType, Level level) {
        super(entityType, level);
    }

    public Torch(Level level, LivingEntity shooter, ItemStack pickupStack) {
        this(Fireflight.EntityTypes.TORCH.get(), level, shooter, pickupStack);
    }

    public Torch(Level level, double x, double y, double z, ItemStack pickupStack) {
        this(Fireflight.EntityTypes.TORCH.get(), level, x, y, z, pickupStack);
    }

    protected Torch(EntityType<? extends Torch> entityType, Level level, LivingEntity shooter, ItemStack pickupStack) {
        super(entityType, shooter, level, pickupStack, null);
    }

    protected Torch(EntityType<? extends AbstractArrow> entityType, Level level, double x, double y, double z, ItemStack pickupStack) {
        super(entityType, x, y, z, level, pickupStack, null);
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.WOOD_PLACE;
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent pSoundEvent) {
        super.setSoundEvent(getDefaultHitGroundSoundEvent());
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.TORCH);
    }

    @Override
    public boolean isCritArrow() {
        return false;
    }

    @Override
    public void tick() {
        if (torchWasPlaced) {
            this.remove(RemovalReason.DISCARDED);
        }

        if (inGround && !torchWasPlaced) {
            if (inGroundItemDropTimer == 0) {
                if (!level().isClientSide) {
                    Containers.dropItemStack(level(), getX(), getY(), getZ(), getPickupItem());
                    playSound(SoundEvents.WOOD_BREAK, 0.7f, 0.7f);
                }

                this.remove(RemovalReason.DISCARDED);
            } else {
                inGroundItemDropTimer--;
            }
        }

        super.tick();

        if (level().isClientSide) {
            spawnOnTickParticles(level());
        }
    }

    @Override
    public @NotNull Vec3 getLightProbePosition(float partialTicks) {
        return position();
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        // Crossbow sets pickup to CREATIVE ONLY for additional projectiles with multishot.
        // Since we consume 3 torches instead of 1 when loading - we should be able to pick up all projectiles.
        if (getOwner() instanceof Player player && !player.isCreative())
            this.pickup = Pickup.ALLOWED; // Hopefully this wouldn't cause a dupe of some sort.

        super.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity targetEntity) {
        int seconds = Config.Common.TORCH_IGNITE_SECONDS.get();
        if (seconds > 0) {
            targetEntity.igniteForSeconds(seconds);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);

        Level level = level();
        Direction impactFace = blockHitResult.getDirection();
        BlockPos blockPos = blockHitResult.getBlockPos().relative(impactFace);
        BlockState blockStateAtPos = level().getBlockState(blockPos);

        boolean canPlaceTorchAtHitPos = (blockStateAtPos.isAir() || blockStateAtPos.canBeReplaced()) && !(blockStateAtPos.getBlock() instanceof LiquidBlock);
        boolean isSameItem = getPickupItem().getItem() == blockStateAtPos.getBlock().asItem();

        if (!isInWater() && canPlaceTorchAtHitPos && !isSameItem) {
            Direction torchFacing = null;

            if (impactFace == Direction.DOWN) { // Search the nearest block that can hold a torch
                boolean hasHorizontalBlock = false;

                for (Direction direction : Direction.orderedByNearest(this)) {
                    if (direction.getAxis().isHorizontal()) {
                        BlockPos pos = blockPos.relative(direction);
                        Direction oppositeFace = direction.getOpposite();
                        if (level.getBlockState(pos).isFaceSturdy(level, pos, oppositeFace)) {
                            torchFacing = oppositeFace;
                            hasHorizontalBlock = true;
                        }
                    }
                }

                if (!hasHorizontalBlock) { // Check block below
                    BlockPos belowPos = blockPos.relative(Direction.DOWN);
                    BlockState belowBlockState = level.getBlockState(belowPos);
                    if (belowBlockState.isFaceSturdy(level, belowPos, Direction.UP, SupportType.CENTER))
                        torchFacing = Direction.UP;
                }
            } else if (level.getBlockState(blockHitResult.getBlockPos())
                  .isFaceSturdy(level, blockHitResult.getBlockPos(), impactFace, impactFace == Direction.UP ? SupportType.CENTER : SupportType.FULL)) {
                torchFacing = impactFace;
            } else if (level.getBlockState(blockPos.below())
                  .isFaceSturdy(level, blockPos.below(), Direction.UP, SupportType.CENTER)) {
                torchFacing = Direction.UP;
            }

            if (torchFacing != null) {
                try {
                    BlockState torchBlockState = createTorchBlockStateForFacing(torchFacing);
                    if (!level.isClientSide) {
                        level.setBlockAndUpdate(blockPos, torchBlockState);
                    }
                    torchWasPlaced = true;
                } catch (OperationNotSupportedException ignored) {
                }
            }
        }

        if (!torchWasPlaced && impactFace != Direction.UP) {
            startFalling(); // Fall to the ground to be reachable (or have another chance for placing
        }

        if (level.isClientSide) {
            this.spawnOnBlockHitParticles(level, blockHitResult);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        if (level() instanceof ServerLevel serverLevel) {
            spawnServerOnEntityHitParticles(serverLevel, entityHitResult);
        }
    }

    protected BlockState createTorchBlockStateForFacing(Direction facing) throws OperationNotSupportedException {
        if (facing == Direction.DOWN) {
            throw new OperationNotSupportedException("Ceiling torches are not supported.");
        }

        Item torchItem = getPickupItem().getItem();

        if (torchItem instanceof StandingAndWallBlockItem standingAndWallBlockItem) {

            //TODO: maybe use BlockPlaceContext?

            // Checking this firstly to prevent torch mis-matching:
            // If the torch cannot work fully (standing and wall) - better to use default torch.
            if (!standingAndWallBlockItem.wallBlock.defaultBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                LogUtils.getLogger().error("Failed to place torch from arrow: '{}' has no HorizontalFacing property. " +
                      "Default torch will be used instead. (Even if the direction is UP).", torchItem);
                return getDefaultTorchBlockStateForFacing(facing);
            }

            if (facing == Direction.UP) {
                return standingAndWallBlockItem.getBlock().defaultBlockState();
            } else {
                return standingAndWallBlockItem.wallBlock.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
            }
        } else if (torchItem instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().hasProperty(BlockStateProperties.FACING))
            return blockItem.getBlock().defaultBlockState().setValue(BlockStateProperties.FACING, facing);
        else
            return getDefaultTorchBlockStateForFacing(facing);
    }

    /**
     * Used as a backup in case Block from specified TorchItem cannot be placed correctly.
     */
    protected BlockState getDefaultTorchBlockStateForFacing(Direction facing) throws OperationNotSupportedException {
        if (facing == Direction.DOWN) {
            throw new OperationNotSupportedException("Ceiling torches are not supported.");
        }

        if (facing == Direction.UP) {
            return Blocks.TORCH.defaultBlockState();
        } else {
            return Blocks.WALL_TORCH.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }
    }

    /**
     * Only called client-side.
     */
    protected void spawnOnTickParticles(Level level) {
        if (isInWater()) {
            return;
        }

        if (inGround && random.nextInt(3) != 0) {
            return;
        }

        if (level.random.nextFloat() >= 0.7) {
            level.addAlwaysVisibleParticle(ParticleTypes.FLAME, true, this.getX(), this.getY(), this.getZ(), 0.0D, 0.03D, 0.0D);
        } else {
            level.addAlwaysVisibleParticle(ParticleTypes.SMALL_FLAME, true, this.getX(), this.getY(), this.getZ(), 0.0D, 0.03D, 0.0D);
        }

        if (level.getRandom().nextInt(2) == 0) {
            level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.03D, 0.0D);
        }
    }

    /**
     * Only called client-side.
     */
    protected void spawnOnBlockHitParticles(Level level, @NotNull BlockHitResult blockHitResult) {
        Direction impactFace = blockHitResult.getDirection();
        BlockPos blockPos = blockHitResult.getBlockPos().relative(impactFace);

        boolean isInWater = isInWater() || level.getBlockState(blockPos).is(Blocks.WATER);
        if (isInWater) {
            return;
        }

        Vec3i impactFaceNormal = impactFace.getOpposite().getNormal();
        Vec3 faceOffset = new Vec3(impactFaceNormal.getX() / 3D, impactFaceNormal.getY() / 3D, impactFaceNormal.getZ() / 3D);
        Vec3 pos = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5).add(faceOffset);

        int particles = torchWasPlaced ? 14 : 5;

        for (int i = 0; i < particles; i++) {
            double xo = impactFaceNormal.getX() * 0.15D + (level.random.nextDouble() * 0.2 - 0.1);
            double yo = impactFaceNormal.getY() * 0.15D + (level.random.nextDouble() * 0.2 - 0.1);
            double zo = impactFaceNormal.getZ() * 0.15D + (level.random.nextDouble() * 0.2 - 0.1);

            if (random.nextInt(3) == 0)
                level.addAlwaysVisibleParticle(ParticleTypes.SMALL_FLAME, true, pos.x, pos.y, pos.z, xo * -1, yo * -1, zo * -1);
            else
                level.addAlwaysVisibleParticle(ParticleTypes.LAVA, true, pos.x, pos.y, pos.z, xo * -1, yo * -1, zo * -1);
        }
    }

    /**
     * Only called server-side. #onEntityHit is not working reliably on client-side.
     */
    protected void spawnServerOnEntityHitParticles(ServerLevel level, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        for (int i = 0; i < 8; i++) {
            double x = random.nextDouble() * 0.2;
            double y = random.nextDouble() * 0.2;
            double z = random.nextDouble() * 0.2;

            level.sendParticles(ParticleTypes.LAVA, entity.getX(), position().y, entity.getZ(), 1, x, y, z, 0);
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("TorchWasPlaced", true);
        tag.putInt("InGroundItemDropTimer", inGroundItemDropTimer);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        torchWasPlaced = tag.getBoolean("TorchWasPlaced");
        inGroundItemDropTimer = tag.getInt("InGroundItemDropTimer");
    }
}