package bunnythekenzo.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import bunnythekenzo.client.hud.HudElement;
import bunnythekenzo.client.hud.HudWidget;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.setting.SettingCodec;

public final class JsonSupport2 {
  public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  public static List list;
  public static List list2;
  public static List list3;

  public static List<CollectionData3> getList() {
    if (list == null) {
      list = getList2();
      update2();
    }

    return list;
  }

  public static void setList(List list4) {
    list = List.copyOf(list4);
  }

  public static int computeInt() {
    int index = 0;

    for (CollectionData3 collectionData3 :
        (java.lang.Iterable<CollectionData3>) (java.lang.Iterable<?>) (getList())) {
      for (GuiModuleEntry guiModuleEntry :
          (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (collectionData3.list())) {
        if (guiModuleEntry.test()) {
          index++;
        }
      }
    }

    return index;
  }

  public static List getList2() {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    ArrayList arrayList = new ArrayList();
    if (moduleManager != null) {
      for (ModuleCategory moduleCategory : ModuleCategory.values()) {
        ArrayList arrayList2 = new ArrayList();

        for (Module module :
            (java.lang.Iterable<Module>)
                (java.lang.Iterable<?>) (moduleManager.getList(moduleCategory))) {
          arrayList2.add(GuiModuleEntry.of(module));
        }

        arrayList2.addAll(getList(moduleCategory));
        if (!arrayList2.isEmpty()) {
          arrayList.add(
              new CollectionData3(
                  moduleCategory.getDisplayName(),
                  moduleCategory.getIconId(),
                  List.copyOf(arrayList2)));
        }
      }
    }

    arrayList.add(
        CollectionData3.getCollectionData3(
            "Anti-Cheat Off", "anti_cheat", MovementOverrides.guiModules()));
    return List.copyOf(arrayList);
  }

  public static List getList(ModuleCategory moduleCategory) {
    return switch (JsonSupport.countArray[moduleCategory.ordinal()]) {
      case 1 ->
          List.of(
              PanicOrderAutomation.getGuiModuleEntry(), PearlEscapeAutomation.getGuiModuleEntry());
      default -> List.of();
    };
  }

  public static JsonObject getJsonObject() {
    JsonObject jsonObject = new JsonObject();

    for (CollectionData3 collectionData3 :
        (java.lang.Iterable<CollectionData3>) (java.lang.Iterable<?>) (getList())) {
      for (GuiModuleEntry guiModuleEntry :
          (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (collectionData3.list())) {
        if (guiModuleEntry.getModule() == null) {
          JsonObject jsonObject2 = new JsonObject();
          jsonObject2.addProperty("enabled", guiModuleEntry.test());
          jsonObject2.addProperty("key", guiModuleEntry.computeInt());
          JsonObject jsonObject3 = new JsonObject();

          for (CollectionData collectionData :
              (java.lang.Iterable<CollectionData>)
                  (java.lang.Iterable<?>) (guiModuleEntry.getList())) {
            jsonObject3.add(
                collectionData.string(), SettingCodec.getJsonObject(collectionData.list()));
          }

          jsonObject2.add("settings", jsonObject3);
          jsonObject.add(guiModuleEntry.getString(), jsonObject2);
        }
      }
    }

    JsonObject jsonObject4 = new JsonObject();
    jsonObject4.add("modules", jsonObject);
    return jsonObject4;
  }

  public static void apply(JsonObject jsonObject) {
    if (jsonObject != null && jsonObject.has("modules")) {
      JsonObject asJsonObject = jsonObject.getAsJsonObject("modules");

      for (CollectionData3 collectionData3 :
          (java.lang.Iterable<CollectionData3>) (java.lang.Iterable<?>) (getList())) {
        for (GuiModuleEntry guiModuleEntry :
            (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (collectionData3.list())) {
          if (guiModuleEntry.getModule() == null && asJsonObject.has(guiModuleEntry.getString())) {
            JsonObject asJsonObject2 = asJsonObject.getAsJsonObject(guiModuleEntry.getString());
            if (asJsonObject2.has("enabled")) {
              boolean asBoolean = asJsonObject2.get("enabled").getAsBoolean();
              guiModuleEntry.apply(asBoolean && !collectionData3.enabled());
            }

            if (asJsonObject2.has("key")) {
              int asInt = asJsonObject2.get("key").getAsInt();
              guiModuleEntry.apply(collectionData3.enabled() && asInt == 83 ? -1 : asInt);
            }

            if (asJsonObject2.has("settings")) {
              JsonObject asJsonObject3 = asJsonObject2.getAsJsonObject("settings");

              for (CollectionData collectionData :
                  (java.lang.Iterable<CollectionData>)
                      (java.lang.Iterable<?>) (guiModuleEntry.getList())) {
                SettingCodec.apply(
                    asJsonObject3.getAsJsonObject(collectionData.string()), collectionData.list());
              }
            }
          }
        }
      }
    }
  }

  public static List getList3() {
    if (list2 == null) {
      list2 = HudFactory.create();
    }

    return list2;
  }

  public static void update() {
    getList3()
        .forEach(
            (referenceArgument7_0) ->
                ((bunnythekenzo.client.hud.HudWidget) referenceArgument7_0).update3());
  }

  public static List getList4() {
    if (list3 == null) {
      list3 = new ArrayList();
      update5();
    }

    return list3;
  }

  public static ConfigSummary getConfigSummary() {
    List list4 = getList4();
    int index = list4.size() + 1;

    while (nameTaken("Config " + index)) {
      index++;
    }

    ConfigSummary configSummary = new ConfigSummary("Config " + index, "", false);
    list4.add(configSummary);
    return configSummary;
  }

  public static ConfigSummary getConfigSummary(ConfigSummary configSummary) {
    String string = configSummary.getString();
    if (nameTaken(string)) {
      int index = 2;

      while (nameTaken(string + " (" + index + ")")) {
        index++;
      }

      configSummary.setString(string + " (" + index + ")");
    }

    getList4().add(configSummary);
    return configSummary;
  }

  public static boolean nameTaken(String text) {
    return getList4().stream()
        .anyMatch(
            value -> {
              return ((bunnythekenzo.client.ConfigSummary) (value)).getString().equals(text);
            });
  }

  public static void apply(ConfigSummary configSummary) {
    getList4().remove(configSummary);
  }

  public static Path getPath() {
    return FabricLoader.getInstance().getConfigDir().resolve("bunnythekenzo");
  }

  public static void update2() {
    Path path = getPath().resolve("modules.json");
    if (Files.exists(path)) {
      try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
        apply(gson.fromJson(bufferedReader, JsonObject.class));
      } catch (Exception failure) {
        ClientInitializer.logger.warn(
            "Could not read {}; using default module settings", path, failure);
      }
    }
  }

  public static void update3() {
    Path path = getPath().resolve("modules.json");

    try {
      Files.createDirectories(path.getParent());

      try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        gson.toJson(getJsonObject(), bufferedWriter);
      }
    } catch (Exception failure) {
      ClientInitializer.logger.error("Could not write {}", path, failure);
    }
  }

  public static void update4() {
    if (ClientInitializer.getClientConfig() != null) {
      ClientInitializer.getClientConfig().update();
    }
  }

  public static JsonObject getJsonObject2() {
    JsonObject jsonObject = new JsonObject();

    for (HudWidget hudWidget :
        (java.lang.Iterable<HudWidget>) (java.lang.Iterable<?>) (getList3())) {
      JsonObject jsonObject2 = new JsonObject();
      jsonObject2.addProperty("x", hudWidget.getAmount5());
      jsonObject2.addProperty("y", hudWidget.getAmount6());
      jsonObject2.addProperty("scale", hudWidget.getAmount7());
      if (!(hudWidget instanceof HudElement)) {
        jsonObject2.addProperty("enabled", hudWidget.isEnabled2());
      }

      if (!hudWidget.getList2().isEmpty()) {
        jsonObject2.add("settings", SettingCodec.getJsonObject(hudWidget.getList2()));
      }

      jsonObject.add(hudWidget.getString2(), jsonObject2);
    }

    return jsonObject;
  }

  public static void apply2(JsonObject jsonObject) {
    if (jsonObject != null) {
      for (HudWidget hudWidget :
          (java.lang.Iterable<HudWidget>) (java.lang.Iterable<?>) (getList3())) {
        JsonObject asJsonObject = jsonObject.getAsJsonObject(hudWidget.getString2());
        if (asJsonObject != null) {
          if (asJsonObject.has("enabled") && !(hudWidget instanceof HudElement)) {
            hudWidget.setEnabled2(asJsonObject.get("enabled").getAsBoolean());
          }

          if (asJsonObject.has("x") && asJsonObject.has("y")) {
            hudWidget.apply2(
                asJsonObject.get("x").getAsFloat(), asJsonObject.get("y").getAsFloat());
          }

          if (asJsonObject.has("scale")) {
            hudWidget.apply(asJsonObject.get("scale").getAsFloat());
          }

          SettingCodec.apply(asJsonObject.getAsJsonObject("settings"), hudWidget.getList2());
        }
      }
    }
  }

  public static void update5() {
    Path path = getPath().resolve("configs.json");
    if (Files.exists(path)) {
      try {
        label78:
        {
          try (BufferedReader bufferedReader =
              Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonArray jsonArray = gson.fromJson(bufferedReader, JsonArray.class);
            if (jsonArray != null) {
              for (JsonElement jsonElement : jsonArray) {
                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                list3.add(
                    new ConfigSummary(
                        asJsonObject.get("name").getAsString(),
                        asJsonObject.has("summary")
                            ? asJsonObject.get("summary").getAsString()
                            : "",
                        asJsonObject.get("filled").getAsBoolean(),
                        asJsonObject.has("payload")
                            ? asJsonObject.getAsJsonObject("payload")
                            : null));
              }
              break label78;
            }
          }

          return;
        }
      } catch (Exception failure) {
        ClientInitializer.logger.warn("Could not read {}; starting with no configs", path, failure);
      }
    }
  }

  public static void update6() {
    Path path = getPath().resolve("configs.json");

    try {
      Files.createDirectories(path.getParent());
      JsonArray jsonArray = new JsonArray();

      for (ConfigSummary configSummary :
          (java.lang.Iterable<ConfigSummary>) (java.lang.Iterable<?>) (getList4())) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", configSummary.getString());
        jsonObject.addProperty("summary", configSummary.getString22());
        jsonObject.addProperty("filled", configSummary.isEnabled());
        if (configSummary.getJsonObject() != null) {
          jsonObject.add("payload", configSummary.getJsonObject());
        }

        jsonArray.add(jsonObject);
      }

      try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        gson.toJson(jsonArray, bufferedWriter);
      }
    } catch (Exception failure) {
      ClientInitializer.logger.error("Could not write {}", path, failure);
    }
  }
}
