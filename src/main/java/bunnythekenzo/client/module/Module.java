package bunnythekenzo.client.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import bunnythekenzo.client.setting.KeybindSetting;
import bunnythekenzo.client.setting.Setting;

public abstract class Module {
  public final String name;
  public final String description;
  public final ModuleCategory moduleCategory;
  public final KeybindSetting keybindSetting;
  public final List settings = new ArrayList();
  public boolean enabled;
  public BiConsumer stateChangeListener;

  public Module(String string3, String string22, ModuleCategory moduleCategory2) {
    this.name = string3;
    this.description = string22;
    this.moduleCategory = moduleCategory2;
    this.keybindSetting = new KeybindSetting("Keybind", "Toggles " + string3, -1);
  }

  public Setting registerSetting(Setting setting) {
    this.settings.add(setting);
    return setting;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public ModuleCategory getModuleCategory() {
    return this.moduleCategory;
  }

  public KeybindSetting getKeybindSetting() {
    return this.keybindSetting;
  }

  public List getSettings() {
    return Collections.unmodifiableList(this.settings);
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled2) {
    if (this.enabled != enabled2) {
      this.enabled = enabled2;
      if (enabled2) {
        this.onEnable();
      } else {
        this.onDisable();
      }

      if (this.stateChangeListener != null) {
        this.stateChangeListener.accept(this, enabled2);
      }
    }
  }

  public void setStateChangeListener(BiConsumer biConsumer2) {
    this.stateChangeListener = biConsumer2;
  }

  public void toggle() {
    this.setEnabled(!this.enabled);
  }

  public void toggleFromKeybind() {
    this.toggle();
  }

  public boolean test2() {
    return false;
  }

  public void onEnable() {}

  public void onDisable() {}

  public void onTick() {}

  public boolean usesCombatTick() {
    return this.moduleCategory == ModuleCategory.COMBAT;
  }

  public boolean onKeyPress(int intValue) {
    return false;
  }
}
