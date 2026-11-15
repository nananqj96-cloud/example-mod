package bunnythekenzo.client.setting;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public final class ToggleSettingView extends SettingView {
  public final BooleanSupplier valueSupplier;
  public final Consumer valueConsumer;
  public boolean value;

  public ToggleSettingView(String text, String text2, boolean flag) {
    this(text, text2, flag, null, null);
  }

  public ToggleSettingView(
      String text,
      String text2,
      boolean enabled2,
      BooleanSupplier booleanSupplier2,
      Consumer consumer2) {
    super(text, text2);
    this.valueSupplier = booleanSupplier2;
    this.valueConsumer = consumer2;
    this.value = enabled2;
  }

  @Override
  public boolean isBound() {
    return this.valueSupplier != null;
  }

  public boolean getValue() {
    return this.valueSupplier != null ? this.valueSupplier.getAsBoolean() : this.value;
  }

  public void setValue(boolean enabled2) {
    if (this.valueConsumer != null) {
      this.valueConsumer.accept(enabled2);
    } else {
      this.value = enabled2;
    }
  }

  public boolean toggle() {
    this.setValue(!this.getValue());
    return this.getValue();
  }
}
