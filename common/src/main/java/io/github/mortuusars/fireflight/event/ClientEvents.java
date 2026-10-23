package io.github.mortuusars.fireflight.event;

import io.github.mortuusars.fireflight.Config;
import io.github.mortuusars.fireflight.Fireflight;
import io.github.mortuusars.fireflight.torch_shooting.TorchType;
import io.github.mortuusars.fireflight.render.TorchArrowRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ClientEvents {
    //
//    public static class Forge {
//        @SubscribeEvent
//        public static void onItemTooltipEvent(ItemTooltipEvent event) {
//            if (!Config.Client.TORCHES_TOOLTIP.get())
//                return;
//
//            ItemStack stack = event.getItemStack();
//
//            if (Fireflight.isShootableTorch(stack)) {
//                List<Component> toolTip = event.getToolTip();
//                toolTip.add(Component.translatable("item.fireflight.torches.tooltip"));
//            }
//        }
//    }
}
