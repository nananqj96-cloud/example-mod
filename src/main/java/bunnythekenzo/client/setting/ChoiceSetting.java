package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.List;
import bunnythekenzo.client.DiagnosticTokenGenerator;

public class ChoiceSetting extends Setting<String> {
  public final List choices;

  public ChoiceSetting(String text, String text2, String text3, String[] textArray) {
    super(text, text2, text3);
    this.choices = List.of(textArray);
    if (!this.choices.contains(text3)) {
      throw new IllegalArgumentException(DiagnosticTokenGenerator.tokenFor(1501));
    }
  }

  public List getChoices() {
    return this.choices;
  }

  public boolean isSelected(String text) {
    return ((String) this.getValue()).equals(text);
  }

  public void cycleNext() {
    int intValue = (this.choices.indexOf(this.getValue()) + 1) % this.choices.size();
    this.setValue((String) this.choices.get(intValue));
  }

  @Override
  public JsonElement toJson() {
    return new JsonPrimitive((String) this.value);
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null
        && jsonElement.isJsonPrimitive()
        && this.choices.contains(jsonElement.getAsString())) {
      this.value = jsonElement.getAsString();
    }
  }
}
