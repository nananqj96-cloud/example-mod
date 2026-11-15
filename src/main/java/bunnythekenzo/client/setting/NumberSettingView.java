package bunnythekenzo.client.setting;

import java.util.Locale;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;

public final class NumberSettingView extends SettingView {
  public final DoubleSupplier valueSupplier;
  public final DoubleConsumer valueConsumer;
  public final DoubleFunction formatter;
  public final float minimum;
  public final String unit;
  public final float maximum;
  public final float step;
  public float value;

  public NumberSettingView(
      String text,
      String text2,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      String text3) {
    this(text, text2, floatValue, floatValue2, floatValue3, floatValue4, text3, null, null, null);
  }

  public NumberSettingView(
      String text,
      String text2,
      float amount42,
      float amount5,
      float amount22,
      float floatValue,
      String string2,
      DoubleSupplier doubleSupplier2,
      DoubleConsumer doubleConsumer2,
      DoubleFunction doubleFunction2) {
    super(text, text2);
    this.minimum = amount5;
    this.maximum = amount22;
    this.step = floatValue <= 0.0F ? 1.0F : floatValue;
    this.unit = string2;
    this.valueSupplier = doubleSupplier2;
    this.valueConsumer = doubleConsumer2;
    this.formatter = doubleFunction2;
    this.value = amount42;
  }

  @Override
  public boolean isBound() {
    return this.valueSupplier != null;
  }

  public float getValue() {
    return this.valueSupplier != null ? (float) this.valueSupplier.getAsDouble() : this.value;
  }

  public float getMinimum() {
    return this.minimum;
  }

  public float getMaximum() {
    return this.maximum;
  }

  public float getStep() {
    return this.step;
  }

  public String getUnit() {
    return this.unit;
  }

  public float getFraction() {
    return this.maximum <= this.minimum
        ? 0.0F
        : (this.getValue() - this.minimum) / (this.maximum - this.minimum);
  }

  public void setValue(float floatValue) {
    float amount42 =
        Math.clamp(Math.round(floatValue / this.step) * this.step, this.minimum, this.maximum);
    if (this.valueConsumer != null) {
      this.valueConsumer.accept(amount42);
    } else {
      this.value = amount42;
    }
  }

  public void setFraction(float floatValue) {
    this.setValue(
        this.minimum + Math.clamp(floatValue, 0.0F, 1.0F) * (this.maximum - this.minimum));
  }

  public String getFormattedValue() {
    float value = this.getValue();
    if (this.formatter != null) {
      return (String) this.formatter.apply(value);
    }

    String text =
        this.step >= 1.0F && value == Math.floor(value)
            ? String.valueOf((int) value)
            : String.format(Locale.ROOT, "%.2f", value);
    return text + this.unit;
  }
}
