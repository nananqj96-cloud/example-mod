package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.Setting;

public final class CosmeticsModule extends Module {
  public final BooleanSetting wingsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Wings", "A full pair of articulated, three-dimensional feather wings", true));
  public final ChoiceSetting wingStyleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Wing Style",
                  "Quartz and gold, obsidian, fiery copper, or your own skin texture",
                  "Seraph",
                  new String[] {"Seraph", "Ender", "Phoenix", "Your Skin"}));
  public final NumberSetting wingScaleSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Wing Scale", "Size of both wings", 100.0, 65.0, 135.0, 5.0, "%"));
  public final BooleanSetting wingJewelsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Wing Jewels", "Inset gems at the shoulders and layered feather tips", true));
  public final BooleanSetting hideElytraSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Hide Elytra",
                  "Hide your worn elytra while cosmetic wings are visible; flying still works",
                  false));
  public final BooleanSetting crownSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Crown",
                  "A sculpted gold crown with amethyst gems, attached to your head",
                  false));
  public final ChoiceSetting crownStyleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Crown Style",
                  "Choose the metal and gemstones",
                  "Royal",
                  new String[] {"Royal", "Ender", "Ice"}));
  public final NumberSetting crownScaleSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Crown Scale",
                  "Crown fit, including room for a helmet",
                  100.0,
                  90.0,
                  135.0,
                  5.0,
                  "%"));
  public final BooleanSetting miniYouSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Mini You",
                  "A tiny flying version of your actual skin beside your shoulder",
                  false));
  public final ChoiceSetting companionSideSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Companion Side",
                  "Which shoulder your miniature follows",
                  "Right",
                  new String[] {"Right", "Left"}));
  public final NumberSetting companionScaleSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Companion Scale",
                  "Size of your flying skin companion",
                  100.0,
                  70.0,
                  140.0,
                  5.0,
                  "%"));
  public final ChoiceSetting motionSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Motion",
                  "Reactive flight, a gentle idle flutter, or a completely still model",
                  "Reactive",
                  new String[] {"Reactive", "Gentle", "Still"}));
  public final NumberSetting animationSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Animation Speed",
                  "Animation tempo; zero freezes the entire rig",
                  100.0,
                  0.0,
                  180.0,
                  5.0,
                  "%"));
  public final NumberSetting wingbeatSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Wingbeat",
                  "How far the wings sweep with each beat",
                  70.0,
                  0.0,
                  100.0,
                  5.0,
                  "%"));
  public float amount;
  public float amount2;
  public float amount3;
  public float amount4;
  public Object object;
  public static int count;

  public CosmeticsModule() {
    super(
        "Cosmetics",
        "3D feather wings, gemstone crowns & your own flying skin companion",
        ModuleCategory.VISUALS);
    this.wingStyleSetting.setVisibilityCondition(this.wingsSetting::getValue);
    this.wingScaleSetting.setVisibilityCondition(this.wingsSetting::getValue);
    this.wingJewelsSetting.setVisibilityCondition(this.wingsSetting::getValue);
    this.hideElytraSetting.setVisibilityCondition(this.wingsSetting::getValue);
    this.crownStyleSetting.setVisibilityCondition(this.crownSetting::getValue);
    this.crownScaleSetting.setVisibilityCondition(this.crownSetting::getValue);
    this.companionSideSetting.setVisibilityCondition(this.miniYouSetting::getValue);
    this.companionScaleSetting.setVisibilityCondition(this.miniYouSetting::getValue);
    this.animationSpeedSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return !this.motionSetting.isSelected("Still");
        });
    this.wingbeatSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return (Boolean) this.wingsSetting.getValue() && !this.motionSetting.isSelected("Still");
        });
  }

  public String getString(Setting setting) {
    if (setting == this.wingsSetting
        || setting == this.wingStyleSetting
        || setting == this.wingScaleSetting
        || setting == this.wingJewelsSetting
        || setting == this.hideElytraSetting) {
      return "Wings";
    } else if (setting == this.crownSetting
        || setting == this.crownStyleSetting
        || setting == this.crownScaleSetting) {
      return "Crown";
    } else {
      return setting != this.miniYouSetting
              && setting != this.companionSideSetting
              && setting != this.companionScaleSetting
          ? "Motion"
          : "Mini You";
    }
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player == null || instance.world != this.object) {
      this.update4();
      this.object = instance.world;
    }

    if (instance.player != null) {
      this.amount = this.amount2;
      this.amount3 = this.amount4;
      if (!this.test()) {
        this.amount2 = this.amount2 + this.animationSpeedSetting.floatValue() / 2000.0F;
        float floatValue =
            this.motionSetting.isSelected("Gentle")
                ? 0.0F
                : (instance.player.isGliding()
                    ? 1.0F
                    : Math.clamp(
                        (float) instance.player.getVelocity().horizontalLength() * 3.0F
                            + (instance.player.isOnGround() ? 0.0F : 0.35F),
                        0.0F,
                        1.0F));
        this.amount4 = this.amount4 + (floatValue - this.amount4) * 0.18F;
      }
    }
  }

  public float computeFloat(float floatValue) {
    FakeLootDropModule.ensureInitialized();
    return this.test() ? this.amount2 : MathHelper.lerp(floatValue, this.amount, this.amount2);
  }

  public float computeFloat2(float floatValue) {
    FakeLootDropModule.ensureInitialized();
    return this.motionSetting.isSelected("Still")
        ? 0.0F
        : (this.test() ? this.amount4 : MathHelper.lerp(floatValue, this.amount3, this.amount4));
  }

  public boolean test() {
    FakeLootDropModule.ensureInitialized();
    return this.motionSetting.isSelected("Still")
        || this.animationSpeedSetting.floatValue() == 0.0F;
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    this.amount = this.amount2 = this.amount3 = this.amount4 = 0.0F;
    this.object = null;
  }

  public static void setCount(int count2) {
    count = count2;
  }

  public static int getCount() {
    return count;
  }

  public static int computeInt() {
    int intValue = getCount();
    return intValue == 0 ? 82 : 0;
  }

  static {
    setCount(48);
  }
}
