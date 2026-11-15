package bunnythekenzo.client;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;

public final class SearchTextNormalizer {
  public static String getString(String text) {
    if (text != null && !text.isBlank()) {
      String text2 = Normalizer.normalize(getString2(text), Form.NFKD).toLowerCase(Locale.ROOT);
      StringBuilder builder = new StringBuilder(text2.length());
      boolean flag = true;
      int intValue = 0;

      while (intValue < text2.length()) {
        int intValue2 = text2.codePointAt(intValue);
        intValue += Character.charCount(intValue2);
        if (!test(intValue2) && !test2(intValue2)) {
          int intValue3 = computeInt(intValue2);
          if ((intValue3 < 97 || intValue3 > 122) && (intValue3 < 48 || intValue3 > 57)) {
            if (!flag) {
              builder.append(' ');
              flag = true;
            }
          } else {
            builder.appendCodePoint(intValue3);
            flag = false;
          }
        }
      }

      intValue = builder.length();
      if (intValue > 0 && builder.charAt(intValue - 1) == ' ') {
        builder.setLength(intValue - 1);
      }

      return builder.toString();
    } else {
      return "";
    }
  }

  public static boolean test(String text, String text2) {
    String text3 = getString2(text == null ? "" : text).toLowerCase(Locale.ROOT);
    String text4 = getString2(text2 == null ? "" : text2).trim().toLowerCase(Locale.ROOT);
    if (text4.matches("[a-z0-9_]{1,16}")) {
      int intValue = 0;

      while (intValue <= text3.length() - text4.length()) {
        int intValue2 = text3.indexOf(text4, intValue);
        if (intValue2 < 0) {
          return false;
        }

        int intValue3 = intValue2 + text4.length();
        boolean flag = intValue2 == 0 || !test(text3.charAt(intValue2 - 1));
        boolean flag2 = intValue3 == text3.length() || !test(text3.charAt(intValue3));
        if (flag && flag2) {
          return true;
        }

        intValue = intValue2 + 1;
      }

      return false;
    } else {
      String text5 = getString(text);
      String text6 = getString(text2);
      return !text5.isBlank() && !text6.isBlank()
          ? text5.equals(text6) || (" " + text5 + " ").contains(" " + text6 + " ")
          : false;
    }
  }

  public static boolean test2(String text, String text2) {
    if (!test(text, text2)) {
      return false;
    }

    String text3 = " " + getString(text) + " ";
    String text4 = getString(text2);
    if (text4.isBlank()) {
      return false;
    }

    String text5 = " " + text4 + " ";
    return text3.contains(text5 + "left the game ")
        || text3.contains(text5 + "left ")
        || text3.contains(text5 + "has left ")
        || text3.contains(text5 + "quit ")
        || text3.contains(text5 + "has quit ")
        || text3.contains(text5 + "disconnected ")
        || text3.contains(text5 + "logged out ");
  }

  public static boolean test(String text) {
    if (text == null) {
      return false;
    }

    String text2 = text.trim();
    int length = text2.length();
    return length >= 3 && length <= 16 ? text2.indexOf(32) < 0 : false;
  }

  public static boolean test2(String text) {
    if (text == null) {
      return false;
    }

    int length = text.length();
    if (length >= 2 && length <= 32) {
      boolean flag = false;

      for (int index = 0; index < length; index++) {
        char character = text.charAt(index);
        boolean flag2 =
            character >= 'A' && character <= 'Z'
                || character >= 'a' && character <= 'z'
                || character >= '0' && character <= '9';
        if (flag2) {
          flag = true;
        } else if (character != '_' && character != '.' && character != ' ') {
          return false;
        }
      }

      return flag;
    } else {
      return false;
    }
  }

  public static boolean test(char character) {
    return character >= 'a' && character <= 'z'
        || character >= '0' && character <= '9'
        || character == '_';
  }

  public static boolean test(int intValue) {
    int type = Character.getType(intValue);
    return type == 6 || type == 8 || type == 7;
  }

  public static boolean test2(int intValue) {
    return intValue == 167
        || intValue == 8203
        || intValue == 8204
        || intValue == 8205
        || intValue == 65279;
  }

  public static String getString2(String text) {
    if (text != null && !text.isEmpty()) {
      StringBuilder builder = new StringBuilder(text.length());

      for (int index = 0; index < text.length(); index++) {
        char character = text.charAt(index);
        if (character == 167 && index + 1 < text.length()) {
          char character2 = Character.toLowerCase(text.charAt(index + 1));
          if ("0123456789abcdefklmnorx".indexOf(character2) >= 0) {
            index++;
            continue;
          }
        }

        builder.append(character);
      }

      return builder.toString();
    } else {
      return "";
    }
  }

  public static int computeInt(int intValue) {
    return switch (intValue) {
      case 593, 945, 1072, 7424 -> 97;
      case 609, 610 -> 103;
      case 618, 953, 1110 -> 105;
      case 628, 1400 -> 110;
      case 640 -> 114;
      case 655, 1091 -> 121;
      case 665, 946, 1074 -> 98;
      case 668, 1085 -> 104;
      case 671 -> 108;
      case 949, 1077, 7431 -> 101;
      case 954, 1082, 7435 -> 107;
      case 957, 7456 -> 118;
      case 959, 1086, 7439 -> 111;
      case 961, 1088, 7448 -> 112;
      case 964, 1090, 7451 -> 116;
      case 965, 7452 -> 117;
      case 1010, 1089, 7428 -> 99;
      case 1084, 7437 -> 109;
      case 1093 -> 120;
      case 1109, 42801 -> 115;
      case 1112, 7434 -> 106;
      case 1121, 7457 -> 119;
      case 1281, 7429 -> 100;
      case 1307 -> 113;
      case 7458, 7459 -> 122;
      case 42800 -> 102;
      default -> intValue;
    };
  }
}
