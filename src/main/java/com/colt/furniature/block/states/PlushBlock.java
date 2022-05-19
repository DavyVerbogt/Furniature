package com.colt.furniature.block.states;

import com.colt.furniature.Furniature;
import com.colt.furniature.block.Plush;
import com.colt.furniature.blockentities.PlushBluckEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;

import javax.annotation.Nullable;

public abstract class PlushBlock extends BaseEntityBlock {
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    private final Plush.Type type;
    public PlushBlock(Plush.Type Type , BlockBehaviour.Properties properties) {
        super(properties);
        this.type = Type;
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation rotation) {
        return state.setValue(ROTATION, Integer.valueOf(rotation.rotate(state.getValue(ROTATION), 16)));
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.defaultBlockState().setValue(ROTATION, Integer.valueOf(Mth.floor((double) (context.getRotation() * 16.0F / 360.0F) + 0.5D) & 15));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, Integer.valueOf(mirror.mirror(state.getValue(ROTATION), 16)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56329_) {
        p_56329_.add(ROTATION);
    }

    public BlockEntity newBlockEntity(BlockPos Pos, BlockState State) {
        return new PlushBluckEntity(Pos, State);
    }

    @Override
    public boolean isPathfindable(BlockState State, BlockGetter BlockGet, BlockPos Pos, PathComputationType Path) {
        return false;
    }

    public Plush.Type getType() {
        return this.type;
    }
    
}
