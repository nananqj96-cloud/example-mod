package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanSetting extends Setting<Boolean> {
  public BooleanSetting(String text, String text2, boolean flag) {
    super(text, text2, flag);
  }

  public void toggle() {
    this.setValue(!(Boolean) this.getValue());
  }

  @Override
  public JsonElement toJson() {
    return new JsonPrimitive((Boolean) this.value);
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null
        && jsonElement.isJsonPrimitive()
        && jsonElement.getAsJsonPrimitive().isBoolean()) {
      this.value = jsonElement.getAsBoolean();
    }
  }
}
