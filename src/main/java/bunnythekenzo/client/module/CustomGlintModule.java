package bunnythekenzo.client.module;

import java.util.EnumMap;
import net.minecraft.item.ItemDisplayContext;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientData2;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.CustomGlintSupport;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.GlintStyle;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.GlintStyleSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.Setting;

public class CustomGlintModule extends Module {
  public final GlintStyleSetting glintStyleSetting =
      (GlintStyleSetting) this.registerSetting(new GlintStyleSetting());
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode",
                  "Where the classic foil colour comes from",
                  "Theme",
                  new String[] {"Solid", "Rainbow", "Theme"}));
  public final ColorSetting colorSetting;
  public final ChoiceSetting finishSetting;
  public final NumberSetting textureVisibilitySetting;
  public final NumberSetting strengthSetting;
  public final NumberSetting patternSizeSetting;
  public final NumberSetting glowSetting;
  public final NumberSetting speedSetting;
  public final BooleanSetting reverseMotionSetting;
  public final ChoiceSetting applyToSetting;
  public final BooleanSetting inventoryHotbarSetting;
  public final BooleanSetting heldItemsSetting;
  public final BooleanSetting droppedItemsSetting;
  public final BooleanSetting itemFramesSetting;
  public final BooleanSetting wornArmorSetting;
  public final EnumMap enumMap;
  public long timestamp;
  public double value;
  public static boolean enabled;
  public static final long timestamp2 = 125025999770313656L;

  public CustomGlintModule() {
    super(
        "Custom Glint",
        "Living item effects, from a subtle overlay to a complete animated finish",
        ModuleCategory.MISC);
    this.colorSetting =
        (ColorSetting)
            this.registerSetting(
                new ColorSetting("Color", "Classic foil colour", -40008).getColorSetting());
    FakeLootDropModule.ensureInitialized();

    this.finishSetting =
        (ChoiceSetting)
            this.registerSetting(
                new ChoiceSetting(
                    "Finish",
                    "Overlay keeps item detail; Full effect fills the item silhouette",
                    "Overlay",
                    new String[] {"Overlay", "Full effect", "Blend"}));
    this.textureVisibilitySetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Texture visibility",
                    "Original texture visible through the effect: 0% is pure effect",
                    45.0,
                    0.0,
                    100.0,
                    5.0,
                    "%"));
    this.strengthSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Strength",
                    "Effect intensity; 0% shows the original item",
                    85.0,
                    0.0,
                    100.0,
                    5.0,
                    "%"));
    this.patternSizeSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Pattern size",
                    "Larger values reveal larger clouds, flames and details",
                    100.0,
                    50.0,
                    200.0,
                    5.0,
                    "%"));
    this.glowSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Glow",
                    "How brightly the effect shines in dark places",
                    85.0,
                    0.0,
                    100.0,
                    5.0,
                    "%"));
    this.speedSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Speed",
                    "Animation speed; 0% freezes the effect",
                    100.0,
                    0.0,
                    300.0,
                    5.0,
                    "%"));
    this.reverseMotionSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting("Reverse motion", "Reverse the flow of the animation", false));
    this.applyToSetting =
        (ChoiceSetting)
            this.registerSetting(
                new ChoiceSetting(
                    "Apply to",
                    "Choose enchanted items or give every item an effect",
                    "Enchanted items",
                    new String[] {"Enchanted items", "All items"}));
    this.inventoryHotbarSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Inventory & hotbar", "Show effects in inventory slots and the hotbar", true));
    this.heldItemsSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Held items", "Show effects in first and third person, in either hand", true));
    this.droppedItemsSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Dropped items", "Show effects on items falling or lying on the ground", true));
    this.itemFramesSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Item frames",
                    "Show effects on framed items and other fixed item displays",
                    true));
    this.wornArmorSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Worn armor", "Show effects on worn equipment, including elytra", true));
    this.enumMap = new EnumMap<>(GlintStyle.class);
    this.timestamp = System.nanoTime();
    this.modeSetting.setVisibilityCondition(
        () -> {
          return this.glintStyleSetting.isSelected("Default");
        });
    this.colorSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return this.glintStyleSetting.isSelected("Default")
              && this.modeSetting.isSelected("Solid");
        });
    this.textureVisibilitySetting.setVisibilityCondition(
        () -> {
          return this.finishSetting.isSelected("Blend");
        });
    this.addDetails(
        GlintStyle.GALAXY,
        "Nebula depth",
        "Layered violet and blue dust clouds",
        80.0,
        "Star density",
        "Number of scattered, twinkling stars",
        65.0);
    this.addDetails(
        GlintStyle.ENDER,
        "Portal ripples",
        "Strength of swirling portal rings",
        75.0,
        "Ender sparks",
        "Turquoise motes drifting through the portal",
        70.0);
    this.addDetails(
        GlintStyle.VOID,
        "Rift width",
        "Width of luminous tears in the dark void",
        45.0,
        "Void pulse",
        "Energy surging through the rifts",
        70.0);
    this.addDetails(
        GlintStyle.TOXIC,
        "Acid pools",
        "Size and depth of bubbling acid cells",
        70.0,
        "Bubbles",
        "Bright rings and floating toxic specks",
        75.0);
    this.addDetails(
        GlintStyle.AMETHYST,
        "Crystal facets",
        "Contrast between polished crystal faces",
        80.0,
        "Crystal shimmer",
        "Travelling highlights along crystal edges",
        75.0);
    this.addDetails(
        GlintStyle.CYBER,
        "Circuit density",
        "Number of neon circuit traces",
        60.0,
        "Signal energy",
        "Brightness of travelling data pulses",
        85.0);
    this.addDetails(
        GlintStyle.SOLAR,
        "Plasma turbulence",
        "Fine convection cells on the solar surface",
        75.0,
        "Solar flares",
        "White-hot seams and bursts of plasma",
        80.0);
    this.addDetails(
        GlintStyle.PRISMATIC,
        "Ribbon width",
        "Width of flowing bands of colour",
        65.0,
        "Iridescence",
        "Pearlescent highlights between rainbow ribbons",
        75.0);
    this.addDetails(
        GlintStyle.ABYSS,
        "Current depth",
        "Layered deep-blue ocean currents",
        80.0,
        "Bioluminescence",
        "Cyan caustics and drifting luminous plankton",
        70.0);
    this.addDetails(
        GlintStyle.AURORA,
        "Curtain folds",
        "Layers of flowing northern-light curtains",
        75.0,
        "Aurora rays",
        "Fine vertical rays inside the curtains",
        80.0);
    this.addDetails(
        GlintStyle.INFERNO,
        "Flame height",
        "Stretch and turbulence of rising flame tongues",
        80.0,
        "Embers",
        "Hot sparks rising through the flames",
        75.0);
    ClientInitializer.ensureInitialized();
  }

  public void addDetails(
      GlintStyle glintStyle,
      String text,
      String text2,
      double doubleValue,
      String text3,
      String text4,
      double doubleValue2) {
    NumberSetting numberSetting6 =
        (NumberSetting)
            this.registerSetting(new NumberSetting(text, text2, doubleValue, 0.0, 100.0, 5.0, "%"));
    NumberSetting numberSetting7 =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(text3, text4, doubleValue2, 0.0, 100.0, 5.0, "%"));
    numberSetting6.setVisibilityCondition(
        () -> {
          return this.glintStyleSetting.getGlintStyle() == glintStyle;
        });
    numberSetting7.setVisibilityCondition(
        () -> {
          return this.glintStyleSetting.getGlintStyle() == glintStyle;
        });
    this.enumMap.put(glintStyle, new NumberSetting[] {numberSetting6, numberSetting7});
  }

  public String settingGroup(Setting setting) {
    if (setting != this.applyToSetting
        && (!(setting instanceof BooleanSetting) || setting == this.reverseMotionSetting)) {
      return setting != this.speedSetting
              && setting != this.reverseMotionSetting
              && !this.enumMap.values().stream()
                  .anyMatch(
                      (java.util.function.Predicate<bunnythekenzo.client.setting.NumberSetting[]>)
                          (choiceSettingSupport3Array -> {
                            FakeLootDropModule.ensureInitialized();
                            return choiceSettingSupport3Array[0] == setting
                                || choiceSettingSupport3Array[1] == setting;
                          }))
          ? "Appearance"
          : "Effect";
    } else {
      return "Items";
    }
  }

  public ClientData2 getClientData2(GlintStyle glintStyle) {
    NumberSetting[] choiceSettingSupport3Array = (NumberSetting[]) this.enumMap.get(glintStyle);
    return new ClientData2(
        this.patternSizeSetting.floatValue() / 100.0F,
        choiceSettingSupport3Array == null
            ? 0.75F
            : choiceSettingSupport3Array[0].floatValue() / 100.0F,
        choiceSettingSupport3Array == null
            ? 0.75F
            : choiceSettingSupport3Array[1].floatValue() / 100.0F);
  }

  public float computeFloat() {
    FakeLootDropModule.ensureInitialized();
    return this.finishSetting.isSelected("Full effect")
        ? 0.0F
        : (this.finishSetting.isSelected("Overlay")
            ? 1.0F
            : this.textureVisibilitySetting.floatValue() / 100.0F);
  }

  public boolean test(ItemDisplayContext itemDisplayContext, boolean flag) {
    FakeLootDropModule.ensureInitialized();
    if (this.test() && (flag || this.applyToSetting.isSelected("All items"))) {
      return switch (CustomGlintSupport.countArray[itemDisplayContext.ordinal()]) {
        case 1 -> this.inventoryHotbarSetting.getValue();
        case 2 -> this.droppedItemsSetting.getValue();
        case 3 -> this.itemFramesSetting.getValue();
        case 4, 5, 6, 7 -> this.heldItemsSetting.getValue();
        case 8 -> this.wornArmorSetting.getValue();
        default -> false;
      };
    } else {
      return false;
    }
  }

  public boolean test(boolean flag) {
    FakeLootDropModule.ensureInitialized();
    return this.test()
        && (Boolean) this.wornArmorSetting.getValue()
        && (flag || this.applyToSetting.isSelected("All items"));
  }

  public boolean test() {
    return this.isEnabled();
  }

  public boolean test4() {
    FakeLootDropModule.ensureInitialized();
    return !this.glintStyleSetting.isSelected("Default");
  }

  public float computeFloat2() {
    return this.strengthSetting.floatValue() / 100.0F;
  }

  public double computeDouble() {
    FakeLootDropModule.ensureInitialized();

    long timestamp3 = System.nanoTime();
    this.value =
        this.value
            + Math.min(0.1, Math.max(0L, timestamp3 - this.timestamp) / 1.0E9)
                * this.speedSetting.floatValue()
                / 100.0
                * ((java.lang.Boolean) (this.reverseMotionSetting.getValue()) ? -1 : 1);
    this.timestamp = timestamp3;
    return this.value;
  }

  public int computeInt() {
    int int2 = this.computeInt2();
    float float2 = this.computeFloat2();
    return ArgbColors.rgb(
        Math.round(ArgbColors.red(int2) * float2),
        Math.round(ArgbColors.green(int2) * float2),
        Math.round(ArgbColors.blue(int2) * float2));
  }

  public int computeInt2() {
    FakeLootDropModule.ensureInitialized();

    return switch ((String) this.modeSetting.getValue()) {
      case "Rainbow" ->
          ArgbColors.fromHsv(
              (float) ((this.computeDouble() * 36.0 % 360.0 + 360.0) % 360.0), 0.85F, 1.0F);
      case "Theme" -> ClientInitializer.getThemeManager().getColorPalette().getCount();
      default -> this.colorSetting.getInteger();
    };
  }

  public static void setOpaqueGuard(boolean enabled2) {
    enabled = enabled2;
  }

  public static boolean isEnabled2() {
    return enabled;
  }

  public static boolean test5() {
    boolean flag = isEnabled2();
    return !flag;
  }

  static {
    setOpaqueGuard(true);
  }
}
