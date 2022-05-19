package com.colt.furniature.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class CarpetOnStairs extends HorizontalDirectionalBlock {

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(0, -7, 8, 16, 0, 9),
            Block.box(0, -16, 16, 16, -8, 17),
            Block.box(0, -8, 8, 16, -7, 17),
            Block.box(0, 0, 0, 16, 1, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_O = Stream.of(
            Block.box(7, -7, 0, 8, 0, 16),
            Block.box(-1, -16, 0, 0, -8, 16),
            Block.box(-1, -8, 0, 8, -7, 16),
            Block.box(7, 0, 0, 16, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_Z = Stream.of(
            Block.box(0, -7, 7, 16, 0, 8),
            Block.box(0, -16, -1, 16, -8, 0),
            Block.box(0, -8, -1, 16, -7, 8),
            Block.box(0, 0, 7, 16, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(8, -7, 0, 9, 0, 16),
            Block.box(16, -16, 0, 17, -8, 16),
            Block.box(8, -8, 0, 17, -7, 16),
            Block.box(0, 0, 0, 9, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private final Block clone;

    public CarpetOnStairs(Properties properties, Block BlockClone) {
        super(properties.noOcclusion());
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        clone = BlockClone;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
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

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos newPos) {
        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, dir, newState, world, pos, newPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        return (reader.getBlockState(pos.below()).getBlock() instanceof StairBlock);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(clone);
    }
}
