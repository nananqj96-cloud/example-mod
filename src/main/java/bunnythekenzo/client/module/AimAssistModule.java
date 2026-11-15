package bunnythekenzo.client.module;

import bunnythekenzo.client.AimAssistEngine;
import bunnythekenzo.client.EntityAimAssistEngine;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class AimAssistModule extends Module {
  public final AimAssistEngine aimAssistEngine = new EntityAimAssistEngine();
  public final NumberSetting rangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Range", "Assist any target within this distance", 4.0, 1.0, 8.0, 0.5, "m"));
  public final NumberSetting speedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Speed", "How fast the aim is pulled (higher = snappier)", 4.0, 1.0, 10.0, 1.0));
  public final NumberSetting smoothnessSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Smoothness",
                  "How eased / human the pull is (higher = smoother)",
                  6.0,
                  1.0,
                  10.0,
                  1.0));
  public final ChoiceSetting targetSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Target",
                  "Which part of the target to aim at",
                  "Body",
                  new String[] {"Head", "Body", "Feet", "Nearest"}));
  public final NumberSetting fovSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "FOV",
                  "Only assist within this facing cone (180 = all around)",
                  180.0,
                  10.0,
                  180.0,
                  5.0,
                  "°"));
  public final BooleanSetting verticalSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Vertical", "Also correct pitch (up/down), not just yaw", true));
  public final BooleanSetting playersSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Players", "Target other players", true));
  public final BooleanSetting hostilesSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Hostiles", "Target hostile mobs", false));
  public final BooleanSetting passiveSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Passive", "Target passive mobs", false));
  public final BooleanSetting invisiblesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Invisibles", "Also target invisible entities", false));
  public final BooleanSetting wallCheckSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Wall Check", "Only assist targets you can actually see", false));
  public final BooleanSetting alwaysActiveSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Always Active",
                  "Assist all the time — off = only while attacking (holding left-click)",
                  true));
  public final BooleanSetting stickyTargetSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Sticky Target", "Keep one target until it leaves range", true));
  public static String[] stringArray;

  public AimAssistModule() {
    super(
        "AimAssist",
        "Legit aim assist — smoothly pulls toward targets in range",
        ModuleCategory.COMBAT);
  }

  @Override
  public void onDisable() {
    this.aimAssistEngine.reset();
  }

  public double[] getValueArray(double doubleValue, double doubleValue2, double doubleValue3) {
    FreecamModule instance = FreecamModule.getInstance();
    FakeLootDropModule.ensureInitialized();

    FreeLookModule instance2 = FreeLookModule.getInstance();
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (this.isEnabled()
        && !PearlEscapeAutomation.test()
        && (instance == null || !instance.test())
        && (instance2 == null || !instance2.test5())
        && (fakeLootDropModule == null || !fakeLootDropModule.equipmentPreview.test3())
        && Double.isFinite(doubleValue)
        && !(doubleValue <= 0.0)
        && Double.isFinite(doubleValue2)
        && Double.isFinite(doubleValue3)) {
      return this.aimAssistEngine.computeCorrection(this, doubleValue, doubleValue2, doubleValue3);
    }

    this.aimAssistEngine.reset();
    return null;
  }

  public static void setStringArray(String[] stringArray2) {
    stringArray = stringArray2;
  }

  public static String[] getStringArray() {
    return stringArray;
  }

  static {
    String[] textArray = new String[3];

    setStringArray(textArray);
  }
}
