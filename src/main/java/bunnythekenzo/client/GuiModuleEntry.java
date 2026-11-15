package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import bunnythekenzo.client.module.AuroraModule;
import bunnythekenzo.client.module.CosmeticsModule;
import bunnythekenzo.client.module.CrystalPulseModule;
import bunnythekenzo.client.module.CustomBlockOutlineModule;
import bunnythekenzo.client.module.CustomGlintModule;
import bunnythekenzo.client.module.LumaModule;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.setting.BlockListSettingView;
import bunnythekenzo.client.setting.ColorSettingView;
import bunnythekenzo.client.setting.EntityTypeSettingView;
import bunnythekenzo.client.setting.ItemSettingView;
import bunnythekenzo.client.setting.KeybindSettingView;
import bunnythekenzo.client.setting.Setting;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.SettingViewFactory;

public final class GuiModuleEntry {
  public final String string;
  public final String string2;
  public final List list;
  public final Module module;
  public boolean enabled;
  public int count = -1;

  public GuiModuleEntry(String string3, String string22, CollectionData[] collectionDataArray) {
    this.string = string3;
    this.string2 = string22;
    this.list = List.of(collectionDataArray);
    this.module = null;
  }

  public GuiModuleEntry(Module module2, List list2) {
    this.string = module2.getName();
    this.string2 = module2.getDescription();
    this.list = List.copyOf(list2);
    this.module = module2;
  }

  public static GuiModuleEntry of(Module module2) {
    if (module2 instanceof CustomBlockOutlineModule customBlockOutlineModule) {
      LinkedHashMap linkedHashMap = new LinkedHashMap();

      for (String text : List.of("Sculpture", "Colors", "Motion")) {
        linkedHashMap.put(text, new ArrayList());
      }

      for (Setting setting :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module2.getSettings())) {
        SettingEntry settingEntry = SettingViewFactory.getSettingEntry(setting);
        if (settingEntry != null) {
          ((List) linkedHashMap.get(customBlockOutlineModule.getString(setting))).add(settingEntry);
        }
      }

      return new GuiModuleEntry(
          module2,
          linkedHashMap.entrySet().stream()
              .map(
                  value -> {
                    return new CollectionData(
                        (String) ((java.util.Map.Entry) (value)).getKey(),
                        List.copyOf((Collection) ((java.util.Map.Entry) (value)).getValue()));
                  })
              .toList());
    } else if (module2 instanceof LumaModule lumaModule) {
      LinkedHashMap linkedHashMap2 = new LinkedHashMap();

      for (String text2 : List.of("Swarm", "Light", "Motion")) {
        linkedHashMap2.put(text2, new ArrayList());
      }

      for (Setting setting2 :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module2.getSettings())) {
        SettingEntry settingEntry2 = SettingViewFactory.getSettingEntry(setting2);
        if (settingEntry2 != null) {
          ((List) linkedHashMap2.get(lumaModule.getString(setting2))).add(settingEntry2);
        }
      }

