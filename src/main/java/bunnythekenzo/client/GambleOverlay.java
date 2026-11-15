package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import bunnythekenzo.client.mixin.HandledScreenAccessor;

public class GambleOverlay {
  public static final int count = 8;
  public static final int count2 = 22;
  public static final int count3 = 4;
  public static final int count4 = 12;
  public static final int count5 = 16;
  public static final int count6 = 12;
  public static final int count7 = -3750202;
  public static final int count8 = -2697514;
  public static final int count9 = -5263441;
  public static final int count10 = -1;
  public static final int count11 = -11184811;
  public static final int count12 = -7631989;
  public static final int count13 = -13158601;
  public static final int count14 = -16777216;
  public static final int count15 = -12566464;
  public static final int count16 = -1;
  public static final int count17 = -96;
  public static final int count18 = -6250336;
  public final HandledScreen handledScreen;
  public final HandledScreenAccessor handledScreenAccessor;
  public final GambleRiggerStateSupport gambleRiggerStateSupport;

  public GambleOverlay(
      HandledScreen handledScreen2, GambleRiggerStateSupport gambleRiggerStateSupport2) {
    this.handledScreen = handledScreen2;
    this.handledScreenAccessor = (HandledScreenAccessor) (Object) (handledScreen2);
    this.gambleRiggerStateSupport = gambleRiggerStateSupport2;
  }

  public int computeInt() {
    return 74;
  }

  public int computeInt2() {
    return this.computeInt() + 16;
  }

  public int computeInt3() {
    int intValue =
        this.handledScreenAccessor.getLeftPos() + this.handledScreenAccessor.getImageWidth() + 6;
    if (intValue + this.computeInt2() > this.handledScreen.width) {
      int intValue2 = this.handledScreenAccessor.getLeftPos() - this.computeInt2() - 6;
      if (intValue2 >= 2) {
        return intValue2;
      }
    }

    return intValue;
  }

  public int computeInt4() {
    return this.handledScreenAccessor.getTopPos();
  }

  public int computeInt5() {
    return this.computeInt4() + 8 + 12 + 4;
  }

  public int computeInt6() {
    return 74;
  }

  public int computeInt7() {
    return this.computeInt5() + this.computeInt6() + 6;
  }

  public int computeInt8() {
    return this.computeInt7() + 16 + 3;
  }

  public int computeInt9() {
    return this.computeInt8() + 12 + 5;
  }

  public int computeInt10() {
    return this.computeInt9() + 16 + 7 - this.computeInt4();
  }

  public int computeInt(int intValue) {
    return this.computeInt3() + 8 + intValue % 3 * 26;
  }

  public int computeInt2(int intValue) {
    return this.computeInt5() + intValue / 3 * 26;
  }

  public int computeInt11() {
    return this.computeInt3() + 8;
  }

  public static boolean test(
      double doubleValue,
      double doubleValue2,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4) {
    return doubleValue >= intValue
        && doubleValue < intValue + intValue3
        && doubleValue2 >= intValue2
        && doubleValue2 < intValue2 + intValue4;
  }

  public void apply(DrawContext drawContext, int intValue, int intValue2) {
    if (this.gambleRiggerStateSupport.isEnabled()) {
      TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
      int int3 = this.computeInt3();
      int int4 = this.computeInt4();
      int int2 = this.computeInt2();
      int int10 = this.computeInt10();
      drawContext.fill(int3 - 1, int4 - 1, int3 + int2 + 1, int4 + int10 + 1, -16777216);
      apply(drawContext, int3, int4, int2, int10, -3750202);
      this.apply(
          drawContext,
          textRenderer,
          Text.literal("Gamble Rigger"),
          int3 + int2 / 2,
          int4 + 8,
          -12566464,
          false);
      boolean flag = this.gambleRiggerStateSupport.test4();

      for (int index = 0; index < 9; index++) {
        this.apply(drawContext, textRenderer, index, intValue, intValue2, flag);
      }

      this.apply(
          drawContext,
          textRenderer,
          this.computeInt11(),
          this.computeInt7(),
          this.computeInt(),
          16,
          "Restore",
          this.gambleRiggerStateSupport.test5(),
          intValue,
          intValue2);
      boolean flag2 =
          this.gambleRiggerStateSupport.getGambleRiggerState() != GambleRiggerState.IDLE
              || this.gambleRiggerStateSupport.getCount5() >= 0;
      this.apply(
          drawContext,
          textRenderer,
          this.computeInt11(),
          this.computeInt8(),
          this.computeInt(),
          12,
          "Reset",
          flag2,
          intValue,
          intValue2);

      String text =
          switch (GambleStateSwitchTable.countArray[
              this.gambleRiggerStateSupport.getGambleRiggerState().ordinal()]) {
            case 1 -> "Taking... " + this.gambleRiggerStateSupport.computeInt();
            case 2 -> "Kept #" + (this.gambleRiggerStateSupport.getCount5() + 1);
            case 3 -> "Restoring... " + this.gambleRiggerStateSupport.computeInt();
            default -> "Pick a slot";
          };
      this.apply(
          drawContext,
          textRenderer,
          Text.literal(text),
          int3 + int2 / 2,
          this.computeInt9(),
          -12566464,
          false);
    }
  }

