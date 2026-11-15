package bunnythekenzo.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.setting.Setting;

public class ClientConfig {
  public static final Object object = ConfigWriterSupport3.getObject();
  public final Object object2 = ConfigWriterSupport2.getObject("bunnythekenzo.json");
  public final Object object3;
  public final Object object4;
  public final Object object5 = new LinkedHashMap();
  public final Object object6 = ConfigWriter.executor();
  public long timestamp;
  public boolean enabled;

  public ClientConfig(ModuleManager object32, ThemeManager object42) {
    this.object3 = object32;
    this.object4 = object42;
  }

  public void apply(String text, Supplier supplier, Consumer consumer) {
    this.getMap().put(text, new ConfigWriterData(supplier, consumer));
  }

  public ModuleManager getModuleManager() {
    return (ModuleManager) this.object3;
  }

  public ThemeManager getThemeManager() {
    return (ThemeManager) this.object4;
  }

  public Map getMap() {
    return (Map) this.object5;
  }

  public JsonObject getJsonObject() {
    Object object22 = ConfigWriterSupport3.getObject2();
    ConfigWriterSupport3.apply(object22, "theme", this.getThemeManager().getJsonObject());
    Object object23 = ConfigWriterSupport3.getObject2();

    for (Module module :
        (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.getModuleManager().getList())) {
      Object object24 = ConfigWriterSupport3.getObject2();
      ConfigWriterSupport3.apply(object24, "enabled", module.isEnabled());
      ConfigWriterSupport3.apply(object24, "keybind", module.getKeybindSetting().toJson());
      Object object25 = ConfigWriterSupport3.getObject2();

      for (Setting setting :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module.getSettings())) {
        ConfigWriterSupport3.apply(object25, setting.getName(), setting.toJson());
      }

      ConfigWriterSupport3.apply(object24, "settings", object25);
      ConfigWriterSupport3.apply(
          object23, module.getName() + "@" + module.getModuleCategory().getString(), object24);
    }

    ConfigWriterSupport3.apply(object22, "modules", object23);

    for (Entry entry :
        (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (this.getMap().entrySet())) {
      ConfigWriterSupport3.apply(
          object22,
          (String) entry.getKey(),
          ((ConfigWriterData) entry.getValue()).supplier().get());
    }

    return ConfigWriterSupport3.getJsonObject(object22);
  }

