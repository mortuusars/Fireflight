package io.github.mortuusars.fireflight;

import net.minecraft.world.item.ItemStack;

public enum TorchType {
    NONE(0F),
    TORCH(0.1F),
    SOUL(0.2F),
    REDSTONE(0.3F);

    private final float value;

    TorchType(float value) {
        this.value = value;
    }

    /*
    * Value is used in ItemOverrides to choose model for bow/crossbow.
    */
    public float getFloatValue() {
        return value;
    }

    public static boolean isShootableTorch(ItemStack itemStack) {
        return getFromStack(itemStack) != NONE;
    }

    public static TorchType getFromStack(ItemStack stack) {
        if (stack.is(Fireflight.Tags.Items.TORCH))
            return TORCH;
        else if (stack.is(Fireflight.Tags.Items.SOUL_TORCH))
            return SOUL;
        else if (stack.is(Fireflight.Tags.Items.REDSTONE_TORCH))
            return REDSTONE;

        return NONE;
    }
}