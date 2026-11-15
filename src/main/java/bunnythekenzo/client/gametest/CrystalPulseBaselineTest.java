package bunnythekenzo.client.gametest;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.WeightedPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.module.CrystalPulseModule;
import bunnythekenzo.client.render.CrystalPulseRenderer;
import bunnythekenzo.client.ui.PanelScreen;

public final class CrystalPulseBaselineTest {

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    Runnable onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              LinkedHashMap linkedHashMap = new LinkedHashMap();
              ClientInitializer.getModuleManager()
                  .getList()
                  .forEach(
                      value2 -> {
                        linkedHashMap.put(
                            value2, ((bunnythekenzo.client.module.Module) (value2)).isEnabled());
                      });
              LinkedHashMap linkedHashMap2 = new LinkedHashMap();
              getCrystalPulseModule()
                  .getSettings()
                  .forEach(
                      (java.util.function.Consumer<bunnythekenzo.client.setting.Setting>)
                          (setting -> {
                            linkedHashMap2.put(setting, setting.toJson());
                          }));
              Perspective perspective = minecraftClient.options.getPerspective();
              int value3 = minecraftClient.options.getFov().getValue();
              int value4 = minecraftClient.options.getMaxFps().getValue();
              boolean hudHidden = minecraftClient.options.hudHidden;
              boolean enabled = AnimationPreferences.isEnabled();
              return () -> {
                ((net.minecraft.client.MinecraftClient) (minecraftClient)).setScreen(null);
                getCrystalPulseModule().setEnabled(false);
                linkedHashMap2.forEach(
                    (referenceArgument30_0, referenceArgument30_1) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument30_0)
                            .fromJson((com.google.gson.JsonElement) referenceArgument30_1));
                linkedHashMap.forEach(
                    (referenceArgument31_0, referenceArgument31_1) ->
                        ((bunnythekenzo.client.module.Module) referenceArgument31_0)
                            .setEnabled((java.lang.Boolean) referenceArgument31_1));
                minecraftClient.options.setPerspective(perspective);
                minecraftClient.options.getFov().setValue(value3);
                minecraftClient.options.getMaxFps().setValue(value4);
                minecraftClient.options.hudHidden = hudHidden;
                AnimationPreferences.setEnabled(enabled);
              };
            });
    AtomicInteger atomicInteger = new AtomicInteger();

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            ProtectionPerformanceTest.ensureInitialized();

            getCrystalPulseModule()
                .getSettings()
                .forEach(
                    (referenceArgument32_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument32_0).resetToDefault());
            AnimationPreferences.setEnabled(true);
            minecraftClient2.options.getMaxFps().setValue(60);
            minecraftClient2.options.getFov().setValue(70);
            minecraftClient2.options.hudHidden = true;
            minecraftClient2.options.setPerspective(Perspective.FIRST_PERSON);
            GuiModuleEntry guiModuleEntry = GuiModuleEntry.of(getCrystalPulseModule());
            apply(
                guiModuleEntry.getList().size() == 3
                    && guiModuleEntry.getList().stream()
                            .mapToInt(
                                collectionData -> {
                                  return ((bunnythekenzo.client.CollectionData) (collectionData))
                                      .list()
                                      .size();
                                })
                            .sum()
                        == getCrystalPulseModule().getSettings().size(),
                "every setting is reachable in three groups");
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ProtectionPerformanceTest.ensureInitialized();

                ServerWorld overworld = minecraftServer.getOverworld();
                overworld.setTimeOfDay(13000L);

                for (int index = -16; index <= 16; index++) {
                  for (int index2 = -16; index2 <= 16; index2++) {
                    Block block =
                        Math.abs(index) != 9 && Math.abs(index2) != 9
                            ? Blocks.OBSIDIAN
                            : Blocks.POLISHED_DIORITE;
                    overworld.setBlockState(
                        new BlockPos(index, 140, index2), block.getDefaultState());
                  }
                }

                for (int intValue : new int[] {-7, 7}) {
                  for (int intValue2 : new int[] {-7, 7}) {
                    overworld.setBlockState(
                        new BlockPos(intValue, 141, intValue2),
                        Blocks.SEA_LANTERN.getDefaultState());
                  }
                }

                for (double doubleValue : new double[] {0.5, -3.5, 4.5}) {
                  EndCrystalEntity endCrystalEntity =
                      new EndCrystalEntity(
                          overworld, doubleValue, 141.0, doubleValue == 0.5 ? 0.5 : 3.5);
                  overworld.spawnEntity(endCrystalEntity);
                  if (doubleValue == 0.5) {
                    atomicInteger.set(endCrystalEntity.getId());
                  }
                }

                ServerPlayerEntity first =
                    minecraftServer.getPlayerManager().getPlayerList().getFirst();
                first.changeGameMode(GameMode.CREATIVE);
                first.setPosition(0.5, 141.0, -5.5);
              });
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            minecraftClient3.player.setPosition(0.5, 141.0, -5.5);
            minecraftClient3.player.setVelocity(Vec3d.ZERO);
            minecraftClient3.player.setYaw(0.0F);
            minecraftClient3.player.setPitch(14.0F);
            minecraftClient3.player.resetPosition();
          });
      clientGameTestContext.waitTicks(45);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            apply(
                minecraftClient4.world.getBlockState(new BlockPos(0, 140, 0)).isOf(Blocks.OBSIDIAN),
                "arena reached the client");
            minecraftClient4.worldRenderer.reload();
          });
      clientGameTestContext.waitTicks(30);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.takeScreenshot("crystal-pulse-baseline");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            getCrystalPulseModule().setEnabled(true);
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getCrystalPulseModule().halos(1.0F).size() == 3,
                "enabling discovers existing server crystals");
            apply(CrystalPulseRenderer.getCount() > 5, "native GPU ring pass submits");
            apply(
                getCrystalPulseModule().getList2().isEmpty(),
                "loading crystals does not invent explosions");
          });
      clientGameTestContext.takeScreenshot("crystal-pulse-opal");

      for (String text : new String[] {"Amethyst", "Glacier", "Rose", "Ember"}) {
        clientGameTestContext.runOnClient(
            minecraftClient7 -> {
              getCrystalPulseModule().toneSetting.setValue(text);
              JsonElement jsonElement = getCrystalPulseModule().toneSetting.toJson();
              getCrystalPulseModule().toneSetting.resetToDefault();
              getCrystalPulseModule().toneSetting.fromJson(jsonElement);
              apply(
                  getCrystalPulseModule().toneSetting.isSelected(text),
                  "palette survives config round-trip");
            });
        clientGameTestContext.waitTicks(2);
        clientGameTestContext.takeScreenshot("crystal-pulse-" + text.toLowerCase(Locale.ROOT));
      }

      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            getCrystalPulseModule().toneSetting.resetToDefault();
            getCrystalPulseModule().styleSetting.setValue("Orbit");
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("crystal-pulse-orbit");
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            getCrystalPulseModule().styleSetting.setValue("Minimal");
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("crystal-pulse-minimal");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                minecraftServer2.getOverworld().setTimeOfDay(6000L);
              });
      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            getCrystalPulseModule().styleSetting.resetToDefault();
          });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.takeScreenshot("crystal-pulse-daylight");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer3 -> {
                minecraftServer3.getOverworld().setTimeOfDay(13000L);
              });
      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            getCrystalPulseModule().fadeTimeSetting.setNumber(Double.valueOf(1.2));
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer4 -> {
                ServerWorld overworld2 = minecraftServer4.getOverworld();
                EndCrystalEntity endCrystalEntity2 =
                    (EndCrystalEntity) overworld2.getEntityById(atomicInteger.get());
                apply(endCrystalEntity2 != null, "server crystal exists");
                endCrystalEntity2.damage(
                    overworld2,
                    overworld2
                        .getDamageSources()
                        .playerAttack(
                            minecraftServer4.getPlayerManager().getPlayerList().getFirst()),
                    1.0F);
              });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !getCrystalPulseModule().getList2().isEmpty(),
                "real server explosion packet creates impact");
          });
      clientGameTestContext.takeScreenshot("crystal-pulse-impact");
      clientGameTestContext.waitTicks(28);
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            apply(getCrystalPulseModule().getList2().isEmpty(), "impacts expire completely");
          });
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            apply(minecraftClient14);
            getCrystalPulseModule().update4();
            getCrystalPulseModule().onTick();
            ProtectionPerformanceTest.ensureInitialized();

            EndCrystalEntity endCrystalEntity3 =
                new EndCrystalEntity(minecraftClient14.world, 0.5, 141.0, 0.5);
            endCrystalEntity3.setId(910001);
            minecraftClient14.world.addEntity(endCrystalEntity3);
            apply(
                getCrystalPulseModule().computeInt() > 0,
                "load callback sees crystals immediately");
            minecraftClient14.world.removeEntity(
                endCrystalEntity3.getId(), RemovalReason.DISCARDED);
            apply(
                getCrystalPulseModule().getList2().isEmpty(),
                "entity unload alone creates no impact");
            getCrystalPulseModule().test(endCrystalEntity3.getEntityPos());
            apply(
                getCrystalPulseModule().getList2().size() == 1,
                "same-tick spawn/remove remains matchable");
            getCrystalPulseModule().test(endCrystalEntity3.getEntityPos());
            apply(
                getCrystalPulseModule().getList2().size() == 1,
                "duplicate packet does not duplicate a burst");
            EndCrystalEntity endCrystalEntity4 =
                new EndCrystalEntity(minecraftClient14.world, 0.5, 141.0, 0.5);
            endCrystalEntity4.setId(910002);
            minecraftClient14.world.addEntity(endCrystalEntity4);
            getCrystalPulseModule().test(endCrystalEntity4.getEntityPos());
            apply(
                getCrystalPulseModule().getList2().size() == 2,
                "same-tick replacement at the same base gets its own impact");
            minecraftClient14.world.removeEntity(
                endCrystalEntity4.getId(), RemovalReason.DISCARDED);
            getCrystalPulseModule().test(new Vec3d(18.0, 141.0, 18.0));
            apply(getCrystalPulseModule().getList2().size() == 2, "unrelated explosion is ignored");
          });
      clientGameTestContext.waitTicks(28);
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            apply(getCrystalPulseModule().getList2().isEmpty(), "event bursts expire");
            getCrystalPulseModule().test(new Vec3d(0.5, 141.0, 0.5));
            ProtectionPerformanceTest.ensureInitialized();

            apply(
                getCrystalPulseModule().getList2().isEmpty(),
                "expired tombstones cannot trigger a later explosion");
            ArrayList arrayList = new ArrayList();

            for (int index3 = 0; index3 < 90; index3++) {
              EndCrystalEntity endCrystalEntity5 =
                  new EndCrystalEntity(
                      minecraftClient15.world, index3 % 9 - 4, 141.0, 1.0 + index3 / 9 * 0.5);
              endCrystalEntity5.setId(920000 + index3);
              minecraftClient15.world.addEntity(endCrystalEntity5);
              arrayList.add(endCrystalEntity5);
              getCrystalPulseModule().test(endCrystalEntity5.getEntityPos());
            }

            getCrystalPulseModule().onTick();
            apply(getCrystalPulseModule().computeInt() <= 64, "tracking is bounded under load");
            apply(getCrystalPulseModule().halos(1.0F).size() <= 12, "visible halos are bounded");
            apply(
                getCrystalPulseModule().getList2().size() == 12,
                "impact cap handles crystal chains");
            AnimationPreferences.setEnabled(false);

            for (EndCrystalEntity endCrystalEntity6 :
                (java.lang.Iterable<EndCrystalEntity>) (java.lang.Iterable<?>) (arrayList)) {
              minecraftClient15.world.removeEntity(
                  endCrystalEntity6.getId(), RemovalReason.DISCARDED);
            }
          });
      double onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient16 -> {
                return getCrystalPulseModule().computeDouble2(1.0F);
              });
      clientGameTestContext.waitTicks(28);
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getCrystalPulseModule().computeDouble2(0.5F) == onClient2,
                "reduced motion freezes orbit animation");
            apply(
                getCrystalPulseModule().getList2().isEmpty(),
                "reduced motion still expires impacts");
            AnimationPreferences.setEnabled(true);
            getCrystalPulseModule().impactWavesSetting.setValue(false);
            apply(
                !getCrystalPulseModule().fadeTimeSetting.isVisible()
                    && !getCrystalPulseModule().crystalShardsSetting.isVisible(),
                "dependent impact controls hide");
            getCrystalPulseModule().crystalHalosSetting.setValue(false);
            apply(
                getCrystalPulseModule().halos(1.0F).isEmpty()
                    && !getCrystalPulseModule().styleSetting.isVisible(),
                "halo toggle hides geometry and controls");
            getCrystalPulseModule()
                .getSettings()
                .forEach(
                    (referenceArgument33_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument33_0).resetToDefault());
            Vec3d entityPos = minecraftClient17.player.getEntityPos();
            minecraftClient17.player.setPosition(entityPos.add(100.0, 0.0, 0.0));
            getCrystalPulseModule().onTick();
            apply(
                getCrystalPulseModule().computeInt() == 0
                    && getCrystalPulseModule().getList2().isEmpty(),
                "teleport clears previous effects");
            minecraftClient17.player.setPosition(entityPos);
            getCrystalPulseModule().onTick();
            minecraftClient17.options.hudHidden = false;
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient17.setScreen(panelScreen);
            apply(panelScreen.test("Crystal Pulse"), "Crystal Pulse opens in Visuals");
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.takeScreenshot("crystal-pulse-settings");
      clientGameTestContext.runOnClient(
          minecraftClient18 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient18.setScreen(null);
            getCrystalPulseModule().setEnabled(false);
            apply(
                getCrystalPulseModule().computeInt() == 0
                    && getCrystalPulseModule().getList2().isEmpty(),
                "disable clears state");
          });
      clientGameTestContext.waitTicks(2);
      int onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient19 -> {
                return CrystalPulseRenderer.getCount();
              });
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(CrystalPulseRenderer.getCount() == onClient3, "disabled module submits nothing");
          });
      ClientInitializer.logger.info(
          "Crystal Pulse verified: real explosion packet, native shaders, five palettes, three"
              + " styles, GUI, same-tick chains, false-unload prevention, duplicate/expired events,"
              + " bounds, reduced motion and cleanup");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient21 -> {
            onClient.run();
          });
    }
  }

  public static CrystalPulseModule getCrystalPulseModule() {
    return ClientInitializer.getModuleManager().crystalPulseModule;
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    getCrystalPulseModule().update4();
    getCrystalPulseModule().onTick();
    EndCrystalEntity endCrystalEntity =
        new EndCrystalEntity(minecraftClient.world, 0.5, 141.0, 0.5);
    endCrystalEntity.setId(930000);
    minecraftClient.world.addEntity(endCrystalEntity);
    Vec3d vec3d = new Vec3d(0.17, 0.23, -0.11);
    ExplosionS2CPacket explosionS2CPacket =
        new ExplosionS2CPacket(
            endCrystalEntity.getEntityPos(),
            6.0F,
            0,
            Optional.of(vec3d),
            ParticleTypes.EXPLOSION_EMITTER,
            SoundEvents.ENTITY_GENERIC_EXPLODE,
            WeightedPool.empty());
    minecraftClient.particleManager.clearParticles();
    Vec3d velocity = minecraftClient.player.getVelocity();
    minecraftClient.getNetworkHandler().onExplosion(explosionS2CPacket);
    minecraftClient.particleManager.tick();
    apply(
        Integer.parseInt(minecraftClient.particleManager.getDebugString()) == 0,
        "clean crystal packet suppresses vanilla cloud");
    apply(
        minecraftClient.player.getVelocity().subtract(velocity).distanceTo(vec3d) < 1.0E-5,
        "clean explosion preserves vanilla knockback");
    getCrystalPulseModule().cleanExplosionsSetting.setValue(false);
    minecraftClient.getNetworkHandler().onExplosion(explosionS2CPacket);
    minecraftClient.particleManager.tick();
    apply(
        Integer.parseInt(minecraftClient.particleManager.getDebugString()) > 0,
        "turning Clean Explosions off restores vanilla particles");
    minecraftClient.particleManager.clearParticles();
    getCrystalPulseModule().cleanExplosionsSetting.resetToDefault();
    ExplosionS2CPacket explosionS2CPacket2 =
        new ExplosionS2CPacket(
            new Vec3d(6.0, 141.0, 6.0),
            4.0F,
            0,
            Optional.empty(),
            ParticleTypes.EXPLOSION_EMITTER,
            SoundEvents.ENTITY_GENERIC_EXPLODE,
            WeightedPool.empty());
    minecraftClient.getNetworkHandler().onExplosion(explosionS2CPacket2);
    minecraftClient.particleManager.tick();
    apply(
        Integer.parseInt(minecraftClient.particleManager.getDebugString()) > 0,
        "non-crystal explosion particles remain visible");
    minecraftClient.particleManager.clearParticles();
    minecraftClient.player.setVelocity(velocity);
    minecraftClient.world.removeEntity(endCrystalEntity.getId(), RemovalReason.DISCARDED);
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}
