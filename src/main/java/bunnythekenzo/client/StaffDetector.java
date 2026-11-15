package bunnythekenzo.client;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public final class StaffDetector {

  public static final List list =
      List.of(
          "coowner",
          "owner",
          "manager",
          "administrator",
          "admin",
          "developer",
          "dev",
          "srmod",
          "seniormod",
          "moderator",
          "mod",
          "srhelper",
          "seniorhelper",
          "helper",
          "trialmod",
          "trial",
          "builder",
          "support",
          "staff");
  public static final String string6 = String.join(", ", list);
  public static final Map map =
      Map.ofEntries(
          Map.entry("coowner", "Co-Owner"),
          Map.entry("owner", "Owner"),
          Map.entry("manager", "Manager"),
          Map.entry("administrator", "Admin"),
          Map.entry("admin", "Admin"),
          Map.entry("developer", "Dev"),
          Map.entry("dev", "Dev"),
          Map.entry("srmod", "Sr.Mod"),
          Map.entry("seniormod", "Sr.Mod"),
          Map.entry("moderator", "Mod"),
          Map.entry("mod", "Mod"),
          Map.entry("srhelper", "Sr.Helper"),
          Map.entry("seniorhelper", "Sr.Helper"),
          Map.entry("helper", "Helper"),
          Map.entry("trialmod", "Trial"),
          Map.entry("trial", "Trial"),
          Map.entry("builder", "Builder"),
          Map.entry("support", "Support"),
          Map.entry("staff", "Staff"));
  public static ClientInitializer[] clientInitializerArray;

  public static ClientData getClientData(
      String string,
      Text text,
      Text text2,
      Text text3,
      String text4,
      boolean enabled,
      int count2,
      StaffDetectorData staffDetectorData) {
    ClientData.ensureInitialized();
    if (string != null && !string.isEmpty()) {
      boolean flag = staffDetectorData.set().contains(string.toLowerCase(Locale.ROOT));
      Integer intValue = scanMarker(text, staffDetectorData);
      if (intValue == null) {
        intValue = scanMarker(text2, staffDetectorData);
      }

      if (intValue == null) {
        intValue = scanMarker(text3, staffDetectorData);
      }

      boolean flag2 = intValue != null;
      String text5 =
          getString(text)
              + " "
              + getString(text2)
              + " "
              + getString(text3)
              + " "
              + (text4 == null ? "" : text4);
      StaffDetectorData2 staffDetectorData2 =
          getStaffDetectorData2(getString(text5, string), staffDetectorData.list());
      boolean flag3 = staffDetectorData2 != null;

      boolean flag4 =
          switch (staffDetectorData.string()) {
            case "Star Only" -> flag2;
            case "Rank Only" -> flag3;
            case "Names Only" -> false;
            default -> flag2 || flag3;
          };
      if (!flag && !flag4) {
        return null;
      }

      int count = flag2 ? intValue : 0;
      String string2 = flag3 ? staffDetectorData2.string() : "";
      int count3 = flag3 ? staffDetectorData2.count() : (flag2 ? 1 : 0);
      return new ClientData(string, string2, count, enabled, count2, count3);
    } else {
      return null;
    }
  }

  public static Integer scanMarker(Text text, StaffDetectorData staffDetectorData) {
    ClientData.ensureInitialized();
    return text == null
        ? null
        : text.<Integer>visit(
                (style, text2) -> {
                  ClientData.ensureInitialized();

                  int intValue = 0;

                  while (intValue < text2.length()) {
                    int intValue2 = text2.codePointAt(intValue);
                    intValue += Character.charCount(intValue2);
                    if (test(intValue2, staffDetectorData)) {
                      TextColor color = style.getColor();
                      return Optional.of(color != null ? 0xFF000000 | color.getRgb() : 0);
                    }
                  }

                  return Optional.empty();
                },
                Style.EMPTY)
            .orElse(null);
  }

  public static boolean test(int intValue, StaffDetectorData staffDetectorData) {
    ClientData.ensureInitialized();
    if (staffDetectorData.string2().indexOf(intValue) >= 0) {
      return true;
    } else {
      return !staffDetectorData.enabled()
          ? false
          : intValue >= 57344 && intValue <= 63743
              || intValue >= 983040 && intValue <= 1048573
              || intValue >= 1048576 && intValue <= 1114109;
    }
  }

  public static StaffDetectorData2 getStaffDetectorData2(String text, List list2) {
    ClientData.ensureInitialized();

    String text3 = getString2(text.toLowerCase(Locale.ROOT));
    if (text3.isEmpty()) {
      return null;
    }

    for (int index = 0; index < list2.size(); index++) {
      String text4 = (String) list2.get(index);
      if (!text4.isEmpty() && text3.contains(text4)) {
        return new StaffDetectorData2(getString(text4), list2.size() - index);
      }
    }

    return null;
  }

  public static String getString(String text) {
    ClientData.ensureInitialized();

    String text3 = (String) map.get(text);
    if (text3 != null) {
      return text3;
    } else {
      return text.isEmpty() ? "Staff" : Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
  }

  public static String getString(Text text) {
    ClientData.ensureInitialized();
    return text == null ? "" : text.getString();
  }

  public static String getString2(String text) {
    ClientData.ensureInitialized();

    StringBuilder builder = new StringBuilder(text.length());

    for (int index = 0; index < text.length(); index++) {
      char character = text.charAt(index);
      if (character >= 'a' && character <= 'z') {
        builder.append(character);
      }
    }

    return builder.toString();
  }

  public static String getString(String text, String text2) {
    ClientData.ensureInitialized();
    return text2 != null && !text2.isEmpty()
        ? text.replaceAll("(?i)" + Pattern.quote(text2), " ")
        : text;
  }

  static {
    ClientInitializer[] clientInitializerArray2 = new ClientInitializer[1];

    setClientInitializerArray(clientInitializerArray2);
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }
}
