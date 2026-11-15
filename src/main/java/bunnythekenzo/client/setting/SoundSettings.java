package bunnythekenzo.client.setting;

import com.google.gson.JsonObject;
import java.util.List;

public class SoundSettings {
  public final NumberSetting masterVolumeSetting =
      new NumberSetting(
          "Master Volume", "Volume for all client sounds", 60.0, 0.0, 100.0, 5.0, "%");
  public final BooleanSetting guiOpenCloseSetting =
      new BooleanSetting("GUI Open/Close", "Whoosh when the menu opens and closes", true);
  public final BooleanSetting hoverSetting =
      new BooleanSetting("Hover", "Soft ticks when hovering elements", true);
  public final BooleanSetting clicksTogglesSetting =
      new BooleanSetting("Clicks & Toggles", "Pops for toggles, sliders and keybinds", true);
  public final BooleanSetting notificationsSetting =
      new BooleanSetting("Notifications", "Chimes with toggle toasts", true);
  public final BooleanSetting sound67Setting =
      new BooleanSetting("67", "Startup track candidate", true);
  public final BooleanSetting sound67SadSongSetting =
      new BooleanSetting("67 Sad Song", "Startup track candidate", false);
  public final BooleanSetting sound67SongSetting =
      new BooleanSetting("67 Song", "Startup track candidate", false);
  public final BooleanSetting sound67TikiPhonkSetting =
      new BooleanSetting("67 Tiki Phonk", "Startup track candidate", false);
  public final List list =
      List.of(
          this.masterVolumeSetting,
          this.guiOpenCloseSetting,
          this.hoverSetting,
          this.clicksTogglesSetting,
          this.notificationsSetting,
          this.sound67Setting,
          this.sound67SadSongSetting,
          this.sound67SongSetting,
          this.sound67TikiPhonkSetting);

  public List getList() {
    return this.list;
  }

  public float computeFloat() {
    return this.masterVolumeSetting.floatValue() / 100.0F;
  }

  public JsonObject getJsonObject() {
    JsonObject jsonObject = new JsonObject();

    for (Setting setting : (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (this.list)) {
      jsonObject.add(setting.getName(), setting.toJson());
    }

    return jsonObject;
  }

  public void apply(JsonObject jsonObject) {
    for (Setting setting : (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (this.list)) {
      if (jsonObject.has(setting.getName())) {
        setting.fromJson(jsonObject.get(setting.getName()));
      }
    }
  }
}
