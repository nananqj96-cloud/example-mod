package bunnythekenzo.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class ThemeManager {
  public static final int count = -40008;
  public final List list = new ArrayList();

  public ThemeManager() {
    this.list.add(ColorPalette.colorPalette);
    this.list.add(new ColorPalette("Pink", -40008, false));
    this.list.add(new ColorPalette("Purple", -5743361, false));
    this.list.add(new ColorPalette("Blue", -11689985, false));
    this.list.add(new ColorPalette("Red", -45715, false));
    this.list.add(new ColorPalette("Emerald", -12654960, false));
    this.list.add(new RainbowPalette());
    this.apply2((ColorPalette) this.list.getFirst());
  }

  public ColorPalette getColorPalette() {
    return ColorPalette.getInstance();
  }

  public List getList() {
    return this.list;
  }

  public void apply(ColorPalette colorPalette) {
    if (this.list.contains(colorPalette)) {
      this.apply2(colorPalette);
    }
  }

  public boolean test(String text) {
    for (ColorPalette colorPalette :
        (java.lang.Iterable<ColorPalette>) (java.lang.Iterable<?>) (this.list)) {
      if (colorPalette.getString2().equals(text)) {
        this.apply2(colorPalette);
        return true;
      }
    }

    return false;
  }

  public void apply2(ColorPalette colorPalette) {
    ColorPalette.apply(colorPalette);
    GuiPreferences.getGuiPreferences().apply(colorPalette.getCount2());
  }

  public ColorPalette getColorPalette(int intValue) {
    int index = 1;

    for (ColorPalette colorPalette :
        (java.lang.Iterable<ColorPalette>) (java.lang.Iterable<?>) (this.list)) {
      if (colorPalette.isEnabled()) {
        index++;
      }
    }

    ColorPalette colorPalette2 = new ColorPalette("Custom " + index, intValue, true);
    this.list.add(colorPalette2);
    return colorPalette2;
  }

  public void apply3(ColorPalette colorPalette) {
    if (colorPalette.isEnabled()
        && this.list.remove(colorPalette)
        && this.getColorPalette() == colorPalette) {
      this.apply2((ColorPalette) this.list.getFirst());
    }
  }

  public JsonObject getJsonObject() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("current", this.getColorPalette().getString2());
    jsonObject.addProperty("accent", ColorPalette.colorPalette.getCount2());
    JsonArray jsonArray = new JsonArray();

    for (ColorPalette colorPalette :
        (java.lang.Iterable<ColorPalette>) (java.lang.Iterable<?>) (this.list)) {
      if (colorPalette.isEnabled()) {
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("name", colorPalette.getString2());
        jsonObject2.addProperty("accent", colorPalette.getCount2());
        jsonArray.add(jsonObject2);
      }
    }

    jsonObject.add("custom", jsonArray);
    return jsonObject;
  }

  public void apply(JsonObject jsonObject) {
    if (jsonObject != null) {
      if (jsonObject.has("accent")) {
        ColorPalette.colorPalette.apply2(jsonObject.get("accent").getAsInt());
      }

      this.list.removeIf(
          (referenceArgument11_0) ->
              ((bunnythekenzo.client.ColorPalette) referenceArgument11_0).isEnabled());
      if (jsonObject.has("custom")) {
        for (JsonElement jsonElement : jsonObject.getAsJsonArray("custom")) {
          JsonObject asJsonObject = jsonElement.getAsJsonObject();
          this.list.add(
              new ColorPalette(
                  asJsonObject.get("name").getAsString(),
                  asJsonObject.get("accent").getAsInt(),
                  true));
        }
      }

      if (jsonObject.has("current")) {
        this.test(jsonObject.get("current").getAsString());
      }

      if (!this.list.contains(this.getColorPalette())) {
        this.apply2((ColorPalette) this.list.getFirst());
      } else {
        this.apply2(this.getColorPalette());
      }
    }
  }
}
