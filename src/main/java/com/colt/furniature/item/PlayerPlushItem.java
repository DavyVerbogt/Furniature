package com.colt.furniature.item;

import com.colt.furniature.itemgroup.FurniatureItemGroup;
import com.colt.furniature.registries.FurniatureItems;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class PlayerPlushItem extends PlushItem {
    public static final String TAG_SKULL_OWNER = "SkullOwner";

    public PlayerPlushItem(Block block, Properties tab) {
        super(block, new Properties().tab(FurniatureItemGroup.FURNIATURE));
    }

    @Override
    public Component getName(ItemStack p_42977_) {
        if (p_42977_.is(FurniatureItems.PLAYER_PLUSH.get()) && p_42977_.hasTag()) {
            String s = null;
            CompoundTag compoundtag = p_42977_.getTag();
            if (compoundtag.contains("SkullOwner", 8)) {
                s = compoundtag.getString("SkullOwner");
            } else if (compoundtag.contains("SkullOwner", 10)) {
                CompoundTag compoundtag1 = compoundtag.getCompound("SkullOwner");
                if (compoundtag1.contains("Name", 8)) {
                    s = compoundtag1.getString("Name");
                }
            }

            if (s != null) {
                return new TranslatableComponent(this.getDescriptionId() + ".named", s);
            }
        }

        return super.getName(p_42977_);
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag p_151179_) {
        super.verifyTagAfterLoad(p_151179_);
        if (p_151179_.contains("SkullOwner", 8) && !StringUtils.isBlank(p_151179_.getString("SkullOwner"))) {
            GameProfile gameprofile = new GameProfile((UUID)null, p_151179_.getString("SkullOwner"));
            SkullBlockEntity.updateGameprofile(gameprofile, (p_151177_) -> {
                p_151179_.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), p_151177_));
            });
        }

    }

}