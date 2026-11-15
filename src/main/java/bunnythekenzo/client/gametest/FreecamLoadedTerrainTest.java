package bunnythekenzo.client.gametest;

import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import org.joml.Vector3d;
import bunnythekenzo.client.CameraChunkCulling;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.DampedVectorIntegrator;
import bunnythekenzo.client.module.CustomGlintModule;
import bunnythekenzo.client.module.FreeLookModule;
import bunnythekenzo.client.module.FreecamModule;
import bunnythekenzo.client.module.ModuleManager;

public final class FreecamLoadedTerrainTest {
  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    testSingleplayerContext
        .getServer()
        .runOnServer(
            minecraftServer -> {
              ProtectionPerformanceTest.ensureInitialized();

              for (int index = -24; index < 40; index++) {
                for (int index2 = -24; index2 < 40; index2++) {
                  minecraftServer
                      .getOverworld()
                      .setBlockState(
                          new BlockPos(index, 20, index2),
                          ((index >> 4) + (index2 >> 4)) % 2 == 0
                              ? Blocks.PURPUR_BLOCK.getDefaultState()
                              : Blocks.QUARTZ_BLOCK.getDefaultState());
                }
              }
            });
    testSingleplayerContext.getServer().runCommand("tp @a 8 45 8");
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.options.getViewDistance().setValue(32);
          minecraftClient.options.getFov().setValue(70);
          minecraftClient.options.setPerspective(Perspective.FIRST_PERSON);
          minecraftClient.player.setPitch(65.0F);
          minecraftClient.player.setYaw(25.0F);
        });
    clientGameTestContext.waitTicks(40);
    clientGameTestContext.waitFor(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();
          return minecraftClient2.worldRenderer.getCompletedChunkCount() > 0;
        },
        200);
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          ClientInitializer.logger.info(
              "[terrain-test] baseline slider={} effective={} loaded={} rendered={} stats={}",
              minecraftClient3.options.getViewDistance().getValue(),
              minecraftClient3.options.getClampedViewDistance(),
              minecraftClient3.world.getChunkManager().getLoadedChunkCount(),
              minecraftClient3.worldRenderer.getCompletedChunkCount(),
              minecraftClient3.worldRenderer.getChunksDebugString());
        });
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          Vector3d vector3d = new Vector3d();
          Vector3d vector3d2 = new Vector3d();
          Vector3d vector3d3 = new Vector3d();
          ProtectionPerformanceTest.ensureInitialized();

          Vector3d vector3d4 = new Vector3d();

          for (int index3 = 0; index3 < 30; index3++) {
            DampedVectorIntegrator.render(
                vector3d, vector3d2, 10.0, 2.0, -3.0, Math.log(1000.0), 0.03333333333333333);
          }

          for (int index4 = 0; index4 < 240; index4++) {
            DampedVectorIntegrator.render(
                vector3d3, vector3d4, 10.0, 2.0, -3.0, Math.log(1000.0), 0.004166666666666667);
          }

          apply(vector3d.distance(vector3d3) < 1.0E-9, "smooth movement matches at 30 and 240 fps");
          ModuleManager moduleManager = ClientInitializer.getModuleManager();
          moduleManager.deepscanChunkFinderModule.setEnabled(false);
          moduleManager.freeLookModule.setEnabled(false);
          moduleManager.freecamModule.setEnabled(false);
          minecraftClient4.options.forwardKey.setPressed(false);
          minecraftClient4.options.jumpKey.setPressed(false);
          minecraftClient4.options.sneakKey.setPressed(false);
          minecraftClient4.chunkCullingEnabled = true;
          minecraftClient4.options.setPerspective(Perspective.FIRST_PERSON);
          minecraftClient4.player.setYaw(0.0F);
          minecraftClient4.player.setPitch(50.0F);
          WorldChunk worldChunk =
              minecraftClient4.world.getChunkManager().getWorldChunk(0, 0, false);
          int loadedChunkCount = minecraftClient4.world.getChunkManager().getLoadedChunkCount();
          apply(worldChunk != null, "body chunk loaded before detach");
          FreecamModule freecamModule = moduleManager.freecamModule;
          freecamModule.smoothingSetting.setValue(false);
          freecamModule.speedSetting.setNumber(Double.valueOf(50.0));
          freecamModule.setEnabled(true);
          freecamModule.apply(minecraftClient4);
          apply(!minecraftClient4.chunkCullingEnabled, "freecam disables occlusion");
          Vec3d entityPos = minecraftClient4.player.getEntityPos();
          float yaw = minecraftClient4.player.getYaw();
          float amount = freecamModule.getAmount();
          minecraftClient4.player.changeLookDirection(20.0, 10.0);
          apply(
              minecraftClient4.player.getYaw() == yaw && freecamModule.getAmount() != amount,
              "mouse turns detached camera only");
          freecamModule.apply(0.0F, 50.0F);
          minecraftClient4.options.forwardKey.setPressed(true);

          for (int index5 = 0; index5 < 160; index5++) {
            freecamModule.apply(0.05);
          }

          minecraftClient4.options.forwardKey.setPressed(false);
          apply(
              freecamModule.getVec3d(1.0F).distanceTo(entityPos) > 7000.0,
              "camera crossed many chunk boundaries");
          apply(minecraftClient4.player.getEntityPos().equals(entityPos), "body stays in place");
          apply(
              minecraftClient4.world.getChunkManager().getWorldChunk(0, 0, false) == worldChunk,
              "detaching does not discard body chunk");
          apply(
              minecraftClient4.world.getChunkManager().getLoadedChunkCount() == loadedChunkCount,
              "camera does not resize or reload chunk storage");
          FreeLookModule freeLookModule = moduleManager.freeLookModule;
          freeLookModule.throughWallsSetting.setValue(true);
          freeLookModule.setEnabled(true);
          freecamModule.setEnabled(false);
          apply(
              !minecraftClient4.chunkCullingEnabled,
              "freelook retains shared override after freecam disables");
          freeLookModule.setEnabled(false);
          apply(minecraftClient4.chunkCullingEnabled, "last camera restores original culling");
          minecraftClient4.chunkCullingEnabled = false;
          freeLookModule.setEnabled(true);
          freeLookModule.setEnabled(false);
          apply(!minecraftClient4.chunkCullingEnabled, "pre-existing disabled culling preserved");
          minecraftClient4.chunkCullingEnabled = true;
          freeLookModule.togglePerspectiveSetting.setValue(false);
          freeLookModule.throughWallsSetting.setValue(false);
          freeLookModule.setEnabled(true);
          Camera camera = new Camera();
          camera.update(minecraftClient4.world, minecraftClient4.player, true, false, 1.0F);
          apply(
              Math.abs(MathHelper.wrapDegrees(camera.getYaw() - freeLookModule.getAmount())) < 0.01,
              "back view camera rotation");
          camera.update(minecraftClient4.world, minecraftClient4.player, true, true, 1.0F);
          apply(
              Math.abs(
                      MathHelper.wrapDegrees(camera.getYaw() - freeLookModule.getAmount() - 180.0F))
                  < 0.01,
              "front view keeps 180-degree reversal");
          apply(
              Math.abs(camera.getPitch() + freeLookModule.getAmount2()) < 0.01,
              "front view reverses pitch");
          freeLookModule.throughWallsSetting.setValue(true);
          CameraChunkCulling.apply(minecraftClient4);
          apply(
              !minecraftClient4.chunkCullingEnabled, "live through-walls setting updates culling");
          freeLookModule.throughWallsSetting.setValue(false);
          CameraChunkCulling.apply(minecraftClient4);
          apply(
              !minecraftClient4.chunkCullingEnabled,
              "orbit camera keeps loaded geometry visible without wall clip");
          freeLookModule.setEnabled(false);
          freecamModule.speedSetting.setNumber(Double.valueOf(1.0));
          freecamModule.setEnabled(true);
          freecamModule.apply(minecraftClient4);
          freecamModule.apply(25.0F, 65.0F);
          minecraftClient4.inGameHud.getChatHud().clear(true);
        });
    clientGameTestContext.waitTicks(30);
    clientGameTestContext.waitFor(
        minecraftClient5 -> {
          ProtectionPerformanceTest.ensureInitialized();
          return minecraftClient5.worldRenderer.getCompletedChunkCount() > 0;
        },
        200);
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              minecraftClient6.options.getViewDistance().getValue() == 32,
              "freecam preserves max user render-distance setting");
          ClientInitializer.logger.info(
              "[terrain-test] freecam slider={} effective={} loaded={} rendered={} stats={}",
              minecraftClient6.options.getViewDistance().getValue(),
              minecraftClient6.options.getClampedViewDistance(),
              minecraftClient6.world.getChunkManager().getLoadedChunkCount(),
              minecraftClient6.worldRenderer.getCompletedChunkCount(),
              minecraftClient6.worldRenderer.getChunksDebugString());
        });
    clientGameTestContext.takeScreenshot("freecam-loaded-terrain");
    clientGameTestContext.waitTicks(5);
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          ClientInitializer.getModuleManager().freecamModule.setEnabled(false);
          FreeLookModule freeLookModule2 = ClientInitializer.getModuleManager().freeLookModule;
          minecraftClient7.player.setPitch(-30.0F);
          freeLookModule2.throughWallsSetting.setValue(true);
          freeLookModule2.cameraDistanceSetting.setNumber(Double.valueOf(12.0));
          freeLookModule2.setEnabled(true);
          minecraftClient7.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
        });
    clientGameTestContext.waitTicks(30);
    clientGameTestContext.waitFor(
        minecraftClient8 -> {
          ProtectionPerformanceTest.ensureInitialized();
          return minecraftClient8.worldRenderer.getCompletedChunkCount() > 0;
        },
        200);
    clientGameTestContext.takeScreenshot("freelook-front-terrain");
    clientGameTestContext.waitTicks(5);
    clientGameTestContext.runOnClient(
        minecraftClient9 -> {
          CustomGlintModule customGlintModule =
              ClientInitializer.getModuleManager().customGlintModule;
          customGlintModule.glintStyleSetting.apply("Prismatic");
          customGlintModule.setEnabled(true);
        });
    clientGameTestContext.waitTicks(80);
    clientGameTestContext.runOnClient(
        minecraftClient10 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              minecraftClient10.options.getViewDistance().getValue() == 32
                  && minecraftClient10.worldRenderer.getCompletedChunkCount() > 0,
              "terrain remains visible with custom glint active");
          ClientInitializer.logger.info(
              "[terrain-test] glint worker active={} rendered={}",
              Thread.getAllStackTraces().keySet().stream()
                  .anyMatch(
                      thread -> {
                        return thread.getName().equals("bunnythekenzo-glint-texture");
                      }),
              minecraftClient10.worldRenderer.getCompletedChunkCount());
          ClientInitializer.getModuleManager().customGlintModule.setEnabled(false);
        });
    clientGameTestContext.runOnClient(
        minecraftClient11 -> {
          ClientInitializer.getModuleManager().freeLookModule.setEnabled(false);
          apply(minecraftClient11.chunkCullingEnabled, "culling restored after visual pass");
          ClientInitializer.logger.info(
              "[camera-test] PASS: chunk preservation at 8000 blocks; frame-rate-independent"
                  + " movement and mouse routing, shared culling lifecycle, live settings,"
                  + " front/back rotation");
        });
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}
