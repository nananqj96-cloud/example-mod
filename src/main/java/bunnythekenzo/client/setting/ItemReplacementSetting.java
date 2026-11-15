package bunnythekenzo.client.setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public final class ItemReplacementSetting extends Setting<java.util.List<ItemReplacementRule>> {
  public volatile Map map = Map.of();

  public ItemReplacementSetting(String text, String text2) {
    super(
        text,
        text2,
        List.of(new ItemReplacementRule(Items.GRASS_BLOCK, Items.ANCIENT_DEBRIS, true)));
    this.apply((List) this.defaultValue);
  }

  public void apply(List list) {
    if (list != null) {
      LinkedHashMap linkedHashMap = new LinkedHashMap();

      for (ItemReplacementRule choiceSettingData :
          (java.lang.Iterable<ItemReplacementRule>) (java.lang.Iterable<?>) (list)) {
        if (choiceSettingData != null
            && test(choiceSettingData.item())
            && test(choiceSettingData.item2())) {
          linkedHashMap.put(choiceSettingData.item(), choiceSettingData);
        }
      }

      this.value = List.copyOf(linkedHashMap.values());
      LinkedHashMap linkedHashMap2 = new LinkedHashMap();

      for (ItemReplacementRule choiceSettingData2 :
          (java.lang.Iterable<ItemReplacementRule>) (java.lang.Iterable<?>) ((List) this.value)) {
        if (choiceSettingData2.enabled()
            && choiceSettingData2.item() != choiceSettingData2.item2()) {
          linkedHashMap2.put(choiceSettingData2.item(), choiceSettingData2.item2());
        }
      }

      this.map = Map.copyOf(linkedHashMap2);
    }
  }

  public Item getItem(Item item) {
    return (Item) this.map.get(item);
  }

  public int computeInt() {
    return this.map.size();
  }

  public void put(Item item, Item item2) {
    ArrayList arrayList = new ArrayList((Collection) this.value);
    ((java.util.List) (arrayList))
        .removeIf(
            value -> {
              return ((bunnythekenzo.client.setting.ItemReplacementRule) (value)).item() == item;
            });
    arrayList.add(new ItemReplacementRule(item, item2, true));
    this.apply(arrayList);
  }

  public void update(
      ItemReplacementRule choiceSettingData, ItemReplacementRule choiceSettingData2) {
    ArrayList arrayList = new ArrayList((Collection) this.value);
    int intValue = arrayList.indexOf(choiceSettingData);
    if (intValue >= 0) {
      arrayList.set(intValue, choiceSettingData2);
      ((java.util.List) (arrayList))
          .removeIf(
              value -> {
                return value != choiceSettingData2
                    && ((bunnythekenzo.client.setting.ItemReplacementRule) (value)).item()
                        == choiceSettingData2.item();
              });
      this.apply(arrayList);
    }
  }

  public void apply(ItemReplacementRule choiceSettingData) {
    ArrayList arrayList = new ArrayList((Collection) this.value);
    arrayList.remove(choiceSettingData);
    this.apply(arrayList);
  }

  @Override
  public void resetToDefault() {
    this.apply((List) this.defaultValue);
  }

  @Override
  public JsonElement toJson() {
    JsonArray jsonArray = new JsonArray();

    for (ItemReplacementRule choiceSettingData :
        (java.lang.Iterable<ItemReplacementRule>) (java.lang.Iterable<?>) ((List) this.value)) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("source", Registries.ITEM.getId(choiceSettingData.item()).toString());
      jsonObject.addProperty("target", Registries.ITEM.getId(choiceSettingData.item2()).toString());
      jsonObject.addProperty("enabled", choiceSettingData.enabled());
      jsonArray.add(jsonObject);
    }

    return jsonArray;
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null && jsonElement.isJsonArray()) {
      ArrayList arrayList = new ArrayList();

      for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
        try {
          JsonObject asJsonObject = jsonElement2.getAsJsonObject();
          Item item = Registries.ITEM.get(Identifier.of(asJsonObject.get("source").getAsString()));
          Item item2 = Registries.ITEM.get(Identifier.of(asJsonObject.get("target").getAsString()));
          if (test(item) && test(item2)) {
            arrayList.add(
                new ItemReplacementRule(
                    item,
                    item2,
                    !asJsonObject.has("enabled") || asJsonObject.get("enabled").getAsBoolean()));
          }
        } catch (RuntimeException failure) {
        }
      }

      this.apply(arrayList);
    }
  }

  public static boolean test(Item item) {
    return item != null && item != Items.AIR;
  }

  @Override
  public void setValue(List<ItemReplacementRule> list) {
    this.apply((List) list);
  }
}
