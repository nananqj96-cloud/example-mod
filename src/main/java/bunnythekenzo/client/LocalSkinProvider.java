package bunnythekenzo.client;

import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.SkinTextures;
import bunnythekenzo.client.module.ModuleManager;

public final class LocalSkinProvider {
  public final Supplier supplier =
      MinecraftClient.getInstance()
          .getSkinProvider()
          .supplySkinTextures(MinecraftClient.getInstance().getGameProfile(), false);

  public SkinTextures getSkinTextures() {
    ProfileDefaults.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null) {
      return instance.player.getSkin();
    }

    SkinTextures skinTextures = (SkinTextures) this.supplier.get();
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null && moduleManager.skinProtectModule != null
        ? moduleManager.skinProtectModule.getSkinTextures(
            instance.getSession().getUuidOrNull(), skinTextures)
        : skinTextures;
  }
}
