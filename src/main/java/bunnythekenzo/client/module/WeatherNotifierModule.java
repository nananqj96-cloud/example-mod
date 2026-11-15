package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.WeatherState;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;

public class WeatherNotifierModule extends Module {
  public final BooleanSetting rainSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Rain", "Announce when rain starts/stops", true));
  public final BooleanSetting thunderSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Thunder", "Announce when a thunderstorm starts/stops", true));
  public final BooleanSetting soundSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Sound", "Play a ping on each change", true));
  public final ChoiceSetting outputSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Output",
                  "How the change is shown",
                  "Notification",
                  new String[] {"Notification", "Chat", "Action Bar"}));
  public Boolean booleanValue;
  public Boolean boolean2;

  public WeatherNotifierModule() {
    super("WeatherNotifier", "Themed toast when the weather changes", ModuleCategory.MISC);
  }

  @Override
  public void onEnable() {
    this.booleanValue = null;
    this.boolean2 = null;
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    ClientWorld world = instance.world;
    if (world != null && instance.player != null) {
      boolean raining = world.isRaining();
      boolean thundering = world.isThundering();
      if (this.booleanValue != null && this.boolean2 != null) {
        if (thundering != this.boolean2) {
          this.boolean2 = thundering;
          if ((Boolean) this.thunderSetting.getValue()) {
            if (thundering) {
              this.apply(instance, "Thunderstorm", "A storm rolls in", WeatherState.THUNDER, true);
            } else {
              this.apply(
                  instance, "Storm cleared", "The thunder has passed", WeatherState.CLEAR, false);
            }
          }
        }

        if (raining != this.booleanValue) {
          this.booleanValue = raining;
          if ((Boolean) this.rainSetting.getValue() && !thundering) {
            if (raining) {
              this.apply(instance, "Rain", "Rain starts to fall", WeatherState.RAIN, true);
            } else {
              this.apply(
                  instance, "Skies cleared", "The rain has stopped", WeatherState.CLEAR, false);
            }
          }
        }
      } else {
        this.booleanValue = raining;
        this.boolean2 = thundering;
      }
    }
  }

  public void apply(
      MinecraftClient minecraftClient,
      String text,
      String text2,
      WeatherState weatherState,
      boolean flag) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.player != null) {
      if (this.outputSetting.isSelected("Notification")) {
        if (ClientInitializer.getNotificationsHud() != null) {
          ClientInitializer.getNotificationsHud().apply(text, text2, weatherState, flag);
        }
      } else {
        boolean flag2 = this.outputSetting.isSelected("Action Bar");
        minecraftClient.player.sendMessage(Text.literal("§d[bunnythekenzo] §f" + text + " — " + text2), flag2);
      }

      if ((Boolean) this.soundSetting.getValue()) {
        float floatValue = flag ? 1.2F : 0.8F;
        minecraftClient
            .getSoundManager()
            .play(
                PositionedSoundInstance.ui(
                    SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), floatValue, 0.6F));
      }
    }
  }
}
