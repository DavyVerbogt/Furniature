package com.colt.furniature.client.render;

import com.colt.furniature.Furniature;
import com.colt.furniature.block.Plush;
import com.colt.furniature.block.states.PlushBlock;
import com.colt.furniature.blockentities.PlushBluckEntity;
import com.colt.furniature.client.model.FurniatureModelLayers;
import com.colt.furniature.client.model.PlushModel;
import com.colt.furniature.client.model.modelbase.PlushModelBase;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PlushBlockRenderer implements BlockEntityRenderer<PlushBluckEntity> {

    private final Map<Plush.Type, PlushModelBase> model;

    private static final Map<Plush.Type, ResourceLocation> SKIN_BY_TYPE = Util.make(Maps.newHashMap(), (p_112552_) -> {
        p_112552_.put(Plush.Types.HEATH, new ResourceLocation(Furniature.MOD_ID, "textures/entity/plush/heath_plush.png"));
        p_112552_.put(Plush.Types.PLAYER, DefaultPlayerSkin.getDefaultSkin());
    });

    public static Map<Plush.Type, PlushModelBase> createPlushRenderers(EntityModelSet modelSet) {
        ImmutableMap.Builder<Plush.Type, PlushModelBase> builder = ImmutableMap.builder();
        builder.put(Plush.Types.HEATH, new PlushModel(modelSet.bakeLayer(FurniatureModelLayers.HEATH_PLUSH)));
        builder.put(Plush.Types.PLAYER, new PlushModel(modelSet.bakeLayer(FurniatureModelLayers.PLAYER_PLUSH)));
        return builder.build();
    }

    public PlushBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.model = createPlushRenderers(context.getModelSet());
    }

    public void render(PlushBluckEntity p_112534_, float p_112535_, PoseStack p_112536_, MultiBufferSource p_112537_, int p_112538_, int p_112539_) {
        BlockState blockstate = p_112534_.getBlockState();
        float f1 = 22.5F * (float)(blockstate.getValue(Plush.ROTATION));
        Plush.Type plushtype = ((PlushBlock)blockstate.getBlock()).getType();
        PlushModelBase plushModelBase = this.model.get(plushtype);
        RenderType rendertype = getRenderType(plushtype, p_112534_.getOwnerProfile());
        renderPlush(null, f1, 0.0F, p_112536_, p_112537_, p_112538_, plushModelBase, rendertype);
    }

    public static void renderPlush(@Nullable Direction direction, float YRotation, float v, PoseStack poseStack, MultiBufferSource bufferSource, int p_173669_, PlushModelBase plushModelBase, RenderType renderType) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.0D, 0.5D);

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(renderType);
        plushModelBase.setupAnim(YRotation);
        plushModelBase.renderToBuffer(poseStack, vertexconsumer, p_173669_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    public static RenderType getRenderType(Plush.Type p_112524_, @Nullable GameProfile p_112525_) {
        ResourceLocation resourcelocation = SKIN_BY_TYPE.get(p_112524_);
        if (p_112524_ == Plush.Types.PLAYER && p_112525_ != null) {
            Minecraft minecraft = Minecraft.getInstance();
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(p_112525_);
            return map.containsKey(MinecraftProfileTexture.Type.SKIN) ? RenderType.entityTranslucent(minecraft.getSkinManager().registerTexture(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN)) : RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(Player.createPlayerUUID(p_112525_)));
        } else {
            return RenderType.entityCutoutNoCullZOffset(resourcelocation);
        }
    }
}