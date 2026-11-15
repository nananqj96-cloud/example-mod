package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import java.util.function.BooleanSupplier;

public abstract class Setting<T> {
  public final String name;
  public final String description;
  public T value;
  public final T defaultValue;
  public BooleanSupplier visibilityCondition =
      () -> {
        return true;
      };

  public Setting(String string3, String string22, T object3) {
    this.name = string3;
    this.description = string22;
    this.value = object3;
    this.defaultValue = object3;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public T getValue() {
    return this.value;
  }

  public void setValue(T object3) {
    this.value = object3;
  }

  public void resetToDefault() {
    this.value = this.defaultValue;
  }

  public void setVisibilityCondition(BooleanSupplier booleanSupplier2) {
    this.visibilityCondition = booleanSupplier2;
  }

  public boolean isVisible() {
    return this.visibilityCondition.getAsBoolean();
  }

  public abstract JsonElement toJson();

  public abstract void fromJson(JsonElement jsonElement);
}
