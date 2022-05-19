package com.colt.furniature.block;

import com.colt.furniature.block.states.HorizontalDirectionalWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class ModernTable extends HorizontalDirectionalWaterloggedBlock {

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(0, 0, 12, 16, 14, 16),
            Block.box(0, 14, 0, 16, 16, 16),
            Block.box(0, 8, 8, 16, 14, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_O = Stream.of(
            Block.box(0, 0, 0, 4, 14, 16),
            Block.box(0, 14, 0, 16, 16, 16),
            Block.box(4, 8, 0, 8, 14, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_Z = Stream.of(
            Block.box(0, 0, 0, 16, 14, 4),
            Block.box(0, 14, 0, 16, 16, 16),
            Block.box(0, 8, 4, 16, 14, 8)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(12, 0, 0, 16, 14, 16),
            Block.box(0, 14, 0, 16, 16, 16),
            Block.box(8, 8, 0, 12, 14, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public ModernTable(Properties copy) {
        super(copy.noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
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
