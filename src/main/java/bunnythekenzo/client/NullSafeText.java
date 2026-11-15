package bunnythekenzo.client;

public final class NullSafeText {
  public static String orEmpty(String text) {
    ChatMessageBuilder.getCountArray();
    return text == null ? "" : text;
  }
}
