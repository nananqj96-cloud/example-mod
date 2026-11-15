package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import bunnythekenzo.client.render.FontManager;

public final class ProfileNames {
  public String string;
  public int count;
  public int count2;
  public boolean enabled;
  public float amount;
  public float amount2;
  public float amount3;
  public float amount4;

  public ProfileNames(String text) {
    this.apply(text);
  }

  public String getString() {
    return this.string;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public boolean test() {
    return ProfileDefaults.test(this.string);
  }

  public void apply(String string2) {
    this.string = string2;
    this.count = this.count2 = this.string.length();
  }

  public void setEnabled(boolean enabled2) {
    this.enabled = enabled2;
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.render(fontManager, floatValue, floatValue2, floatValue3, floatValue4, 12.0F);
  }

  public void render(
      FontManager fontManager,
      float amount5,
      float amount22,
      float amount32,
      float amount42,
      float floatValue) {
    this.amount = amount5;
    this.amount2 = amount22;
    this.amount3 = amount32;
    this.amount4 = amount42;
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.render(amount5, amount22, amount32, amount42, 7.0F, -15855336);
    fontManager.render(
        amount5,
        amount22,
        amount32,
        amount42,
        7.0F,
        1.0F,
        this.enabled ? instance.computeInt(0.8F) : instance.computeInt14());
    fontManager.update2();
    fontManager.render(amount5 + 10.0F, amount22 + 3.0F, amount32 - 20.0F, amount42 - 6.0F);
    float value = fontManager.computeFloat(this.string.substring(0, this.count), floatValue);
    float floatValue2 = amount5 + 12.0F - Math.max(0.0F, value - (amount32 - 30.0F));
    if (this.enabled && this.count != this.count2) {
      float value2 =
          fontManager.computeFloat(
              this.string.substring(0, Math.min(this.count, this.count2)), floatValue);
      float value3 =
          fontManager.computeFloat(
              this.string.substring(0, Math.max(this.count, this.count2)), floatValue);
      fontManager.render(
          floatValue2 + value2,
          amount22 + 8.0F,
          value3 - value2,
          amount42 - 16.0F,
          2.0F,
          instance.computeInt(0.22F));
    }

    fontManager.computeFloat(
        this.string.isEmpty() ? "your name" : this.string,
        floatValue2,
        amount22 + amount42 / 2.0F,
        floatValue,
        this.string.isEmpty() ? -5853249 : instance.computeInt16());
    if (this.enabled && (System.nanoTime() / 500000000L & 1L) == 0L) {
      fontManager.render(
          floatValue2 + value,
          amount22 + 9.0F,
          1.0F,
          amount42 - 18.0F,
          0.0F,
          instance.computeInt());
    }

    fontManager.update3();
  }

  public boolean test(float floatValue, float floatValue2) {
    this.enabled =
        GuiDrawing.test(
            floatValue, floatValue2, this.amount, this.amount2, this.amount3, this.amount4);
    if (this.enabled) {
      this.count2 = 0;
      this.count = this.string.length();
    }

    return this.enabled;
  }

  public boolean test(int intValue) {
    if (!this.enabled) {
      return false;
    }

    if (intValue < 128
        && (intValue >= 97 && intValue <= 122
            || intValue >= 65 && intValue <= 90
            || intValue >= 48 && intValue <= 57
            || intValue == 95
            || intValue == 45)) {
      this.apply2(Character.toString(intValue));
    }

    return true;
  }

  public void apply2(String text) {
    String text2 = text.replaceAll("[^A-Za-z0-9_-]", "");
    int intValue = Math.min(this.count, this.count2);
    int intValue2 = Math.max(this.count, this.count2);
    int intValue3 = Math.max(0, 24 - this.string.length() + intValue2 - intValue);
    text2 = text2.substring(0, Math.min(text2.length(), intValue3));
    this.string = this.string.substring(0, intValue) + text2 + this.string.substring(intValue2);
    this.count = this.count2 = intValue + text2.length();
  }

  public boolean test2(int intValue) {
    if (!this.enabled) {
      return false;
    }

    long handle = MinecraftClient.getInstance().getWindow().getHandle();
    boolean flag = test(handle, 341) || test(handle, 345) || test(handle, 343) || test(handle, 347);
    boolean flag2 = test(handle, 340) || test(handle, 344);
    if (flag && intValue == 65) {
      this.count2 = 0;
      this.count = this.string.length();
    } else if (flag && intValue == 86) {
      this.apply2(MinecraftClient.getInstance().keyboard.getClipboard());
    } else if (!flag || intValue != 67 && intValue != 88) {
      if (intValue != 259 && intValue != 261) {
        if (intValue == 263 || intValue == 262 || intValue == 268 || intValue == 269) {
          this.count =
              switch (intValue) {
                case 263 -> {
                  if (!flag2 && this.count2 != this.count) {
                    yield Math.min(this.count2, this.count);
                  }

                  yield Math.max(0, this.count - 1);
                }
                case 268 -> 0;
                case 269 -> this.string.length();
                default ->
                    !flag2 && this.count2 != this.count
                        ? Math.max(this.count2, this.count)
                        : Math.min(this.string.length(), this.count + 1);
              };
          if (!flag2) {
            this.count2 = this.count;
          }
        } else if (intValue == 256) {
          this.enabled = false;
        }
      } else {
        if (this.count == this.count2) {
          if (intValue == 259) {
            this.count2 = Math.max(0, this.count - 1);
          } else {
            this.count2 = Math.min(this.string.length(), this.count + 1);
          }
        }

        this.apply2("");
      }
    } else if (this.count2 != this.count) {
      MinecraftClient.getInstance()
          .keyboard
          .setClipboard(
              this.string.substring(
                  Math.min(this.count, this.count2), Math.max(this.count, this.count2)));
      if (intValue == 88) {
        this.apply2("");
      }
    }

    return true;
  }

  public static boolean test(long longValue, int intValue) {
    return GLFW.glfwGetKey(longValue, intValue) == 1;
  }
}
