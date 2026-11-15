package bunnythekenzo.client;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public final class ChatMessageBuilder {
  public final MutableText mutableText = Text.empty();
  public static int[] countArray;

  public ChatMessageBuilder getChatMessageBuilder(String text) {
    getCountArray();
    this.mutableText.append(Text.literal(text == null ? "" : text).withColor(15067115));
    return this;
  }

  public ChatMessageBuilder getChatMessageBuilder2(String text) {
    getCountArray();
    this.mutableText.append(Text.literal(text == null ? "" : text).withColor(6809849));
    return this;
  }

  public ChatMessageBuilder getChatMessageBuilder3(String text) {
    getCountArray();
    this.mutableText.append(Text.literal(text == null ? "" : text).withColor(16639626));
    return this;
  }

  public ChatMessageBuilder getChatMessageBuilder4(String text) {
    getCountArray();
    this.mutableText.append(Text.literal(text == null ? "" : text).withColor(8843180));
    return this;
  }

  public ChatMessageBuilder getChatMessageBuilder5(String text) {
    getCountArray();
    this.mutableText.append(Text.literal(text == null ? "" : text).withColor(16498468));
    return this;
  }

  public ChatMessageBuilder getChatMessageBuilder6(String text) {
    getCountArray();
    this.mutableText.append(Text.literal(text == null ? "" : text).withColor(16281969));
    ClientInitializer.ensureInitialized();
    return this;
  }

  public ChatMessageBuilder getLookupSupport4(Text text) {
    if (text != null) {
      this.mutableText.append(text);
    }

    return this;
  }

  public Text getText() {
    return this.mutableText.copy();
  }

  public static void setCountArray(int[] countArray2) {
    countArray = countArray2;
  }

  public static int[] getCountArray() {
    return countArray;
  }

  static {
    int[] intValues = new int[1];

    setCountArray(intValues);
  }
}
