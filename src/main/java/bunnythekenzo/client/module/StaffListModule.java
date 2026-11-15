package bunnythekenzo.client.module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.ChatMessageBuilder;
import bunnythekenzo.client.ClientData;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.StaffDetector;
import bunnythekenzo.client.StaffDetectorData;
import bunnythekenzo.client.StaffRosterTracker;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.StringSetting;

public class StaffListModule extends Module {
  public final ChoiceSetting detectBySetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Detect By",
                  "How staff are recognised: the DonutSMP coloured star, a text rank prefix, or"
                      + " both",
                  "Star + Rank",
                  new String[] {"Star + Rank", "Star Only", "Rank Only", "Names Only"}));
  public final StringSetting staffNamesSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Staff Names",
                  "Extra known staff usernames (comma-separated) — always shown when online",
                  "",
                  220,
                  "e.g. Notch, jeb_"));
  public final StringSetting rankKeywordsSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Rank Keywords",
                  "Words in a name tag that mark staff (comma-separated), most senior first",
                  StaffDetector.string6,
                  256,
                  "owner, admin, mod…"));
  public final StringSetting starSymbolsSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Star Symbols",
                  "Marker glyphs that mean 'staff'. Paste the server's star here if detection"
                      + " misses",
                  "★☆✦✧✪✩✫✬✭✮✯⭐✰❂⚝✴✵✶✷✸✹⍟",
                  96,
                  "★ ✦ ⭐"));
  public final BooleanSetting fontIconsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Font Icons",
                  "Also treat custom resource-pack icons (private-use glyphs) as staff stars",
                  true));
  public final BooleanSetting showRankSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Show Rank",
                  "Show each staff member's rank label when the server exposes one",
                  true));
  public final BooleanSetting showPingSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Show Ping", "Show each staff member's latency", false));
  public final BooleanSetting vanishedStaffSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Vanished Staff",
                  "Include soft-vanished staff (spectator / hidden from tab), marked separately",
                  true));
  public final NumberSetting maxRowsSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Max Rows",
                  "Most staff rows to show before collapsing into a '+N more' line",
                  6.0,
                  1.0,
                  20.0,
                  1.0));
  public final ChoiceSetting alertsSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Alerts",
                  "Announce when a new staff member appears in your tab",
                  "Toast",
                  new String[] {"Toast", "Chat", "Off"}));
  public final BooleanSetting alertSoundSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Alert Sound", "Play a bell when a staff alert fires", true));
  public final StaffRosterTracker staffRosterTracker = new StaffRosterTracker(this);
  public static String[] stringArray;

  public StaffListModule() {
    super(
        "StaffList",
        "Lists online staff on the HUD (DonutSMP coloured-star detection)",
        ModuleCategory.MISC);
    this.rankKeywordsSetting.setVisibilityCondition(this::test4);
    this.starSymbolsSetting.setVisibilityCondition(this::test);
    this.fontIconsSetting.setVisibilityCondition(this::test);
    this.alertSoundSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return !this.alertsSetting.isSelected("Off");
        });
  }

  public boolean test() {
    FakeLootDropModule.ensureInitialized();
    return this.detectBySetting.isSelected("Star + Rank")
        || this.detectBySetting.isSelected("Star Only");
  }

  public boolean test4() {
    FakeLootDropModule.ensureInitialized();
    return this.detectBySetting.isSelected("Star + Rank")
        || this.detectBySetting.isSelected("Rank Only");
  }

  @Override
  public void onEnable() {
    this.staffRosterTracker.update();
  }

  @Override
  public void onDisable() {
    this.staffRosterTracker.update2();
  }

  @Override
  public void onTick() {
    this.staffRosterTracker.update3();
  }

  public List getList2() {
    return this.staffRosterTracker.getList();
  }

  public static boolean test(String text) {
    FakeLootDropModule.ensureInitialized();
    if (text != null
        && ClientInitializer.getModuleManager() != null
        && ClientInitializer.getModuleManager().staffListModule != null) {
      StaffListModule staffListModule = ClientInitializer.getModuleManager().staffListModule;

      for (ClientData clientData :
          (java.lang.Iterable<ClientData>) (java.lang.Iterable<?>) (staffListModule.getList2())) {
        if (clientData.string().equalsIgnoreCase(text)) {
          return true;
        }
      }

      return getSet((String) staffListModule.staffNamesSetting.getValue())
          .contains(text.toLowerCase(Locale.ROOT));
    } else {
      return false;
    }
  }

  public static boolean test(UUID uuid) {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    return uuid != null && instance.world != null && instance.world.getPlayerByUuid(uuid) != null;
  }

  public static boolean test2(UUID uuid) {
    return false;
  }

  public static ChatMessageBuilder getChatMessageBuilder(
      ChatMessageBuilder chatMessageBuilder, String text) {
    FakeLootDropModule.ensureInitialized();
    return chatMessageBuilder
        .getChatMessageBuilder("switched to ")
        .getChatMessageBuilder3(text == null ? GameMode.DEFAULT.getId() : text);
  }

  public StaffDetectorData getStaffDetectorData() {
    List list = getList((String) this.rankKeywordsSetting.getValue());
    return new StaffDetectorData(
        (String) this.detectBySetting.getValue(),
        getSet((String) this.staffNamesSetting.getValue()),
        list.isEmpty() ? StaffDetector.list : list,
        (String) this.starSymbolsSetting.getValue(),
        (Boolean) this.fontIconsSetting.getValue(),
        (Boolean) this.vanishedStaffSetting.getValue());
  }

  public void apply(ClientData clientData) {
    FakeLootDropModule.ensureInitialized();
    ClientPlayerEntity clientPlayerEntity = null;
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null && !this.alertsSetting.isSelected("Off")) {
      String text = clientData.string2().isEmpty() ? "Staff" : clientData.string2();
      String text2 = clientData.enabled() ? " (vanished)" : "";
      if (this.alertsSetting.isSelected("Toast")) {
        if (ClientInitializer.getNotificationsHud() != null) {
          ClientInitializer.getNotificationsHud()
              .apply(text + " " + clientData.string() + " online" + text2);
        }
      } else if (this.alertsSetting.isSelected("Chat")) {
        clientPlayerEntity = instance.player;
        String text3 = clientData.string();
        String text4 = text2;
        String text5 = text3;
        String text6 = text;
        clientPlayerEntity.sendMessage(
            Text.literal("§d[bunnythekenzo] §f" + text6 + " §b" + text5 + "§7 is online" + text4), false);
      }

      if ((Boolean) this.alertSoundSetting.getValue()) {
        instance
            .getSoundManager()
            .play(
                PositionedSoundInstance.ui(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 1.5F, 0.6F));
      }
    }
  }

  public static Set getSet(String text) {
    HashSet hashSet = new HashSet();
    FakeLootDropModule.ensureInitialized();

    for (String text2 : text.split("[,\\s]+")) {
      if (!text2.isEmpty()) {
        hashSet.add(text2.toLowerCase(Locale.ROOT));
      }
    }

    return hashSet;
  }

  public static List getList(String text) {
    ArrayList arrayList = new ArrayList();
    String[] textArray = text.split("[,\\s]+");
    int length = textArray.length;
    FakeLootDropModule.ensureInitialized();

    for (int index = 0; index < length; index++) {
      String text2 = textArray[index];
      String text3 = text2.trim().toLowerCase(Locale.ROOT);
      if (!text3.isEmpty()) {
        arrayList.add(text3);
      }
    }

    return arrayList;
  }

  public static void setStringArray(String[] stringArray2) {
    stringArray = stringArray2;
  }

  public static String[] getStringArray() {
    return stringArray;
  }

  static {
    String[] textArray = new String[5];

    setStringArray(textArray);
  }
}
