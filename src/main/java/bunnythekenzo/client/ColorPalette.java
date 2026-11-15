package bunnythekenzo.client;

public class ColorPalette {
  public static final ColorPalette colorPalette = new ColorPalette("Custom", -40008);
  public static ColorPalette colorPalette2 = colorPalette;
  public final String string;
  public int count;
  public final boolean enabled;

  public static ColorPalette getInstance() {
    return colorPalette2;
  }

  public static void apply(ColorPalette colorPalette3) {
    colorPalette2 = colorPalette3 != null ? colorPalette3 : colorPalette;
  }

  public static void apply(int intValue) {
    colorPalette.apply2(intValue);
    colorPalette2 = colorPalette;
  }

  public ColorPalette(String text, int intValue) {
    this(text, intValue, false);
  }

  public ColorPalette(String string2, int count2, boolean enabled2) {
    this.string = string2;
    this.count = count2;
    this.enabled = enabled2;
  }

  public String getString() {
    return this.string;
  }

  public String getString2() {
    return this.string;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void apply2(int intValue) {
    this.count = 0xFF000000 | intValue & 16777215;
  }

  public final int getCount2() {
    return this.count;
  }

  public int getCount() {
    return this.count;
  }

  public int computeInt() {
    return ArgbColors.lighten(this.getCount(), 0.3F);
  }

  public int computeInt(float floatValue) {
    return ArgbColors.withOpacity(this.getCount(), floatValue);
  }

  public int computeInt2() {
    return -16317174;
  }

  public int computeInt3() {
    return ArgbColors.withOpacity(-15331043, 0.97F);
  }

  public int computeInt4() {
    return ArgbColors.withOpacity(-15922670, 0.97F);
  }

  public int computeInt5() {
    return ArgbColors.withOpacity(-14870745, 0.98F);
  }

  public int computeInt6() {
    return ArgbColors.withOpacity(-15462628, 0.98F);
  }

  public int computeInt7() {
    return ArgbColors.withOpacity(-15331043, 0.9F);
  }

  public int computeInt8() {
    return ArgbColors.withOpacity(-15922670, 0.9F);
  }

  public int computeInt9() {
    return ArgbColors.withOpacity(-15659752, 0.55F);
  }

  public int computeInt10() {
    return ArgbColors.withOpacity(-14936538, 0.85F);
  }

  public int computeInt11() {
    return ArgbColors.withOpacity(-14213069, 0.92F);
  }

  public int computeInt12() {
    return ArgbColors.withOpacity(ArgbColors.lerp(-14936538, this.getCount(), 0.17F), 0.95F);
  }

  public int computeInt13() {
    return ArgbColors.withOpacity(-1, 0.07F);
  }

  public int computeInt14() {
    return ArgbColors.withOpacity(-1, 0.1F);
  }

  public int computeInt15() {
    return ArgbColors.withOpacity(this.getCount(), 0.55F);
  }

  public int computeInt16() {
    return -790792;
  }

  public int computeInt17() {
    return -5791816;
  }

  public int computeInt18() {
    return -9344641;
  }

  public int computeInt19() {
    return -11646887;
  }

  public int computeInt20() {
    return -12654964;
  }

  public int computeInt21() {
    return -11252384;
  }

  public int computeInt22() {
    return -2055622;
  }

  public int computeInt23() {
    return ArgbColors.lighten(this.computeInt22(), 0.3F);
  }

  public int computeInt2(float floatValue) {
    return ArgbColors.withOpacity(this.computeInt22(), floatValue);
  }

  public int computeInt24() {
    return ArgbColors.withOpacity(-14017013, 0.96F);
  }
}
