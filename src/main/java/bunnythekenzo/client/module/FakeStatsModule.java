package bunnythekenzo.client.module;

import java.util.Locale;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import bunnythekenzo.client.CompactNumberFormat;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.StringSetting;

public class FakeStatsModule extends Module {

  public static final String[] stringArray = new String[] {"money", "balance", "coins", "cash"};
  public static final String[] stringArray2 = new String[] {"shard"};
  public static final String[] stringArray3 = new String[] {"kill"};
  public static final String[] stringArray4 = new String[] {"death"};
  public static final String[] stringArray5 =
      new String[] {"playtime", "play time", "time played", "played"};
  public final StringSetting moneySetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Money",
                  "Your fake balance (1m, 67k, 250000). Wired to FakePay. Blank = off.",
                  "1m",
                  32,
                  "e.g. 1m, 250k"));
  public final NumberSetting moneyLineSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                      "Money Line",
                      "Which sidebar line is money (count from the top). 0 = auto by name.",
                      1.0,
                      0.0,
                      15.0,
                      1.0)
                  .withFormatter(FakeStatsModule::getString));
  public final StringSetting shardsSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting("Shards", "Fake shard count. Blank = off.", "", 32, "e.g. 9,999"));
  public final NumberSetting shardsLineSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                      "Shards Line",
                      "Which sidebar line is shards. 0 = auto by name.",
                      2.0,
                      0.0,
                      15.0,
                      1.0)
                  .withFormatter(FakeStatsModule::getString));
  public final StringSetting killsSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting("Kills", "Fake kill count. Blank = off.", "", 32, "e.g. 1,000"));
  public final NumberSetting killsLineSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                      "Kills Line",
                      "Which sidebar line is kills. 0 = auto by name.",
                      3.0,
                      0.0,
                      15.0,
                      1.0)
                  .withFormatter(FakeStatsModule::getString));
  public final StringSetting deathsSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting("Deaths", "Fake death count. Blank = off.", "", 32, "e.g. 0"));
  public final NumberSetting deathsLineSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                      "Deaths Line",
                      "Which sidebar line is deaths. 0 = auto by name.",
                      4.0,
                      0.0,
                      15.0,
                      1.0)
                  .withFormatter(FakeStatsModule::getString));
  public final StringSetting playtimeSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Playtime", "Fake playtime, any text. Blank = off.", "", 32, "e.g. 365d 12h"));
  public final NumberSetting playtimeLineSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                      "Playtime Line",
                      "Which sidebar line is playtime. 0 = auto by name.",
                      5.0,
                      0.0,
                      15.0,
                      1.0)
                  .withFormatter(FakeStatsModule::getString));
  public final BooleanSetting sidebarSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Sidebar", "Rewrite the server scoreboard (leaderboard) on the right.", true));
  public final BooleanSetting balanceCommandSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Balance Command", "Intercept /bal & /balance to show your fake balance.", true));
  public final BooleanSetting deductOnPaySetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Deduct On Pay", "FakePay subtracts what you pay from Money.", true));
  public String string;
  public double value;
  public int count3;
  public int count4;

  public FakeStatsModule() {
    super(
        "FakeStats", "Fake balance + editable leaderboard, wired to FakePay", ModuleCategory.MISC);
    this.moneyLineSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return (Boolean) this.sidebarSetting.getValue()
              && !((String) this.moneySetting.getValue()).isBlank();
        });
    this.shardsLineSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return (Boolean) this.sidebarSetting.getValue()
              && !((String) this.shardsSetting.getValue()).isBlank();
        });
    this.killsLineSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return (Boolean) this.sidebarSetting.getValue()
              && !((String) this.killsSetting.getValue()).isBlank();
        });
    this.deathsLineSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return (Boolean) this.sidebarSetting.getValue()
              && !((String) this.deathsSetting.getValue()).isBlank();
        });
    this.playtimeLineSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return (Boolean) this.sidebarSetting.getValue()
              && !((String) this.playtimeSetting.getValue()).isBlank();
        });
  }

  @Override
  public void onEnable() {
    this.string = null;
    this.update4();
  }

  public static String getString(double doubleValue) {
    FakeLootDropModule.ensureInitialized();
    return (int) doubleValue == 0 ? "Auto" : "Line " + (int) doubleValue;
  }

  public void update4() {
    FakeLootDropModule.ensureInitialized();

    String string2 = (String) this.moneySetting.getValue();
    if (!Objects.equals(string2, this.string)) {
      this.string = string2;
      double value3 = CompactNumberFormat.computeDouble(string2);
      this.value = Double.isNaN(value3) ? 0.0 : Math.max(0.0, value3);
    }
  }

  public double computeDouble() {
    this.update4();
    return this.value;
  }

  public void apply(double doubleValue) {
    this.update4();
    this.value = Math.max(0.0, this.value - Math.max(0.0, doubleValue));
  }

  public void update5() {
    this.count3 = 0;
    this.count4 = 0;
  }

  public Text getText(Text text) {
    return this.getText(text, true);
  }

  public Text getText2(Text text) {
    return this.getText(text, false);
  }

  public Text getText(Text text, boolean flag) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled() && (Boolean) this.sidebarSetting.getValue()) {
      String text2 = getString(text.getString());
      if (text2.isBlank()) {
        return text;
      }

      int intValue = flag ? this.count3++ : this.count4++;
      return this.getText(intValue, text2.toLowerCase(Locale.ROOT), text);
    } else {
      return text;
    }
  }

  public Text getText(int intValue, String text, Text text2) {
    FakeLootDropModule.ensureInitialized();
    if (!((String) this.moneySetting.getValue()).isBlank()
        && test(this.moneyLineSetting, intValue, text, stringArray)) {
      return CompactNumberFormat.getText(
          text2, CompactNumberFormat.getString(this.computeDouble()));
    } else if (!((String) this.shardsSetting.getValue()).isBlank()
        && test(this.shardsLineSetting, intValue, text, stringArray2)) {
      return CompactNumberFormat.getText(text2, ((String) this.shardsSetting.getValue()).trim());
    } else if (!((String) this.killsSetting.getValue()).isBlank()
        && test(this.killsLineSetting, intValue, text, stringArray3)) {
      return CompactNumberFormat.getText(text2, ((String) this.killsSetting.getValue()).trim());
    } else if (!((String) this.deathsSetting.getValue()).isBlank()
        && test(this.deathsLineSetting, intValue, text, stringArray4)) {
      return CompactNumberFormat.getText(text2, ((String) this.deathsSetting.getValue()).trim());
    } else {
      return !((String) this.playtimeSetting.getValue()).isBlank()
              && test(this.playtimeLineSetting, intValue, text, stringArray5)
          ? CompactNumberFormat.getText2(text2, ((String) this.playtimeSetting.getValue()).trim())
          : text2;
    }
  }

  public static boolean test(
      NumberSetting numberSetting6, int intValue, String text, String[] textArray) {
    FakeLootDropModule.ensureInitialized();

    int value3 = numberSetting6.intValue();
    return value3 > 0 ? intValue + 1 == value3 : test(text, textArray);
  }

  public static boolean test(String text, String[] textArray) {
    String[] textArray2 = textArray;
    FakeLootDropModule.ensureInitialized();

    for (String text2 : textArray2) {
      if (text.contains(text2)) {
        return true;
      }
    }

    return false;
  }

  public boolean test(String text) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled() && (Boolean) this.balanceCommandSetting.getValue()) {
      String[] textArray = text.trim().split("\\s+");
      if (textArray.length == 0) {
        return false;
      }

      String text2 = textArray[0];
      if (text2.startsWith("/")) {
        text2 = text2.substring(1);
      }

      if (!text2.equalsIgnoreCase("bal") && !text2.equalsIgnoreCase("balance")) {
        return false;
      }

      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.player == null) {
        return false;
      }

      if (textArray.length >= 2
          && !textArray[1].equalsIgnoreCase(instance.player.getGameProfile().name())) {
        return false;
      }

      String string2 = CompactNumberFormat.getString(this.computeDouble());
      MutableText mutableText =
          Text.empty()
              .append(Text.literal("You have ").withColor(11184810))
              .append(Text.literal(string2).withColor(5635925));
      instance.player.sendMessage(mutableText, false);
      instance.player.sendMessage(mutableText, true);
      return true;
    } else {
      return false;
    }
  }

  public static String getString(String text) {
    FakeLootDropModule.ensureInitialized();
    return text == null ? "" : text.replaceAll("(?i)§[0-9A-FK-OR]", "");
  }
}
