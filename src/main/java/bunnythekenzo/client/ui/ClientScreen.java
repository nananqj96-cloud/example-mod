package bunnythekenzo.client.ui;

import bunnythekenzo.client.render.FontManager;

public interface ClientScreen {
  default void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {}

  void render2(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4);
}
