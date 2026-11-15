package bunnythekenzo.client.mixin;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import bunnythekenzo.client.FakeBlockItemRenderer;
import bunnythekenzo.client.FakeLootDropModule;

@Pseudo
@Mixin(targets = "net/caffeinemc/mods/sodium/client/world/LevelSlice", remap = false)
public abstract class LevelSliceMixin {
  @Shadow @Final public ClientWorld level;
  @Unique public boolean enabled;

  @Shadow
  public abstract BlockState getBlockState(int intValue, int intValue2, int intValue3);

  @Inject(
      target =
          @Desc(
              args = {int.class, int.class, int.class},
              ret = BlockState.class,
              value = "getBlockState"),
      at = @At("RETURN"),
      cancellable = true)
  public void apply(
      int intValue, int intValue2, int intValue3, CallbackInfoReturnable callbackInfoReturnable) {
    if (!this.enabled) {
      FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
      if (fakeLootDropModule != null && fakeLootDropModule.isEnabled()) {
        BlockState blockState = (BlockState) callbackInfoReturnable.getReturnValue();
        if (fakeLootDropModule.getItem(blockState) != null) {
          boolean flag = false;
          this.enabled = true;

          try {
            for (Direction direction : Direction.values()) {
              if (!this.getBlockState(
                      intValue + direction.getOffsetX(),
                      intValue2 + direction.getOffsetY(),
                      intValue3 + direction.getOffsetZ())
                  .isOpaqueFullCube()) {
                flag = true;
                break;
              }
            }
          } finally {
            this.enabled = false;
          }

          BlockPos blockPos = new BlockPos(intValue, intValue2, intValue3);
          if (flag) {
            FakeBlockItemRenderer.apply(this.level, blockPos, blockState.getBlock().asItem());
          } else {
            FakeBlockItemRenderer.apply(blockPos);
          }
        }

        callbackInfoReturnable.setReturnValue(fakeLootDropModule.getBlockState(blockState));
      }
    }
  }

  @Inject(
      target =
          @Desc(
              args = {int.class, int.class, int.class},
              ret = BlockEntity.class,
              value = "getBlockEntity"),
      at = @At("RETURN"),
      cancellable = true)
  public void apply2(
      int intValue, int intValue2, int intValue3, CallbackInfoReturnable callbackInfoReturnable) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null && fakeLootDropModule.isEnabled()) {
      BlockEntity blockEntity = (BlockEntity) callbackInfoReturnable.getReturnValue();
      BlockState blockState = this.getBlockState(intValue, intValue2, intValue3);
      if (blockEntity == null || !blockEntity.getCachedState().isOf(blockState.getBlock())) {
        BlockEntity blockEntity2 =
            blockState.getBlock() instanceof BlockEntityProvider blockEntityProvider
                ? blockEntityProvider.createBlockEntity(
                    new BlockPos(intValue, intValue2, intValue3), blockState)
                : null;
        if (blockEntity2 != null) {
          blockEntity2.setWorld(this.level);
        }

        callbackInfoReturnable.setReturnValue(blockEntity2);
      }
    }
  }
}
