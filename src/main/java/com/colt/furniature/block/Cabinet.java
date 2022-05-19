package com.colt.furniature.block;

import com.colt.furniature.Furniature;
import com.colt.furniature.block.states.HorizontalDirectionalWaterloggedBlockEntity;
import com.colt.furniature.blockentities.CabinetBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class Cabinet extends HorizontalDirectionalWaterloggedBlockEntity {

    public static final BooleanProperty FULL = BooleanProperty.create("full");
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(0, 10, 0, 2, 16, 16),
            Block.box(14, 10, 0, 16, 16, 16),
            Block.box(2, 10, 14, 14, 16, 16),
            Block.box(2, 14, 0, 14, 16, 14),
            Block.box(0, 0, 0, 16, 10, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_O = Stream.of(
            Block.box(0, 10, 0, 16, 16, 2),
            Block.box(0, 10, 14, 16, 16, 16),
            Block.box(0, 10, 2, 2, 16, 14),
            Block.box(2, 14, 2, 16, 16, 14),
            Block.box(0, 0, 0, 16, 10, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_Z = Stream.of(
            Block.box(14, 10, 0, 16, 16, 16),
            Block.box(0, 10, 0, 2, 16, 16),
            Block.box(2, 10, 0, 14, 16, 2),
            Block.box(2, 14, 2, 14, 16, 16),
            Block.box(0, 0, 0, 16, 10, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(0, 10, 14, 16, 16, 16),
            Block.box(0, 10, 0, 16, 16, 2),
            Block.box(14, 10, 2, 16, 16, 14),
            Block.box(0, 14, 2, 14, 16, 14),
            Block.box(0, 0, 0, 16, 10, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public Cabinet(Properties copy) {
        super(copy.noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(OPEN, false).setValue(FULL, false));
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
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity tile = level.getBlockEntity(pos);
            Furniature.LOGGER.info(tile);
            if (tile instanceof CabinetBlockEntity) {
                player.openMenu((CabinetBlockEntity) tile);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof Container) {
                Containers.dropContents(worldIn, pos, (Container) tileEntity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof CabinetBlockEntity) {
            ((CabinetBlockEntity) tileEntity).recheckOpen();
        }
    }


    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof CabinetBlockEntity) {
                ((CabinetBlockEntity) tileEntity).setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN, FULL);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(worldIn.getBlockEntity(pos));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CabinetBlockEntity(pos, state);
    }
}
