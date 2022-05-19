package com.colt.furniature.block;

import com.colt.furniature.block.container.FurniatureCraftingTableContainer;
import com.colt.furniature.block.states.HorizontalDirectionalWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class FurniatureCraftingTable extends HorizontalDirectionalWaterloggedBlock {

    private static final TranslatableComponent Container_Name = new TranslatableComponent("container.table_saw");
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(2, 0, 0, 16, 8, 16),
            Block.box(2, 8, 0, 4, 14, 16),
            Block.box(2, 14, 0, 16, 16, 16),
            Block.box(10.3, 8, 0, 12.3, 14, 2),
            Block.box(14, 8, 0, 16, 14, 2),
            Block.box(11.3, 8, 2, 11.3, 14, 14),
            Block.box(15.1, 8, 2, 15.1, 14, 14),
            Block.box(14, 8, 14, 16, 14, 16),
            Block.box(10.3, 8, 14, 12.3, 14, 16),
            Block.box(0.7, 14, 0, 2, 16, 2),
            Block.box(0, 14, 0, 0.7, 16, 16),
            Block.box(0.7, 14, 14, 2, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_O = Stream.of(
            Block.box(0, 0, 2, 16, 8, 16),
            Block.box(0, 8, 2, 16, 14, 4),
            Block.box(0, 14, 2, 16, 16, 16),
            Block.box(14, 8, 10.3, 16, 14, 12.3),
            Block.box(14, 8, 14, 16, 14, 16),
            Block.box(2, 8, 11.3, 14, 14, 11.3),
            Block.box(2, 8, 15.1, 14, 14, 15.1),
            Block.box(0, 8, 14, 2, 14, 16),
            Block.box(0, 8, 10.3, 2, 14, 12.3),
            Block.box(14, 14, 1, 16, 16, 2),
            Block.box(0, 14, 0, 16, 16, 1),
            Block.box(0, 14, 1, 2, 16, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_Z = Stream.of(
            Block.box(0, 0, 0, 14, 8, 16),
            Block.box(12, 8, 0, 14, 14, 16),
            Block.box(0, 14, 0, 14, 16, 16),
            Block.box(4, 8, 14, 6, 14, 16),
            Block.box(0, 8, 14, 2, 14, 16),
            Block.box(5, 8, 2, 5, 14, 14),
            Block.box(1, 8, 2, 1, 14, 14),
            Block.box(0, 8, 0, 2, 14, 2),
            Block.box(4, 8, 0, 6, 14, 2),
            Block.box(14, 14, 14, 15.3, 16, 16),
            Block.box(15.3, 14, 0, 16, 16, 16),
            Block.box(14, 14, 0, 15.3, 16, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(0, 0, 0, 16, 8, 14),
            Block.box(0, 8, 12, 16, 14, 14),
            Block.box(0, 14, 0, 16, 16, 14),
            Block.box(0, 8, 4, 2, 14, 6),
            Block.box(0, 8, 0, 2, 14, 2),
            Block.box(2, 8, 5, 14, 14, 5),
            Block.box(2, 8, 1, 14, 14, 1),
            Block.box(14, 8, 0, 16, 14, 2),
            Block.box(14, 8, 4, 16, 14, 6),
            Block.box(0, 14, 14, 2, 16, 15.3),
            Block.box(0, 14, 15.3, 16, 16, 16),
            Block.box(14, 14, 14, 16, 16, 15.3)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public FurniatureCraftingTable() {
        super(Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.WOOD).noOcclusion());
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide()) return InteractionResult.SUCCESS;
        else {
            player.openMenu(state.getMenuProvider(worldIn, pos));
            player.awardStat(Stats.INTERACT_WITH_STONECUTTER);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    @Nullable
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((p_57074_, p_57075_, p_57076_) -> new FurniatureCraftingTableContainer(p_57074_, p_57075_, ContainerLevelAccess.create(level, pos)), Container_Name);
    }

    @Override
    public VoxelShape getShape(BlockState State, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        switch (State.getValue(FACING)) {
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_O;
            case SOUTH:
                return SHAPE_Z;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;

        }
    }
}

