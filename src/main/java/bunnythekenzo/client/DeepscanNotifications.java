package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import bunnythekenzo.client.render.ChestPreviewRenderer;
import bunnythekenzo.client.render.FontManager;

public final class DeepscanNotifications {
  public static final ChestPreviewRenderer chestPreviewRenderer = new ChestPreviewRenderer();
  public static String string;
  public static String string2;
  public static long timestamp;
  public static boolean enabled;
  public static int count;
  public static final double value = 7.0;

  public static void update() {
    apply("Scanning chunks", "Scanning loaded chunks");
  }

  public static void apply(int intValue, int intValue2, int intValue3) {
    apply(
        "Chunk found",
        "X "
            + intValue
            + "   Z "
            + intValue2
            + "   ·   "
            + intValue3
            + (intValue3 == 1 ? " source" : " sources"));
  }

  public static void apply(String string3, String string22) {
    string = string3;
    string2 = string22;
    timestamp = System.nanoTime();
    count = 0;
  }

  public static void update2() {
    string = null;
  }

  public static boolean test() {
    return string != null && computeDouble() < 7.0;
  }

  public static int getCount() {
    return count;
  }

  public static double computeDouble() {
    return (System.nanoTime() - timestamp) / 1.0E9;
  }

  public static void apply(FontManager fontManager) {
    if (MinecraftClient.getInstance().world == null) {
      update2();
    } else if (test() && !enabled) {
      try {
        chestPreviewRenderer.prepare(fontManager);
      } catch (RuntimeException failure) {
        enabled = true;
        ClientInitializer.logger.error("Deepscan chest preview failed", failure);
      }
    }
  }

  public static void apply(FontManager fontManager, float floatValue) {
    if (test()) {
      double doubleValue = computeDouble();
      float floatValue2 = (float) (1.0 - Math.pow(1.0 - Math.min(1.0, doubleValue / 0.22), 3.0));
      float floatValue3 = (float) Math.min(1.0, (7.0 - doubleValue) / 0.3);
      float floatValue4 = Math.min(310.0F, floatValue - 24.0F);
      float floatValue5 =
          floatValue - floatValue4 - 12.0F + (1.0F - floatValue2) * (floatValue4 + 12.0F);
      ColorPalette instance = ColorPalette.getInstance();
      fontManager.update2();
      fontManager.apply3(floatValue3);
      fontManager.render(floatValue5, 48.0F, floatValue4, 72.0F, 8.0F, 10.0F, 3.0F, 1711276032);
      fontManager.render(
          floatValue5,
          48.0F,
          floatValue4,
          72.0F,
          8.0F,
          instance.computeInt3(),
          instance.computeInt4(),
          true);
      fontManager.render(
          floatValue5, 48.0F, floatValue4, 72.0F, 8.0F, 1.0F, instance.computeInt14());
      if (!enabled) {
        chestPreviewRenderer.render(fontManager, floatValue5 + 39.0F, 83.0F, 78.0F);
      }

      fontManager.computeFloat("DEEPSCAN", floatValue5 + 76.0F, 63.0F, 9.0F, instance.computeInt());
      fontManager.computeFloat(
          string, floatValue5 + 76.0F, 81.0F, 13.0F, instance.computeInt16(), floatValue4 - 88.0F);
      fontManager.computeFloat(
          string2,
          floatValue5 + 76.0F,
          100.0F,
          10.5F,
          instance.computeInt18(),
          floatValue4 - 88.0F);
      fontManager.render(
          floatValue5 + 10.0F,
          117.0F,
          (floatValue4 - 20.0F) * (float) (1.0 - doubleValue / 7.0),
          1.0F,
          0.0F,
          ArgbColors.withOpacity(instance.getCount(), 0.65F));
      fontManager.update3();
      count++;
    }
  }
}
