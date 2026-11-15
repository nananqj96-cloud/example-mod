package bunnythekenzo.client.setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;

public class EntityTypeSetting extends Setting<java.util.List<EntityFilterEntry>> {
  public final Map map = new LinkedHashMap();

  public EntityTypeSetting(String text, String text2) {
    super(text, text2, new ArrayList());
  }

  public EntityFilterEntry getEntityFilterEntry(
      String text, String text2, Item item, boolean flag, int intValue) {
    EntityFilterEntry choiceSettingSupport2 =
        new EntityFilterEntry(text, text2, item, flag, intValue);
    ((List) this.value).add(choiceSettingSupport2);
    this.map.put(text, choiceSettingSupport2);
    return choiceSettingSupport2;
  }

  public List getList() {
    return (List) this.value;
  }

  public EntityFilterEntry getEntityFilterEntry(String text) {
    return (EntityFilterEntry) this.map.get(text);
  }

  public boolean test(String text) {
    EntityFilterEntry choiceSettingSupport2 = (EntityFilterEntry) this.map.get(text);
    return choiceSettingSupport2 != null
        && (Boolean) choiceSettingSupport2.enabledSetting.getValue();
  }

  public int computeInt(String text) {
    EntityFilterEntry choiceSettingSupport2 = (EntityFilterEntry) this.map.get(text);
    return choiceSettingSupport2 != null ? choiceSettingSupport2.colorSetting.getInteger() : -1;
  }

  public int computeInt() {
    return ((List) this.value).size();
  }

  public long enabledCount() {
    return ((List) this.value)
        .stream()
            .filter(
                value -> {
                  return (Boolean)
                      ((bunnythekenzo.client.setting.EntityFilterEntry) (value))
                          .enabledSetting.getValue();
                })
            .count();
  }

  @Override
  public JsonElement toJson() {
    JsonArray jsonArray = new JsonArray();

    for (EntityFilterEntry choiceSettingSupport2 :
        (java.lang.Iterable<EntityFilterEntry>) (java.lang.Iterable<?>) ((List) this.value)) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("key", choiceSettingSupport2.string);
      jsonObject.addProperty("enabled", (Boolean) choiceSettingSupport2.enabledSetting.getValue());
      jsonObject.addProperty("color", choiceSettingSupport2.colorSetting.getInteger());
      jsonArray.add(jsonObject);
    }

    return jsonArray;
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null && jsonElement.isJsonArray()) {
      for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
        if (jsonElement2.isJsonObject()) {
          JsonObject asJsonObject = jsonElement2.getAsJsonObject();
          if (asJsonObject.has("key")) {
            EntityFilterEntry choiceSettingSupport2 =
                (EntityFilterEntry) this.map.get(asJsonObject.get("key").getAsString());
            if (choiceSettingSupport2 != null) {
              if (asJsonObject.has("enabled")) {
                choiceSettingSupport2.enabledSetting.setValue(
                    asJsonObject.get("enabled").getAsBoolean());
              }

              if (asJsonObject.has("color")) {
                choiceSettingSupport2.colorSetting.apply(
                    Integer.valueOf(asJsonObject.get("color").getAsInt()));
              }
            }
          }
        }
      }
    }
  }
}
