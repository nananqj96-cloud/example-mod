package bunnythekenzo.client.module;

import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class AnimationsModule extends Module {
  public final NumberSetting swingSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Swing Speed", "Swing tempo (<1 = slower/smoother)", 0.3, 0.1, 3.0, 0.1, "x"));
  public final ChoiceSetting swingStyleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Swing Style",
                  "Timing curve used during a swing",
                  "Vanilla",
                  new String[] {"Vanilla", "Smooth", "Snappy", "Heavy"}));
  public final NumberSetting swingForceSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Swing Force", "Strength of the visible swing", 1.0, 0.0, 3.0, 0.1, "x"));
  public final ChoiceSetting swingMotionSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Swing Motion",
                  "Shape of the first-person swing",
                  "Vanilla",
                  new String[] {"Vanilla", "Compact", "Rotate", "Static"}));
  public final NumberSetting swingArcSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Swing Arc",
                  "How much of the selected swing arc is used",
                  100.0,
                  0.0,
                  100.0,
                  5.0,
                  "%"));
  public final NumberSetting pitchMovementSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Pitch Movement",
                  "Peak pitch movement used by Rotate",
                  35.0,
                  -90.0,
                  90.0,
                  5.0,
                  "deg"));
  public final BooleanSetting noReEquipSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "No Re-equip", "Keep the held item raised after attacks and item updates", true));
  public final NumberSetting centerPullSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Center Pull",
                  "Pull the hand horizontally toward the screen center",
                  0.0,
                  -0.15,
                  0.38,
                  0.01));
  public final NumberSetting liftSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Lift", "Extra vertical hand position", 0.0, -0.15, 0.25, 0.01));
  public final NumberSetting depthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Depth", "Extra hand depth", 0.0, -0.25, 0.15, 0.01));
  public final NumberSetting handPitchSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Hand Pitch", "Base first-person hand pitch", 0.0, -180.0, 180.0, 5.0, "deg"));
  public final NumberSetting handYawSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Hand Yaw", "Base first-person hand yaw", 0.0, -180.0, 180.0, 5.0, "deg"));
  public final NumberSetting handRollSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Hand Roll", "Base first-person hand roll", 0.0, -180.0, 180.0, 5.0, "deg"));

  public AnimationsModule() {
    super(
        "Animations",
        "Smooth, consistent first-person hand and item motion",
        ModuleCategory.getInstance());
    this.pitchMovementSetting.setVisibilityCondition(this::test);
  }

  public float computeFloat() {
    return this.swingSpeedSetting.floatValue();
  }

  public float computeFloat(float floatValue) {
    float floatValue2 =
        computeFloat(
            (String) this.swingStyleSetting.getValue(), Math.clamp(floatValue, 0.0F, 1.0F));
    return Math.min(1.0F, floatValue2 * this.swingForceSetting.floatValue());
  }

  public float computeFloat2(float floatValue) {
    float floatValue2 =
        computeFloat(
            (String) this.swingStyleSetting.getValue(), Math.clamp(floatValue, 0.0F, 1.0F));
    float floatValue3 = (float) Math.sin(Math.PI * floatValue2);
    return this.pitchMovementSetting.floatValue()
        * this.computeFloat2()
        * this.swingForceSetting.floatValue()
        * floatValue3;
  }

  public boolean test() {
    return this.swingMotionSetting.isSelected("Rotate");
  }

  public float computeFloat2() {
    return this.swingArcSetting.floatValue() / 100.0F;
  }

  public boolean test4() {
    FakeLootDropModule.ensureInitialized();
    return this.test() || (Boolean) this.noReEquipSetting.getValue();
  }

  public static float computeFloat(String text, float floatValue) {
    FakeLootDropModule.ensureInitialized();

    return switch (text) {
      case "Smooth" -> 0.5F - 0.5F * (float) Math.cos(Math.PI * floatValue);
      case "Snappy" -> 1.0F - (1.0F - floatValue) * (1.0F - floatValue) * (1.0F - floatValue);
      case "Heavy" -> floatValue * floatValue;
      default -> floatValue;
    };
  }
}
