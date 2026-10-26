package io.github.mortuusars.fireflight.torch_shooting;

import com.mojang.serialization.Codec;
import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public enum TorchType implements StringRepresentable {
    TORCH("torch",
          Config.Server.TORCH_VELOCITY_MULTIPLIER,
          Config.Server.TORCH_DAMAGE,
          Config.Server.TORCH_WEAPON_DURABILITY_COST,
          0.1F),
    SOUL_TORCH("soul_torch",
          Config.Server.SOUL_TORCH_VELOCITY_MULTIPLIER,
          Config.Server.SOUL_TORCH_DAMAGE,
          Config.Server.SOUL_TORCH_WEAPON_DURABILITY_COST,
          0.2F),
    REDSTONE_TORCH("redstone_torch",
          Config.Server.REDSTONE_TORCH_VELOCITY_MULTIPLIER,
          Config.Server.REDSTONE_TORCH_DAMAGE,
          Config.Server.REDSTONE_TORCH_WEAPON_DURABILITY_COST,
          0.3F);

    public static final Codec<TorchType> CODEC = StringRepresentable.fromEnum(TorchType::values);

    private final String name;
    private final Supplier<Double> velocityMultiplier;
    private final Supplier<Double> damage;
    private final Supplier<Integer> weaponDurabilityCost;
    private final float modelOverrideValue;

    TorchType(String name, Supplier<Double> velocityMultiplier, Supplier<Double> damage, Supplier<Integer> weaponDurabilityCost, float modelOverrideValue) {
        this.name = name;
        this.velocityMultiplier = velocityMultiplier;
        this.damage = damage;
        this.weaponDurabilityCost = weaponDurabilityCost;
        this.modelOverrideValue = modelOverrideValue;
    }

    public Supplier<Double> getVelocityMultiplier() {
        return velocityMultiplier;
    }

    public double getDamage() {
        return damage.get();
    }

    public int getWeaponDurabilityCost() {
        return weaponDurabilityCost.get();
    }

    public float getModelOverrideValue() {
        return modelOverrideValue;
    }

    // --

    public static boolean isShootableTorch(ItemStack itemStack) {
        return getFromStack(itemStack) != null;
    }

    public static @Nullable TorchType getFromStack(ItemStack stack) {
        if (stack.is(Fireflight.Tags.Items.TORCH))
            return TORCH;
        else if (stack.is(Fireflight.Tags.Items.SOUL_TORCH))
            return SOUL_TORCH;
        else if (stack.is(Fireflight.Tags.Items.REDSTONE_TORCH))
            return REDSTONE_TORCH;

        return null;
    }

    public static <R> @Nullable R map(ItemStack stack, Function<@NotNull TorchType, R> mapper) {
        @Nullable TorchType type = getFromStack(stack);
        if (type != null) {
            return mapper.apply(type);
        }
        return null;
    }

    public static <R> @NotNull R mapOrDefault(ItemStack stack, Function<@NotNull TorchType, R> mapper, R defaultValue) {
        @Nullable TorchType type = getFromStack(stack);
        if (type != null) {
            return mapper.apply(type);
        }
        return defaultValue;
    }

    public static <R> @NotNull R mapOrGet(ItemStack stack, Function<@NotNull TorchType, R> mapper, Supplier<R> defaultValueGetter) {
        @Nullable TorchType type = getFromStack(stack);
        if (type != null) {
            return mapper.apply(type);
        }
        return defaultValueGetter.get();
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}