  public void apply(JsonObject jsonObject) {
    if (jsonObject != null) {
      if (ConfigWriterSupport3.test2(jsonObject, "theme")) {
        this.getThemeManager()
            .apply(
                ConfigWriterSupport3.getJsonObject(
                    ConfigWriterSupport3.getObject(jsonObject, "theme")));
      }

      if (ConfigWriterSupport3.test2(jsonObject, "modules")) {
        Object object7 = ConfigWriterSupport3.getObject(jsonObject, "modules");

        for (Module module :
            (java.lang.Iterable<Module>)
                (java.lang.Iterable<?>) (this.getModuleManager().getList())) {
          Object object8 =
              ConfigWriterSupport3.getObject(
                  object7, module.getName() + "@" + module.getModuleCategory().getString());
          if (object8 == null && "Custom Glint".equals(module.getName())) {
            object8 = ConfigWriterSupport3.getObject(object7, "CustomGlint@MISC");
          }

          if (object8 == null && "Animations".equals(module.getName())) {
            object8 = ConfigWriterSupport3.getObject(object7, "SwingSpeed@CLIENT");
          }

          if (object8 == null && "Cosmetics".equals(module.getName())) {
            object8 = ConfigWriterSupport3.getObject(object7, "CustomAccessories@VISUALS");
          }

          if (object8 != null) {
            if (ConfigWriterSupport3.test(object8, "enabled")
                && ConfigWriterSupport3.test3(object8, "enabled") != module.isEnabled()) {
              module.setEnabled(ConfigWriterSupport3.test3(object8, "enabled"));
            }

            if (ConfigWriterSupport3.test(object8, "keybind")) {
              module
                  .getKeybindSetting()
                  .fromJson((JsonElement) ConfigWriterSupport3.getObject2(object8, "keybind"));
            }

            Object object9 = ConfigWriterSupport3.getObject(object8, "settings");
            if (object9 != null) {
              if (module instanceof FakeLootDropModule fakeLootDropModule
                  && !ConfigWriterSupport3.test(
                      object9, fakeLootDropModule.itemReplacementsSetting.getName())
                  && ConfigWriterSupport3.test(object9, "Replace Item")
                  && ConfigWriterSupport3.test(object9, "With Item")) {
                Object object32 = ConfigWriterSupport3.getObject3();
                Object object22 = ConfigWriterSupport3.getObject2();
                ConfigWriterSupport3.apply(
                    object22, "source", ConfigWriterSupport3.getObject2(object9, "Replace Item"));
                ConfigWriterSupport3.apply(
                    object22, "target", ConfigWriterSupport3.getObject2(object9, "With Item"));
                ConfigWriterSupport3.apply(object32, object22);
                fakeLootDropModule.itemReplacementsSetting.fromJson((JsonElement) object32);
              }

              for (Setting setting :
                  (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module.getSettings())) {
                if (ConfigWriterSupport3.test(object9, setting.getName())) {
                  setting.fromJson(
                      (JsonElement) ConfigWriterSupport3.getObject2(object9, setting.getName()));
                } else if ("Cosmetics".equals(module.getName())
                    && "Primary Color".equals(setting.getName())
                    && ConfigWriterSupport3.test(object9, "Color")) {
                  setting.fromJson((JsonElement) ConfigWriterSupport3.getObject2(object9, "Color"));
                } else if ("Animations".equals(module.getName())
                    && "Swing Speed".equals(setting.getName())
                    && ConfigWriterSupport3.test(object9, "Speed")) {
                  setting.fromJson((JsonElement) ConfigWriterSupport3.getObject2(object9, "Speed"));
                }
              }
            }
          }
        }
      }

      for (Entry entry :
          (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (this.getMap().entrySet())) {
        if (ConfigWriterSupport3.test2(jsonObject, (String) entry.getKey())) {
          ((ConfigWriterData) entry.getValue())
              .consumer()
              .accept(
                  ConfigWriterSupport3.getJsonObject(
                      ConfigWriterSupport3.getObject(jsonObject, (String) entry.getKey())));
        }
      }
    }
  }

  public void update() {
    this.enabled = true;
    this.timestamp = System.nanoTime() + 500000000L;
  }

  public void update2() {
    if (this.enabled && System.nanoTime() - this.timestamp >= 0L) {
      this.enqueueSave();
    }
  }

  public Object enqueueSave() {
    this.enabled = false;
    JsonObject jsonObject = this.getJsonObject();
    return ConfigWriter.getObject(
        () -> {
          this.apply2(jsonObject);
        },
        this.object6);
  }

  public void update3() {
    ConfigWriter.apply(this.enqueueSave());
  }

  public void apply2(JsonObject jsonObject) {
    try {
      ConfigWriterSupport.apply(this.object2, ConfigWriterSupport3.getString(object, jsonObject));
    } catch (Exception failure) {
      ClientInitializer.logger.error("Failed to save config", failure);
    }
  }

  public synchronized void update4() {
    if (ConfigWriterSupport.test(this.object2)) {
      JsonObject jsonObject;
      try {
        jsonObject =
            ConfigWriterSupport3.getJsonObject(
                ConfigWriterSupport3.getObject(ConfigWriterSupport.getString(this.object2)));
      } catch (Exception failure) {
        ClientInitializer.logger.error("Failed to read config, using defaults", failure);
        return;
      }

      this.apply(jsonObject);
    }
  }
}
