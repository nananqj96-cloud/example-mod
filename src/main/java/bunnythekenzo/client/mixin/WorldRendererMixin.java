package bunnythekenzo.client.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.memory.ObjectAllocator;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeBlockItemRenderer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.FramebufferSupport;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.render.AuroraRenderer;
import bunnythekenzo.client.render.CrystalPulseRenderer;
import bunnythekenzo.client.render.LumaRenderer;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
  @Inject(method = "fillEntityRenderStates", at = @At("TAIL"))
  public void apply(
      Camera camera,
      Frustum frustum,
      RenderTickCounter renderTickCounter,
      WorldRenderState worldRenderState,
      CallbackInfo callbackInfo) {
    FakeBlockItemRenderer.setFrustum(frustum);
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    MinecraftClient instance = MinecraftClient.getInstance();
    if (fakeLootDropModule != null
        && fakeLootDropModule.equipmentPreview.test3()
        && instance.player != null
        && !instance.options.getPerspective().isFirstPerson()) {
      boolean flag =
          worldRenderState.entityRenderStates.stream()
              .anyMatch(
                  (java.util.function.Predicate<
                          net.minecraft.client.render.entity.state.EntityRenderState>)
                      ((referenceArgument59_0) ->
                          WorldRendererMixin.isLocalPlayerRenderState(
                              instance,
                              (net.minecraft.client.render.entity.state.EntityRenderState)
                                  referenceArgument59_0)));
      if (!flag) {
        worldRenderState.entityRenderStates.add(
            instance
                .getEntityRenderDispatcher()
                .getAndUpdateRenderState(
                    instance.player, renderTickCounter.getTickProgress(false)));
      }
    }
  }

  @Inject(method = "pushEntityRenders", at = @At("TAIL"))
  public void apply(
      MatrixStack matrixStack,
      WorldRenderState worldRenderState,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      CallbackInfo callbackInfo) {
    FakeBlockItemRenderer.submit(matrixStack, worldRenderState, orderedRenderCommandQueue);
    LumaRenderer.submit(matrixStack, worldRenderState, orderedRenderCommandQueue);
    AuroraRenderer.apply(matrixStack, worldRenderState, orderedRenderCommandQueue);
    CrystalPulseRenderer.apply(matrixStack, worldRenderState, orderedRenderCommandQueue);
  }

  @Inject(method = "render", at = @At("HEAD"))
  public void apply(
      ObjectAllocator objectAllocator,
      RenderTickCounter renderTickCounter,
      boolean flag,
      Camera camera,
      Matrix4f matrix4f,
      Matrix4f matrix4f2,
      Matrix4f matrix4f3,
      GpuBufferSlice gpuBufferSlice,
      Vector4f vector4f,
      boolean flag2,
      CallbackInfo callbackInfo) {
    FramebufferSupport.render(matrix4f, matrix4f3, renderTickCounter.getTickProgress(false));
  }

  @Inject(
      method =
          "renderTargetBlockOutline(Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/util/math/MatrixStack;ZLnet/minecraft/client/render/state/WorldRenderState;)V",
      at = @At("HEAD"),
      cancellable = true)
  public void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      boolean flag,
      WorldRenderState worldRenderState,
      CallbackInfo callbackInfo) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null) {
      if (moduleManager.freecamModule != null && moduleManager.freecamModule.test()) {
        callbackInfo.cancel();
      } else {
        if (moduleManager.customBlockOutlineModule.isEnabled()) {
          callbackInfo.cancel();
        }
      }
    }
  }

  private static boolean isLocalPlayerRenderState(
      MinecraftClient minecraftClient, EntityRenderState entityRenderState) {
    return entityRenderState instanceof PlayerEntityRenderState playerEntityRenderState
        && playerEntityRenderState.id == minecraftClient.player.getId();
  }
}
