package bunnythekenzo.client.ui;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.ColorSetting;

public interface GalleryItem {
  default boolean test(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    return false;
  }

  default String getString() {
    return this.getString2();
  }

  ItemStack getItemStack();

  Identifier getIdentifier();

  String getString2();

  boolean test(String text);

  boolean test();

  boolean test2();

  boolean test3();

  int computeInt();

  void update();

  ColorSetting getColorSetting();
}
