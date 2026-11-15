package bunnythekenzo.client;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.render.BlockSelectorRenderer;
import bunnythekenzo.client.render.EmoteConfettiRenderer;
import bunnythekenzo.client.render.GeodeClusterRenderer;
import bunnythekenzo.client.render.HitboxRenderer;
import bunnythekenzo.client.render.TargetEspRenderer;

public final class WorldOverlayDispatcher {
  public static boolean enabled;

  public static void update() {
    if (!enabled) {
      enabled = true;
      WorldRenderEvents.END_MAIN.register(WorldOverlayDispatcher::apply);
    }
  }

  public static void apply(WorldRenderContext worldRenderContext) {
    WorldLabelRenderer.update();
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    MatrixStack matrixStack = worldRenderContext.matrices();
    if (moduleManager != null
        && matrixStack != null
        && worldRenderContext.consumers() instanceof Immediate immediate) {
      Vec3d pos = worldRenderContext.worldState().cameraRenderState.pos;
      if (ClientInitializer.getEmoteCatalog() != null) {
        EmoteConfettiRenderer.apply(
            immediate,
            matrixStack,
            pos,
            ClientInitializer.getEmoteCatalog().getEmoteConfettiSystem());
      }

      BlockSelectorRenderer.apply(
          immediate,
          matrixStack,
          worldRenderContext.worldState(),
          moduleManager.customBlockOutlineModule);
      if (moduleManager.clusterESPModule != null && moduleManager.clusterESPModule.isEnabled()) {
        GeodeClusterRenderer.apply(immediate, matrixStack, pos, moduleManager.clusterESPModule);
      }

      if (moduleManager.susChunkFinderModule != null
          && moduleManager.susChunkFinderModule.isEnabled()) {
        LookupSupport3.apply(immediate, matrixStack, pos, moduleManager.susChunkFinderModule);
      }

      if (moduleManager.chunkFinderModule != null && moduleManager.chunkFinderModule.isEnabled()) {
        ChunkFinderRenderer.apply(immediate, matrixStack, pos, moduleManager.chunkFinderModule);
      }

      if (moduleManager.deepscanChunkFinderModule != null
          && moduleManager.deepscanChunkFinderModule.isEnabled()) {
        DeepscanChunkRenderer.apply(
            immediate, matrixStack, pos, moduleManager.deepscanChunkFinderModule);
      }

      if (moduleManager.storageESPModule != null && moduleManager.storageESPModule.isEnabled()) {
        StorageScanner.apply(immediate, matrixStack, pos, moduleManager.storageESPModule);
      }

      if (moduleManager.blockESPModule != null && moduleManager.blockESPModule.isEnabled()) {
        BlockEntityScanner.apply(immediate, matrixStack, pos, moduleManager.blockESPModule);
      }

      if (moduleManager.blockEntityESPModule != null
          && moduleManager.blockEntityESPModule.isEnabled()) {
        BlockEntityEspRenderer.apply(
            immediate, matrixStack, pos, moduleManager.blockEntityESPModule);
      }

      if (moduleManager.spawnerNametagsModule != null
          && moduleManager.spawnerNametagsModule.isEnabled()) {
        SpawnerEspRenderer.apply(immediate, matrixStack, pos, moduleManager.spawnerNametagsModule);
      }

      if (moduleManager.spawnerBeamModule != null && moduleManager.spawnerBeamModule.isEnabled()) {
        VertexConsumerSupport2.apply(immediate, matrixStack, pos, moduleManager.spawnerBeamModule);
      }

      if (moduleManager.holeESPModule != null && moduleManager.holeESPModule.isEnabled()) {
        HoleScanner.apply(immediate, matrixStack, pos, moduleManager.holeESPModule);
      }

      if (moduleManager.playerESPModule != null && moduleManager.playerESPModule.isEnabled()) {
        EntityEspRenderer.apply(immediate, matrixStack, pos, moduleManager.playerESPModule);
      }

      if (moduleManager.mobESPModule != null && moduleManager.mobESPModule.isEnabled()) {
        EntityEspRenderer.apply(immediate, matrixStack, pos, moduleManager.mobESPModule);
      }

      if (moduleManager.client67JumpCirclesModule != null
          && moduleManager.client67JumpCirclesModule.isEnabled()) {
        VertexConsumerSupport5.apply(
            immediate, matrixStack, pos, moduleManager.client67JumpCirclesModule);
      }

      if (moduleManager.hitParticlesModule != null
          && moduleManager.hitParticlesModule.isEnabled()) {
        VertexConsumerSupport3.apply(immediate, matrixStack, pos, moduleManager.hitParticlesModule);
      }

      if (moduleManager.targetESPModule != null && moduleManager.targetESPModule.isEnabled()) {
        TargetEspRenderer.apply(immediate, matrixStack, pos, moduleManager.targetESPModule);
      }

      if (moduleManager.hitBoxModule != null && moduleManager.hitBoxModule.isEnabled()) {
        HitboxRenderer.render(immediate, matrixStack, pos, moduleManager.hitBoxModule);
      }
    }
  }
}
