package com.colt.furniature.client.render;

import com.colt.furniature.entity.Sitting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SittingRender extends EntityRenderer<Sitting> {
    public SittingRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Sitting sitting) {
        return null;
    }

    @Override
    protected void renderNameTag(Sitting entity, Component component, PoseStack stack, MultiBufferSource source, int light) {
    }
}
