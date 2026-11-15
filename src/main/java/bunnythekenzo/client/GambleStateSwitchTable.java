package bunnythekenzo.client;

public class GambleStateSwitchTable {
  public static final int[] countArray = new int[GambleRiggerState.values().length];

  static {
    try {
      countArray[GambleRiggerState.EXTRACTING.ordinal()] = 1;
    } catch (NoSuchFieldError failure) {
    }

    try {
      countArray[GambleRiggerState.EXTRACTED.ordinal()] = 2;
    } catch (NoSuchFieldError failure2) {
    }

    try {
      countArray[GambleRiggerState.RESTORING.ordinal()] = 3;
    } catch (NoSuchFieldError failure3) {
    }
  }
}
