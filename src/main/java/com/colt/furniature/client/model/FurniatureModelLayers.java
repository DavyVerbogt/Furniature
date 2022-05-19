package com.colt.furniature.client.model;


import com.colt.furniature.Furniature;
import com.colt.furniature.client.model.PlushModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class FurniatureModelLayers extends ModelLayers {

    public static final ModelLayerLocation PLAYER_PLUSH = createLocation("player_plush", "main");
    public static final ModelLayerLocation HEATH_PLUSH = createLocation("heath_plush", "main");

    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PLAYER_PLUSH, () -> PlushModel.createPlush());
        event.registerLayerDefinition(HEATH_PLUSH, () -> PlushModel.createPlush());
    }

    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(Furniature.MOD_ID, model), layer);
    }
}
