package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.lwjgl.glfw.GLFW;

public class KeybindSetting extends Setting<Integer> {
  public static final int UNBOUND = -1;

  public KeybindSetting(String text, String text2, int intValue) {
    super(text, text2, intValue);
  }

  public boolean isBound() {
    return (Integer) this.getValue() != -1;
  }

  public boolean matches(int intValue) {
    return this.isBound() && (Integer) this.getValue() == intValue;
  }

  public String getDisplayName() {
    int intValue = (Integer) this.getValue();
    if (intValue == -1) {
      return "None";
    }

    if (intValue >= 0 && intValue <= 7) {
      return switch (intValue) {
        case 0 -> "LMB";
        case 1 -> "RMB";
        case 2 -> "MMB";
        default -> "MB" + (intValue + 1);
      };
    } else {
      String text = GLFW.glfwGetKeyName(intValue, 0);
      if (text != null) {
        return text.toUpperCase();
      }

      return switch (intValue) {
        case 32 -> "SPACE";
        case 257 -> "ENTER";
        case 258 -> "TAB";
        case 259 -> "BACK";
        case 260 -> "INSERT";
        case 261 -> "DELETE";
        case 262 -> "RIGHT";
        case 263 -> "LEFT";
        case 264 -> "DOWN";
        case 265 -> "UP";
        case 266 -> "PGUP";
        case 267 -> "PGDN";
        case 268 -> "HOME";
        case 269 -> "END";
        case 280 -> "CAPS";
        case 340 -> "LSHIFT";
        case 341 -> "LCTRL";
        case 342 -> "LALT";
        case 344 -> "RSHIFT";
        case 345 -> "RCTRL";
        case 346 -> "RALT";
        default ->
            intValue >= 290 && intValue <= 314 ? "F" + (intValue - 290 + 1) : "KEY" + intValue;
      };
    }
  }

  @Override
  public JsonElement toJson() {
    return new JsonPrimitive((Number) this.value);
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null
        && jsonElement.isJsonPrimitive()
        && jsonElement.getAsJsonPrimitive().isNumber()) {
      this.value = jsonElement.getAsInt();
    }
  }
}
