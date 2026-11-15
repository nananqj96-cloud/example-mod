package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.function.DoubleFunction;

public class NumberSetting extends Setting<Double> {
  public final double minimum;
  public final double maximum;
  public final double step;
  public final String unit;
  public DoubleFunction formatter;

  public NumberSetting(
      String text,
      String text2,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4) {
    this(text, text2, doubleValue, doubleValue2, doubleValue3, doubleValue4, "");
  }

  public NumberSetting(
      String text,
      String text2,
      double doubleValue,
      double value4,
      double value22,
      double value32,
      String string2) {
    super(text, text2, doubleValue);
    this.minimum = value4;
    this.maximum = value22;
    this.step = value32;
    this.unit = string2;
  }

  public double getMinimum() {
    return this.minimum;
  }

  public double getMaximum() {
    return this.maximum;
  }

  public double getStep() {
    return this.step;
  }

  public String getUnit() {
    return this.unit;
  }

  public float floatValue() {
    return ((Double) this.getValue()).floatValue();
  }

  public int intValue() {
    return (int) Math.round((Double) this.getValue());
  }

  public double getFraction() {
    return ((Double) this.getValue() - this.minimum) / (this.maximum - this.minimum);
  }

  public void setFraction(double doubleValue) {
    this.setNumber(
        Double.valueOf(
            this.minimum + (this.maximum - this.minimum) * Math.clamp(doubleValue, 0.0, 1.0)));
  }

  public void setNumber(Double doubleValue) {
    double doubleValue2 = Math.round(doubleValue / this.step) * this.step;
    doubleValue2 = Math.round(doubleValue2 * 1000000.0) / 1000000.0;
    super.setValue(Math.clamp(doubleValue2, this.minimum, this.maximum));
  }

  public NumberSetting withFormatter(DoubleFunction doubleFunction2) {
    this.formatter = doubleFunction2;
    return this;
  }

  public String getFormattedValue() {
    if (this.formatter != null) {
      return (String) this.formatter.apply((Double) this.getValue());
    }

    double doubleValue = (Double) this.getValue();
    String text;
    if (this.step >= 1.0 && doubleValue == Math.floor(doubleValue)) {
      text = Long.toString((long) doubleValue);
    } else {
      text = String.valueOf(Math.round(doubleValue * 100.0) / 100.0);
      if (text.endsWith(".0")) {
        text = text.substring(0, text.length() - 2);
      }
    }

    return text + this.unit;
  }

  @Override
  public JsonElement toJson() {
    return new JsonPrimitive((Number) this.value);
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null
        && jsonElement.isJsonPrimitive()
        && jsonElement.getAsJsonPrimitive().isNumber()) {
      this.setNumber(Double.valueOf(jsonElement.getAsDouble()));
    }
  }

  @Override
  public void setValue(Double doubleValue) {
    this.setNumber((Double) doubleValue);
  }
}
