package com.colt.furniature.datagen;

import com.colt.furniature.Furniature;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.colt.furniature.registries.FurniatureBlocks.*;

public class LootTableGenerator extends LootTableProvider {

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> Tabels = ImmutableList.of(Pair.of(BlockDrops::new, LootContextParamSets.BLOCK));

    public LootTableGenerator(DataGenerator Generator) {
        super(Generator);
    }

    @Override
    public List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return this.Tabels;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
    }

    private static class BlockDrops extends BlockLoot {
        @Override
        protected void addTables() {
            dropSelf(TABLE_SAW.get());
            dropSelf(HEATH_PLUSH.get());
            dropSelf(PLAYER_PLUSH.get());

            for (RegistryObject<Block> Chair : CHAIR) {
                dropSelf(Chair.get());
            }
            for (RegistryObject<Block> Stair : STRIPPED_STAIR) {
                dropSelf(Stair.get());
            }
            for (RegistryObject<Block> ModernTable : STAINED_GLASS_MODERN_TABLE) {
                dropSelf(ModernTable.get());
            }
            for (RegistryObject<Block> ModernTable : CABINET) {
                dropSelf(ModernTable.get());
            }
            /*
            for (DyeColor dyeColor : DyeColor.values()) {
                String color = dyeColor.getName();
                Block coloredCarpet = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(String.format("minecraft:%s_carpet", color)));
                dropOther(CARPETS_ON_STARIS.get(i).get(), coloredCarpet);
                i++;
            }
             */
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && Furniature.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
        }
    }
}