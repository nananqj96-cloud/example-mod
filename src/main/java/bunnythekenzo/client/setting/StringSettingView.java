package bunnythekenzo.client.setting;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class StringSettingView extends SettingView {
  public final Supplier valueSupplier;
  public final Consumer valueConsumer;
  public final String placeholder;
  public final int maxCodePoints;
  public String value;

  public StringSettingView(String text, String text2, String text3, String text4, int intValue) {
    this(text, text2, text3, text4, intValue, null, null);
  }

  public StringSettingView(
      String text,
      String text2,
      String text3,
      String string3,
      int intValue,
      Supplier supplier2,
      Consumer consumer2) {
    super(text, text2);
    this.placeholder = string3;
    this.maxCodePoints = Math.max(1, intValue);
    this.valueSupplier = supplier2;
    this.valueConsumer = consumer2;
    if (supplier2 == null) {
      this.setValue(text3);
    }
  }

  @Override
  public boolean isBound() {
    return this.valueSupplier != null;
  }

  public String getValue() {
    return this.valueSupplier != null ? (String) this.valueSupplier.get() : this.value;
  }

  public String getPlaceholder() {
    return this.placeholder;
  }

  public int getMaxCodePoints() {
    return this.maxCodePoints;
  }

  public void setValue(String text) {
    String string22 = text == null ? "" : text;
    int intValue = string22.codePointCount(0, string22.length());
    if (intValue > this.maxCodePoints) {
      string22 = string22.substring(0, string22.offsetByCodePoints(0, this.maxCodePoints));
    }

    if (this.valueConsumer != null) {
      this.valueConsumer.accept(string22);
    } else {
      this.value = string22;
    }
  }
}
