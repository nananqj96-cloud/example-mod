package bunnythekenzo.client;

import bunnythekenzo.client.module.ModuleCategory;

public class JsonSupport {
  public static final int[] countArray = new int[ModuleCategory.values().length];

  static {
    try {
      countArray[ModuleCategory.MISC.ordinal()] = 1;
    } catch (NoSuchFieldError failure) {
    }
  }
}
