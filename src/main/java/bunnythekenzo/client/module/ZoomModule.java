package bunnythekenzo.client.module;

import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class ZoomModule extends Module {
  public static final double value = 14.0;
  public final NumberSetting factorSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Factor", "Zoom factor", 4.0, 2.0, 10.0, 0.5, "x"));
  public final BooleanSetting smoothSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Smooth", "Smooth zoom in/out", true));
  public double value2 = 1.0;
  public long timestamp = 0L;

  public ZoomModule() {
    super("Zoom", "Optical zoom on a key", ModuleCategory.MISC);
  }

  public double computeDouble() {
    FakeLootDropModule.ensureInitialized();

    long timestamp2 = System.nanoTime();
    double doubleValue = this.timestamp == 0L ? 0.0 : (timestamp2 - this.timestamp) / 1.0E9;
    this.timestamp = timestamp2;
    double value22 = this.isEnabled() ? this.factorSetting.floatValue() : 1.0;
    if (!(Boolean) this.smoothSetting.getValue()) {
      this.value2 = value22;
      return this.value2;
    }

    double doubleValue2 = 1.0 - Math.exp(-14.0 * Math.max(0.0, doubleValue));
    this.value2 = this.value2 + (value22 - this.value2) * doubleValue2;
    if (Math.abs(this.value2 - value22) < 0.001) {
      this.value2 = value22;
    }

    return this.value2;
  }
}
