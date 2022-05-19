package com.colt.furniature.block;

import com.colt.furniature.block.states.PlushBlock;
import com.colt.furniature.blockentities.PlushBluckEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerPlush extends Plush {

    public PlayerPlush(BlockBehaviour.Properties p_55177_) {
        super(Plush.Types.PLAYER, p_55177_);
    }

    public void setPlacedBy(Level p_55179_, BlockPos p_55180_, BlockState p_55181_, @Nullable LivingEntity p_55182_, ItemStack p_55183_) {
        super.setPlacedBy(p_55179_, p_55180_, p_55181_, p_55182_, p_55183_);
        BlockEntity blockentity = p_55179_.getBlockEntity(p_55180_);
        if (blockentity instanceof PlushBluckEntity) {
            PlushBluckEntity PlushEntity = (PlushBluckEntity)blockentity;
            GameProfile gameprofile = null;
            if (p_55183_.hasTag()) {
                CompoundTag compoundtag = p_55183_.getTag();
                if (compoundtag.contains("SkullOwner", 10)) {
                    gameprofile = NbtUtils.readGameProfile(compoundtag.getCompound("SkullOwner"));
                } else if (compoundtag.contains("SkullOwner", 8) && !StringUtils.isBlank(compoundtag.getString("SkullOwner"))) {
                    gameprofile = new GameProfile((UUID)null, compoundtag.getString("SkullOwner"));
                }
            }

            PlushEntity.setOwner(gameprofile);
        }

    }
}