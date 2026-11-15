package bunnythekenzo.client;

public class GlintStyleSwitchTable {
  public static final int[] countArray = new int[GlintStyle.values().length];

  static {
    try {
      countArray[GlintStyle.DEFAULT.ordinal()] = 1;
    } catch (NoSuchFieldError failure) {
    }

    try {
      countArray[GlintStyle.GALAXY.ordinal()] = 2;
    } catch (NoSuchFieldError failure2) {
    }

    try {
      countArray[GlintStyle.ENDER.ordinal()] = 3;
    } catch (NoSuchFieldError failure3) {
    }

    try {
      countArray[GlintStyle.VOID.ordinal()] = 4;
    } catch (NoSuchFieldError failure4) {
    }

    try {
      countArray[GlintStyle.TOXIC.ordinal()] = 5;
    } catch (NoSuchFieldError failure5) {
    }

    try {
      countArray[GlintStyle.AMETHYST.ordinal()] = 6;
    } catch (NoSuchFieldError failure6) {
    }

    try {
      countArray[GlintStyle.CYBER.ordinal()] = 7;
    } catch (NoSuchFieldError failure7) {
    }

    try {
      countArray[GlintStyle.SOLAR.ordinal()] = 8;
    } catch (NoSuchFieldError failure8) {
    }

    try {
      countArray[GlintStyle.PRISMATIC.ordinal()] = 9;
    } catch (NoSuchFieldError failure9) {
    }

    try {
      countArray[GlintStyle.ABYSS.ordinal()] = 10;
    } catch (NoSuchFieldError failure10) {
    }

    try {
      countArray[GlintStyle.AURORA.ordinal()] = 11;
    } catch (NoSuchFieldError failure11) {
    }

    try {
      countArray[GlintStyle.INFERNO.ordinal()] = 12;
    } catch (NoSuchFieldError failure12) {
    }
  }
}
