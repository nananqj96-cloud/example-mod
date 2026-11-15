package bunnythekenzo.client.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.AuroraData;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.Setting;

public final class AuroraModule extends Module {

  public final ChoiceSetting toneSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Tone",
                  "Hand-picked gradients for your ribbons of light",
                  "Borealis",
                  new String[] {"Borealis", "Candy", "Sunset", "Glacier", "Amethyst"}));
  public final ChoiceSetting shapeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Shape",
                  "Silk flows around you; Helix spirals upwards; Halo floats above your shoulders",
                  "Silk",
                  new String[] {"Silk", "Helix", "Halo"}));
  public final NumberSetting ribbonsSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Ribbons", "Number of intertwined light ribbons", 3.0, 1.0, 4.0, 1.0));
  public final NumberSetting ribbonWidthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Ribbon Width",
                  "From fine strands to soft flowing silk",
                  75.0,
                  25.0,
                  150.0,
                  5.0,
                  "%"));
  public final NumberSetting brightnessSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Brightness",
                  "Strength of the luminous silk and its soft bloom",
                  80.0,
                  15.0,
                  100.0,
                  5.0,
                  "%"));
  public final NumberSetting orbitSizeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Orbit Size",
                  "Space around you for the ribbons to dance",
                  1.45,
                  0.8,
                  2.5,
                  0.05,
                  "m"));
  public final NumberSetting trailLengthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Trail Length",
                  "How long your movement stays written in light",
                  1.8,
                  0.6,
                  3.0,
                  0.1,
                  "s"));
  public final NumberSetting flowSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Flow Speed",
                  "Speed of the dance; zero holds the light still",
                  85.0,
                  0.0,
                  160.0,
                  5.0,
                  "%"));
  public final BooleanSetting movementReactivitySetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Movement Reactivity", "Ribbons open gently as you move faster", true));
  public final BooleanSetting stardustSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Stardust", "Tiny four-point stars drift from the ribbons", true));
  public final BooleanSetting jumpBloomSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Jump Bloom", "A spreading flower of light blooms beneath each jump", true));
  public final BooleanSetting firstPersonSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "First Person",
                  "Also show the effect in first person, with a softer glow",
                  true));
  public final Vec3d[] vec3dArray = new Vec3d[68];
  public final List list = new ArrayList(3);
  public ClientWorld clientWorld;
  public int count2;
  public int count3;
  public int count4;
  public double value;
  public double value2;
  public double value3;
  public double value4;
  public float amount;
  public float amount2;
  public float amount3;
  public float amount4;
  public boolean enabled;
  public static String string;

  public AuroraModule() {
    super(
        "Aurora",
        "Living ribbons of light, drifting stardust & a bloom with every jump",
        ModuleCategory.VISUALS);
  }

  public String getString(Setting setting) {
    if (setting == this.orbitSizeSetting
        || setting == this.trailLengthSetting
        || setting == this.flowSpeedSetting
        || setting == this.movementReactivitySetting) {
      return "Flow";
    } else {
      return setting != this.stardustSetting
              && setting != this.jumpBloomSetting
              && setting != this.firstPersonSetting
          ? "Appearance"
          : "Details";
    }
  }

  public boolean test() {
    FakeLootDropModule.ensureInitialized();
    return !AnimationPreferences.isEnabled() || (Double) this.flowSpeedSetting.getValue() == 0.0;
  }

  public boolean test(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    return minecraftClient.world != null
        && minecraftClient.player != null
        && minecraftClient.player.isAlive()
        && !minecraftClient.player.isSpectator()
        && !minecraftClient.player.isInvisible()
        && !minecraftClient.player.isSleeping()
        && (ClientInitializer.getModuleManager() == null
            || !ClientInitializer.getModuleManager().freecamModule.test());
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != this.clientWorld) {
      this.update4();
      this.clientWorld = instance.world;
    }

    if (!this.test(instance)) {
      this.update4();
    } else if (!instance.isPaused()) {
      Vec3d entityPos = instance.player.getEntityPos();
      boolean flag =
          this.count3 == 0 || entityPos.squaredDistanceTo(this.vec3dArray[this.count2]) > 64.0;
      if (flag) {
        Arrays.fill(this.vec3dArray, entityPos);
        this.count2 = 0;
        this.count3 = 1;
        this.list.clear();
        this.enabled = instance.player.isOnGround();
        this.amount = this.amount2 = this.amount3 = this.amount4 = 0.0F;
      } else {
        this.count2 = (this.count2 + 1) % 68;
        this.vec3dArray[this.count2] = entityPos;
        this.count3 = Math.min(68, this.count3 + 1);
      }

      this.value2 = this.value;
      this.value4 = this.value3;
      this.amount2 = this.amount;
      this.amount4 = this.amount3;
      if (!this.test()) {
        this.value = this.value + 0.05 * (Double) this.flowSpeedSetting.getValue() / 100.0;
        this.value3 += 0.05;
      }

      this.amount = AnimationPreferences.isEnabled() ? Math.min(1.0F, this.amount + 0.075F) : 1.0F;
      float floatValue =
          this.movementReactivitySetting.getValue() && !this.test()
              ? Math.clamp(
                  (float) instance.player.getVelocity().horizontalLength() * 2.5F, 0.0F, 1.0F)
              : 0.0F;
      this.amount3 = this.amount3 + (floatValue - this.amount3) * 0.18F;
      if (this.count4 > 0) {
        this.count4--;
      }

      boolean onGround = instance.player.isOnGround();
      if ((Boolean) this.jumpBloomSetting.getValue()
          && !this.test()
          && !flag
          && this.enabled
          && !onGround
          && this.count4 == 0
          && instance.player.getVelocity().y > 0.08
          && !instance.player.isTouchingWater()
          && !instance.player.isInLava()
          && !instance.player.getAbilities().flying) {
        if (this.list.size() == 3) {
          this.list.removeFirst();
        }

        this.list.add(
            new AuroraData(
                this.vec3dArray[Math.floorMod(this.count2 - 1, 68)].add(0.0, 0.035, 0.0),
                this.value3));
        this.count4 = 7;
      }

      this.enabled = onGround;
      this.list.removeIf(
          value6 -> {
            FakeLootDropModule.ensureInitialized();
            return this.value3 - ((bunnythekenzo.client.AuroraData) (value6)).value() > 1.35;
          });
      if (!(Boolean) this.jumpBloomSetting.getValue() || this.test()) {
        this.list.clear();
      }
    }
  }

  public Vec3d getVec3d(double doubleValue, float floatValue) {
    if (this.count3 == 0) {
      return null;
    }

    double doubleValue2 = Math.clamp(doubleValue * 20.0 + 1.0 - floatValue, 0.0, 66.999);
    int intValue = (int) doubleValue2;
    Vec3d vec3d = this.getVec3d(intValue - 1);
    Vec3d vec3d2 = this.getVec3d(intValue);
    Vec3d vec3d3 = this.getVec3d(intValue + 1);
    Vec3d vec3d4 = this.getVec3d(intValue + 2);
    double doubleValue3 = doubleValue2 - intValue;
    double doubleValue4 = doubleValue3 * doubleValue3;
    double doubleValue5 = doubleValue4 * doubleValue3;
    return vec3d2
        .multiply(2.0 * doubleValue5 - 3.0 * doubleValue4 + 1.0)
        .add(vec3d3.multiply(-2.0 * doubleValue5 + 3.0 * doubleValue4))
        .add(
            vec3d3
                .subtract(vec3d)
                .multiply((doubleValue5 - 2.0 * doubleValue4 + doubleValue3) * 0.5))
        .add(vec3d4.subtract(vec3d2).multiply((doubleValue5 - doubleValue4) * 0.5));
  }

  public Vec3d getVec3d(int intValue) {
    return this.vec3dArray[Math.floorMod(this.count2 - Math.clamp(intValue, 0, 67), 68)];
  }

  public double computeDouble(float floatValue) {
    FakeLootDropModule.ensureInitialized();
    return this.test() ? this.value : MathHelper.lerp(floatValue, this.value2, this.value);
  }

  public double computeDouble2(float floatValue) {
    FakeLootDropModule.ensureInitialized();
    return this.test() ? this.value3 : MathHelper.lerp(floatValue, this.value4, this.value3);
  }

  public float computeFloat(float floatValue) {
    return MathHelper.lerp(floatValue, this.amount2, this.amount);
  }

  public float computeFloat2(float floatValue) {
    FakeLootDropModule.ensureInitialized();
    return this.movementReactivitySetting.getValue() && !this.test()
        ? MathHelper.lerp(floatValue, this.amount4, this.amount3)
        : 0.0F;
  }

  public List getList2() {
    return List.copyOf(this.list);
  }

  public int getCount3() {
    return this.count3;
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    Arrays.fill(this.vec3dArray, null);
    this.list.clear();
    this.clientWorld = null;
    this.count2 = this.count3 = this.count4 = 0;
    this.value = this.value2 = this.value3 = this.value4 = 0.0;
    this.amount = this.amount2 = this.amount3 = this.amount4 = 0.0F;
    this.enabled = false;
  }

  public static void setString(String string2) {
    string = string2;
  }

  public static String getString() {
    return string;
  }

  static {
    setString("mbGMb");
  }
}
