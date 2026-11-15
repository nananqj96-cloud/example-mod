package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import bunnythekenzo.client.BlockPosData3;
import bunnythekenzo.client.BlockPosData4;
import bunnythekenzo.client.BlockPosData5;
import bunnythekenzo.client.BlockPosData6;
import bunnythekenzo.client.BlockPosData7;
import bunnythekenzo.client.ChunkClusterCalculator;
import bunnythekenzo.client.ChunkLightCandidateScanner;
import bunnythekenzo.client.ChunkRouteTracker;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.DeepscanNotifications;
import bunnythekenzo.client.DeepscanWorker;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.LightSectionCache;
import bunnythekenzo.client.SuspiciousChunkScanner;
import bunnythekenzo.client.WorldSignalTypeData2;
import bunnythekenzo.client.module.DeepscanChunkFinderModule;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.module.SusChunkFinderModule;

public final class DeepscanToastStartTest {
  public static final BlockPos blockPos = new BlockPos(8, 20, 8);
  public static final BlockPos blockPos2 = blockPos.up();

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ProtectionPerformanceTest.ensureInitialized();

            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            DeepscanChunkFinderModule deepscanChunkFinderModule =
                ClientInitializer.getModuleManager().deepscanChunkFinderModule;
            ClientInitializer.getModuleManager()
                .susChunkFinderModule
                .getSettings()
                .forEach(
                    (referenceArgument34_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument34_0).resetToDefault());
            ClientInitializer.getModuleManager()
                .susChunkFinderModule
                .suspiciousChunkScanner
                .update();
            deepscanChunkFinderModule
                .getSettings()
                .forEach(
                    (referenceArgument35_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument35_0).resetToDefault());
            apply(true, "30x50 defaults");
            apply(
                deepscanChunkFinderModule.getModuleCategory() == ModuleCategory.DONUTSMP,
                "DonutSMP category");
            apply(
                JsonSupport2.getList().stream()
                    .anyMatch(
                        collectionData3 -> {
                          return ((bunnythekenzo.client.CollectionData3) (collectionData3))
                              .string()
                              .equals("DonutSMP");
                        }),
                "GUI category");
            pureChecks();
            apply(deepscanChunkFinderModule.getSettings().isEmpty(), "Deepscan exposes no options");
            apply(true, "fixed render height Y63");
            minecraftClient2.options.getMaxFps().setValue(60);
            minecraftClient2.setScreen(null);
          });
      testSingleplayerContext.getServer().runCommand("gamerule minecraft:random_tick_speed 0");
      testSingleplayerContext.getServer().runCommand("gamemode spectator @a");
      testSingleplayerContext.getServer().runCommand("tp @a 8 26 8");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ServerWorld overworld = minecraftServer.getOverworld();
                ProtectionPerformanceTest.ensureInitialized();

                for (int index = 4; index < 13; index++) {
                  for (int index2 = 4; index2 < 13; index2++) {
                    for (int index3 = 19; index3 < 25; index3++) {
                      overworld.setBlockState(
                          new BlockPos(index, index3, index2), Blocks.AIR.getDefaultState());
                    }
                  }
                }

                overworld.setBlockState(blockPos, Blocks.BUDDING_AMETHYST.getDefaultState());
                overworld.setBlockState(
                    blockPos2,
                    Blocks.SMALL_AMETHYST_BUD
                        .getDefaultState()
                        .with(Properties.FACING, Direction.UP));
                overworld.setBlockState(
                    new BlockPos(12, 21, 12), Blocks.AMETHYST_CLUSTER.getDefaultState());
              });
      clientGameTestContext.waitFor(
          minecraftClient3 -> {
            return minecraftClient3.world.getBlockState(blockPos2).isOf(Blocks.SMALL_AMETHYST_BUD);
          },
          400);
      clientGameTestContext.runOnClient(DeepscanToastStartTest::queueChecks);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ClientInitializer.getModuleManager().deepscanChunkFinderModule.setEnabled(true);
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                DeepscanNotifications.test() && DeepscanNotifications.getCount() > 0,
                "toast renders while HUD module is disabled");
          });
      clientGameTestContext.takeScreenshot("deepscan-toast-start");
      clientGameTestContext.waitFor(
          minecraftClient6 -> {
            return ClientInitializer.getModuleManager()
                .deepscanChunkFinderModule
                .deepscanWorker
                .getBlockPosData7()
                .list()
                .stream()
                .anyMatch(
                    value3 -> {
                      ProtectionPerformanceTest.ensureInitialized();
                      return Math.abs(((bunnythekenzo.client.BlockPosData4) (value3)).value() - 8.5)
                              < 1.0
                          && Math.abs(((bunnythekenzo.client.BlockPosData4) (value3)).value3() - 8.5)
                              < 1.0;
                    });
          },
          600);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();

            DeepscanWorker deepscanWorker =
                ClientInitializer.getModuleManager().deepscanChunkFinderModule.deepscanWorker;
            apply(
                deepscanWorker.getBlockPosData7().count() == 0,
                "initial crystals are baseline, not growth");
            apply(
                deepscanWorker.getBlockPosData6()._X() * deepscanWorker.getBlockPosData6()._u()
                    == 1500,
                "exact scan area");
            BlockPosData4 blockPosData4 =
                (bunnythekenzo.client.BlockPosData4)
                    (deepscanWorker.getBlockPosData7().list().stream()
                        .filter(
                            value5 -> {
                              ProtectionPerformanceTest.ensureInitialized();
                              return Math.abs(
                                          ((bunnythekenzo.client.BlockPosData4) (value5)).value() - 8.5)
                                      < 1.0
                                  && Math.abs(
                                          ((bunnythekenzo.client.BlockPosData4) (value5)).value3() - 8.5)
                                      < 1.0;
                            })
                        .findFirst()
                        .orElseThrow());
            apply(blockPosData4.count() == 1, "unsupported cluster rejected");
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                ProtectionPerformanceTest.ensureInitialized();

                minecraftServer2
                    .getOverworld()
                    .setBlockState(
                        blockPos2,
                        Blocks.AMETHYST_CLUSTER
                            .getDefaultState()
                            .with(Properties.FACING, Direction.UP));

                for (int intValue : new int[] {10, 12}) {
                  minecraftServer2
                      .getOverworld()
                      .setBlockState(
                          new BlockPos(intValue, 20, 12), Blocks.AMETHYST_BLOCK.getDefaultState());
                  minecraftServer2
                      .getOverworld()
                      .setBlockState(
                          new BlockPos(intValue, 21, 12),
                          Blocks.AMETHYST_CLUSTER
                              .getDefaultState()
                              .with(Properties.FACING, Direction.UP));
                }

                for (int intValue2 : new int[] {72, 74, 76}) {
                  minecraftServer2
                      .getOverworld()
                      .setBlockState(
                          new BlockPos(intValue2, 20, 12), Blocks.AMETHYST_BLOCK.getDefaultState());
                  minecraftServer2
                      .getOverworld()
                      .setBlockState(
                          new BlockPos(intValue2, 21, 12),
                          Blocks.AMETHYST_CLUSTER
                              .getDefaultState()
                              .with(Properties.FACING, Direction.UP));
                }
              });
      clientGameTestContext.waitFor(
          minecraftClient8 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return ClientInitializer.getModuleManager()
                    .deepscanChunkFinderModule
                    .deepscanWorker
                    .getBlockPosData7()
                    .count()
                >= 3;
          },
          600);
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            LightSectionCache.getInstance().injectForTest(8, 21, 8, 5);
            LightSectionCache.getInstance().injectForTest(10, 21, 12, 5);
            ProtectionPerformanceTest.ensureInitialized();

            LightSectionCache.getInstance().injectForTest(12, 21, 12, 5);

            for (int intValue3 : new int[] {72, 74, 76}) {
              LightSectionCache.getInstance().injectForTest(intValue3, 21, 12, 5);
            }
          });
      clientGameTestContext.waitFor(
          minecraftClient10 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return ClientInitializer.getModuleManager()
                    .deepscanChunkFinderModule
                    .deepscanWorker
                    .getList()
                    .size()
                == 1;
          },
          600);
      testSingleplayerContext.getServer().runCommand("tp @a 8 110 8");
      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            minecraftClient11.player.setPitch(90.0F);
            minecraftClient11.inGameHud.getChatHud().clear(true);
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.takeScreenshot("deepscan-growth-area");
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            ProtectionPerformanceTest.ensureInitialized();

            DeepscanWorker deepscanWorker2 =
                ClientInitializer.getModuleManager().deepscanChunkFinderModule.deepscanWorker;
            apply(deepscanWorker2.getList().size() == 1, "exactly one base candidate");
            ChunkPos chunkPos = (ChunkPos) deepscanWorker2.getList().getFirst();
            apply(
                ClientInitializer.getModuleManager()
                    .susChunkFinderModule
                    .suspiciousChunkScanner
                    .getList()
                    .stream()
                    .noneMatch(
                        (java.util.function.Predicate<bunnythekenzo.client.WorldSignalTypeData3>)
                            (worldSignalTypeData3 -> {
                              ProtectionPerformanceTest.ensureInitialized();
                              return Math.abs(
                                          ChunkPos.getPackedX(worldSignalTypeData3.timestamp())
                                              - chunkPos.x)
                                      <= 1
                                  && Math.abs(
                                          ChunkPos.getPackedZ(worldSignalTypeData3.timestamp())
                                              - chunkPos.z)
                                      <= 1;
                            })),
                "target never touches a Sus chunk, including diagonals");
            apply(
                deepscanWorker2.getBlockPosData7().list2().stream()
                    .anyMatch(
                        value9 -> {
                          ProtectionPerformanceTest.ensureInitialized();
                          return ((bunnythekenzo.client.BlockPosData3) value9).timestamp()
                              == chunkPos.toLong();
                        }),
                "rendered chunk comes from candidate calculation");
            deepscanWorker2.apply(chunkPos.toLong());
            apply(
                deepscanWorker2.getList().equals(List.of(chunkPos)),
                "target persists when its chunk unloads");
            minecraftClient12.player.setPosition(24.5, 110.0, 8.5);
            deepscanWorker2.tick();
            apply(
                deepscanWorker2.getList().equals(List.of(chunkPos)),
                "nearby player movement cannot move target");
          });
      testSingleplayerContext.getServer().runCommand("tp @a 24 110 8");
      clientGameTestContext.waitTicks(25);
      clientGameTestContext.takeScreenshot("deepscan-world-anchored-after-movement");
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            apply(
                ClientInitializer.getModuleManager()
                    .deepscanChunkFinderModule
                    .deepscanWorker
                    .getBlockPosData7()
                    .list()
                    .stream()
                    .anyMatch(
                        value10 -> {
                          return ((bunnythekenzo.client.BlockPosData4) (value10))
                              .set()
                              .contains(ChunkPos.toLong(0, 0));
                        }),
                "source remains in its world chunk after player movement");
          });
      testSingleplayerContext.getServer().runCommand("tp @a 8 110 8");
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();

            DeepscanChunkFinderModule deepscanChunkFinderModule2 =
                ClientInitializer.getModuleManager().deepscanChunkFinderModule;
            JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
            deepscanChunkFinderModule2.setEnabled(false);
            ClientInitializer.getClientConfig().apply(jsonObject);
            apply(
                deepscanChunkFinderModule2.isEnabled()
                    && deepscanChunkFinderModule2.getSettings().isEmpty(),
                "toggle round trip without options");
            deepscanChunkFinderModule2.deepscanWorker.apply(ChunkPos.toLong(0, 0));
            apply(
                deepscanChunkFinderModule2.deepscanWorker.getList().isEmpty(),
                "toggle resets target before fresh analysis");
          });
      clientGameTestContext.waitFor(
          minecraftClient15 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return !ClientInitializer.getModuleManager()
                .deepscanChunkFinderModule
                .deepscanWorker
                .getBlockPosData7()
                .list()
                .isEmpty();
          },
          600);
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            ProtectionPerformanceTest.ensureInitialized();

            DeepscanChunkFinderModule deepscanChunkFinderModule3 =
                ClientInitializer.getModuleManager().deepscanChunkFinderModule;
            apply(
                deepscanChunkFinderModule3.deepscanWorker.getBlockPosData7().count() == 0,
                "reloaded chunk starts new baseline");
            deepscanChunkFinderModule3.setEnabled(false);
            apply(
                deepscanChunkFinderModule3.deepscanWorker.getBlockPosData7().list().isEmpty()
                    && deepscanChunkFinderModule3.deepscanWorker.computeInt() == 0,
                "disable clears state");
            minecraftClient16.world.setBlockState(blockPos2, Blocks.STONE.getDefaultState(), 3);

            for (Direction direction : Direction.values()) {
              BlockPos blockPos3 = blockPos2.offset(direction);
              LightSectionCache.getInstance()
                  .injectForTest(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ(), 4);
            }

            LightSectionCache.getInstance().injectForTest(8, 21, 8, 5);
            deepscanChunkFinderModule3.setEnabled(true);
          });
      clientGameTestContext.waitFor(
          minecraftClient17 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return ClientInitializer.getModuleManager()
                    .deepscanChunkFinderModule
                    .deepscanWorker
                    .getBlockPosData7()
                    .count2()
                > 0;
          },
          600);
      clientGameTestContext.runOnClient(
          minecraftClient18 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager()
                        .deepscanChunkFinderModule
                        .deepscanWorker
                        .getBlockPosData7()
                        .count()
                    == 0,
                "light never creates growth history");
            ClientInitializer.getModuleManager().deepscanChunkFinderModule.setEnabled(false);
            minecraftClient18.world.setBlockState(
                blockPos2.offset(Direction.EAST), Blocks.TORCH.getDefaultState(), 3);
            ClientInitializer.getModuleManager().deepscanChunkFinderModule.setEnabled(true);
          });
      clientGameTestContext.waitTicks(100);
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            ProtectionPerformanceTest.ensureInitialized();

            ClientInitializer.logger.info(
                "[deepscan-test] foreign-light result: {}",
                ClientInitializer.getModuleManager()
                    .deepscanChunkFinderModule
                    .deepscanWorker
                    .getBlockPosData7());
            apply(
                ClientInitializer.getModuleManager()
                        .deepscanChunkFinderModule
                        .deepscanWorker
                        .getBlockPosData7()
                        .count2()
                    == 0,
                "adjacent foreign emitter rejected");
            ClientInitializer.logger.info(
                "[deepscan-test] PASS: defaults, GUI, config, baseline, growth, unload, light and"
                    + " foreign emitter");
          });
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            SusChunkFinderModule susChunkFinderModule =
                ClientInitializer.getModuleManager().susChunkFinderModule;
            DeepscanChunkFinderModule deepscanChunkFinderModule4 =
                ClientInitializer.getModuleManager().deepscanChunkFinderModule;
            ProtectionPerformanceTest.ensureInitialized();

            deepscanChunkFinderModule4.setEnabled(false);
            susChunkFinderModule.suspiciousChunkScanner.update();
            susChunkFinderModule.sensitivitySetting.setNumber(Double.valueOf(1.0));
            minecraftClient20.world.setBlockState(
                blockPos2.offset(Direction.EAST), Blocks.AIR.getDefaultState(), 3);
            BlockPos blockPos4 = new BlockPos(6, 24, 6);
            minecraftClient20.world.setBlockState(blockPos4, Blocks.STONE.getDefaultState(), 3);

            for (Direction direction2 : Direction.values()) {
              BlockPos blockPos5 = blockPos4.offset(direction2);
              LightSectionCache.getInstance()
                  .injectForTest(blockPos5.getX(), blockPos5.getY(), blockPos5.getZ(), 4);
            }

            LightSectionCache.getInstance().injectForTest(6, 24, 6, 5);
            apply(
                ChunkLightCandidateScanner.getList(
                        minecraftClient20, minecraftClient20.world.getChunk(0, 0))
                    .contains(blockPos4),
                "Sus accepts masked peak without adjacent visible anchor");
            deepscanChunkFinderModule4.setEnabled(true);
          });
      clientGameTestContext.waitFor(
          minecraftClient21 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return ClientInitializer.getModuleManager()
                    .deepscanChunkFinderModule
                    .deepscanWorker
                    .getList()
                    .size()
                == 1;
          },
          600);
      clientGameTestContext.runOnClient(
          minecraftClient22 -> {
            DeepscanChunkFinderModule deepscanChunkFinderModule5 =
                ClientInitializer.getModuleManager().deepscanChunkFinderModule;
            apply(
                ClientInitializer.getModuleManager()
                    .susChunkFinderModule
                    .suspiciousChunkScanner
                    .getList()
                    .stream()
                    .anyMatch(
                        value15 -> {
                          ProtectionPerformanceTest.ensureInitialized();
                          return ((bunnythekenzo.client.WorldSignalTypeData3) value15).timestamp()
                              == ChunkPos.toLong(0, 0);
                        }),
                "shared masked-light detector flags while Sus module is disabled");
            deepscanChunkFinderModule5.setEnabled(false);
            SusChunkFinderModule susChunkFinderModule2 =
                ClientInitializer.getModuleManager().susChunkFinderModule;
            susChunkFinderModule2.renderYSetting.setNumber(Double.valueOf(63.0));
            susChunkFinderModule2.smartModeSetting.setValue(false);
            susChunkFinderModule2.centroidMarkerSetting.setValue(false);
            susChunkFinderModule2.setEnabled(true);
            ClientInitializer.getThemeManager().test("Blue");
            minecraftClient22.player.setPitch(90.0F);
            minecraftClient22.inGameHud.getChatHud().clear(true);
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.takeScreenshot("sus-chunk-settings-blue");
      clientGameTestContext.runOnClient(
          minecraftClient23 -> {
            ClientInitializer.getThemeManager().test("Purple");
          });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.takeScreenshot("sus-chunk-settings-purple");
      clientGameTestContext.runOnClient(
          minecraftClient24 -> {
            ClientInitializer.getModuleManager().susChunkFinderModule.setEnabled(false);
          });
      FreecamLoadedTerrainTest.run(clientGameTestContext, testSingleplayerContext);
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient25 -> {
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void queueChecks(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    SuspiciousChunkScanner suspiciousChunkScanner =
        ClientInitializer.getModuleManager().susChunkFinderModule.suspiciousChunkScanner;

    try {
      Field field =
          Arrays.stream(suspiciousChunkScanner.getClass().getDeclaredFields())
              .filter(
                  field2 -> {
                    return Deque.class.isAssignableFrom(field2.getType());
                  })
              .findFirst()
              .orElseThrow();
      field.setAccessible(true);
      Deque deque = (Deque) field.get(suspiciousChunkScanner);
      Method method =
          Arrays.stream(suspiciousChunkScanner.getClass().getDeclaredMethods())
              .filter(
                  method2 -> {
                    ProtectionPerformanceTest.ensureInitialized();
                    return Modifier.isPrivate(method2.getModifiers())
                        && method2.getReturnType() == void.class
                        && Arrays.equals(
                            method2.getParameterTypes(), new Class[] {ChunkPos.class, int.class});
                  })
              .findFirst()
              .orElseThrow();
      method.setAccessible(true);
      suspiciousChunkScanner.update();
      method.invoke(suspiciousChunkScanner, new ChunkPos(0, 0), 8);
      long longValue = ChunkPos.toLong(8, 0);
      long longValue2 = ChunkPos.toLong(-8, 0);
      deque.clear();
      deque.add(longValue);
      deque.add(longValue2);
      method.invoke(suspiciousChunkScanner, new ChunkPos(3, 0), 8);
      apply(
          (Long) deque.peekFirst() == longValue,
          "movement preserves pending outer-ring scan priority");
      apply(!deque.contains(longValue2), "movement removes out-of-range work");
      apply(deque.size() == new HashSet(deque).size(), "movement queues no duplicate chunks");
      apply(deque.size() == 289, "movement retains full scan coverage");
      ClientInitializer.logger.info(
          "[deepscan-test] PASS: scan queue movement, coverage and deduplication");
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError("scan queue regression", failure);
    } finally {
      suspiciousChunkScanner.update();
    }
  }

  public static void pureChecks() {
    ChunkRouteTracker chunkRouteTracker = new ChunkRouteTracker();
    ProtectionPerformanceTest.ensureInitialized();

    long timestamp = ChunkPos.toLong(0, 0);
    long timestamp2 = ChunkPos.toLong(24, 0);
    var list = List.of(new BlockPosData3(timestamp, 20.0, 2));
    var list2 = List.of(new BlockPosData3(timestamp2, 100.0, 3));
    apply(!chunkRouteTracker.test(list, new ChunkPos(0, 0)), "one analysis cannot flag");
    apply(!chunkRouteTracker.test(list, new ChunkPos(0, 0)), "two analyses cannot flag");
    apply(
        chunkRouteTracker.test(list, new ChunkPos(0, 0)),
        "three confirmations select first candidate");
    apply(!chunkRouteTracker.test(list2, new ChunkPos(23, 0)), "23 chunks cannot replace target");
    apply(
        chunkRouteTracker.getList().equals(List.of(new ChunkPos(timestamp))),
        "better score cannot bypass distance lock");
    apply(
        !chunkRouteTracker.test(List.of(), new ChunkPos(24, 0)),
        "no replacement without new evidence");
    apply(chunkRouteTracker.getList().size() == 1, "missing scan never blanks selected target");
    apply(!chunkRouteTracker.test(list2, new ChunkPos(24, 0)), "replacement needs confirmation");
    apply(
        !chunkRouteTracker.test(list2, new ChunkPos(24, 0)),
        "replacement needs third confirmation");
    apply(
        chunkRouteTracker.test(list2, new ChunkPos(24, 0)),
        "24 chunks permits a confirmed nearby candidate");
    apply(
        chunkRouteTracker.getList().equals(List.of(new ChunkPos(timestamp2))),
        "exactly one replacement target");
    apply(
        !chunkRouteTracker.test(list, new ChunkPos(48, 0)),
        "old distant evidence cannot drag marker along");
    apply(
        chunkRouteTracker.test(Set.of(timestamp2)),
        "newly discovered Sus neighbour clears locked target");
    apply(chunkRouteTracker.getList().isEmpty(), "exclusion overrides 24-chunk retention");
    chunkRouteTracker.test(list, new ChunkPos(0, 0));
    chunkRouteTracker.test(List.of(), new ChunkPos(0, 0));
    apply(
        !chunkRouteTracker.test(list, new ChunkPos(0, 0)),
        "lost evidence resets confirmation streak");
    chunkRouteTracker.update();
    apply(chunkRouteTracker.getList().isEmpty(), "world/disable reset clears target");
    BlockPosData6 blockPosData6 = new BlockPosData6(-15, -25, 30, 50);
    apply(
        blockPosData6.test(-15, -25) && blockPosData6.test(14, 24) && !blockPosData6.test(15, 25),
        "negative area edges");
    apply(DeepscanWorker.computeInt(null, 4, 0) == 0, "no fabricated age");
    apply(DeepscanWorker.computeInt(1, 4, 0) == 3, "stage jump");
    apply(DeepscanWorker.computeInt(4, 0, 3) == 3, "harvest does not add growth");
    apply(DeepscanWorker.computeInt(0, 4, 3) == 4, "bounded growth evidence");
    apply(
        DeepscanWorker.computeInt(
                Blocks.SMALL_AMETHYST_BUD.getDefaultState().with(Properties.FACING, Direction.DOWN),
                Direction.UP)
            == -1,
        "wrong support face");
    BlockPosData5 blockPosData5 = new BlockPosData5(BlockPos.asLong(0, 20, 0), 4, 0, false);
    BlockPosData5 blockPosData52 = new BlockPosData5(BlockPos.asLong(64, 20, 0), 4, 3, false);
    BlockPosData7 blockPosData7 =
        ChunkClusterCalculator.calculate(
            List.of(blockPosData5, blockPosData5, blockPosData52), blockPosData6, 1.0, 128, 0);
    apply(
        blockPosData7.list().size() == 2
            && blockPosData7.list().stream()
                    .mapToInt(
                        (referenceArgument36_0) ->
                            ((bunnythekenzo.client.BlockPosData4) referenceArgument36_0).count())
                    .sum()
                == 2,
        "grouping and deduplication");
    apply(
        blockPosData7.list2().stream()
            .anyMatch(
                value2 -> {
                  ProtectionPerformanceTest.ensureInitialized();
                  return ((bunnythekenzo.client.BlockPosData3) (value2)).count() == 2;
                }),
        "overlapping plausible regions");
    apply(blockPosData7.list2().size() > 1, "no invented exact player position");
    WorldSignalTypeData2 worldSignalTypeData2 =
        new WorldSignalTypeData2(Set.of(ChunkPos.toLong(-4, 0)), -56.0, 8.0, 18.0);
    WorldSignalTypeData2 worldSignalTypeData22 =
        new WorldSignalTypeData2(Set.of(ChunkPos.toLong(4, 0)), 72.0, 8.0, 18.0);
    BlockPosData7 blockPosData72 =
        ChunkClusterCalculator.rankBaseAreas(
            BlockPosData7.blockPosData7,
            List.of(worldSignalTypeData2, worldSignalTypeData22),
            blockPosData6,
            128);
    apply(
        ((BlockPosData3) blockPosData72.list2().getFirst()).timestamp() == ChunkPos.toLong(0, 0),
        "symmetric independent evidence ranks overlap center first");
    apply(
        ((BlockPosData3) blockPosData72.list2().getFirst()).count() == 2,
        "one vote per independent source group");
    apply(blockPosData72.list2().size() > 1, "base estimate preserves uncertainty");
    BlockPosData7 blockPosData73 =
        ChunkClusterCalculator.rankBaseAreas(
            BlockPosData7.blockPosData7, List.of(worldSignalTypeData2), blockPosData6, 128);
    apply(blockPosData73.list2().isEmpty(), "single source cannot flag any base candidate");
    apply(
        ChunkClusterCalculator.rankBaseAreas(
                BlockPosData7.blockPosData7,
                List.of(worldSignalTypeData2, worldSignalTypeData2),
                blockPosData6,
                128)
            .list2()
            .isEmpty(),
        "duplicate evidence is not independent");
    WorldSignalTypeData2 worldSignalTypeData23 =
        new WorldSignalTypeData2(Set.of(ChunkPos.toLong(-3, 0)), -40.0, 8.0, 18.0);
    apply(
        ChunkClusterCalculator.rankBaseAreas(
                BlockPosData7.blockPosData7,
                List.of(worldSignalTypeData2, worldSignalTypeData23),
                blockPosData6,
                128)
            .list2()
            .isEmpty(),
        "neighbouring signals cannot inflate support");
    Set set = ChunkClusterCalculator.getSet(List.of(worldSignalTypeData2, worldSignalTypeData22));
    apply(
        blockPosData72.list2().stream()
            .noneMatch(
                value3 -> {
                  return set.contains(((bunnythekenzo.client.BlockPosData3) (value3)).timestamp());
                }),
        "all candidate cells avoid source neighbourhoods");
    apply(
        set.contains(ChunkPos.toLong(-5, -1)) && set.contains(ChunkPos.toLong(-3, 1)),
        "negative and diagonal neighbours excluded");
    BlockPosData7 blockPosData74 =
        ChunkClusterCalculator.calculate(
            List.of(blockPosData5, new BlockPosData5(BlockPos.asLong(0, -30, 0), 4, 0, false)),
            blockPosData6,
            1.0,
            128,
            0);
    apply(blockPosData74.list().size() == 2, "3D geode separation");
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}
