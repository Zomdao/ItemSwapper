package dev.tr7zw.xisumatweeks;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SwitchItemOverlay extends Overlay {

    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
    private static final double limit = 100;
    private static final double deadZone = limit / 3 / 2;
    private static final double handleResetCount = 50;
    private final Minecraft minecraft = Minecraft.getInstance();
    private final ItemRenderer itemRenderer = minecraft.getItemRenderer();

    private double selectX = 0;
    private double selectY = 0;
    private Selection selection = null;

    private double lastX, lastY;
    private int noMovement;

    @Override
    public void render(PoseStack poseStack, int no1, int no2, float f) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        int slotSize = 22;
        int originX = minecraft.getWindow().getGuiScaledWidth() / 2 - slotSize - (slotSize / 2);
        int originY = minecraft.getWindow().getGuiScaledHeight() / 2 - slotSize - (slotSize / 2) - 1;
        List<Runnable> itemRenderList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            renderSelection(poseStack, i, originX + i * slotSize, originY, itemRenderList);
            renderSelection(poseStack, i + 3, originX + i * slotSize, originY + slotSize * 2, itemRenderList);
            if (i == 0 || i == 2) {
                renderSelection(poseStack, i == 0 ? 6 : 7, originX + i * slotSize, originY + slotSize, itemRenderList);
            }
        }
        itemRenderList.forEach(Runnable::run);
    }
    
    private void renderSelection(PoseStack poseStack, int id, int x, int y, List<Runnable> itemRenderList) {
        blit(poseStack, x, y, 24, 22, 29, 24);
        //dummy item code
        itemRenderList.add(() -> renderSlot(x+3, y+3, minecraft.player, minecraft.player.getInventory().getItem(id), 1));
        if(selection != null && selection.ordinal() == id) {
            blit(poseStack, x, y, 0, 22, 24, 22);
        }
    }

    public void handleInput(double x, double y) {
        if (x == lastX && y == lastY) {
            noMovement++;
        } else {
            if (noMovement > handleResetCount) {
                selectX = 0;
                selectY = 0;
            }
            noMovement = 0;
        }
        selectX += x;
        selectY += y;
        selectX = Mth.clamp(selectX, -limit, limit);
        selectY = Mth.clamp(selectY, -limit, limit);
        lastX = x;
        lastY = y;
        updateSelection();
    }

    private void updateSelection() {
        if (selectY < -deadZone) { // up(mc has the Y flipped)
            if (selectX > deadZone) {
                selection = Selection.TOP_RIGHT;
            } else if (selectX < -deadZone) {
                selection = Selection.TOP_LEFT;
            } else {
                selection = Selection.TOP;
            }
        } else if (selectY > deadZone) { // down(mc has the Y flipped)
            if (selectX > deadZone) {
                selection = Selection.BOTTOM_RIGHT;
            } else if (selectX < -deadZone) {
                selection = Selection.BOTTOM_LEFT;
            } else {
                selection = Selection.BOTTOM;
            }
        } else { // just left/right
            if (selectX > deadZone) {
                selection = Selection.RIGHT;
            } else if (selectX < -deadZone) {
                selection = Selection.LEFT;
            }
        }
    }

    public void onClose() {
        System.out.println("Final: " + selection);
    }

    private void renderSlot(int x, int y, Player arg, ItemStack arg2, int k) {
        if (!arg2.isEmpty()) {
            this.itemRenderer.renderAndDecorateItem(arg, arg2, x, y, k);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            this.itemRenderer.renderGuiItemDecorations(this.minecraft.font, arg2, x, y);
        }
    }

    public enum Selection {
        TOP_LEFT, TOP, TOP_RIGHT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT, LEFT, RIGHT, 
    }

}
