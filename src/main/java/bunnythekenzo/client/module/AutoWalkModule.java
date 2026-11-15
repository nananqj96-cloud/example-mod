package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.client.util.InputUtil.Type;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.mixin.KeyBindingAccessor;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;

public class AutoWalkModule extends Module {
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode", "Walking mode.", "Simple", new String[] {"Simple", "Smart"}));
  public final ChoiceSetting directionSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Direction",
                  "The direction to walk in Simple mode.",
                  "Forwards",
                  new String[] {"Forwards", "Backwards", "Left", "Right"}));
  public final BooleanSetting disableOnInputSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Disable On Input", "Disable the module on manual movement input.", false));
  public final BooleanSetting disableOnYChangeSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Disable On Y Change", "Disable the module if you move vertically.", false));
  public final BooleanSetting noUnloadedChunksSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("No Unloaded Chunks", "Do not walk into unloaded chunks.", true));

  public AutoWalkModule() {
    super("AutoWalk", "Automatically walks forward.", ModuleCategory.MISC);
    this.directionSetting.setVisibilityCondition(
        () -> {
          return this.modeSetting.isSelected("Simple");
        });
    this.disableOnYChangeSetting.setVisibilityCondition(
        () -> {
          return this.modeSetting.isSelected("Simple");
        });
    this.noUnloadedChunksSetting.setVisibilityCondition(
        () -> {
          return this.modeSetting.isSelected("Simple");
        });
  }

  @Override
  public void onDisable() {
    this.apply(MinecraftClient.getInstance());
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null && instance.world != null) {
      this.apply(instance);
      if (this.modeSetting.isSelected("Smart")) {
        instance.options.forwardKey.setPressed(true);
        instance.options.sprintKey.setPressed(true);
        if (instance.player.horizontalCollision && instance.player.isOnGround()) {
          instance.options.jumpKey.setPressed(true);
        }
      } else if ((Boolean) this.disableOnYChangeSetting.getValue()
          && instance.player.lastY != instance.player.getY()) {
        this.toggle();
      } else if (!(Boolean) this.noUnloadedChunksSetting.getValue() || this.test(instance)) {
        switch ((String) this.directionSetting.getValue()) {
          case "Forwards":
            instance.options.forwardKey.setPressed(true);
            break;
          case "Backwards":
            instance.options.backKey.setPressed(true);
            break;
          case "Left":
            instance.options.leftKey.setPressed(true);
            break;
          case "Right":
            instance.options.rightKey.setPressed(true);
        }
      }
    } else {
      this.apply(instance);
    }
  }

  @Override
  public boolean onKeyPress(int intValue) {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if ((Boolean) this.disableOnInputSetting.getValue()
        && instance.currentScreen == null
        && this.test(instance, intValue)) {
      this.toggle();
    }

    return false;
  }

  public boolean test(MinecraftClient minecraftClient) {
    double doubleValue = Math.toRadians(minecraftClient.player.getYaw());
    double doubleValue2 = -Math.sin(doubleValue);
    double doubleValue3 = Math.cos(doubleValue);
    String text = (String) this.directionSetting.getValue();
    FakeLootDropModule.ensureInitialized();
    double doubleValue4;
    double doubleValue5;
    switch (text) {
      case "Backwards":
        doubleValue4 = -doubleValue2;
        doubleValue5 = -doubleValue3;
        break;
      case "Left":
        doubleValue4 = -doubleValue3;
        doubleValue5 = doubleValue2;
        break;
      case "Right":
        doubleValue4 = doubleValue3;
        doubleValue5 = -doubleValue2;
        break;
      default:
        doubleValue4 = doubleValue2;
        doubleValue5 = doubleValue3;
    }

    int intValue = (int) Math.floor(minecraftClient.player.getX() + doubleValue4 * 2.0);
    int intValue2 = (int) Math.floor(minecraftClient.player.getZ() + doubleValue5 * 2.0);
    return minecraftClient.world.getChunkManager().isChunkLoaded(intValue >> 4, intValue2 >> 4);
  }

  public void apply(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.options != null) {
      minecraftClient.options.forwardKey.setPressed(false);
      minecraftClient.options.backKey.setPressed(false);
      minecraftClient.options.leftKey.setPressed(false);
      minecraftClient.options.rightKey.setPressed(false);
      minecraftClient.options.jumpKey.setPressed(false);
      minecraftClient.options.sprintKey.setPressed(false);
    }
  }

  public boolean test(MinecraftClient minecraftClient, int intValue) {
    FakeLootDropModule.ensureInitialized();

    GameOptions options = minecraftClient.options;
    return test(options.forwardKey, intValue)
        || test(options.backKey, intValue)
        || test(options.leftKey, intValue)
        || test(options.rightKey, intValue)
        || test(options.jumpKey, intValue)
        || test(options.sneakKey, intValue);
  }

  public static boolean test(KeyBinding keyBinding, int intValue) {
    FakeLootDropModule.ensureInitialized();

    Key key = ((KeyBindingAccessor) (Object) (keyBinding)).getBoundKey();
    return key.getCategory() == Type.KEYSYM && key.getCode() == intValue;
  }
}
