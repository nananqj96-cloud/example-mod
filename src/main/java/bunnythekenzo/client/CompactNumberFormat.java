package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public final class CompactNumberFormat {
  public static final String[] stringArray = new String[] {"qt", "q", "t", "b", "m", "k"};
  public static final String[] stringArray2 = new String[] {"QT", "Q", "T", "B", "M", "k"};
  public static final int[] countArray = new int[] {18, 15, 12, 9, 6, 3};
  public static final Pattern pattern =
      Pattern.compile("\\d[\\d.,]*(?:\\s?(?:[qQ][tT]|[kKmMbBtTqQ]))?");

  public static double computeDouble(String text) {
    if (text == null) {
      return Double.NaN;
    }

    String text2 = text.trim().toLowerCase(Locale.ROOT).replaceAll("[,\\s_]", "");

    while (!text2.isEmpty()
        && (text2.charAt(0) == '$' || text2.charAt(0) == 8364 || text2.charAt(0) == 163)) {
      text2 = text2.substring(1);
    }

    if (text2.isEmpty()) {
      return Double.NaN;
    }

    for (int index = 0; index < stringArray.length; index++) {
      if (text2.endsWith(stringArray[index]) && text2.length() > stringArray[index].length()) {
        String text3 = text2.substring(0, text2.length() - stringArray[index].length());

        try {
          return Double.parseDouble(text3) * Math.pow(10.0, countArray[index]);
        } catch (NumberFormatException failure) {
          return Double.NaN;
        }
      }
    }

    try {
      return Double.parseDouble(text2);
    } catch (NumberFormatException failure2) {
      return Double.NaN;
    }
  }

  public static String getString(double doubleValue) {
    double doubleValue2 = Math.abs(doubleValue);

    for (int index = 0; index < countArray.length; index++) {
      double doubleValue3 = Math.pow(10.0, countArray[index]);
      if (doubleValue2 >= doubleValue3) {
        double doubleValue4 = Math.round(doubleValue / doubleValue3 * 10.0) / 10.0;
        return getString4(doubleValue4) + stringArray2[index];
      }
    }

    return Long.toString(Math.round(doubleValue));
  }

  public static String getString2(double doubleValue) {
    return String.format(Locale.US, "%,d", (long) Math.floor(doubleValue));
  }

  public static String getString3(double doubleValue) {
    return Long.toString((long) Math.floor(doubleValue));
  }

  public static String getString(double doubleValue, String text) {
    return switch (text) {
      case "Short" -> getString(doubleValue);
      case "Plain" -> getString3(doubleValue);
      default -> getString2(doubleValue);
    };
  }

  public static String getString4(double doubleValue) {
    return doubleValue == Math.floor(doubleValue) && !Double.isInfinite(doubleValue)
        ? Long.toString((long) doubleValue)
        : String.valueOf(doubleValue);
  }

  public static int[] getCountArray(String text) {
    Matcher matcher = pattern.matcher(text);
    int intValue = -1;

    int intValue2;
    for (intValue2 = -1; matcher.find(); intValue2 = matcher.end()) {
      intValue = matcher.start();
    }

    return intValue < 0 ? null : new int[] {intValue, intValue2};
  }

  public static int[] getCountArray2(String text) {
    for (int index = 0; index < text.length(); index++) {
      if (Character.isDigit(text.charAt(index))) {
        return new int[] {index, text.length()};
      }
    }

    return null;
  }

  public static Text getText(Text text, String text2) {
    return spliceStyled(text, text2, false);
  }

  public static Text getText2(Text text, String text2) {
    return spliceStyled(text, text2, true);
  }

  public static Text spliceStyled(Text text, String text2, boolean flag) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    text.visit(
        (style, text3) -> {
          arrayList.add(style);
          arrayList2.add(text3);
          return Optional.empty();
        },
        Style.EMPTY);
    StringBuilder builder = new StringBuilder();

    for (String text4 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (arrayList2)) {
      builder.append(text4);
    }

    int[] intValues = flag ? getCountArray2(builder.toString()) : getCountArray(builder.toString());
    if (intValues == null) {
      return text;
    }

    int intValue = intValues[0];
    int intValue2 = intValues[1];
    MutableText mutableText = Text.empty();
    int intValue3 = 0;
    boolean flag2 = false;

    for (int index = 0; index < arrayList2.size(); index++) {
      String text5 = (String) arrayList2.get(index);
      Style style2 = (Style) arrayList.get(index);
      int intValue4 = intValue3;
      int intValue5 = intValue3 + text5.length();
      int intValue6 = Math.min(intValue5, intValue);
      if (intValue6 > intValue4) {
        mutableText.append(
            Text.literal(text5.substring(0, intValue6 - intValue4)).setStyle(style2));
      }

      if (!flag2 && intValue >= intValue4 && intValue < intValue5) {
        mutableText.append(Text.literal(text2).setStyle(style2));
        flag2 = true;
      }

      int intValue7 = Math.max(intValue4, intValue2);
      if (intValue5 > intValue7) {
        mutableText.append(Text.literal(text5.substring(intValue7 - intValue4)).setStyle(style2));
      }

      intValue3 = intValue5;
    }

    if (!flag2) {
      mutableText.append(Text.literal(text2));
    }

    return mutableText;
  }
}
