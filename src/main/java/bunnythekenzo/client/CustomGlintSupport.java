package bunnythekenzo.client;

import net.minecraft.item.ItemDisplayContext;

public class CustomGlintSupport {
  public static final int[] countArray = new int[ItemDisplayContext.values().length];

  static {
    long[] longValues =
        new long[] {-4041708063990743032L, -3386015696335929337L, -4280678927648686074L};

    try {
      countArray[ItemDisplayContext.GUI.ordinal()] = 1;
    } catch (NoSuchFieldError failure) {
    }

    try {
      countArray[ItemDisplayContext.GROUND.ordinal()] = 2;
    } catch (NoSuchFieldError failure2) {
    }

    try {
      countArray[ItemDisplayContext.FIXED.ordinal()] = 3;
    } catch (NoSuchFieldError failure3) {
    }

    try {
      countArray[ItemDisplayContext.FIRST_PERSON_LEFT_HAND.ordinal()] = 4;
    } catch (NoSuchFieldError failure4) {
    }

    try {
      countArray[ItemDisplayContext.FIRST_PERSON_RIGHT_HAND.ordinal()] = 5;
    } catch (NoSuchFieldError failure5) {
    }

    try {
      countArray[ItemDisplayContext.THIRD_PERSON_LEFT_HAND.ordinal()] = (int) longValues[2];
    } catch (NoSuchFieldError failure6) {
    }

    try {
      countArray[ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal()] = (int) longValues[1];
    } catch (NoSuchFieldError failure7) {
    }

    try {
      countArray[ItemDisplayContext.HEAD.ordinal()] = (int) longValues[0];
    } catch (NoSuchFieldError failure8) {
    }
  }
}
