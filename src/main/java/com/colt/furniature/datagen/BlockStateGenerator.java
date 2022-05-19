package com.colt.furniature.datagen;

import com.colt.furniature.Furniature;
import com.colt.furniature.block.Cabinet;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.colt.furniature.registries.FurniatureBlocks.*;

public class BlockStateGenerator extends BlockStateProvider {
    ExistingFileHelper existingFileHelper;

    public BlockStateGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Furniature.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        CHAIR.forEach(chair -> {
            String modelLocation = Furniature.MOD_ID + ":block/chair/chair";
            String Name = String.valueOf(chair.get().asItem());
            ModelBuilder<?> builder = models().withExistingParent("block/" + Name, modelLocation);

            if (!String.valueOf(chair.get().asItem()).contains("warped") && !String.valueOf(chair.get().asItem()).contains("crimson")) {
                builder.texture("0", new ResourceLocation("minecraft",
                        "block/stripped_" + String.valueOf(chair.get().asItem()).replace("_chair", "") + "_log"));

                builder.texture("2", new ResourceLocation("minecraft",
                        "block/" + String.valueOf(chair.get().asItem()).replace("_chair", "") + "_log"));

                builder.texture("particle ", new ResourceLocation("minecraft",
                        "block/stripped_" + String.valueOf(chair.get().asItem()).replace("_chair", "") + "_log"));
            } else {
                builder.texture("0", new ResourceLocation("minecraft",
                        "block/stripped_" + String.valueOf(chair.get().asItem()).replace("_chair", "") + "_stem"));

                builder.texture("2", new ResourceLocation("minecraft",
                        "block/" + String.valueOf(chair.get().asItem()).replace("_chair", "") + "_stem"));

                builder.texture("particle ", new ResourceLocation("minecraft",
                        "block/stripped_" + String.valueOf(chair.get().asItem()).replace("_chair", "") + "_stem"));
            }
            builder.texture("3", new ResourceLocation("minecraft",
                    "block/" + String.valueOf(chair.get().asItem()).replace("_chair", "") + "_trapdoor"));

            horizontalBlock(chair.get(), state -> builder);
            Furniature.LOGGER.info("Added Blockstate for " + chair.get().asItem());
        });

        CABINET.forEach(cabinet -> {

            CabinetBlock(cabinet.get());
            Furniature.LOGGER.info("Added Blockstate for " + cabinet.get().asItem());

        });

        STRIPPED_STAIR.forEach(stair -> {
            String TextureName = "block/" + String.valueOf(stair.get().asItem()).replace("_stairs", "") + "_log";

            if (TextureName.contains("warped") || TextureName.contains("crimson")) {
                TextureName = TextureName.replace("_log", "_stem");
            }
            stairsBlock((StairBlock) stair.get(), new ResourceLocation("minecraft", TextureName));
            Furniature.LOGGER.info("Added Blockstate for " + stair.get().asItem());
        });

        STAINED_GLASS_MODERN_TABLE.forEach(modern_table -> {
            String modelLocation = Furniature.MOD_ID + ":block/moderntable/modern_table";
            String Name = String.valueOf(modern_table.get().asItem());
            ModelBuilder<?> builder = models().withExistingParent("block/" + Name, modelLocation);
            builder.texture("0", new ResourceLocation("minecraft",
                    "block/" + String.valueOf(modern_table.get().asItem()).split("modern_")[1].replace("_table", "")
            ));
            if (!String.valueOf(modern_table.get().asItem()).startsWith("modern")) {
                builder.texture("1", new ResourceLocation("minecraft",
                        "block/" + String.valueOf(modern_table.get().asItem()).split("_modern")[0]));
            } else {
                builder.texture("1", new ResourceLocation("minecraft",
                        "block/glass"));
            }
            builder.texture("particle ", new ResourceLocation("minecraft",
                    "block/" + String.valueOf(modern_table.get().asItem()).split("modern_")[1].replace("_table", "")));
            horizontalBlock(modern_table.get(), state -> builder);
            Furniature.LOGGER.info("Added Blockstate for " + modern_table.get().asItem());
        });
    }

    public void CabinetBlock(Block cabinet) {

        String Name = String.valueOf(cabinet.asItem());
        this.horizontalBlock(cabinet, cabinetstate -> {
            String OPEN = cabinetstate.getValue(Cabinet.OPEN) ? "_open" : "";
            String FULL = cabinetstate.getValue(Cabinet.FULL) ? "_full" : "_empty";
            String FACING = cabinetstate.getValue(Cabinet.FACING).toString();
            ModelBuilder builder;
            if (FULL == "_empty") {
                String modelLocation = Furniature.MOD_ID + ":block/cabinet/cabinet" + OPEN + FULL;
                builder = models().withExistingParent("block/" + Name + OPEN + FULL, modelLocation);
            } else {
                String modelLocation = Furniature.MOD_ID + ":block/cabinet/cabinet" + OPEN + FULL + "_" + FACING;
                builder = models().withExistingParent("block/" + Name + OPEN + FULL + FACING, modelLocation);
            }

            if (!String.valueOf(cabinet.asItem()).contains("warped") && !String.valueOf(cabinet.asItem()).contains("crimson")) {
                builder.texture("0", new ResourceLocation("minecraft",
                        "block/" + String.valueOf(cabinet.asItem()).replace("_cabinet", "") + "_log"));

                builder.texture("2", new ResourceLocation("minecraft",
                        "block/stripped_" + String.valueOf(cabinet.asItem()).replace("_cabinet", "") + "_log"));

                builder.texture("particle ", new ResourceLocation("minecraft",
                        "block/" + String.valueOf(cabinet.asItem()).replace("_cabinet", "") + "_log"));
            } else {
                builder.texture("0", new ResourceLocation("minecraft",
                        "block/" + String.valueOf(cabinet.asItem()).replace("_cabinet", "") + "_stem"));

                builder.texture("2", new ResourceLocation("minecraft",
                        "block/stripped_" + String.valueOf(cabinet.asItem()).replace("_cabinet", "") + "_stem"));

                builder.texture("particle ", new ResourceLocation("minecraft",
                        "block/" + String.valueOf(cabinet.asItem()).replace("_cabinet", "") + "_stem"));
            }
            builder.texture("1", new ResourceLocation("minecraft",
                    "block/" + String.valueOf(cabinet.asItem()).replace("_cabinet", "") + "_planks"));
            return builder;
        });
    }
}
