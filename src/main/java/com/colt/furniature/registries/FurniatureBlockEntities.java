package com.colt.furniature.registries;

import com.colt.furniature.Furniature;
import com.colt.furniature.block.states.PlushBlock;
import com.colt.furniature.blockentities.CabinetBlockEntity;
import com.colt.furniature.blockentities.PlushBluckEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FurniatureBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,
            Furniature.MOD_ID);

    public static final RegistryObject<BlockEntityType<PlushBluckEntity>> PLUSH = BLOCK_ENTITY.register("plush", () ->
            BlockEntityType.Builder.of(PlushBluckEntity::new,
                    FurniatureBlocks.HEATH_PLUSH.get(),
                    FurniatureBlocks.PLAYER_PLUSH.get()
            ).build(null));

    public static final RegistryObject<BlockEntityType<CabinetBlockEntity>> CABINET = BLOCK_ENTITY.register("cabinet", () ->
            BlockEntityType.Builder.of(CabinetBlockEntity::new,
                    FurniatureBlocks.CABINET.get(0).get(),
                    FurniatureBlocks.CABINET.get(1).get(),
                    FurniatureBlocks.CABINET.get(2).get(),
                    FurniatureBlocks.CABINET.get(3).get(),
                    FurniatureBlocks.CABINET.get(4).get(),
                    FurniatureBlocks.CABINET.get(5).get(),
                    FurniatureBlocks.CABINET.get(6).get(),
                    FurniatureBlocks.CABINET.get(7).get()
            ).build(null));

}
