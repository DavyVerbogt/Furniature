package com.colt.furniature.blockentities;

import com.colt.furniature.registries.FurniatureBlockEntities;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlushBluckEntity extends BlockEntity {
    public static final String TAG_SKULL_OWNER = "SkullOwner";
    @Nullable
    private GameProfile owner;

    public PlushBluckEntity(BlockPos Pos, BlockState State) {
        super(FurniatureBlockEntities.PLUSH.get(), Pos, State);
    }
    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (this.owner != null) {
            CompoundTag compoundtag = new CompoundTag();
            NbtUtils.writeGameProfile(compoundtag, this.owner);
            compoundTag.put("SkullOwner", compoundtag);
        }
    }
@Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("SkullOwner", 10)) {
            this.setOwner(NbtUtils.readGameProfile(compound.getCompound("SkullOwner")));
        } else if (compound.contains("ExtraType", 8)) {
            String s = compound.getString("ExtraType");
            if (!StringUtil.isNullOrEmpty(s)) {
                this.setOwner(new GameProfile((UUID)null, s));
            }
        }
    }

    @Nullable
    public GameProfile getOwnerProfile() {
        return this.owner;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public void setOwner(@Nullable GameProfile ProfileOwner) {
        synchronized(this) {
            this.owner = ProfileOwner;
        }
        this.updateOwnerProfile();
    }

    private void updateOwnerProfile() {
        SkullBlockEntity.updateGameprofile(this.owner, (p_155747_) -> {
            this.owner = p_155747_;
            this.setChanged();
        });
    }
}