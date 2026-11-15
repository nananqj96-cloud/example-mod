package bunnythekenzo.client;

import java.util.Locale;
import org.lwjgl.glfw.GLFW;

public final class KeyNames {
  public static String getString(int intValue) {
    if (intValue != -1 && intValue >= 0) {
      String text =
          switch (intValue) {
            case 32 -> "Space";
            case 256 -> "Esc";
            case 257 -> "Enter";
            case 258 -> "Tab";
            case 259 -> "Bksp";
            case 260 -> "Ins";
            case 261 -> "Del";
            case 262 -> "Right";
            case 263 -> "Left";
            case 264 -> "Down";
            case 265 -> "Up";
            case 266 -> "PgUp";
            case 267 -> "PgDn";
            case 268 -> "Home";
            case 269 -> "End";
            case 280 -> "Caps";
            case 340 -> "LShift";
            case 341 -> "LCtrl";
            case 342 -> "LAlt";
            case 344 -> "RShift";
            case 345 -> "RCtrl";
            case 346 -> "RAlt";
            default -> null;
          };
      if (text != null) {
        return text;
      }

      if (intValue >= 290 && intValue <= 314) {
        return "F" + (intValue - 290 + 1);
      }

      if (intValue >= 320 && intValue <= 329) {
        return "Num" + (intValue - 320);
      }

      String text2 = GLFW.glfwGetKeyName(intValue, 0);
      return text2 == null ? "Key " + intValue : text2.toUpperCase(Locale.ROOT);
    } else {
      return "None";
    }
  }
}
