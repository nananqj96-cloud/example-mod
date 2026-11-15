package bunnythekenzo.client.module;

import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.MotionBlurRenderer;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class MotionBlurModule extends Module {
  public final NumberSetting strengthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Strength", "Blur strength", 30.0, 5.0, 100.0, 5.0, "%")
                  .withFormatter(
                      doubleValue -> {
                        FakeLootDropModule.ensureInitialized();

                        int intValue = (int) Math.round(doubleValue);
                        String text =
                            intValue <= 20
                                ? "Subtle"
                                : (intValue <= 45
                                    ? "Balanced"
                                    : (intValue <= 70
                                        ? "Smooth"
                                        : (intValue <= 90 ? "Heavy" : "Cinematic")));
                        return intValue + "% · " + text;
                      }));
  public final BooleanSetting pinkTrailsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Pink Trails", "Tint the trails with your theme accent (67 look)", true));
  public final NumberSetting tintSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Tint", "How strongly trails take the theme color", 30.0, 0.0, 100.0, 5.0, "%"));
  public final BooleanSetting fpsCompensatedSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "FPS Compensated", "Keep the blur consistent across framerates", true));

  public MotionBlurModule() {
    super("MotionBlur", "Cinematic motion blur", ModuleCategory.VISUALS);
    this.tintSetting.setVisibilityCondition(this.pinkTrailsSetting::getValue);
  }

  @Override
  public void onDisable() {
    MotionBlurRenderer.update();
  }

  public double computeDouble() {
    return Math.clamp(this.strengthSetting.floatValue() / 100.0, 0.05, 0.95);
  }

  public float computeFloat() {
    FakeLootDropModule.ensureInitialized();
    return (java.lang.Boolean) (this.pinkTrailsSetting.getValue())
        ? (float) Math.clamp(this.tintSetting.floatValue() / 100.0, 0.0, 1.0)
        : 0.0F;
  }

  public int computeInt() {
    return ClientInitializer.getThemeManager().getColorPalette().getCount();
  }
}