  public void apply(
      DrawContext drawContext,
      TextRenderer textRenderer,
      int intValue,
      int intValue2,
      int intValue3,
      boolean flag) {
    int value = this.computeInt(intValue);
    int int2 = this.computeInt2(intValue);
    boolean flag2 =
        this.gambleRiggerStateSupport.getCount5() == intValue
            && this.gambleRiggerStateSupport.getGambleRiggerState() != GambleRiggerState.IDLE;
    boolean flag3 = flag && test(intValue2, intValue3, value, int2, 22, 22);
    MutableText mutableText = Text.literal(Integer.toString(intValue + 1));
    int intValue4 = value + 11;
    int intValue5 = int2 + 7;
    if (flag2) {
      apply2(drawContext, value, int2, 22, 22, -7631989);
      this.apply(drawContext, textRenderer, mutableText, intValue4, intValue5, -1, true);
    } else {
      apply(drawContext, value, int2, 22, 22, flag3 ? -2697514 : -3750202);
      int intValue6 = !flag ? -6250336 : (flag3 ? -96 : -1);
      this.apply(drawContext, textRenderer, mutableText, intValue4, intValue5, intValue6, true);
    }
  }

  public void apply(
      DrawContext drawContext,
      TextRenderer textRenderer,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      String text,
      boolean flag,
      int intValue5,
      int intValue6) {
    boolean flag2 = flag && test(intValue5, intValue6, intValue, intValue2, intValue3, intValue4);
    int intValue7 = !flag ? -5263441 : (flag2 ? -2697514 : -3750202);
    apply(drawContext, intValue, intValue2, intValue3, intValue4, intValue7);
    int intValue8 = !flag ? -6250336 : (flag2 ? -96 : -1);
    this.apply(
        drawContext,
        textRenderer,
        Text.literal(text),
        intValue + intValue3 / 2,
        intValue2 + (intValue4 - 8) / 2,
        intValue8,
        true);
  }

  public static void apply(
      DrawContext drawContext,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      int intValue5) {
    drawContext.fill(intValue, intValue2, intValue + intValue3, intValue2 + intValue4, intValue5);
    drawContext.fill(intValue, intValue2, intValue + intValue3, intValue2 + 1, -1);
    drawContext.fill(intValue, intValue2, intValue + 1, intValue2 + intValue4, -1);
    drawContext.fill(
        intValue,
        intValue2 + intValue4 - 1,
        intValue + intValue3,
        intValue2 + intValue4,
        -11184811);
    drawContext.fill(
        intValue + intValue3 - 1,
        intValue2,
        intValue + intValue3,
        intValue2 + intValue4,
        -11184811);
  }

  public static void apply2(
      DrawContext drawContext,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      int intValue5) {
    drawContext.fill(intValue, intValue2, intValue + intValue3, intValue2 + intValue4, intValue5);
    drawContext.fill(intValue, intValue2, intValue + intValue3, intValue2 + 1, -13158601);
    drawContext.fill(intValue, intValue2, intValue + 1, intValue2 + intValue4, -13158601);
    drawContext.fill(
        intValue, intValue2 + intValue4 - 1, intValue + intValue3, intValue2 + intValue4, -1);
    drawContext.fill(
        intValue + intValue3 - 1, intValue2, intValue + intValue3, intValue2 + intValue4, -1);
  }

  public void apply(
      DrawContext drawContext,
      TextRenderer textRenderer,
      Text text,
      int intValue,
      int intValue2,
      int intValue3,
      boolean flag) {
    drawContext.drawText(
        textRenderer, text, intValue - textRenderer.getWidth(text) / 2, intValue2, intValue3, flag);
  }

  public boolean test(double doubleValue, double doubleValue2, int intValue) {
    if (!this.gambleRiggerStateSupport.isEnabled()) {
      return false;
    }

    int int3 = this.computeInt3();
    int int4 = this.computeInt4();
    if (!test(doubleValue, doubleValue2, int3, int4, this.computeInt2(), this.computeInt10())) {
      return false;
    }

    if (intValue == 0) {
      if (this.gambleRiggerStateSupport.test4()) {
        for (int index = 0; index < 9; index++) {
          if (test(
              doubleValue, doubleValue2, this.computeInt(index), this.computeInt2(index), 22, 22)) {
            this.gambleRiggerStateSupport.apply(index);
            return true;
          }
        }
      }

      if (this.gambleRiggerStateSupport.test5()
          && test(
              doubleValue,
              doubleValue2,
              this.computeInt11(),
              this.computeInt7(),
              this.computeInt(),
              16)) {
        this.gambleRiggerStateSupport.update4();
        return true;
      }

      if (test(
          doubleValue,
          doubleValue2,
          this.computeInt11(),
          this.computeInt8(),
          this.computeInt(),
          12)) {
        this.gambleRiggerStateSupport.update5();
        return true;
      }
    }

    return true;
  }
}
