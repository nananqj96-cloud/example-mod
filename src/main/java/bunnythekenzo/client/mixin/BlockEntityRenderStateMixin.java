package bunnythekenzo.client.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer.CrumblingOverlayCommand;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(BlockEntityRenderState.class)
public class BlockEntityRenderStateMixin {
  @Inject(method = "updateBlockEntityRenderState", at = @At("TAIL"))
  private static void apply(
      BlockEntity blockEntity,
      BlockEntityRenderState blockEntityRenderState,
      CrumblingOverlayCommand crumblingOverlayCommand,
      CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    World world = blockEntity.getWorld();
    if (fakeLootDropModule != null && fakeLootDropModule.isEnabled() && world != null) {
      BlockState blockState = world.getBlockState(blockEntity.getPos());
      if (!blockState.isOf(blockEntityRenderState.blockState.getBlock())
          && fakeLootDropModule
              .getBlockState(blockState)
              .isOf(blockEntityRenderState.blockState.getBlock())) {
        int blockLightCoordinates =
            LightmapTextureManager.getBlockLightCoordinates(
                blockEntityRenderState.lightmapCoordinates);
        int skyLightCoordinates =
            LightmapTextureManager.getSkyLightCoordinates(
                blockEntityRenderState.lightmapCoordinates);

        for (Direction direction : Direction.values()) {
          int lightmapCoordinates =
              WorldRenderer.getLightmapCoordinates(world, blockEntity.getPos().offset(direction));
          blockLightCoordinates =
              Math.max(
                  blockLightCoordinates,
                  LightmapTextureManager.getBlockLightCoordinates(lightmapCoordinates));
          skyLightCoordinates =
              Math.max(
                  skyLightCoordinates,
                  LightmapTextureManager.getSkyLightCoordinates(lightmapCoordinates));
        }

        blockEntityRenderState.lightmapCoordinates =
            LightmapTextureManager.pack(blockLightCoordinates, skyLightCoordinates);
      }
    }
  }
}
