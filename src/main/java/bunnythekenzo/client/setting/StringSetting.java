package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class StringSetting extends Setting<String> {
  public final int maxLength;
  public final String placeholder;

  public StringSetting(String text, String text2, String text3) {
    this(text, text2, text3, 32, "");
  }

  public StringSetting(String text, String text2, String text3, int count2, String string2) {
    super(text, text2, text3);
    this.maxLength = count2;
    this.placeholder = string2;
  }

  public int getMaxLength() {
    return this.maxLength;
  }

  public String getPlaceholder() {
    return this.placeholder;
  }

  @Override
  public JsonElement toJson() {
    return new JsonPrimitive((String) this.value);
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null && jsonElement.isJsonPrimitive()) {
      String asString = jsonElement.getAsString();
      this.value =
          asString.length() > this.maxLength ? asString.substring(0, this.maxLength) : asString;
    }
  }
}
