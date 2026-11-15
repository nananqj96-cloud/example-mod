package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class AutoClickerModule extends Module {
  public final BooleanSetting whileInScreensSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "While In Screens", "Whether to click while a screen is open.", true));
  public final ChoiceSetting leftClickModeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Left Click Mode",
                  "The method of clicking for left clicks.",
                  "Press",
                  new String[] {"Disabled", "Hold", "Press"}));
  public final NumberSetting leftClickDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Left Click Delay",
                  "Delay between left clicks in ticks.",
                  2.0,
                  0.0,
                  60.0,
                  1.0,
                  " ticks"));
  public final ChoiceSetting rightClickModeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Right Click Mode",
                  "The method of clicking for right clicks.",
                  "Press",
                  new String[] {"Disabled", "Hold", "Press"}));
  public final NumberSetting rightClickDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Right Click Delay",
                  "Delay between right clicks in ticks.",
                  2.0,
                  0.0,
                  60.0,
                  1.0,
                  " ticks"));
  public int count;
  public int count2;
  public static final long timestamp = -8465089680935737584L;

  public AutoClickerModule() {
    super("AutoClicker", "Automatically clicks.", ModuleCategory.MISC);
    this.leftClickDelaySetting.setVisibilityCondition(
        () -> {
          return this.leftClickModeSetting.isSelected("Press");
        });
    this.rightClickDelaySetting.setVisibilityCondition(
        () -> {
          return this.rightClickModeSetting.isSelected("Press");
        });
  }

  @Override
  public void onEnable() {
    this.count = 0;
    this.count2 = 0;
    this.update4();
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.options != null) {
      instance.options.attackKey.setPressed(false);
      instance.options.useKey.setPressed(false);
    }
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null) {
      if ((Boolean) this.whileInScreensSetting.getValue() || instance.currentScreen == null) {
        switch ((String) this.leftClickModeSetting.getValue()) {
          case "Hold":
            instance.options.attackKey.setPressed(true);
            break;
          case "Press":
            this.count++;
            if (this.count > this.leftClickDelaySetting.intValue()) {
              this.apply(instance);
              this.count = 0;
            }
        }

        switch ((String) this.rightClickModeSetting.getValue()) {
          case "Hold":
            instance.options.useKey.setPressed(true);
            break;
          case "Press":
            this.count2++;
            if (this.count2 > this.rightClickDelaySetting.intValue()) {
              this.apply2(instance);
              this.count2 = 0;
            }
        }
      }
    }
  }

  public void apply(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.attackCooldown == 10000) {
      minecraftClient.attackCooldown = 0;
    }

    minecraftClient.options.attackKey.setPressed(true);
    ((MinecraftClientAccessor) (Object) (minecraftClient)).invokeDoAttack();
    minecraftClient.options.attackKey.setPressed(false);
  }

  public void apply2(MinecraftClient minecraftClient) {
    ((MinecraftClientAccessor) (Object) (minecraftClient)).invokeDoItemUse();
  }
}
