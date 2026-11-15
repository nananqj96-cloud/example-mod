package bunnythekenzo.client;

import net.minecraft.util.math.Direction;

public class HudFactorySupport {
  public static final int[] countArray = new int[Direction.values().length];

  static {
    try {
      countArray[Direction.NORTH.ordinal()] = 1;
    } catch (NoSuchFieldError failure) {
    }

    try {
      countArray[Direction.SOUTH.ordinal()] = 2;
    } catch (NoSuchFieldError failure2) {
    }

    try {
      countArray[Direction.WEST.ordinal()] = 3;
    } catch (NoSuchFieldError failure3) {
    }

    try {
      countArray[Direction.EAST.ordinal()] = 4;
    } catch (NoSuchFieldError failure4) {
    }
  }
}
