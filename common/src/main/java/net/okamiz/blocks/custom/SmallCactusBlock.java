package net.okamiz.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallCactusBlock extends CactusBlock {

    public static final VoxelShape SHAPE = Block.box((double)4.0F, (double)0.0F, (double)4.0F, (double)12.0F, (double)8.0F, (double)12.0F);

    public SmallCactusBlock(Properties properties) {
        super(properties);
    }

    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction));
            if (blockState2.isSolid() || levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA)) {
                return false;
            }
        }

        BlockState blockState3 = levelReader.getBlockState(blockPos.below());
        return (blockState3.is(Blocks.CACTUS) || blockState3.is(BlockTags.SAND))
                && !levelReader.getBlockState(blockPos.above()).liquid();
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }
}
