package bunnythekenzo.client.setting;

import java.util.Locale;
import net.minecraft.item.Item;

public final class EntityFilterEntry {
  public final String string;
  public final String string2;
  public final Item item;
  public final BooleanSetting enabledSetting;
  public final ColorSetting colorSetting;

  public EntityFilterEntry(
      String string3, String string22, Item item2, boolean flag, int intValue) {
    this.string = string3;
    this.string2 = string22;
    this.item = item2;
    this.enabledSetting = new BooleanSetting("Enabled", "Highlight this type", flag);
    this.colorSetting = new ColorSetting(string22, "Highlight color", intValue);
  }

  public String getString() {
    return this.string;
  }

  public String getString2() {
    return this.string2;
  }

  public Item getItem() {
    return this.item;
  }

  public boolean test(String text) {
    return this.string2.toLowerCase(Locale.ROOT).contains(text) || this.string.contains(text);
  }
}
