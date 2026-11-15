package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.LumaSupport;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.Setting;

public final class LumaModule extends Module {

  public final ChoiceSetting paletteSetting;
  public final NumberSetting butterfliesSetting;
  public final NumberSetting wingSizeSetting;
  public final NumberSetting flightRadiusSetting;
  public final NumberSetting glowSetting;
  public final BooleanSetting silkTrailsSetting;
  public final NumberSetting trailLengthSetting;
  public final BooleanSetting stardustSetting;
  public final ChoiceSetting motionSetting;
  public final NumberSetting flightSpeedSetting;
  public final BooleanSetting jumpBloomSetting;
  public final BooleanSetting crouchVortexSetting;
  public final BooleanSetting swingBurstSetting;
  public final LumaSupport[] lumaSupportArray;
  public ClientWorld clientWorld;
  public Vec3d vec3d;
  public double value;
  public double value2;
  public float amount;
  public float amount2;
  public float amount3;
  public float amount4;
  public float amount5;
  public float amount6;
  public float amount7;
  public boolean enabled;
  public boolean enabled2;
  public int count3;
  public int count4;
  public static ClientInitializer[] clientInitializerArray;

  public LumaModule() {
    super(
        "Luma",
        "Iridescent butterflies, silk trails & a little everyday magic",
        ModuleCategory.VISUALS);

    FakeLootDropModule.ensureInitialized();

    this.paletteSetting =
        (ChoiceSetting)
            this.registerSetting(
                new ChoiceSetting(
                    "Palette",
                    "Aurora mint, Sakura blush, Sunset gold or Glacier blue",
                    "Aurora",
                    new String[] {"Aurora", "Sakura", "Sunset", "Glacier"}));
    this.butterfliesSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Butterflies",
                    "How many little lights keep you company",
                    16.0,
                    6.0,
                    28.0,
                    1.0));
    this.wingSizeSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Wing Size", "Size of the iridescent wings", 100.0, 60.0, 160.0, 5.0, "%"));
    this.flightRadiusSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Flight Radius",
                    "Space around you for the swarm to explore",
                    2.8,
                    1.8,
                    5.0,
                    0.1,
                    "m"));
    this.glowSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Glow", "Soft light around each butterfly", 65.0, 0.0, 100.0, 5.0, "%"));
    this.silkTrailsSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Silk Trails", "Fine, fading ribbons follow each flight path", true));
    this.trailLengthSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Trail Length", "How long each ribbon lingers", 1.2, 0.3, 1.6, 0.1, "s"));
    this.stardustSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting("Stardust", "Tiny lights sparkle along the ribbons", true));
    this.motionSetting =
        (ChoiceSetting)
            this.registerSetting(
                new ChoiceSetting(
                    "Motion",
                    "Reactive plays with you; Gentle drifts; Still holds the wings open",
                    "Reactive",
                    new String[] {"Reactive", "Gentle", "Still"}));
    this.flightSpeedSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Flight Speed",
                    "Pace of the drifting and wingbeats",
                    85.0,
                    0.0,
                    150.0,
                    5.0,
                    "%"));
    this.jumpBloomSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting("Jump Bloom", "Jump to open the swarm like a flower", true));
    this.crouchVortexSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Crouch Vortex", "Hold sneak to gather a dancing halo in front of you", true));
    this.swingBurstSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Swing Burst", "Swing your hand to scatter the butterflies", true));
    this.lumaSupportArray = new LumaSupport[28];

    for (int index = 0; index < this.lumaSupportArray.length; index++) {
      this.lumaSupportArray[index] = new LumaSupport();
    }

    this.trailLengthSetting.setVisibilityCondition(this.silkTrailsSetting::getValue);
    this.stardustSetting.setVisibilityCondition(this.silkTrailsSetting::getValue);
    this.flightSpeedSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return !this.motionSetting.isSelected("Still");
        });
    this.jumpBloomSetting.setVisibilityCondition(
        () -> {
          return this.motionSetting.isSelected("Reactive");
        });
    this.crouchVortexSetting.setVisibilityCondition(
        () -> {
          return this.motionSetting.isSelected("Reactive");
        });
    this.swingBurstSetting.setVisibilityCondition(
        () -> {
          return this.motionSetting.isSelected("Reactive");
        });
  }

  public String getString(Setting setting) {
    if (setting == this.glowSetting
        || setting == this.silkTrailsSetting
        || setting == this.trailLengthSetting
        || setting == this.stardustSetting) {
      return "Light";
    } else {
      return setting != this.motionSetting
              && setting != this.flightSpeedSetting
              && setting != this.jumpBloomSetting
              && setting != this.crouchVortexSetting
              && setting != this.swingBurstSetting
          ? "Swarm"
          : "Motion";
    }
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != this.clientWorld || instance.player == null) {
      this.update4();
      this.clientWorld = instance.world;
    }

    if (instance.player != null && instance.world != null && !instance.isPaused()) {
      ClientPlayerEntity player = instance.player;
      Vec3d entityPos = player.getEntityPos();
      boolean flag = this.vec3d == null || entityPos.squaredDistanceTo(this.vec3d) > 64.0;
      Vec3d vec3d2 = flag ? Vec3d.ZERO : entityPos.subtract(this.vec3d);
      if (flag) {
        this.amount =
            this.amount2 = this.amount3 = this.amount4 = this.amount5 = this.amount6 = 0.0F;
        this.amount7 = player.getYaw();
        this.enabled = player.isOnGround();
        this.enabled2 = player.handSwinging;
        this.count3 = 0;
      }

      this.value2 = this.value;
      this.amount2 = this.amount;
      this.amount4 = this.amount3;
      this.amount6 = this.amount5;
      boolean flag2 = this.test();
      if (!flag2) {
        this.value = this.value + (Double) this.flightSpeedSetting.getValue() / 2000.0;
      }

      this.amount5 =
          AnimationPreferences.isEnabled() ? this.amount5 + (1.0F - this.amount5) * 0.13F : 1.0F;
      if (this.count3 > 0) {
        this.count3--;
      }

      boolean flag3 =
          !flag2
              && this.motionSetting.isSelected("Reactive")
              && player.isAlive()
              && !player.isSpectator();
      boolean flag4 =
          this.enabled
              && !player.isOnGround()
              && player.getVelocity().y > 0.08
              && !player.isTouchingWater();
      boolean flag5 = player.handSwinging && !this.enabled2 && instance.currentScreen == null;
      if (flag
          || !flag3
          || this.count3 != 0
          || (!(Boolean) this.jumpBloomSetting.getValue() || !flag4)
              && (!(Boolean) this.swingBurstSetting.getValue() || !flag5)) {
        this.amount *= 0.86F;
      } else {
        this.amount = 1.0F;
        this.count3 = 10;
      }

      this.amount3 =
          this.amount3
              + ((flag3 && this.crouchVortexSetting.getValue() && player.isSneaking() ? 1 : 0)
                      - this.amount3)
                  * 0.18F;
      this.amount7 = this.amount7 + MathHelper.wrapDegrees(player.getYaw() - this.amount7) * 0.18F;
      int value4 = this.butterfliesSetting.intValue();

      for (int index = 0; index < value4; index++) {
        LumaSupport lumaSupport = this.lumaSupportArray[index];
        Vec3d vec3d3 = this.getVec3d(index, value4, entityPos, player.getStandingEyeHeight());
        if (!flag && index < this.count4 && lumaSupport.vec3d != null) {
          lumaSupport.vec3d2 = lumaSupport.vec3d;
          lumaSupport.amount2 = lumaSupport.amount;
          Vec3d vec3d4 = lumaSupport.vec3d.add(vec3d2);
          if (!flag2) {
            vec3d4 = vec3d4.lerp(vec3d3, 0.24 + this.amount3 * 0.13);
            Vec3d vec3d5 = vec3d4.subtract(lumaSupport.vec3d.add(vec3d2));
            if (vec3d5.horizontalLengthSquared() > 5.0E-6) {
              float floatValue = (float) Math.atan2(vec3d5.x, vec3d5.z);
              lumaSupport.amount =
                  lumaSupport.amount
                      + MathHelper.wrapDegrees(
                              (floatValue - lumaSupport.amount) * (180.0F / (float) Math.PI))
                          * (float) (Math.PI / 180.0)
                          * 0.28F;
            }
          }

          lumaSupport.vec3d = vec3d4;
          lumaSupport.apply2(vec3d4);
        } else {
          lumaSupport.apply(vec3d3);
        }
      }

      for (int index2 = value4; index2 < this.count4; index2++) {
        this.lumaSupportArray[index2].update();
      }

      this.count4 = value4;
      this.vec3d = entityPos;
      this.enabled = player.isOnGround();
      this.enabled2 = player.handSwinging;
    }
  }

  public Vec3d getVec3d(int intValue, int intValue2, Vec3d vec3d2, float floatValue) {
    double doubleValue = intValue * 2.39996322973;
    double doubleValue2 = doubleValue + this.value * (0.22 + intValue % 4 * 0.038);
    double doubleValue3 =
        (Double) this.flightRadiusSetting.getValue()
                * (0.7 + 0.22 * Math.sin(doubleValue * 1.7 + this.value * 0.33))
            + this.amount * (0.8 + intValue % 3 * 0.2);
    double doubleValue4 =
        Math.cos(doubleValue2) * doubleValue3 + Math.sin(this.value * 0.7 + doubleValue) * 0.25;
    double doubleValue5 = Math.sin(doubleValue2) * doubleValue3;
    double doubleValue6 =
        0.9
            + intValue % 5 * 0.31
            + Math.sin(this.value * 0.85 + doubleValue * 1.4) * 0.3
            + this.amount * 0.55;
    double doubleValue7 = (Math.PI * 2) * intValue / intValue2 + this.value * 0.95;
    double doubleValue8 = Math.cos(doubleValue7) * 1.18;
    double doubleValue9 = floatValue + Math.sin(doubleValue7) * 0.76;
    double doubleValue10 = 3.05 + Math.sin(doubleValue7 * 2.0 + this.value) * 0.12;
    double doubleValue11 = this.amount7 * (float) (Math.PI / 180.0);
    double doubleValue12 =
        doubleValue8 * Math.cos(doubleValue11) - doubleValue10 * Math.sin(doubleValue11);
    double doubleValue13 =
        doubleValue8 * Math.sin(doubleValue11) + doubleValue10 * Math.cos(doubleValue11);
    return vec3d2.add(
        MathHelper.lerp(this.amount3, doubleValue4, doubleValue12),
        MathHelper.lerp(this.amount3, doubleValue6, doubleValue9),
        MathHelper.lerp(this.amount3, doubleValue5, doubleValue13));
  }

  public boolean test() {
    FakeLootDropModule.ensureInitialized();
    return !AnimationPreferences.isEnabled()
        || this.motionSetting.isSelected("Still")
        || (Double) this.flightSpeedSetting.getValue() == 0.0;
  }

  public double computeDouble(float floatValue) {
    FakeLootDropModule.ensureInitialized();
    return this.test() ? this.value : MathHelper.lerp(floatValue, this.value2, this.value);
  }

  public float computeFloat(float floatValue) {
    return this.test() ? 0.0F : MathHelper.lerp(floatValue, this.amount2, this.amount);
  }

  public float computeFloat2(float floatValue) {
    return MathHelper.lerp(floatValue, this.amount4, this.amount3);
  }

  public float computeFloat3(float floatValue) {
    return MathHelper.lerp(floatValue, this.amount6, this.amount5);
  }

  public int getCount4() {
    return this.count4;
  }

  public LumaSupport getLumaSupport(int index) {
    return this.lumaSupportArray[index];
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    this.clientWorld = null;
    this.vec3d = null;
    this.value = this.value2 = 0.0;
    this.amount = this.amount2 = this.amount3 = this.amount4 = this.amount5 = this.amount6 = 0.0F;
    this.count4 = this.count3 = 0;
    FakeLootDropModule.ensureInitialized();

    this.enabled = this.enabled2 = false;

    for (LumaSupport lumaSupport : this.lumaSupportArray) {
      lumaSupport.update();
    }
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }

  static {
    ClientInitializer[] clientInitializerArray2 = new ClientInitializer[5];
    setClientInitializerArray(clientInitializerArray2);
  }
}
