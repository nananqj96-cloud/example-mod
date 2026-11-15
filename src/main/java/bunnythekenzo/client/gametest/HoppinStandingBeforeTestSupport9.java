package bunnythekenzo.client.gametest;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.player.SkinTextures;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.LocalSkinProvider;
import bunnythekenzo.client.TextureImageCache;
import bunnythekenzo.client.module.SkinProtectModule;
import bunnythekenzo.client.render.FontManager;

public final class HoppinStandingBeforeTestSupport9 {
  public static Runnable enableReplacement(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    SkinProtectModule skinProtectModule = ClientInitializer.getModuleManager().skinProtectModule;
    boolean enabled = skinProtectModule.isEnabled();
    String text = (String) skinProtectModule.applyToSetting.getValue();

    try {
      Field declaredField = SkinProtectModule.class.getDeclaredField("skinTextures");
      Field declaredField2 = SkinProtectModule.class.getDeclaredField("string");
      declaredField.setAccessible(true);
      declaredField2.setAccessible(true);
      Object value2 = declaredField.get(skinProtectModule);
      Object value3 = declaredField2.get(skinProtectModule);
      skinProtectModule.setEnabled(false);
      LocalSkinProvider localSkinProvider = new LocalSkinProvider();
      SkinTextures skinTextures = localSkinProvider.getSkinTextures();
      SkinTextures skinTextures2 = skinTextures;

      for (int index = 1; index < 100 && test(skinTextures2, skinTextures); index++) {
        skinTextures2 = DefaultSkinHelper.getSkinTextures(new UUID(0L, index));
      }

      apply(!test(skinTextures2, skinTextures), "a visibly different test skin exists");
      declaredField.set(skinProtectModule, skinTextures2);
      declaredField2.set(
          skinProtectModule, ((String) skinProtectModule.skinIgnSetting.getValue()).trim());
      skinProtectModule.applyToSetting.setValue("Self");
      skinProtectModule.setEnabled(true);
      apply(
          test(localSkinProvider.getSkinTextures(), skinTextures2),
          "Self updates the profile with SkinProtect");
      if (minecraftClient.player != null) {
        apply(
            test(minecraftClient.player.getSkin(), skinTextures2),
            "world player and preview agree");
      }

      skinProtectModule.applyToSetting.setValue("Others");
      apply(test(localSkinProvider.getSkinTextures(), skinTextures), "Others keeps your own skin");
      skinProtectModule.applyToSetting.setValue("Everyone");
      apply(
          test(localSkinProvider.getSkinTextures(), skinTextures2),
          "Everyone updates your profile skin");
      skinProtectModule.setEnabled(false);
      apply(
          test(localSkinProvider.getSkinTextures(), skinTextures),
          "disabling SkinProtect restores the account skin");
      skinProtectModule.applyToSetting.setValue("Self");
      skinProtectModule.setEnabled(true);
      return () -> {
        skinProtectModule.setEnabled(false);

        try {
          declaredField.set(skinProtectModule, value2);
          declaredField2.set(skinProtectModule, value3);
        } catch (ReflectiveOperationException failure) {
          throw new AssertionError(failure);
        }

        skinProtectModule.applyToSetting.setValue(text);
        skinProtectModule.setEnabled(enabled);
      };
    } catch (ReflectiveOperationException failure2) {
      throw new AssertionError(failure2);
    }
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    SkinTextures skinTextures = new LocalSkinProvider().getSkinTextures();
    GlTexture glTexture =
        (GlTexture)
            minecraftClient
                .getTextureManager()
                .getTexture(skinTextures.body().texturePath())
                .getGlTexture();
    int value2 = TextureImageCache.computeInt(skinTextures.body().texturePath());
    int intValue = NanoVGGL3.nvglImageHandle(FontManager.getFontManager().getTimestamp(), value2);
    apply(intValue != glTexture.getGlId(), "preview owns a separate texture");

    int intValue2 = GL33C.glGetInteger(32873);
    boolean flag = false  ;

    try {
      flag = true;
      GL33C.glBindTexture(3553, intValue);

      apply(
          GL33C.glGetTexParameteri(3553, 10241) == 9728
              && GL33C.glGetTexParameteri(3553, 10240) == 9728,
          "preview uses nearest-neighbour sampling");

      apply(GL33C.glGetTexParameteri(3553, 33085) == 0, "preview cannot sample blurry mip levels");

      int intValue3 = glTexture.getWidth(0) * glTexture.getHeight(0) * 4;

      apply(
          Arrays.equals(
              getByteValueArray(intValue, intValue3),
              getByteValueArray(glTexture.getGlId(), intValue3)),
          "every source skin pixel is preserved exactly");
      flag = false;
    } finally {
      if (flag) {

        GL33C.glBindTexture(3553, intValue2);
      }
    }

    GL33C.glBindTexture(3553, intValue2);
  }

  public static byte[] getByteValueArray(int intValue, int intValue2) {
    ProtectionPerformanceTest.ensureInitialized();

    int intValue3 = GL33C.glGetInteger(35053);
    int[] intValues = new int[] {3333, 3330, 3332, 3331};
    int[] intValues2 = new int[intValues.length];
    ByteBuffer byteBuffer = MemoryUtil.memAlloc(intValue2);
    boolean flag = false  ;

    byte[] bytes;
    try {
      flag = true;

      for (int index = 0; index < intValues.length; index++) {
        intValues2[index] = GL33C.glGetInteger(intValues[index]);
        GL33C.glPixelStorei(intValues[index], index == 0 ? 1 : 0);
      }

      GL33C.glBindBuffer(35051, 0);

      GL33C.glBindTexture(3553, intValue);

      GL33C.glGetTexImage(3553, 0, 6408, 5121, byteBuffer);

      byte[] bytes2 = new byte[intValue2];

      byteBuffer.get(bytes2);
      bytes = bytes2;
      flag = false;
    } finally {
      if (flag) {

        GL33C.glBindBuffer(35051, intValue3);

        for (int index2 = 0; index2 < intValues.length; index2++) {
          GL33C.glPixelStorei(intValues[index2], intValues2[index2]);
        }

        MemoryUtil.memFree(byteBuffer);
      }
    }

    GL33C.glBindBuffer(35051, intValue3);

    for (int index3 = 0; index3 < intValues.length; index3++) {
      GL33C.glPixelStorei(intValues[index3], intValues2[index3]);
    }

    MemoryUtil.memFree(byteBuffer);
    return bytes;
  }

  public static boolean test(SkinTextures skinTextures, SkinTextures skinTextures2) {
    ProtectionPerformanceTest.ensureInitialized();
    return skinTextures.body().texturePath().equals(skinTextures2.body().texturePath())
        && skinTextures.model() == skinTextures2.model();
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Profile skin: " + text);
    }
  }
}
