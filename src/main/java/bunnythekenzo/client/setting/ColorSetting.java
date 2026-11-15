package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import bunnythekenzo.client.ColorPalette;

public class ColorSetting extends Setting<Integer> {
  public boolean enabled;
  public boolean enabled2;
  public boolean enabled3;

  public ColorSetting(String text, String text2, int intValue) {
    super(text, text2, intValue);
  }

  public ColorSetting getColorSetting() {
    return this.getColorSetting(false);
  }

  public ColorSetting getColorSetting(boolean enabled32) {
    this.enabled = this.enabled2 = true;
    this.enabled3 = enabled32;
    return this;
  }

  public Integer getInteger() {
    if (!this.enabled2) {
      return (Integer) this.value;
    }

    int intValue =
        this.enabled3
            ? ColorPalette.getInstance().computeInt()
            : ColorPalette.getInstance().getCount();
    return (Integer) this.defaultValue & 0xFF000000 | intValue & 16777215;
  }

  public void apply(Integer intValue) {
    super.setValue(intValue);
    this.enabled2 = false;
  }

  @Override
  public void resetToDefault() {
    super.resetToDefault();
    this.enabled2 = this.enabled;
  }

  public int computeInt() {
    return this.getInteger() >> 16 & 0xFF;
  }

  public int computeInt2() {
    return this.getInteger() >> 8 & 0xFF;
  }

  public int computeInt3() {
    return this.getInteger() & 0xFF;
  }

  public int computeInt4() {
    return this.getInteger() >>> 24 & 0xFF;
  }

  public void apply(int intValue) {
    this.apply(Integer.valueOf(this.getInteger() & -16711681 | (intValue & 0xFF) << 16));
  }

  public void apply2(int intValue) {
    this.apply(Integer.valueOf(this.getInteger() & -65281 | (intValue & 0xFF) << 8));
  }

  public void apply3(int intValue) {
    this.apply(Integer.valueOf(this.getInteger() & -256 | intValue & 0xFF));
  }

  public String getString() {
    return String.format("#%06X", this.getInteger() & 16777215);
  }

  @Override
  public JsonElement toJson() {
    if (this.enabled) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("color", (Number) this.value);
      jsonObject.addProperty("followTheme", this.enabled2);
      return jsonObject;
    } else {
      return new JsonPrimitive((Number) this.value);
    }
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null && jsonElement.isJsonObject()) {
      JsonObject asJsonObject = jsonElement.getAsJsonObject();
      if (asJsonObject.has("color")
          && asJsonObject.get("color").isJsonPrimitive()
          && asJsonObject.getAsJsonPrimitive("color").isNumber()) {
        this.value = asJsonObject.get("color").getAsInt();
        this.enabled2 =
            this.enabled
                && asJsonObject.has("followTheme")
                && asJsonObject.get("followTheme").getAsBoolean();
      }
    } else {
      if (jsonElement != null
          && jsonElement.isJsonPrimitive()
          && jsonElement.getAsJsonPrimitive().isNumber()) {
        this.value = jsonElement.getAsInt();
        this.enabled2 = this.enabled && ((Integer) this.value).equals(this.defaultValue);
      }
    }
  }

  @Override
  public void setValue(Integer intValue) {
    this.apply((Integer) intValue);
  }

  @Override
  public Integer getValue() {
    return this.getInteger();
  }
}
