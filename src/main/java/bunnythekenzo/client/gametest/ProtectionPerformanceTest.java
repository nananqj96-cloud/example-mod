package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.function.IntPredicate;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import org.slf4j.LoggerFactory;
import bunnythekenzo.client.ChunkSliceScanOperation;
import bunnythekenzo.client.ClientConfig;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.DynamicLinkageFixture;
import bunnythekenzo.client.IncrementalChunkScanner;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.LightSectionCache;
import bunnythekenzo.client.StorageScanner;
import bunnythekenzo.client.hud.HudWidget;
import bunnythekenzo.client.module.StorageESPModule;

public final class ProtectionPerformanceTest implements FabricClientGameTest {
  public static String[] stringArray;

  @Override
  public void runTest(ClientGameTestContext clientGameTestContext) {

    clientGameTestContext.runOnClient(
        minecraftClient -> {
          for (int index = 0; index < 200; index++) {
            DynamicLinkageFixture.predicate(index).test(index + 1);
            DynamicLinkageFixture.getString(index);
          }

          ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
          long totalLoadedClassCount = classLoadingMXBean.getTotalLoadedClassCount();

          for (int index2 = 0; index2 < 2000; index2++) {
            IntPredicate intPredicate = DynamicLinkageFixture.predicate(index2);
            if (!intPredicate.test(index2 + 1) || intPredicate.test(index2)) {
              throw new AssertionError("Captured lambda argument lost");
            }

            if (!DynamicLinkageFixture.getString(index2).equals("entry=" + index2)) {
              throw new AssertionError("String concat changed");
            }
          }

          long longValue = classLoadingMXBean.getTotalLoadedClassCount() - totalLoadedClassCount;
          LoggerFactory.getLogger("ProtectionPerformance")
              .info("2000 native lambda/concat calls: {} additional loaded classes", longValue);
          if (longValue > 100L) {
            throw new AssertionError("Native dynamic call sites relink repeatedly: " + longValue);
          }
        });
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ClientConfig clientConfig = ClientInitializer.getClientConfig();
          HudWidget hudWidget = (HudWidget) JsonSupport2.getList3().get(0);

          float amount7 = hudWidget.getAmount7();
          Path path = FabricLoader.getInstance().getConfigDir().resolve("bunnythekenzo.json");

          try {
            clientConfig.update3();
            String string = Files.readString(path);
            long longValue2 = System.nanoTime();

            for (int index3 = 0; index3 < 200; index3++) {
              hudWidget.apply(index3 % 2 == 0 ? 0.5F : 0.75F);
              JsonSupport2.update4();
            }

            double doubleValue = (System.nanoTime() - longValue2) / 1000000.0;
            if (!string.equals(Files.readString(path))) {
              throw new AssertionError("HUD resize wrote synchronously");
            }

            if (doubleValue > 50.0) {
              throw new AssertionError("HUD save requests stalled: " + doubleValue + " ms");
            }

            clientConfig.update3();
            JsonObject asJsonObject =
                JsonParser.parseString(Files.readString(path)).getAsJsonObject();
            float asFloat =
                asJsonObject
                    .getAsJsonObject("hud")
                    .getAsJsonObject(hudWidget.getString2())
                    .get("scale")
                    .getAsFloat();
            if (asFloat != 0.75F) {
              throw new AssertionError("Latest HUD scale was not persisted");
            }

            LoggerFactory.getLogger("ProtectionPerformance")
                .info("200 HUD resize/save requests: {} ms; final scale persisted", doubleValue);
          } catch (IOException failure) {
            throw new AssertionError(failure);
          } finally {
            hudWidget.apply(amount7);
            clientConfig.update3();
          }
        });
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          LightSectionCache instance = LightSectionCache.getInstance();
          instance.update();
          long longValue3 = System.nanoTime();

          for (int index4 = 0; index4 < 256; index4++) {
            instance.injectForTest(index4 & 15, 0, index4 >>> 4, 4);
          }

          long longValue4 = (System.nanoTime() - longValue3) / 1000000L;
          if (instance.computeInt(15, 0, 15) != 4) {
            throw new AssertionError("Light cache lost an update");
          }

          instance.update();

          LoggerFactory.getLogger("ProtectionPerformance")
              .info("256 light section scans: {} ms", longValue4);
          if (longValue4 > 2000L) {
            throw new AssertionError("Light scan stalled for " + longValue4 + " ms");
          }
        });

    try (TestSingleplayerContext testSingleplayerContext =
        clientGameTestContext.worldBuilder().create()) {
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      testSingleplayerContext.getServer().runCommand("fill 0 0 0 15 3 15 minecraft:barrel");
      testSingleplayerContext.getServer().runCommand("tp @a 8 5 8");
      clientGameTestContext.waitFor(
          minecraftClient4 -> {
            return minecraftClient4.world != null
                && minecraftClient4
                    .world
                    .getBlockState(new BlockPos(15, 3, 15))
                    .isOf(Blocks.BARREL);
          },
          200);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            IncrementalChunkScanner incrementalChunkScanner =
                new IncrementalChunkScanner(1, 80000, 20, 6000);
            int length =
                minecraftClient5
                    .world
                    .getChunk(
                        minecraftClient5.player.getChunkPos().x,
                        minecraftClient5.player.getChunkPos().z)
                    .getSectionArray()
                    .length;
            int[] intValues = new int[] {0};
            ChunkSliceScanOperation chunkSliceScanOperation =
                (worldChunk, intValue, list, intValue2) -> {
                  intValues[0]++;
                  list.add(intValue);
                  long longValue6 = System.nanoTime() + 2000000L;

                  while (System.nanoTime() < longValue6) {
                    Thread.onSpinWait();
                  }

                  return 4096;
                };
            incrementalChunkScanner.apply(0, chunkSliceScanOperation);
            if (intValues[0] == 1 && incrementalChunkScanner.getList2().isEmpty()) {
              for (int index5 = 1; index5 < length; index5++) {
                incrementalChunkScanner.apply(0, chunkSliceScanOperation);
              }

              if (intValues[0] == length
                  && incrementalChunkScanner.getList2().size() == length
                  && new HashSet(incrementalChunkScanner.getList2()).size() == length) {
                LoggerFactory.getLogger("ProtectionPerformance")
                    .info(
                        "Section budget: {} sections resumed without loss or duplication", length);
                StorageESPModule storageESPModule = new StorageESPModule();
                long longValue7 = 0L;
                int intValue3 = 0;

                for (int index6 = 0; index6 < 18; index6++) {
                  StorageScanner.update();
                  long longValue8 = System.nanoTime();
                  StorageScanner.scan(storageESPModule);
                  long longValue9 = System.nanoTime() - longValue8;
                  if (index6 >= 2) {
                    longValue7 = Math.max(longValue7, longValue9);
                  }

                  intValue3 = Math.max(intValue3, StorageScanner.computeInt());
                }

                StorageScanner.update();

                LoggerFactory.getLogger("ProtectionPerformance")
                    .info(
                        "Storage scan: {} barrels, max warm scan {} ms",
                        intValue3,
                        longValue7 / 1000000.0);
                if (intValue3 < 1024) {
                  throw new AssertionError("Storage scan missed barrels: " + intValue3);
                }

                if (longValue7 > 250000000L) {
                  throw new AssertionError(
                      "Storage scan stalled for " + longValue7 / 1000000L + " ms");
                }
              } else {
                throw new AssertionError("Resumed scan lost or duplicated sections");
              }
            } else {
              throw new AssertionError("Scan did not yield after one section");
            }
          });
    }
  }

  public static void setStringArray(String[] stringArray2) {
    stringArray = stringArray2;
  }

  public static String[] getStringArray() {
    return stringArray;
  }

  static {
    setStringArray(null);
  }

  public static void ensureInitialized() {}
}
