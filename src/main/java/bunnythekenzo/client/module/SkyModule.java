package bunnythekenzo.client.module;

import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.SkyParticleStyle;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class SkyModule extends Module {
  public final BooleanSetting skySetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Sky", "Replaces the sky with the client's own", true));
  public final NumberSetting glowSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Glow", "How strongly the horizon burns", 85.0, 0.0, 100.0, 5.0, "%"));
  public final NumberSetting depthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Depth", "How dark the sky gets overhead", 80.0, 0.0, 100.0, 5.0, "%"));
  public final BooleanSetting horizonGlowSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Horizon Glow", "A wash of light that drifts round with the day", true));
  public final BooleanSetting starsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Stars", "Draws the client's own star field", true));
  public final NumberSetting starCountSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Star Count", "How many stars the field holds", 1200.0, 200.0, 2500.0, 100.0));
  public final BooleanSetting twinkleSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Twinkle", "Lets every star breathe on its own clock", true));
  public final NumberSetting fogTintSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Fog Tint",
                  "Pulls distance fog toward the horizon colour",
                  70.0,
                  0.0,
                  100.0,
                  5.0,
                  "%"));
  public final BooleanSetting particlesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Particles", "Floats motes through the air around you", true));
  public final ChoiceSetting shapeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Shape",
                  "What the motes are, and how they move",
                  "Motes",
                  new String[] {"Motes", "Sparks", "Petals"}));
  public final ChoiceSetting blendSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Blend",
                  "Glow adds light to the scene; Soft lays over it",
                  "Glow",
                  new String[] {"Glow", "Soft"}));
  public final NumberSetting countSetting;
  public final NumberSetting radiusSetting;
  public final NumberSetting sizeSetting;
  public final NumberSetting speedSetting;
  public final NumberSetting opacitySetting;
  public final BooleanSetting milkyWaySetting;
  public final BooleanSetting nebulaeSetting;
  public final BooleanSetting shootingStarsSetting;
  public final NumberSetting meteorRateSetting;
  public final BooleanSetting rocketSetting;
  public final BooleanSetting planetSetting;
  public final BooleanSetting moonSetting;
  public final BooleanSetting iceWorldSetting;
  public final BooleanSetting oceanWorldSetting;
  public final BooleanSetting volcanicWorldSetting;
  public final BooleanSetting supernovaSetting;
  public final NumberSetting motionSetting;
  public final ColorSetting colourSetting;
  public final BooleanSetting followThemeSetting;
  public static int[] countArray;

  public SkyModule() {
    super("Sky", "Replaces the sky and fills the air with light", ModuleCategory.VISUALS);
    FakeLootDropModule.ensureInitialized();

    this.countSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Count", "How many motes are in the air at once", 1000.0, 50.0, 2000.0, 50.0));
    this.radiusSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Radius", "How far out the motes reach", 12.0, 4.0, 32.0, 1.0, " m"));
    this.sizeSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Size", "How large one mote is, in blocks", 0.14, 0.02, 0.4, 0.01));
    this.speedSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Speed", "How fast they drift and sway", 100.0, 0.0, 200.0, 5.0, "%"));
    this.opacitySetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Opacity", "How brightly the motes read", 95.0, 0.0, 100.0, 5.0, "%"));
    this.milkyWaySetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting("Milky Way", "A galactic band, turning with the stars", true));
    this.nebulaeSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting("Nebulae", "Slow clouds of light along the band", true));
    this.shootingStarsSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting("Shooting Stars", "Meteors, now and then", true));
    this.meteorRateSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Meteor Rate", "How often one crosses the sky", 45.0, 0.0, 100.0, 5.0, "%"));
    this.rocketSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Rocket",
                    "A detailed orbiter with three engines, moving controls and a pilot",
                    true));
    this.planetSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Planet", "A gas giant with layered rings, storms and an orbiting moon", true));
    this.moonSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Moon", "A slowly rotating moon with impact craters and dark basins", true));
    this.iceWorldSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Ice World",
                    "A glacial world with drifting clouds and a blue atmosphere",
                    true));
    this.oceanWorldSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Ocean World",
                    "Blue oceans, rugged continents and swirling cloud systems",
                    true));
    this.volcanicWorldSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Volcanic World",
                    "Dark basalt plates, flowing lava and a glowing ash atmosphere",
                    true));
    this.supernovaSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting("Supernova", "A star detonates now and then, high up", true));
    this.motionSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Motion",
                    "Sky drift, planets and rocket animations; 0 freezes their motion",
                    100.0,
                    0.0,
                    300.0,
                    10.0,
                    "%"));
    this.colourSetting =
        (ColorSetting)
            this.registerSetting(
                new ColorSetting("Colour", "Sky hue; follows the theme until edited", -40008)
                    .getColorSetting());
    this.followThemeSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Follow Theme",
                    "Use the shared accent; turn off to choose your own sky colour",
                    true));
    this.colourSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return !(Boolean) this.followThemeSetting.getValue();
        });
    this.starCountSetting.setVisibilityCondition(this.starsSetting::getValue);
    this.twinkleSetting.setVisibilityCondition(this.starsSetting::getValue);
    this.shapeSetting.setVisibilityCondition(this.particlesSetting::getValue);
    this.blendSetting.setVisibilityCondition(this.particlesSetting::getValue);
    this.countSetting.setVisibilityCondition(this.particlesSetting::getValue);
    this.radiusSetting.setVisibilityCondition(this.particlesSetting::getValue);
    this.sizeSetting.setVisibilityCondition(this.particlesSetting::getValue);
    this.speedSetting.setVisibilityCondition(this.particlesSetting::getValue);
    this.opacitySetting.setVisibilityCondition(this.particlesSetting::getValue);
    this.meteorRateSetting.setVisibilityCondition(this.shootingStarsSetting::getValue);
  }

  public static SkyModule getSkyModule() {
    FakeLootDropModule.ensureInitialized();

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager == null ? null : moduleManager.skyModule;
  }

  public static boolean test() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && skyModule.isEnabled() && (Boolean) skyModule.skySetting.getValue();
  }

  public static boolean test4() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null
        && skyModule.isEnabled()
        && (Boolean) skyModule.particlesSetting.getValue();
  }

  public static boolean test5() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.starsSetting.getValue();
  }

  public static boolean test6() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.twinkleSetting.getValue();
  }

  public static boolean test7() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.horizonGlowSetting.getValue();
  }

  public static float computeFloat() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 1200.0F : skyModule.starCountSetting.floatValue();
  }

  public static float computeFloat2() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 0.85F : skyModule.glowSetting.floatValue() / 100.0F;
  }

  public static float computeFloat3() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 0.8F : skyModule.depthSetting.floatValue() / 100.0F;
  }

  public static float computeFloat4() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 0.7F : skyModule.fogTintSetting.floatValue() / 100.0F;
  }

  public static SkyParticleStyle getSkyParticleStyle() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    if (skyModule == null) {
      return SkyParticleStyle.MOTES;
    }

    return switch ((String) skyModule.shapeSetting.getValue()) {
      case "Sparks" -> SkyParticleStyle.SPARKS;
      case "Petals" -> SkyParticleStyle.PETALS;
      default -> SkyParticleStyle.MOTES;
    };
  }

  public static boolean test8() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null || "Glow".equals(skyModule.blendSetting.getValue());
  }

  public static float computeFloat5() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 1000.0F : skyModule.countSetting.floatValue();
  }

  public static float computeFloat6() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 12.0F : skyModule.radiusSetting.floatValue();
  }

  public static float computeFloat7() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 0.14F : skyModule.sizeSetting.floatValue();
  }

  public static float computeFloat8() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 1.0F : skyModule.speedSetting.floatValue() / 100.0F;
  }

  public static float computeFloat9() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 0.95F : skyModule.opacitySetting.floatValue() / 100.0F;
  }

  public static boolean test9() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.milkyWaySetting.getValue();
  }

  public static boolean test10() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.nebulaeSetting.getValue();
  }

  public static boolean test11() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.shootingStarsSetting.getValue();
  }

  public static float computeFloat10() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 0.45F : skyModule.meteorRateSetting.floatValue() / 100.0F;
  }

  public static boolean test12() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.rocketSetting.getValue();
  }

  public static boolean test13() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.planetSetting.getValue();
  }

  public static boolean test14() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.moonSetting.getValue();
  }

  public static boolean test15() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.iceWorldSetting.getValue();
  }

  public static boolean test16() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.supernovaSetting.getValue();
  }

  public static boolean test17() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.oceanWorldSetting.getValue();
  }

  public static boolean test18() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.volcanicWorldSetting.getValue();
  }

  public static float computeFloat11() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? 1.0F : skyModule.motionSetting.floatValue() / 100.0F;
  }

  public static boolean test19() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule != null && (Boolean) skyModule.followThemeSetting.getValue();
  }

  public static int computeInt() {
    FakeLootDropModule.ensureInitialized();

    SkyModule skyModule = getSkyModule();
    return skyModule == null ? -40008 : skyModule.colourSetting.getInteger();
  }

  public static void setCountArray(int[] countArray2) {
    countArray = countArray2;
  }

  public static int[] getCountArray() {
    return countArray;
  }

  static {
    int[] intValues = new int[4];
    setCountArray(intValues);
  }
}
