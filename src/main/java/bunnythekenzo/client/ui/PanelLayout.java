package bunnythekenzo.client.ui;

import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import bunnythekenzo.client.CollectionData3;
import bunnythekenzo.client.JsonSupport2;

public class PanelLayout {
  public final Map map = new LinkedHashMap();
  public final Set set = new HashSet();
  public boolean enabled;
  public boolean enabled2;
  public float amount = -1.0F;
  public float amount2 = -1.0F;
  public static final int count = 4;
  public static final float amount3 = 210.0F;

  public void update() {
    this.enabled2 = true;
  }

  public PanelLayout() {
    float floatValue = 16.0F;

    for (CollectionData3 collectionData3 :
        (java.lang.Iterable<CollectionData3>) (java.lang.Iterable<?>) (JsonSupport2.getList())) {
      this.map.put(collectionData3.string(), new PanelLayoutSupport(floatValue, 64.0F));
      floatValue += 222.0F;
    }
  }

  public void ensureDefaultLayout(float amount4, float amount22) {
    if (!this.enabled2 && (!this.enabled || amount4 != this.amount || amount22 != this.amount2)) {
      this.enabled = true;
      this.amount = amount4;
      this.amount2 = amount22;
      String[] textArray =
          (java.lang.String[])
              (JsonSupport2.getList().stream()
                  .map(
                      collectionData3 -> {
                        return ((bunnythekenzo.client.CollectionData3) (collectionData3)).string();
                      })
                  .toArray(String[]::new));
      float floatValue = textArray.length * 222.0F + 12.0F;
      if (amount4 >= floatValue) {
        float floatValue2 = (amount4 - textArray.length * 210.0F) / (textArray.length + 1);
        float amount5 = floatValue2;

        for (int index = 0; index < textArray.length; index++) {
          PanelLayoutSupport panelLayoutSupport = this.panel(textArray[index]);
          panelLayoutSupport.amount = amount5;
          panelLayoutSupport.amount2 = 64.0F;
          panelLayoutSupport.enabled = false;
          amount5 += 210.0F + floatValue2;
        }
      } else {
        int intValue = Math.max(1, (int) ((amount4 - 24.0F) / 222.0F));

        for (int index2 = 0; index2 < textArray.length; index2++) {
          PanelLayoutSupport panelLayoutSupport2 = this.panel(textArray[index2]);
          panelLayoutSupport2.amount = 12.0F + index2 % intValue * 222.0F;
          int intValue2 = (textArray.length + intValue - 1) / intValue;
          panelLayoutSupport2.amount2 =
              64.0F + index2 / intValue * ((amount22 - 122.0F) / intValue2);
          panelLayoutSupport2.enabled = false;
        }
      }
    }
  }

  public PanelLayoutSupport panel(String text) {
    return (bunnythekenzo.client.ui.PanelLayoutSupport)
        (this.map.computeIfAbsent(
            text,
            value -> {
              return new PanelLayoutSupport(16.0F, 16.0F);
            }));
  }

  public void update2() {
    this.enabled2 = false;
    this.enabled = false;
    this.set.clear();
  }

  public boolean test(String text) {
    return this.set.contains(text);
  }

  public void apply(String text, boolean flag) {
    if (flag) {
      this.set.add(text);
    } else {
      this.set.remove(text);
    }
  }

  public JsonObject getJsonObject() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("v", 4);
    jsonObject.addProperty("custom", this.enabled2);

    for (Entry entry : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (this.map.entrySet())) {
      JsonObject jsonObject2 = new JsonObject();
      jsonObject2.addProperty("x", ((PanelLayoutSupport) entry.getValue()).amount);
      jsonObject2.addProperty("y", ((PanelLayoutSupport) entry.getValue()).amount2);
      jsonObject2.addProperty("collapsed", ((PanelLayoutSupport) entry.getValue()).enabled);
      jsonObject.add((String) entry.getKey(), jsonObject2);
    }

    return jsonObject;
  }

  public void apply(JsonObject jsonObject) {
    if (jsonObject.has("v") && jsonObject.get("v").getAsInt() >= 4) {
      if (jsonObject.has("custom") && jsonObject.get("custom").getAsBoolean()) {
        this.enabled = true;
        this.enabled2 = true;

        for (Entry entry :
            (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (this.map.entrySet())) {
          JsonObject asJsonObject = jsonObject.getAsJsonObject((String) entry.getKey());
          if (asJsonObject != null) {
            if (asJsonObject.has("x")) {
              ((PanelLayoutSupport) entry.getValue()).amount = asJsonObject.get("x").getAsFloat();
            }

            if (asJsonObject.has("y")) {
              ((PanelLayoutSupport) entry.getValue()).amount2 = asJsonObject.get("y").getAsFloat();
            }

            if (asJsonObject.has("collapsed")) {
              ((PanelLayoutSupport) entry.getValue()).enabled =
                  asJsonObject.get("collapsed").getAsBoolean();
            }
          }
        }
      }
    }
  }
}
