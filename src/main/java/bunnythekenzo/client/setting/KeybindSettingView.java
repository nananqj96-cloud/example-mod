package bunnythekenzo.client.setting;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public final class KeybindSettingView extends SettingView {
  public final IntSupplier intSupplier;
  public final IntConsumer intConsumer;
  public int count;

  public KeybindSettingView(String text, String text2, int intValue) {
    this(text, text2, intValue, null, null);
  }

  public KeybindSettingView(
      String text, String text2, int count2, IntSupplier intSupplier2, IntConsumer intConsumer2) {
    super(text, text2);
    this.count = count2;
    this.intSupplier = intSupplier2;
    this.intConsumer = intConsumer2;
  }

  @Override
  public boolean isBound() {
    return this.intSupplier != null;
  }

  public int computeInt() {
    return this.intSupplier != null ? this.intSupplier.getAsInt() : this.count;
  }

  public void apply(int count2) {
    if (this.intConsumer != null) {
      this.intConsumer.accept(count2);
    } else {
      this.count = count2;
    }
  }

  public boolean test3() {
    return this.computeInt() >= 0;
  }
}
