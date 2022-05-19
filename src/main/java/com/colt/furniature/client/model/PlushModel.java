package com.colt.furniature.client.model;

import com.colt.furniature.client.model.modelbase.PlushModelBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class PlushModel extends PlushModelBase {
    private final ModelPart skin;

    public PlushModel(ModelPart root) {
        this.skin = root.getChild("skin");

    }

    public static LayerDefinition createPlush() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition skin = partdefinition.addOrReplaceChild("skin", CubeListBuilder.create().texOffs(20, 8).addBox(-4.0F, -6.5F, 3.3F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 24).addBox(2.0F, -6.5F, 3.3F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = skin.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 24).addBox(-0.1228F, -4.5934F, 3.6109F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, -5.3F, -1.9F, -1.5708F, -0.3927F, 0.0F));

        PartDefinition cube_r2 = skin.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 8).addBox(-1.8772F, -4.5934F, 3.6109F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7F, -5.3F, -2.2F, -1.5708F, 0.3927F, 0.0F));

        PartDefinition cube_r3 = skin.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(8, 8).addBox(-2.0F, -8.0094F, 0.7892F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -11.9333F, -0.5935F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition layer = skin.addOrReplaceChild("layer", CubeListBuilder.create().texOffs(20, 16).addBox(-4.0F, -6.5F, 3.3F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(24, 24).addBox(2.0F, -6.5F, 3.3F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = layer.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 24).addBox(-0.1228F, -4.5934F, 3.6109F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.9F, -5.3F, -1.9F, -1.5708F, -0.3927F, 0.0F));

        PartDefinition cube_r5 = layer.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 16).addBox(-1.8772F, -4.5934F, 3.6109F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.7F, -5.3F, -2.2F, -1.5708F, 0.3927F, 0.0F));

        PartDefinition cube_r6 = layer.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(8, 16).addBox(-2.0F, -8.0094F, 0.7892F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(16, 0).addBox(-2.0F, -11.9333F, -0.5935F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void setupAnim( float YRotation) {
        this.skin.yRot = YRotation * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        skin.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
