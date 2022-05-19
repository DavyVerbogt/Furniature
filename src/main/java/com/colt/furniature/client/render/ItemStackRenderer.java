package com.colt.furniature.client.render;

import com.colt.furniature.Furniature;
import com.colt.furniature.block.Plush;
import com.colt.furniature.block.states.PlushBlock;
import com.colt.furniature.blockentities.PlushBluckEntity;
import com.colt.furniature.client.model.PlushModel;
import com.colt.furniature.client.model.modelbase.PlushModelBase;
import com.colt.furniature.registries.FurniatureItems;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraftforge.client.IItemRenderProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemStackRenderer extends BlockEntityWithoutLevelRenderer {
    private final EntityModelSet entityModelSet;

    private Map<Plush.Type, PlushModelBase> plushModelBaseMap;

    public ItemStackRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        entityModelSet = Minecraft.getInstance().getEntityModels();
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        plushModelBaseMap = PlushBlockRenderer.createPlushRenderers(entityModelSet);
        Furniature.LOGGER.info(plushModelBaseMap+" :plushModelBaseMap");
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Furniature.LOGGER.info(entityModelSet+" :EntityModelSet");
        Item item = stack.getItem();
            if (item instanceof BlockItem) {
                Block block = ((BlockItem) item).getBlock();
                if (block instanceof PlushBlock) {
                    GameProfile gameprofile = null;
                    if (stack.hasTag()) {
                        CompoundTag compoundtag = stack.getTag();
                        if (compoundtag.contains("SkullOwner", 10)) {
                            gameprofile = NbtUtils.readGameProfile(compoundtag.getCompound("SkullOwner"));
                        } else if (compoundtag.contains("SkullOwner", 8) && !StringUtils.isBlank(compoundtag.getString("SkullOwner"))) {
                            gameprofile = new GameProfile(null, compoundtag.getString("SkullOwner"));
                            compoundtag.remove("SkullOwner");
                            SkullBlockEntity.updateGameprofile(gameprofile, (p_172560_) -> {
                                compoundtag.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), p_172560_));
                            });
                        }
                    }

                    Plush.Type PlushType = ((PlushBlock) block).getType();
                    PlushModelBase modelBase = plushModelBaseMap.get(PlushType);
                    RenderType rendertype = PlushBlockRenderer.getRenderType(PlushType, gameprofile);
                    Furniature.LOGGER.info(gameprofile +" :Gameprofile");
                    Furniature.LOGGER.info(PlushType+" :Plush Type");
                    Furniature.LOGGER.info(modelBase+" :Model Base");
                    PlushBlockRenderer.renderPlush((Direction) null, 180.0F, 0.0F, matrixStackIn, bufferIn, combinedLightIn, modelBase, rendertype);
                }
            }
        }
    }
