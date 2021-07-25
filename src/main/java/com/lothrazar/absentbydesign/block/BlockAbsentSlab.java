package com.lothrazar.absentbydesign.block;

import com.lothrazar.absentbydesign.registry.AbsentRegistry;
import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockAbsentSlab extends SlabBlock implements IBlockAbsent {

  private final String rawName;
  // could be any particle. currently only used by Crying Obs
  public SimpleParticleType part = null;

  public BlockAbsentSlab(Properties properties, String reg) {
    super(properties);
    rawName = reg;
    setRegistryName(reg);
  }

  public boolean doVisibility = false;

  @SuppressWarnings("deprecation")
  @Override
  @OnlyIn(Dist.CLIENT)
  public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
    if (doVisibility) {
      return adjacentBlockState.getBlock() == this || adjacentBlockState.is(this);
    }
    return super.skipRendering(state, adjacentBlockState, side); // seems to be always false
  }

  @Override
  public void setTransparent() {
    doVisibility = true;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
    if (part != null
        && worldIn.random.nextDouble() < 0.2) {
      AbsentRegistry.spawnBlockParticles(part, worldIn, pos, rand);
    }
  }

  @Override
  public String rawName() {
    return rawName;
  }
}
