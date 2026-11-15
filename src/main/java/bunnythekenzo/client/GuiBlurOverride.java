package bunnythekenzo.client;

public final class GuiBlurOverride {
  public static volatile float blurRadius = -1.0F;

  public static void setBlurRadius(float amount2) {
    blurRadius = amount2;
  }

  public static void clear() {
    blurRadius = -1.0F;
  }

  public static int applyOverride(int intValue) {
    float floatValue = blurRadius;
    return floatValue < 0.0F ? intValue : Math.round(Math.clamp(floatValue, 0.0F, 10.0F));
  }

  public static boolean isActive() {
    return blurRadius >= 0.0F;
  }
}
