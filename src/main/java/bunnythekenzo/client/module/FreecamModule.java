package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;
import bunnythekenzo.client.CameraChunkCulling;
import bunnythekenzo.client.DampedVectorIntegrator;
import bunnythekenzo.client.mixin.InputAccessor;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class FreecamModule extends Module {
  public static FreecamModule freecamModule;
  public final NumberSetting speedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Speed", "Camera fly speed", 1.0, 0.1, 50.0, 0.1, "x"));
  public final NumberSetting verticalSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Vertical speed", "Up/down speed multiplier", 1.0, 0.2, 5.0, 0.05, "x"));
  public final BooleanSetting scrollToAdjustSpeedSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Scroll to adjust speed",
                  "Use the mouse wheel to change fly speed while Freecam is active",
                  true));
  public final BooleanSetting smoothingSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Smoothing", "Ease camera movement", true));
  public final BooleanSetting showOwnBodySetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Show own body", "Render your body while detached", true));
  public final BooleanSetting showHandsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Show hands", "Keep first-person hands visible", true));
  public final NumberSetting lookSensitivitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Look Sensitivity", "Camera look sensitivity", 0.5, 0.1, 2.0, 0.05));
  public final Vector3d vector3d = new Vector3d();
  public final Vector3d vector3d2 = new Vector3d();
  public ClientPlayerEntity clientPlayerEntity;
  public ClientWorld clientWorld;
  public long timestamp;
  public double value;
  public double value2;
  public double value3;
  public double value4;
  public double value5;
  public double value6;
  public float amount;
  public float amount2;
  public float amount3;
  public float amount4;
  public double value7;
  public double value8;
  public double value9;
  public float amount5;
  public float amount6;
  public boolean enabled;
  public Perspective perspective;
  public boolean enabled2;
  public boolean enabled3;
  public boolean enabled4 = false;
  public boolean enabled5;
  public boolean enabled6;
  public boolean enabled7;
  public boolean enabled8;
  public boolean enabled9;
  public boolean enabled10;
  public boolean enabled11;

  public FreecamModule() {
    super(
        "Freecam",
        "Detached camera. Hold a movement key while enabling to keep the body moving.",
        ModuleCategory.MISC);
    freecamModule = this;
  }

  public static FreecamModule getInstance() {
    return freecamModule;
  }

  @Override
  public void onEnable() {
    this.enabled3 = true;
    this.enabled4 = false;
  }

  @Override
  public void onDisable() {
    MinecraftClient instance = MinecraftClient.getInstance();
    boolean flag =
        this.enabled4
            && instance.player == this.clientPlayerEntity
            && instance.world == this.clientWorld;
    this.vector3d2.zero();
    this.update4();
    this.enabled3 = false;
    this.enabled4 = false;
    if (this.enabled2) {
      instance.options.setPerspective(this.perspective);
      this.enabled2 = false;
    }

    this.apply3(instance);
    CameraChunkCulling.apply(instance);
    if (flag && instance.player != null) {
      instance.player.getAbilities().flying = this.enabled;
    }
  }

  public void apply(MinecraftClient minecraftClient) {
    if (this.isEnabled() && this.enabled3) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
        this.clientPlayerEntity = minecraftClient.player;
        this.clientWorld = minecraftClient.world;
        this.timestamp = System.nanoTime();
        this.vector3d2.zero();
        this.value7 = minecraftClient.player.getX();
        this.value8 = minecraftClient.player.getY();
        this.value9 = minecraftClient.player.getZ();
        this.amount5 = minecraftClient.player.getYaw();
        this.amount6 = minecraftClient.player.getPitch();
        this.value = this.value4 = this.value7;
        this.value2 = this.value5 = this.value8 + minecraftClient.player.getStandingEyeHeight();
        this.value3 = this.value6 = this.value9;
        this.amount = this.amount3 = this.amount5;
        this.amount2 = this.amount4 = this.amount6;
        this.enabled = minecraftClient.player.getAbilities().flying;
        this.enabled2 = false;
        if ((Boolean) this.showOwnBodySetting.getValue()) {
          this.perspective = minecraftClient.options.getPerspective();
          if (this.perspective.isFirstPerson()) {
            minecraftClient.options.setPerspective(Perspective.THIRD_PERSON_BACK);
            this.enabled2 = true;
          }
        }

        this.enabled3 = false;
        this.enabled4 = true;
        this.apply(minecraftClient.options);
        CameraChunkCulling.apply(minecraftClient);
        apply2(minecraftClient);
      }
    }
  }

  public void apply(GameOptions gameOptions) {
    this.enabled5 = gameOptions.forwardKey.isPressed();
    this.enabled6 = gameOptions.backKey.isPressed();
    this.enabled7 = gameOptions.leftKey.isPressed();
    this.enabled8 = gameOptions.rightKey.isPressed();
    this.enabled9 = gameOptions.jumpKey.isPressed();
    this.enabled10 = gameOptions.sneakKey.isPressed();
    this.enabled11 = gameOptions.sprintKey.isPressed();
  }

  public void update4() {
    this.enabled5 = false;
    this.enabled6 = false;
    this.enabled7 = false;
    this.enabled8 = false;
    this.enabled9 = false;
    this.enabled10 = false;
    this.enabled11 = false;
  }

  @Override
  public void onTick() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.isEnabled()) {
      this.apply(instance);
      if (this.enabled4 && instance.player != null) {
        if (!this.test()) {
          this.setEnabled(false);
        } else {
          if (instance.player.getAbilities().creativeMode) {
            instance.player.getAbilities().flying = false;
          }
        }
      }
    }
  }

  public void update5() {
    long timestamp2 = System.nanoTime();
    double doubleValue = (timestamp2 - this.timestamp) / 1.0E9;
    this.timestamp = timestamp2;
    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.isEnabled() && this.enabled4 && !this.test()) {
      this.setEnabled(false);
    } else if (this.test()) {
      if (instance.currentScreen == null && !instance.isPaused() && instance.isWindowFocused()) {
        this.apply(doubleValue);
      } else {
        this.vector3d2.zero();
      }
    }
  }

  public void apply(double doubleValue) {
    if (this.test() && Double.isFinite(doubleValue) && !(doubleValue <= 0.0)) {
      doubleValue = Math.min(doubleValue, 0.05);
      GameOptions options = MinecraftClient.getInstance().options;
      double doubleValue2 =
          (options.forwardKey.isPressed() ? 1 : 0) - (options.backKey.isPressed() ? 1 : 0);
      double doubleValue3 =
          (options.rightKey.isPressed() ? 1 : 0) - (options.leftKey.isPressed() ? 1 : 0);
      double doubleValue4 =
          (options.jumpKey.isPressed() ? 1 : 0) - (options.sneakKey.isPressed() ? 1 : 0);
      double doubleValue5 =
          (Double) this.speedSetting.getValue()
              * 20.0
              / Math.max(
                  1.0,
                  Math.sqrt(
                      doubleValue2 * doubleValue2
                          + doubleValue3 * doubleValue3
                          + doubleValue4 * doubleValue4));
      double doubleValue6 = Math.toRadians(this.amount);
      double doubleValue7 =
          (-Math.sin(doubleValue6) * doubleValue2 - Math.cos(doubleValue6) * doubleValue3)
              * doubleValue5;
      double doubleValue8 =
          doubleValue4 * doubleValue5 * (Double) this.verticalSpeedSetting.getValue();
      double doubleValue9 =
          (Math.cos(doubleValue6) * doubleValue2 - Math.sin(doubleValue6) * doubleValue3)
              * doubleValue5;
      this.vector3d.set(this.value, this.value2, this.value3);
      if ((Boolean) this.smoothingSetting.getValue()) {
        DampedVectorIntegrator.render(
            this.vector3d,
            this.vector3d2,
            doubleValue7,
            doubleValue8,
            doubleValue9,
            Math.log(1000.0),
            doubleValue);
      } else {
        this.vector3d.add(
            doubleValue7 * doubleValue, doubleValue8 * doubleValue, doubleValue9 * doubleValue);
        this.vector3d2.set(doubleValue7, doubleValue8, doubleValue9);
      }

      this.value = this.vector3d.x;
      this.value2 = this.vector3d.y;
      this.value3 = this.vector3d.z;
    }
  }

  public static void apply2(MinecraftClient minecraftClient) {
    FreecamModule freecamModule2 = freecamModule;
    if (freecamModule2 != null && freecamModule2.test() && minecraftClient.player != null) {
      ClientPlayerEntity player = minecraftClient.player;
      boolean enabled52 = freecamModule2.enabled5;
      boolean enabled62 = freecamModule2.enabled6;
      boolean enabled72 = freecamModule2.enabled7;
      boolean enabled82 = freecamModule2.enabled8;
      boolean enabled92 = freecamModule2.enabled9;
      boolean enabled102 = freecamModule2.enabled10;
      boolean enabled112 = freecamModule2.enabled11;
      player.input.playerInput =
          new PlayerInput(
              enabled52, enabled62, enabled72, enabled82, enabled92, enabled102, enabled112);
      float floatValue = (enabled72 ? 1.0F : 0.0F) - (enabled82 ? 1.0F : 0.0F);
      float floatValue2 = (enabled52 ? 1.0F : 0.0F) - (enabled62 ? 1.0F : 0.0F);
      Vec2f vec2f = new Vec2f(floatValue, floatValue2).normalize();
      ((InputAccessor) (Object) (player.input))._p1(vec2f);
      player.setSneaking(enabled102);
    }
  }

  public void apply3(MinecraftClient minecraftClient) {
    if (minecraftClient.interactionManager != null) {
      minecraftClient.interactionManager.cancelBlockBreaking();
    }
  }

  public boolean test() {
    return this.isEnabled()
        && this.enabled4
        && MinecraftClient.getInstance().player == this.clientPlayerEntity
        && MinecraftClient.getInstance().world == this.clientWorld;
  }

  public boolean test(double doubleValue) {
    if (this.test()
        && (Boolean) this.scrollToAdjustSpeedSetting.getValue()
        && doubleValue != 0.0
        && Double.isFinite(doubleValue)) {
      double doubleValue2 = (Double) this.speedSetting.getValue();
      double doubleValue3 = doubleValue2 * Math.pow(1.15, doubleValue);
      if (Math.abs(doubleValue3 - doubleValue2) < 0.1) {
        doubleValue3 = doubleValue2 + Math.signum(doubleValue) * 0.1;
      }

      this.speedSetting.setNumber(Double.valueOf(doubleValue3));
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.player != null) {
        instance.player.sendMessage(
            Text.literal("Freecam speed " + this.speedSetting.getFormattedValue()), true);
      }

      return true;
    } else {
      return false;
    }
  }

  public boolean test4() {
    return (Boolean) this.showOwnBodySetting.getValue();
  }

  public boolean test5() {
    return (Boolean) this.showHandsSetting.getValue();
  }

  public boolean test6() {
    return !this.test() || this.test5();
  }

  public double computeDouble(float floatValue) {
    return this.value;
  }

  public double computeDouble2(float floatValue) {
    return this.value2;
  }

  public double computeDouble3(float floatValue) {
    return this.value3;
  }

  public float computeFloat(float floatValue) {
    return this.amount;
  }

  public float computeFloat2(float floatValue) {
    return this.amount2;
  }

  public Vec3d getVec3d(float floatValue) {
    return new Vec3d(
        this.computeDouble(floatValue),
        this.computeDouble2(floatValue),
        this.computeDouble3(floatValue));
  }

  public void apply(float floatValue, float floatValue2) {
    this.amount = MathHelper.wrapDegrees(floatValue);
    this.amount2 = MathHelper.clamp(floatValue2, -90.0F, 90.0F);
  }

  public float getAmount() {
    return this.amount;
  }

  public float getAmount2() {
    return this.amount2;
  }

  public float computeFloat() {
    return this.lookSensitivitySetting.floatValue();
  }
}
