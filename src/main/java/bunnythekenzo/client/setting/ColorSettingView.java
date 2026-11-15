package bunnythekenzo.client.setting;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public final class ColorSettingView extends SettingView {
  public final IntSupplier intSupplier;
  public final IntConsumer intConsumer;
  public Runnable runnable;
  public final int count;
  public int count2;

  public ColorSettingView(String text, String text2, int intValue) {
    this(text, text2, intValue, null, null);
  }

  public ColorSettingView(
      String text, String text2, int count3, IntSupplier intSupplier2, IntConsumer intConsumer2) {
    super(text, text2);
    this.count = count3;
    this.count2 = count3;
    this.intSupplier = intSupplier2;
    this.intConsumer = intConsumer2;
  }

  @Override
  public boolean isBound() {
    return this.intSupplier != null;
  }

  public int computeInt() {
    return this.intSupplier != null ? this.intSupplier.getAsInt() : this.count2;
  }

  public void apply(int intValue) {
    int count22 = 0xFF000000 | intValue & 16777215;
    if (this.intConsumer != null) {
      this.intConsumer.accept(count22);
    } else {
      this.count2 = count22;
    }
  }

  public int getCount() {
    return this.count;
  }

  public ColorSettingView getColorSettingView(Runnable runnable2) {
    this.runnable = runnable2;
    return this;
  }

  public void update() {
    if (this.runnable != null) {
      this.runnable.run();
    } else {
      this.apply(this.count);
    }
  }
}
