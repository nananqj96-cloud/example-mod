package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import bunnythekenzo.client.CameraChunkCulling;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class FreeLookModule extends Module {
  public static FreeLookModule freeLookModule;
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode",
                  "Which entity the mouse rotates.",
                  "Player",
                  new String[] {"Player", "Camera"}));
  public final BooleanSetting holdSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Hold", "Enable FreeLook only while its keybind is held.", true));
  public final BooleanSetting togglePerspectiveSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Toggle Perspective", "Switch to third person on toggle.", true));
  public final BooleanSetting throughWallsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Through Walls",
                  "See through walls — the third-person camera ignores wall collision.",
                  false));
  public final NumberSetting cameraDistanceSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Camera Distance",
                  "Third-person FreeLook distance.",
                  4.0,
                  1.0,
                  20.0,
                  0.5,
                  " blocks"));
  public final BooleanSetting scrollToAdjustDistanceSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Scroll to adjust distance",
                  "Use the mouse wheel to move the FreeLook camera closer or farther away.",
                  true));
  public final NumberSetting cameraSensitivitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Camera Sensitivity",
                  "How fast the camera moves in Camera mode.",
                  8.0,
                  0.0,
                  10.0,
                  0.1));
  public final BooleanSetting arrowsControlOppositeSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Arrows Control Opposite",
                  "Control the other entity's rotation with the arrow keys.",
                  true));
  public final NumberSetting arrowSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Arrow Speed", "Rotation speed with the arrow keys.", 4.0, 0.0, 10.0, 0.5));
  public ClientPlayerEntity clientPlayerEntity;
  public ClientWorld clientWorld;
  public float amount;
  public float amount2;
  public Perspective perspective;
  public boolean enabled;

  public FreeLookModule() {
    super("FreeLook", "Allows more rotation options in third person.", ModuleCategory.MISC);
    this.getKeybindSetting().setValue(342);
    freeLookModule = this;
  }

  public static FreeLookModule getInstance() {
    return freeLookModule;
  }

  @Override
  public void onEnable() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null) {
      this.clientPlayerEntity = instance.player;
      this.clientWorld = instance.world;
      this.amount = instance.player.getYaw();
      this.amount2 = instance.player.getPitch();
      this.perspective = instance.options.getPerspective();
      if (this.perspective != Perspective.THIRD_PERSON_BACK
          && (Boolean) this.togglePerspectiveSetting.getValue()) {
        instance.options.setPerspective(Perspective.THIRD_PERSON_BACK);
      }

      CameraChunkCulling.apply(instance);
    }
  }

  @Override
  public void onDisable() {
    MinecraftClient instance = MinecraftClient.getInstance();
    this.enabled = false;
    if (this.perspective != null
        && instance.options.getPerspective() != this.perspective
        && (Boolean) this.togglePerspectiveSetting.getValue()) {
      instance.options.setPerspective(this.perspective);
    }

    CameraChunkCulling.apply(instance);
  }

  @Override
  public void toggleFromKeybind() {
    if (!(Boolean) this.holdSetting.getValue()) {
      super.toggleFromKeybind();
    } else {
      this.enabled = true;
      this.setEnabled(true);
    }
  }

  @Override
  public boolean test2() {
    if (!this.enabled) {
      return false;
    }

    this.enabled = false;
    this.setEnabled(false);
    return true;
  }

  @Override
  public void onTick() {
    this.update4();
    if (this.test()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.player != null) {
        if ((Boolean) this.arrowsControlOppositeSetting.getValue()) {
          long handle = instance.getWindow().getHandle();
          boolean flag = GLFW.glfwGetKey(handle, 263) == 1;
          boolean flag2 = GLFW.glfwGetKey(handle, 262) == 1;
          boolean flag3 = GLFW.glfwGetKey(handle, 265) == 1;
          boolean flag4 = GLFW.glfwGetKey(handle, 264) == 1;
          int intValue = (int) ((Double) this.arrowSpeedSetting.getValue() * 2.0);

          for (int index = 0; index < intValue; index++) {
            if (this.modeSetting.isSelected("Player")) {
              if (flag) {
                this.amount -= 0.5F;
              }

              if (flag2) {
                this.amount += 0.5F;
              }

              if (flag3) {
                this.amount2 -= 0.5F;
              }

              if (flag4) {
                this.amount2 += 0.5F;
              }
            } else {
              float yaw = instance.player.getYaw();
              float pitch = instance.player.getPitch();
              if (flag) {
                yaw -= 0.5F;
              }

              if (flag2) {
                yaw += 0.5F;
              }

              if (flag3) {
                pitch -= 0.5F;
              }

              if (flag4) {
                pitch += 0.5F;
              }

              instance.player.setYaw(yaw);
              instance.player.setPitch(pitch);
            }
          }
        }

        instance.player.setPitch(MathHelper.clamp(instance.player.getPitch(), -90.0F, 90.0F));
        this.amount2 = MathHelper.clamp(this.amount2, -90.0F, 90.0F);
      }
    }
  }

  public void update4() {
    if (this.isEnabled() && !this.test()) {
      this.setEnabled(false);
    }
  }

  public boolean test() {
    return this.isEnabled()
        && this.clientPlayerEntity != null
        && MinecraftClient.getInstance().player == this.clientPlayerEntity
        && MinecraftClient.getInstance().world == this.clientWorld;
  }

  public boolean test4() {
    return this.test() && (Boolean) this.throughWallsSetting.getValue();
  }

  public boolean test5() {
    return this.test() && this.modeSetting.isSelected("Camera");
  }

  public boolean test6() {
    return this.test()
        && MinecraftClient.getInstance().options.getPerspective() == Perspective.THIRD_PERSON_BACK
        && this.modeSetting.isSelected("Player");
  }

  public float computeFloat(float floatValue) {
    return floatValue * this.cameraDistanceSetting.floatValue() / 4.0F;
  }

  public boolean test(double doubleValue) {
    if (this.test()
        && (Boolean) this.scrollToAdjustDistanceSetting.getValue()
        && Double.isFinite(doubleValue)
        && doubleValue != 0.0) {
      FreecamModule instance = FreecamModule.getInstance();
      if (instance != null && instance.test()) {
        return false;
      }

      this.cameraDistanceSetting.setNumber(
          Double.valueOf((Double) this.cameraDistanceSetting.getValue() - doubleValue * 0.5));
      MinecraftClient instance2 = MinecraftClient.getInstance();
      if (instance2.player != null) {
        instance2.player.sendMessage(
            Text.literal("FreeLook distance " + this.cameraDistanceSetting.getFormattedValue()),
            true);
      }

      return true;
    } else {
      return false;
    }
  }

  public void apply(double doubleValue, double doubleValue2) {
    float value = this.cameraSensitivitySetting.floatValue();
    if (value <= 0.0F) {
      value = 1.0F;
    }

    this.amount += (float) (doubleValue / value);
    this.amount2 += (float) (doubleValue2 / value);
    if (Math.abs(this.amount2) > 90.0F) {
      this.amount2 = this.amount2 > 0.0F ? 90.0F : -90.0F;
    }
  }

  public float getAmount() {
    return this.amount;
  }

  public float getAmount2() {
    return this.amount2;
  }
}
