package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeBlockItemRenderer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(ChunkRendererRegion.class)
public abstract class ChunkRendererRegionMixin {
  @Shadow @Final public World world;
  @Unique public boolean enabled;

  @Shadow
  public abstract BlockState getBlockState(BlockPos blockPos);

  @ModifyReturnValue(method = "getBlockState", at = @At("RETURN"))
  public BlockState getBlockState(BlockState blockState, BlockPos blockPos) {
    if (this.enabled) {
      return blockState;
    }

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.fakeLootDropModule != null
        && moduleManager.fakeLootDropModule.getItem(blockState) != null) {
      boolean flag = false;
      this.enabled = true;

      try {
        for (Direction direction : Direction.values()) {
          if (!this.getBlockState(blockPos.offset(direction)).isOpaqueFullCube()) {
            flag = true;
            break;
          }
        }
      } finally {
        this.enabled = false;
      }

      if (flag) {
        FakeBlockItemRenderer.apply(this.world, blockPos, blockState.getBlock().asItem());
      } else {
        FakeBlockItemRenderer.apply(blockPos);
      }
    }

    return moduleManager != null && moduleManager.fakeLootDropModule != null
        ? moduleManager.fakeLootDropModule.getBlockState(blockState)
        : blockState;
  }

  @ModifyReturnValue(method = "getFluidState", at = @At("RETURN"))
  public FluidState getFluidState(FluidState fluidState, BlockPos blockPos) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null
            && moduleManager.fakeLootDropModule != null
            && moduleManager.fakeLootDropModule.isEnabled()
        ? this.getBlockState(blockPos).getFluidState()
        : fluidState;
  }

  @ModifyReturnValue(method = "getBlockEntity", at = @At("RETURN"))
  public BlockEntity getBlockEntity(BlockEntity blockEntity, BlockPos blockPos) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.fakeLootDropModule != null
        && moduleManager.fakeLootDropModule.isEnabled()) {
      BlockState blockState = this.getBlockState(blockPos);
      if (blockEntity != null && blockEntity.getCachedState().isOf(blockState.getBlock())) {
        return blockEntity;
      }

      if (blockState.getBlock() instanceof BlockEntityProvider blockEntityProvider) {
        BlockEntity blockEntity2 =
            blockEntityProvider.createBlockEntity(blockPos.toImmutable(), blockState);
        if (blockEntity2 != null) {
          blockEntity2.setWorld(this.world);
        }

        return blockEntity2;
      } else {
        return null;
      }
    } else {
      return blockEntity;
    }
  }
}
