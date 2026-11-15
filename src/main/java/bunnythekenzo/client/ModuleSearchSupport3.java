package bunnythekenzo.client;

import bunnythekenzo.client.ui.MenuInteraction;

public class ModuleSearchSupport3 {
  public static final int[] countArray = new int[MenuInteraction.values().length];

  static {
    try {
      countArray[MenuInteraction.BIND.ordinal()] = 1;
    } catch (NoSuchFieldError failure) {
    }

    try {
      countArray[MenuInteraction.TOGGLE.ordinal()] = 2;
    } catch (NoSuchFieldError failure2) {
    }

    try {
      countArray[MenuInteraction.SELECT.ordinal()] = 3;
    } catch (NoSuchFieldError failure3) {
    }

    try {
      countArray[MenuInteraction.NONE.ordinal()] = 4;
    } catch (NoSuchFieldError failure4) {
    }
  }
}
