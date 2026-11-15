package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.CompactNumberFormat;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.StringSetting;

public class FakePayModule extends Module {

  public final StringSetting commandSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Command", "Command name to hijack, without the slash.", "pay", 32, "pay"));
  public final ChoiceSetting feedbackSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Feedback",
                  "Where the confirmation shows.",
                  "Both",
                  new String[] {"Both", "Action Bar", "Chat"}));
  public final StringSetting currencySetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting("Currency", "Symbol before the amount (colored).", "$", 4, "$"));
  public final ColorSetting currencyColorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting(
                  "Currency Color",
                  "Color of the currency symbol (DonutSMP blue by default).",
                  -16740609));
  public final BooleanSetting soundsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Sounds", "Level-up on success, villager 'no' when it fails.", true));
  public final BooleanSetting checkBalanceSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Check Balance", "With FakeStats on, refuse pays you can't afford.", true));
  public final BooleanSetting selfPayGuardSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Self-Pay Guard", "Block paying your own name, like the real command.", true));

  public FakePayModule() {
    super("FakePay", "Fakes a /pay for clips — blocks the real command", ModuleCategory.MISC);
  }

  public boolean test(String text) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled() && text != null) {
      String[] textArray = text.trim().split("\\s+");
      if (textArray.length == 0) {
        return false;
      }

      String text2 = textArray[0];
      if (text2.startsWith("/")) {
        text2 = text2.substring(1);
      }

      String text3 = ((String) this.commandSetting.getValue()).trim();
      if (!text3.isEmpty() && text2.equalsIgnoreCase(text3)) {
        this.apply(textArray);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public void apply(String[] textArray) {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null) {
      if (textArray.length < 3) {
        this.apply(
            instance,
            Text.literal("Usage: /" + textArray[0] + " <player> <amount>").withColor(16733525));
        this.apply2(instance);
      } else {
        String text = textArray[1];
        double value2 = CompactNumberFormat.computeDouble(textArray[2]);
        if (Double.isNaN(value2) || value2 <= 0.0) {
          this.apply(instance, Text.literal("Invalid amount: " + textArray[2]).withColor(16733525));
          this.apply2(instance);
        } else if ((Boolean) this.selfPayGuardSetting.getValue()
            && text.equalsIgnoreCase(instance.player.getGameProfile().name())) {
          this.apply(instance, Text.literal("You can't pay yourself!").withColor(16733525));
          this.apply2(instance);
        } else {
          FakeStatsModule fakeStatsModule = this.getFakeStatsModule();
          boolean flag =
              fakeStatsModule != null
                  && fakeStatsModule.isEnabled()
                  && (Boolean) fakeStatsModule.deductOnPaySetting.getValue();
          if (flag
              && (Boolean) this.checkBalanceSetting.getValue()
              && fakeStatsModule.computeDouble() < value2) {
            this.apply(instance, Text.literal("You don't have enough money!").withColor(16733525));
            this.apply2(instance);
          } else {
            if (flag) {
              fakeStatsModule.apply(value2);
            }

            MutableText mutableText =
                Text.empty().append(Text.literal("You paid " + text).withColor(16777215));
            String text2 = (String) this.currencySetting.getValue();
            if (!text2.isEmpty()) {
              mutableText
                  .append(
                      Text.literal(" " + text2)
                          .withColor(this.currencyColorSetting.getInteger() & 16777215))
                  .append(Text.literal(CompactNumberFormat.getString(value2)).withColor(16777215));
            } else {
              mutableText.append(
                  Text.literal(" " + CompactNumberFormat.getString(value2)).withColor(16777215));
            }

            this.apply(instance, mutableText);
            this.apply(instance);
          }
        }
      }
    }
  }

  public void apply(MinecraftClient minecraftClient, Text text) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.player != null) {
      String text2 = (String) this.feedbackSetting.getValue();
      if (text2.equals("Action Bar") || text2.equals("Both")) {
        minecraftClient.player.sendMessage(text, true);
      }

      if (text2.equals("Chat") || text2.equals("Both")) {
        minecraftClient.player.sendMessage(text, false);
      }
    }
  }

  public void apply(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if ((Boolean) this.soundsSetting.getValue()) {
      this.render(minecraftClient, SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F);
    }
  }

  public void apply2(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if ((Boolean) this.soundsSetting.getValue()) {
      this.render(minecraftClient, SoundEvents.ENTITY_VILLAGER_NO, 1.0F);
    }
  }

  public void render(MinecraftClient minecraftClient, SoundEvent soundEvent, float floatValue) {
    minecraftClient
        .getSoundManager()
        .play(PositionedSoundInstance.ui(soundEvent, floatValue, 1.0F));
  }

  public FakeStatsModule getFakeStatsModule() {
    FakeLootDropModule.ensureInitialized();

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager == null ? null : moduleManager.fakeStatsModule;
  }
}
