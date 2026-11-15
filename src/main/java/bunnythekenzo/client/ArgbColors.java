package bunnythekenzo.client;

public final class ArgbColors {
  public static int pack(int intValue, int intValue2, int intValue3, int intValue4) {
    return (intValue & 0xFF) << 24
        | (intValue2 & 0xFF) << 16
        | (intValue3 & 0xFF) << 8
        | intValue4 & 0xFF;
  }

  public static int rgb(int intValue, int intValue2, int intValue3) {
    return pack(255, intValue, intValue2, intValue3);
  }

  public static int alpha(int intValue) {
    return intValue >>> 24 & 0xFF;
  }

  public static int red(int intValue) {
    return intValue >> 16 & 0xFF;
  }

  public static int green(int intValue) {
    return intValue >> 8 & 0xFF;
  }

  public static int blue(int intValue) {
    return intValue & 0xFF;
  }

  public static int withAlpha(int intValue, int intValue2) {
    return intValue & 16777215 | (intValue2 & 0xFF) << 24;
  }

  public static int withOpacity(int intValue, float floatValue) {
    return withAlpha(intValue, (int) (Math.clamp(floatValue, 0.0F, 1.0F) * 255.0F + 0.5F));
  }

  public static float[] toHsv(int intValue) {
    float floatValue = red(intValue) / 255.0F;
    float floatValue2 = green(intValue) / 255.0F;
    float floatValue3 = blue(intValue) / 255.0F;
    float floatValue4 = Math.max(floatValue, Math.max(floatValue2, floatValue3));
    float floatValue5 = Math.min(floatValue, Math.min(floatValue2, floatValue3));
    float floatValue6 = floatValue4 - floatValue5;
    float floatValue7;
    if (floatValue6 == 0.0F) {
      floatValue7 = 0.0F;
    } else if (floatValue4 == floatValue) {
      floatValue7 = 60.0F * ((floatValue2 - floatValue3) / floatValue6 % 6.0F);
    } else if (floatValue4 == floatValue2) {
      floatValue7 = 60.0F * ((floatValue3 - floatValue) / floatValue6 + 2.0F);
    } else {
      floatValue7 = 60.0F * ((floatValue - floatValue2) / floatValue6 + 4.0F);
    }

    if (floatValue7 < 0.0F) {
      floatValue7 += 360.0F;
    }

    return new float[] {
      floatValue7, floatValue4 == 0.0F ? 0.0F : floatValue6 / floatValue4, floatValue4
    };
  }

  public static int fromHsv(float floatValue, float floatValue2, float floatValue3) {
    floatValue = (floatValue % 360.0F + 360.0F) % 360.0F;
    float floatValue4 = floatValue3 * floatValue2;
    float floatValue5 = floatValue4 * (1.0F - Math.abs(floatValue / 60.0F % 2.0F - 1.0F));
    float floatValue6 = floatValue3 - floatValue4;
    float floatValue7;
    float floatValue8;
    float floatValue9;
    if (floatValue < 60.0F) {
      floatValue7 = floatValue4;
      floatValue8 = floatValue5;
      floatValue9 = 0.0F;
    } else if (floatValue < 120.0F) {
      floatValue7 = floatValue5;
      floatValue8 = floatValue4;
      floatValue9 = 0.0F;
    } else if (floatValue < 180.0F) {
      floatValue7 = 0.0F;
      floatValue8 = floatValue4;
      floatValue9 = floatValue5;
    } else if (floatValue < 240.0F) {
      floatValue7 = 0.0F;
      floatValue8 = floatValue5;
      floatValue9 = floatValue4;
    } else if (floatValue < 300.0F) {
      floatValue7 = floatValue5;
      floatValue8 = 0.0F;
      floatValue9 = floatValue4;
    } else {
      floatValue7 = floatValue4;
      floatValue8 = 0.0F;
      floatValue9 = floatValue5;
    }

    return rgb(
        Math.round((floatValue7 + floatValue6) * 255.0F),
        Math.round((floatValue8 + floatValue6) * 255.0F),
        Math.round((floatValue9 + floatValue6) * 255.0F));
  }

  public static String toHex(int intValue) {
    return String.format("%06X", intValue & 16777215);
  }

  public static int parseHexOrDefault(String text, int intValue) {
    String text2 = text.startsWith("#") ? text.substring(1) : text;
    if (text2.length() != 6) {
      return intValue;
    }

    try {
      return 0xFF000000 | Integer.parseInt(text2, 16);
    } catch (NumberFormatException failure) {
      return intValue;
    }
  }

  public static int lerp(int intValue, int intValue2, float floatValue) {
    floatValue = Math.clamp(floatValue, 0.0F, 1.0F);
    return pack(
        (int) (alpha(intValue) + (alpha(intValue2) - alpha(intValue)) * floatValue),
        (int) (red(intValue) + (red(intValue2) - red(intValue)) * floatValue),
        (int) (green(intValue) + (green(intValue2) - green(intValue)) * floatValue),
        (int) (blue(intValue) + (blue(intValue2) - blue(intValue)) * floatValue));
  }

  public static int lighten(int intValue, float floatValue) {
    return lerp(intValue, withAlpha(-1, alpha(intValue)), floatValue);
  }

  public static int darken(int intValue, float floatValue) {
    return lerp(intValue, withAlpha(-16777216, alpha(intValue)), floatValue);
  }
}
