package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;

public interface GuiModal {
  default void onClosed() {}

  void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4);

  boolean isCloseComplete();

  boolean acceptsInput();

  boolean mouseClicked(float floatValue, float floatValue2, int intValue);

  default void mouseDragged(float floatValue, float floatValue2) {}

  default void mouseReleased() {}

  boolean mouseScrolled(float floatValue, float floatValue2, double doubleValue);

  boolean keyPressed(int intValue);

  default boolean charTyped(int intValue) {
    return true;
  }
}
