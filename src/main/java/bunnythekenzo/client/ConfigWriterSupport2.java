package bunnythekenzo.client;

import net.fabricmc.loader.api.FabricLoader;

public final class ConfigWriterSupport2 {
  public static Object getObject(String text) {
    return FabricLoader.getInstance().getConfigDir().resolve(text);
  }
}
