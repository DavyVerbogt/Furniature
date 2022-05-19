package com.colt.furniature.client.gui;

import com.colt.furniature.Furniature;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

//Credits for this function goes to MrCrayfish as i used his furniture mod as a reference
//i didnt want to clutter the creative menu with to many tabs but also wanted to keep everything organized


public class ButtonIcons extends Button {
    private static final ResourceLocation BUTTONBACKGROUND = new ResourceLocation(Furniature.MOD_ID, "textures/gui/creative_inventory/buttons.png");

    private ResourceLocation iconTexture;
    private int iconU;
    private int iconV;

    public ButtonIcons(int x, int y, Component message, OnPress onPress, ResourceLocation iconResource, int iconU, int iconV) {
        super(x, y, 20, 20, message, onPress);
        this.iconTexture = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    public void setIcon(ResourceLocation iconResource, int iconU, int iconV) {
        this.iconTexture = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float PartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderTexture(0, BUTTONBACKGROUND);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int offset = this.getYImage(this.isHoveredOrFocused());
        this.blit(poseStack, this.x, this.y, 0, 46 + offset * 20, this.width / 2, this.height);
        this.blit(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + offset * 20, this.width / 2, this.height);
        if (!this.active) {
            RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 1.0F);
        }
        RenderSystem.setShaderTexture(0, this.iconTexture);
        this.blit(poseStack, this.x + 2, this.y + 2, this.iconU, this.iconV, 16, 16);
    }
}
