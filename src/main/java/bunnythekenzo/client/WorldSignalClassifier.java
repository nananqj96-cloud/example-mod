package bunnythekenzo.client;

import java.util.Locale;

public final class WorldSignalClassifier {
  public static boolean isInteractionSound(String text) {
    String text2 = normalizeIdentifier(text);
    return text2.contains("chest.open")
        || text2.contains("chest.close")
        || text2.contains("chest.locked")
        || text2.contains("barrel.open")
        || text2.contains("barrel.close")
        || text2.contains("shulker_box.open")
        || text2.contains("shulker_box.close")
        || text2.contains("door.open")
        || text2.contains("door.close")
        || text2.contains("fence_gate.open")
        || text2.contains("fence_gate.close")
        || text2.contains("button.click")
        || text2.contains("lever.click");
  }

  public static boolean isCombatParticle(String text) {
    String text2 = normalizeIdentifier(text);
    return text2.contains("crit")
        || text2.contains("enchanted_hit")
        || text2.contains("damage_indicator")
        || text2.contains("smoke")
        || text2.equals("block");
  }

  public static String normalizeIdentifier(String text) {
    if (text == null) {
      return "unknown";
    }

    int intValue = text.indexOf(58);
    return (intValue >= 0 && intValue + 1 < text.length() ? text.substring(intValue + 1) : text)
        .toLowerCase(Locale.ROOT);
  }
}
