package com.colt.furniature.block;

import com.colt.furniature.block.states.HorizontalDirectionalWaterloggedBlock;
import com.colt.furniature.entity.Sitting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class Chair extends HorizontalDirectionalWaterloggedBlock {

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(2, 10, 2, 14, 12, 14),
            Block.box(2, 0, 12, 4, 10, 14),
            Block.box(12, 0, 12, 14, 10, 14),
            Block.box(12, 0, 2, 14, 10, 4),
            Block.box(2, 0, 2, 4, 10, 4),
            Block.box(2., 12, 13, 14, 24, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_O = Stream.of(
            Block.box(2, 10, 2, 14, 12, 14),
            Block.box(2, 0, 2, 4, 10, 4),
            Block.box(2, 0, 12, 4, 10, 14),
            Block.box(12, 0, 12, 14, 10, 14),
            Block.box(12, 0, 2, 14, 10, 4),
            Block.box(2, 12, 2, 3, 24, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_Z = Stream.of(
            Block.box(2, 10, 2, 14, 12, 14),
            Block.box(12, 0, 2, 14, 10, 4),
            Block.box(2, 0, 2, 4, 10, 4),
            Block.box(2, 0, 12, 4, 10, 14),
            Block.box(12, 0, 12, 14, 10, 14),
            Block.box(2, 12, 2, 14, 24, 3)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(2, 10, 2, 14, 12, 14),
            Block.box(12, 0, 12, 14, 10, 14),
            Block.box(12, 0, 2, 14, 10, 4),
            Block.box(2, 0, 2, 4, 10, 4),
            Block.box(2, 0, 12, 4, 10, 14),
            Block.box(13, 12, 2, 14, 24, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public Chair(Properties copy) {
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

    @Override
    public InteractionResult use(BlockState State, Level Level, BlockPos SeatPosition, Player player, InteractionHand Hand, BlockHitResult HitResult) {
        return Sitting.create(Level, SeatPosition, 0.45, player, State.getValue(FACING));
    }
}