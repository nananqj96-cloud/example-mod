package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class CustomFOVModule extends Module {
  public static final double value = 12.0;
  public final NumberSetting fovSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("FOV", "Target field of view", 95.0, 30.0, 140.0, 1.0, "°")
                  .withFormatter(
                      doubleValue -> {
                        return (int) doubleValue + "° · " + getString((int) doubleValue);
                      }));
  public final BooleanSetting smoothSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Smooth", "Ease FOV changes in and out", true));
  public final BooleanSetting noSprintZoomSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "No Sprint Zoom", "Cancel the vanilla sprint / speed FOV punch", true));
  public final BooleanSetting speedFovSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Speed FOV", "Widen the view with your movement speed", false));
  public final NumberSetting speedStrengthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Speed Strength", "Extra degrees at full sprint", 12.0, 0.0, 30.0, 1.0, "°"));
  public double value2 = -1.0;
  public long timestamp = 0L;

  public CustomFOVModule() {
    super("CustomFOV", "Overrides the field of view", ModuleCategory.MISC);
    this.speedStrengthSetting.setVisibilityCondition(this.speedFovSetting::getValue);
  }

  public float computeFloat(float floatValue) {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    int value4 = instance.options.getFov().getValue();
    if (value4 <= 0) {
      return floatValue;
    }

    double value22 =
        this.isEnabled()
            ? (Double) this.fovSetting.getValue() + this.computeDouble(instance)
            : value4;
    long timestamp2 = System.nanoTime();
    double doubleValue = this.timestamp == 0L ? 0.0 : (timestamp2 - this.timestamp) / 1.0E9;
    this.timestamp = timestamp2;
    if (this.value2 < 0.0) {
      this.value2 = value4;
    }

    if (!(Boolean) this.smoothSetting.getValue()) {
      this.value2 = value22;
    } else {
      double doubleValue2 = 1.0 - Math.exp(-12.0 * Math.max(0.0, doubleValue));
      this.value2 = this.value2 + (value22 - this.value2) * doubleValue2;
      if (Math.abs(this.value2 - value22) < 0.05) {
        this.value2 = value22;
      }
    }

    if (!this.isEnabled() && this.value2 == value4) {
      return floatValue;
    }

    float floatValue2 = this.isEnabled() && this.noSprintZoomSetting.getValue() ? 1.0F : floatValue;
    return (float) (this.value2 / value4) * floatValue2;
  }

  public double getValue2() {
    return this.value2;
  }

  public double computeDouble(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (!(Boolean) this.speedFovSetting.getValue()) {
      return 0.0;
    }

    ClientPlayerEntity player = minecraftClient.player;
    if (player == null) {
      return 0.0;
    }

    Vec3d velocity = player.getVelocity();
    double doubleValue = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
    double doubleValue2 = Math.min(1.0, doubleValue / 0.2825);
    return this.speedStrengthSetting.floatValue() * doubleValue2;
  }

  public static String getString(int intValue) {
    FakeLootDropModule.ensureInitialized();
    if (intValue <= 45) {
      return "Tunnel";
    } else if (intValue <= 65) {
      return "Focused";
    } else if (intValue <= 80) {
      return "Normal";
    } else if (intValue <= 100) {
      return "Wide";
    } else {
      return intValue <= 118 ? "Ultra" : "Fisheye";
    }
  }
}
