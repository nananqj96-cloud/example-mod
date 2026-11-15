package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;

public final class GuiDrawing {
  public static boolean test(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    return floatValue >= floatValue3
        && floatValue <= floatValue3 + floatValue5
        && floatValue2 >= floatValue4
        && floatValue2 <= floatValue4 + floatValue6;
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.render(
        floatValue, floatValue2, floatValue3, floatValue4, 12.0F, -14607574, -15396324, true);
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        12.0F,
        1.0F,
        instance.computeInt14(),
        369098751,
        true);
  }

  public static void render(
      FontManager fontManager,
      String text,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      boolean flag,
      boolean flag2) {
    ColorPalette instance = ColorPalette.getInstance();
    boolean flag3 =
        flag2 && test(floatValue5, floatValue6, floatValue, floatValue2, floatValue3, floatValue4);
    fontManager.update2();
    if (!flag2) {
      fontManager.apply3(0.4F);
    }

    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        7.0F,
        flag
            ? (flag3 ? instance.computeInt() : instance.getCount())
            : (flag3 ? 553648127 : 167772159));
    if (!flag) {
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          7.0F,
          1.0F,
          flag3 ? instance.computeInt15() : instance.computeInt14());
    }

    fontManager.computeFloat2(
        text,
        floatValue + floatValue3 / 2.0F,
        floatValue2 + floatValue4 / 2.0F,
        11.0F,
        flag ? -15266022 : instance.computeInt16());
    fontManager.update3();
  }
}
