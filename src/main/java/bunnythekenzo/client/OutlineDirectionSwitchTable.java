package bunnythekenzo.client;

import net.minecraft.util.math.Direction;

public class OutlineDirectionSwitchTable {
  public static final int[] countArray = new int[Direction.values().length];

  static {
    try {
      countArray[Direction.DOWN.ordinal()] = 1;
    } catch (NoSuchFieldError failure) {
    }

    try {
      countArray[Direction.UP.ordinal()] = 2;
    } catch (NoSuchFieldError failure2) {
    }

    try {
      countArray[Direction.NORTH.ordinal()] = 3;
    } catch (NoSuchFieldError failure3) {
    }

    try {
      countArray[Direction.SOUTH.ordinal()] = 4;
    } catch (NoSuchFieldError failure4) {
    }

    try {
      countArray[Direction.WEST.ordinal()] = 5;
    } catch (NoSuchFieldError failure5) {
    }

    try {
      countArray[Direction.EAST.ordinal()] = 6;
    } catch (NoSuchFieldError failure6) {
    }
  }
}
