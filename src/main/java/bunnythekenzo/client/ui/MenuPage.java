package bunnythekenzo.client.ui;

import bunnythekenzo.client.render.FontManager;

public interface MenuPage {
  default void apply(FontManager fontManager) {}

  default void update6() {}

  void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6);

  default boolean test(float floatValue, float floatValue2, int intValue) {
    return false;
  }

  default void apply(float floatValue, float floatValue2) {}

  default void update5() {}

  default boolean test(float floatValue, float floatValue2, double doubleValue) {
    return false;
  }

  default boolean test2(int intValue) {
    return false;
  }

  default boolean test(int intValue) {
    return false;
  }

  default void update() {}

  default void update2() {}

  default String getString() {
    return null;
  }

  default boolean test() {
    return false;
  }

  default float computeFloat() {
    return 0.0F;
  }
}
