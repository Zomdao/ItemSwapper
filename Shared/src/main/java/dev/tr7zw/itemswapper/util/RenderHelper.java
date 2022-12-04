package dev.tr7zw.itemswapper.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class RenderHelper {

    private static final Minecraft minecraft = Minecraft.getInstance();
    private static float blitOffset;
    
    private RenderHelper() {
        //private
    }
    
    public static void renderUnavailableItem(PoseStack poseStack, LivingEntity livingEntity, ItemStack itemStack, int i, int j, int k) {
        if (itemStack.isEmpty())
            return;
        BakedModel bakedModel = minecraft.getItemRenderer().getModel(itemStack, null, livingEntity, k);
        blitOffset = bakedModel.isGui3d() ? (blitOffset + 50.0F) : (blitOffset + 50.0F);
        int l = i;
        int m = j;
        boolean bl = true;
        if (k == 0 && bl) {
            GuiComponent.fill(poseStack, l - 4, m - 4, l + 20, m + 20, 822018048);
        } else {
            GuiComponent.fill(poseStack, l, m, l + 16, m + 16, 822018048);
        }
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        itemRenderer.renderAndDecorateFakeItem(itemStack, l, m);
        RenderSystem.depthFunc(516);
        GuiComponent.fill(poseStack, l, m, l + 16, m + 16, 822083583);
        RenderSystem.depthFunc(515);
        if (k == 0)
            itemRenderer.renderGuiItemDecorations(minecraft.font, itemStack, l, m);
        blitOffset = bakedModel.isGui3d() ? (blitOffset - 50.0F) : (blitOffset - 50.0F);
    }
    
    public static void renderGuiItemCount(Font font, String text, int i, int j, int color) {
        renderGuiItemText(font, text, (i + 19 - 2 - font.width(text)), (j + 6 + 3), color);
    }

    public static void renderGuiItemName(Font font, String text, int i, int j, int color) {
        renderGuiItemText(font, text, (i - font.width(text) / 2), j, color);
    }

    public static void renderGuiItemText(Font font, String text, int i, int j, int color) {
        PoseStack poseStack = new PoseStack();
        String string2 = text;
        poseStack.translate(0.0D, 0.0D, (Minecraft.getInstance().getItemRenderer().blitOffset + 200.0F));
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource
                .immediate(Tesselator.getInstance().getBuilder());
        font.drawInBatch(string2, i, j, color, true,
                poseStack.last().pose(), bufferSource, false, 0, 15728880);
        bufferSource.endBatch();
    }

}