      return new GuiModuleEntry(
          module2,
          linkedHashMap2.entrySet().stream()
              .map(
                  value2 -> {
                    return new CollectionData(
                        (String) ((java.util.Map.Entry) (value2)).getKey(),
                        List.copyOf((Collection) ((java.util.Map.Entry) (value2)).getValue()));
                  })
              .toList());
    } else if (module2 instanceof AuroraModule auroraModule) {
      LinkedHashMap linkedHashMap3 = new LinkedHashMap();

      for (String text3 : List.of("Appearance", "Flow", "Details")) {
        linkedHashMap3.put(text3, new ArrayList());
      }

      for (Setting setting3 :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module2.getSettings())) {
        SettingEntry settingEntry3 = SettingViewFactory.getSettingEntry(setting3);
        if (settingEntry3 != null) {
          ((List) linkedHashMap3.get(auroraModule.getString(setting3))).add(settingEntry3);
        }
      }

      return new GuiModuleEntry(
          module2,
          linkedHashMap3.entrySet().stream()
              .map(
                  value3 -> {
                    return new CollectionData(
                        (String) ((java.util.Map.Entry) (value3)).getKey(),
                        List.copyOf((Collection) ((java.util.Map.Entry) (value3)).getValue()));
                  })
              .toList());
    } else if (module2 instanceof CrystalPulseModule crystalPulseModule) {
      LinkedHashMap linkedHashMap4 = new LinkedHashMap();

      for (String text4 : List.of("Appearance", "Impact", "Visibility")) {
        linkedHashMap4.put(text4, new ArrayList());
      }

      for (Setting setting4 :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module2.getSettings())) {
        SettingEntry settingEntry4 = SettingViewFactory.getSettingEntry(setting4);
        if (settingEntry4 != null) {
          ((List) linkedHashMap4.get(crystalPulseModule.getString(setting4))).add(settingEntry4);
        }
      }

      return new GuiModuleEntry(
          module2,
          linkedHashMap4.entrySet().stream()
              .map(
                  value4 -> {
                    return new CollectionData(
                        (String) ((java.util.Map.Entry) (value4)).getKey(),
                        List.copyOf((Collection) ((java.util.Map.Entry) (value4)).getValue()));
                  })
              .toList());
    } else if (module2 instanceof CustomGlintModule customGlintModule) {
      LinkedHashMap linkedHashMap5 = new LinkedHashMap();

      for (String text5 : List.of("Appearance", "Effect", "Items")) {
        linkedHashMap5.put(text5, new ArrayList());
      }

      for (Setting setting5 :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module2.getSettings())) {
        SettingEntry settingEntry5 = SettingViewFactory.getSettingEntry(setting5);
        if (settingEntry5 != null) {
          ((List) linkedHashMap5.get(customGlintModule.settingGroup(setting5))).add(settingEntry5);
        }
      }

      return new GuiModuleEntry(
          module2,
          linkedHashMap5.entrySet().stream()
              .map(
                  value5 -> {
                    return new CollectionData(
                        (String) ((java.util.Map.Entry) (value5)).getKey(),
                        List.copyOf((Collection) ((java.util.Map.Entry) (value5)).getValue()));
                  })
              .toList());
    } else if (module2 instanceof CosmeticsModule cosmeticsModule) {
      LinkedHashMap linkedHashMap6 = new LinkedHashMap();

      for (Setting setting6 :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module2.getSettings())) {
        SettingEntry settingEntry6 = SettingViewFactory.getSettingEntry(setting6);
        if (settingEntry6 != null) {
          ((java.util.List)
                  (linkedHashMap6.computeIfAbsent(
                      cosmeticsModule.getString(setting6),
                      value6 -> {
                        return new ArrayList<>();
                      })))
              .add(settingEntry6);
        }
      }

      return new GuiModuleEntry(
          module2,
          linkedHashMap6.entrySet().stream()
              .map(
                  value7 -> {
                    return new CollectionData(
                        (String) ((java.util.Map.Entry) (value7)).getKey(),
                        List.copyOf((Collection) ((java.util.Map.Entry) (value7)).getValue()));
                  })
              .toList());
    } else {
      LinkedHashMap linkedHashMap7 = new LinkedHashMap();
      linkedHashMap7.put("General", new ArrayList());
      linkedHashMap7.put("Visuals", new ArrayList());
      linkedHashMap7.put("Advanced", new ArrayList());

      for (Setting setting7 :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module2.getSettings())) {
        SettingEntry settingEntry7 = SettingViewFactory.getSettingEntry(setting7);
        if (settingEntry7 != null) {
          ((List) linkedHashMap7.get(getString(setting7, settingEntry7))).add(settingEntry7);
        }
      }

      ArrayList arrayList = new ArrayList();

      for (Entry entry :
          (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (linkedHashMap7.entrySet())) {
        arrayList.add(
            new CollectionData(
                (String) entry.getKey(), List.copyOf((Collection) entry.getValue())));
      }

      return new GuiModuleEntry(module2, arrayList);
    }
  }

  public static String getString(Setting setting, SettingEntry settingEntry) {
    if (settingEntry instanceof KeybindSettingView) {
      return "Advanced";
    }

    if (settingEntry instanceof BlockListSettingView
        || settingEntry instanceof EntityTypeSettingView
        || settingEntry instanceof ItemSettingView) {
      return "General";
    }

    if (settingEntry instanceof ColorSettingView) {
      return "Visuals";
    }

    String text = setting.getName().toLowerCase(Locale.ROOT);
    boolean flag =
        text.contains("color")
            || text.contains("colour")
            || text.contains("opacity")
            || text.contains("alpha")
            || text.contains("render")
            || text.contains("outline")
            || text.contains("tracer")
            || text.contains("glow") && !text.contains("glowstone")
            || text.contains("fill")
            || text.contains("width")
            || text.contains("scale")
            || text.contains("fade")
            || text.contains("show ")
            || text.startsWith("show");
    return flag ? "Visuals" : "General";
  }

  public String getString() {
    return this.string;
  }

  public String getString2() {
    return this.string2;
  }

  public List<CollectionData> getList() {
    return this.list;
  }

  public Module getModule() {
    return this.module;
  }

  public boolean test() {
    return this.module != null ? this.module.isEnabled() : this.enabled;
  }

  public void apply(boolean enabled2) {
    if (this.module != null) {
      this.module.setEnabled(enabled2);
    } else {
      this.enabled = enabled2;
    }
  }

  public boolean test2() {
    this.apply(!this.test());
    return this.test();
  }

  public int computeInt() {
    return this.module != null ? (Integer) this.module.getKeybindSetting().getValue() : this.count;
  }

  public void apply(int count2) {
    if (this.module != null) {
      this.module.getKeybindSetting().setValue(count2);
    } else {
      this.count = count2;
    }
  }

  public boolean test3() {
    return this.computeInt() >= 0;
  }
}
