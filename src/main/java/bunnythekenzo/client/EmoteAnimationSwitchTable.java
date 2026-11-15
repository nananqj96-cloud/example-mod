package bunnythekenzo.client;

public class EmoteAnimationSwitchTable {
  public static final int[] countArray = new int[EmoteAnimation.values().length];

  static {
    try {
      countArray[EmoteAnimation.TWERKING.ordinal()] = 1;
    } catch (NoSuchFieldError failure) {
    }

    try {
      countArray[EmoteAnimation.TAKE_THE_L.ordinal()] = 2;
    } catch (NoSuchFieldError failure2) {
    }

    try {
      countArray[EmoteAnimation.CUSTOM.ordinal()] = 3;
    } catch (NoSuchFieldError failure3) {
    }

    try {
      countArray[EmoteAnimation.SHUFFLE.ordinal()] = 4;
    } catch (NoSuchFieldError failure4) {
    }

    try {
      countArray[EmoteAnimation.WAVE.ordinal()] = 5;
    } catch (NoSuchFieldError failure5) {
    }

    try {
      countArray[EmoteAnimation.WIZOX_BAN.ordinal()] = 6;
    } catch (NoSuchFieldError failure6) {
    }

    try {
      countArray[EmoteAnimation.ORANGE_JUSTICE.ordinal()] = 7;
    } catch (NoSuchFieldError failure7) {
    }

    try {
      countArray[EmoteAnimation.PASSINHO_DO_JAMAL.ordinal()] = 8;
    } catch (NoSuchFieldError failure8) {
    }

    try {
      countArray[EmoteAnimation.HOPPIN_DIH.ordinal()] = 9;
    } catch (NoSuchFieldError failure9) {
    }

    try {
      countArray[EmoteAnimation.LUSH_LIFE.ordinal()] = 10;
    } catch (NoSuchFieldError failure10) {
    }

    try {
      countArray[EmoteAnimation.FAREX_PULL_MY_PEARL.ordinal()] = 11;
    } catch (NoSuchFieldError failure11) {
    }

    try {
      countArray[EmoteAnimation.JERKING_OFF.ordinal()] = 12;
    } catch (NoSuchFieldError failure12) {
    }

    try {
      countArray[EmoteAnimation.NONE.ordinal()] = 13;
    } catch (NoSuchFieldError failure13) {
    }
  }
}
