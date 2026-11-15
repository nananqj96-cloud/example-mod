package bunnythekenzo.client.module;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.NameProtectSupport;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.StringSetting;

public class NameProtectModule extends Module {
  public final StringSetting yourAliasSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Your Alias", "What your own name is replaced with", "You", 16, "You"));
  public final ChoiceSetting othersSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Others",
                  "How other players' names are replaced",
                  "Aliases",
                  new String[] {"Aliases", "Blank", "Player #"}));
  public final BooleanSetting selfOnlySetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Self Only", "Only hide your own name, leave others alone", false));
  public final LinkedHashMap linkedHashMap = new NameProtectSupport(this, 16, 0.75F, true);
  public String string = null;
  public Map map = Map.of();
  public Pattern pattern = null;
  public ClientPlayNetworkHandler clientPlayNetworkHandler = null;
  public static int count;

  public NameProtectModule() {
    super("NameProtect", "Hides player names in clips", ModuleCategory.MISC);
  }

  public String getString() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    return instance.player == null ? null : instance.player.getGameProfile().name();
  }

  public boolean test(String text) {
    FakeLootDropModule.ensureInitialized();

    String string2 = this.getString();
    return string2 != null && string2.equalsIgnoreCase(text);
  }

  public String getString(String text) {
    FakeLootDropModule.ensureInitialized();
    if (!this.test(text)) {
      if (this.othersSetting.isSelected("Blank")) {
        return "";
      } else {
        return this.othersSetting.isSelected("Player #")
            ? "Player " + (Math.floorMod(text.toLowerCase(Locale.ROOT).hashCode(), 99) + 1)
            : "Player";
      }
    } else {
      String text2 = (String) this.yourAliasSetting.getValue();
      return text2 != null && !text2.isBlank() ? text2 : "You";
    }
  }

  public Map buildTargets() {
    FakeLootDropModule.ensureInitialized();

    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientPlayNetworkHandler networkHandler = instance.getNetworkHandler();
    if (networkHandler != this.clientPlayNetworkHandler) {
      this.linkedHashMap.clear();
      this.clientPlayNetworkHandler = networkHandler;
    }

    String string2 = this.getString();
    if (string2 != null && !string2.isEmpty()) {
      this.linkedHashMap.put(string2.toLowerCase(Locale.ROOT), string2);
      linkedHashMap2.put(string2, this.getString(string2));
    }

    boolean flag = !(Boolean) this.selfOnlySetting.getValue();
    if (networkHandler != null) {
      for (PlayerListEntry playerListEntry : networkHandler.getPlayerList()) {
        GameProfile profile = playerListEntry.getProfile();
        String text = profile == null ? null : profile.name();
        if (text != null && !text.isEmpty()) {
          this.linkedHashMap.put(text.toLowerCase(Locale.ROOT), text);
          if (flag) {
            linkedHashMap2.putIfAbsent(text, this.getString(text));
          }
        }
      }
    }

    if (flag) {
      for (String text2 :
          (java.lang.Iterable<String>)
              (java.lang.Iterable<?>) (new ArrayList(this.linkedHashMap.values()))) {
        linkedHashMap2.putIfAbsent(text2, this.getString(text2));
      }
    }

    linkedHashMap2
        .entrySet()
        .removeIf(
            value2 -> {
              return ((String) ((java.util.Map.Entry) (value2)).getKey())
                  .equalsIgnoreCase((String) ((java.util.Map.Entry) (value2)).getValue());
            });
    return linkedHashMap2;
  }

  public void update4() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    int intValue = instance.player == null ? -1 : instance.player.age;
    String string2 =
        intValue
            + "|"
            + this.selfOnlySetting.getValue()
            + "|"
            + (String) this.othersSetting.getValue()
            + "|"
            + (String) this.yourAliasSetting.getValue();
    if (!string2.equals(this.string)) {
      this.string = string2;
      this.map = this.buildTargets();
      this.pattern = buildPattern(this.map);
    }
  }

  public static Pattern buildPattern(Map map2) {
    FakeLootDropModule.ensureInitialized();
    if (map2.isEmpty()) {
      return null;
    }

    ArrayList arrayList = new ArrayList(map2.keySet());
    ((java.util.List) (arrayList))
        .sort(
            (value, value2) -> {
              return Integer.compare(
                  ((java.lang.String) (value2)).length(), ((java.lang.String) (value)).length());
            });
    StringBuilder builder = new StringBuilder("(?i)(?<![A-Za-z0-9_])(");

    for (int index = 0; index < arrayList.size(); index++) {
      if (index > 0) {
        builder.append('|');
      }

      builder.append(Pattern.quote((String) arrayList.get(index)));
    }

    builder.append(")(?![A-Za-z0-9_])");
    return Pattern.compile(builder.toString());
  }

  public String getString2(String text) {
    FakeLootDropModule.ensureInitialized();
    if (text != null && !text.isEmpty()) {
      this.update4();
      if (this.pattern == null) {
        return null;
      }

      String string3 = this.getString3(text);
      return string3.equals(text) ? null : string3;
    } else {
      return null;
    }
  }

  public Text getText(Text text) {
    FakeLootDropModule.ensureInitialized();
    if (text == null) {
      return null;
    }

    this.update4();
    return this.pattern == null ? text : this.getText2(text);
  }

  public Text getText2(Text text) {
    FakeLootDropModule.ensureInitialized();

    TextContent content = text.getContent();
    MutableText mutableText;
    if (content instanceof PlainTextContent plainTextContent) {
      mutableText = MutableText.of(PlainTextContent.of(this.getString3(plainTextContent.string())));
    } else if (content instanceof TranslatableTextContent translatableTextContent) {
      Object[] args = translatableTextContent.getArgs();
      Object[] valueArray = new Object[args.length];

      for (int index = 0; index < args.length; index++) {
        Object value2 = args[index];
        if (value2 instanceof Text text2) {
          valueArray[index] = this.getText2(text2);
        } else if (value2 instanceof String text3) {
          valueArray[index] = this.getString3(text3);
        } else {
          valueArray[index] = value2;
        }
      }

      mutableText =
          MutableText.of(
              new TranslatableTextContent(
                  translatableTextContent.getKey(),
                  translatableTextContent.getFallback(),
                  valueArray));
    } else {
      mutableText = MutableText.of(content);
    }

    mutableText.setStyle(text.getStyle());

    for (Text text4 : text.getSiblings()) {
      mutableText.append(this.getText2(text4));
    }

    return mutableText;
  }

  public String getString3(String text) {
    FakeLootDropModule.ensureInitialized();
    if (text != null && !text.isEmpty() && this.pattern != null) {
      Matcher matcher = this.pattern.matcher(text);
      if (!matcher.find()) {
        return text;
      }

      StringBuilder builder = new StringBuilder(text.length());
      int intValue = 0;

      do {
        String text2 = matcher.group(1);
        String string4 = this.getString4(text2);
        builder.append(text, intValue, matcher.start());
        builder.append(string4 != null ? string4 : text2);
        intValue = matcher.end();
      } while (matcher.find());

      builder.append(text, intValue, text.length());
      return builder.toString();
    } else {
      return text;
    }
  }

  public String getString4(String text) {
    FakeLootDropModule.ensureInitialized();

    String text2 = (String) this.map.get(text);
    if (text2 != null) {
      return text2;
    }

    for (Entry entry : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (this.map.entrySet())) {
      if (((String) entry.getKey()).equalsIgnoreCase(text)) {
        return (String) entry.getValue();
      }
    }

    return null;
  }

  public static void setCount(int count2) {
    count = count2;
  }

  public static int getCount() {
    return count;
  }

  public static int computeInt() {
    int intValue = getCount();
    return intValue == 0 ? 108 : 0;
  }

  static {
    setCount(0);
  }
}
