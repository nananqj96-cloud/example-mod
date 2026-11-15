package bunnythekenzo.client.module;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.MinecraftClient;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.StringSetting;

public class AutoTPAModule extends Module {
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode", "Which request to send.", "TPA", new String[] {"TPA", "TPAHere"}));
  public final StringSetting targetSetting;
  public final NumberSetting delaySetting;
  public final NumberSetting humanizeSetting;
  public final BooleanSetting notifySetting;
  public long timestamp;
  public String string;
  public static final long timestamp2 = -3625371638171697120L;

  public AutoTPAModule() {
    super("AutoTPA", "Spams TPA requests at a target on a humanized timer.", ModuleCategory.MISC);
    this.targetSetting =
        (StringSetting)
            this.registerSetting(
                new StringSetting("Target", "Player to send the request to.", "", 32, "Steve"));
    this.delaySetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Delay",
                    "Time between requests — lower is faster.",
                    2000.0,
                    250.0,
                    10000.0,
                    50.0,
                    "ms"));
    this.humanizeSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Humanize",
                    "Random +/- swing on each delay so the timing isn't a fixed, bot-like interval."
                        + " 0 = off.",
                    25.0,
                    0.0,
                    60.0,
                    5.0,
                    "%"));
    this.notifySetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Notify", "Show a notification each time a request is sent.", false));
    this.timestamp = -1L;
  }

  @Override
  public void onEnable() {
    FakeLootDropModule.ensureInitialized();
    if (((String) this.targetSetting.getValue()).trim().isEmpty()) {
      ClientInitializer.getNotificationsHud().apply("AutoTPA · set a Target first");
    }

    this.string = null;
    this.timestamp = 0L;
  }

  @Override
  public void onDisable() {
    this.timestamp = -1L;
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();
    if (this.timestamp >= 0L) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.player != null && instance.player.networkHandler != null) {
        if (System.currentTimeMillis() >= this.timestamp) {
          String text = ((String) this.targetSetting.getValue()).trim();
          if (text.isEmpty()) {
            this.timestamp = this.computeLong();
          } else {
            String string2 = (this.modeSetting.isSelected("TPAHere") ? "tpahere " : "tpa ") + text;
            instance.player.networkHandler.sendChatCommand(string2);
            this.string = string2;
            if ((Boolean) this.notifySetting.getValue()) {
              ClientInitializer.getNotificationsHud().apply("AutoTPA · /" + string2);
            }

            this.timestamp = this.computeLong();
          }
        }
      }
    }
  }

  public String getString() {
    return this.string;
  }

  public long computeLong() {
    double doubleValue = (Double) this.delaySetting.getValue();
    FakeLootDropModule.ensureInitialized();

    double doubleValue2 = (Double) this.humanizeSetting.getValue() / 100.0;
    double doubleValue3 =
        doubleValue2 <= 0.0
            ? 1.0
            : 1.0 + (ThreadLocalRandom.current().nextDouble() * 2.0 - 1.0) * doubleValue2;
    long longValue = Math.max(0L, Math.round(doubleValue * doubleValue3));
    return System.currentTimeMillis() + longValue;
  }
}
