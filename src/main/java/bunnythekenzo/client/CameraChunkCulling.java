package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import bunnythekenzo.client.module.FreeLookModule;
import bunnythekenzo.client.module.FreecamModule;

public final class CameraChunkCulling {
  public static boolean enabled;
  public static boolean enabled2;

  public static void apply(MinecraftClient minecraftClient) {
    FreecamModule instance = FreecamModule.getInstance();
    FreeLookModule instance2 = FreeLookModule.getInstance();
    boolean enabled3 =
        minecraftClient.world != null
            && minecraftClient.player != null
            && (instance != null && instance.test() || instance2 != null && instance2.test());
    if (enabled3 != enabled) {
      if (enabled3) {
        enabled2 = minecraftClient.chunkCullingEnabled;
        minecraftClient.chunkCullingEnabled = false;
      } else {
        minecraftClient.chunkCullingEnabled = enabled2;
      }

      enabled = enabled3;
      if (minecraftClient.worldRenderer != null) {
        minecraftClient.worldRenderer.scheduleTerrainUpdate();
      }
    }
  }
}
