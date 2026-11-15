package bunnythekenzo.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigRepository {

  public static final int count2 = 1;

  public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  public static volatile boolean enabled;
  public final ClientConfig clientConfig;
  public final Path path;
  public final ConfigRepositorySupport[] configRepositorySupportArray;
  public int count3;
  public static int[] countArray;

  public ConfigRepository(ClientConfig clientConfig2) {
    getCountArray();
    this.configRepositorySupportArray = new ConfigRepositorySupport[5];
    this.count3 = -1;
    this.clientConfig = clientConfig2;
    this.path = FabricLoader.getInstance().getConfigDir().resolve("bunnythekenzo-configs");

    for (int index = 0; index < 5; index++) {
      this.configRepositorySupportArray[index] = new ConfigRepositorySupport(index);
    }

    ClientInitializer.ensureInitialized();
  }

  public static String getString(int intValue) {
    return "Config " + (intValue + 1);
  }

  public ConfigRepositorySupport getConfigRepositorySupport(int index) {
    return this.configRepositorySupportArray[index];
  }

  public ConfigRepositorySupport[] getConfigRepositorySupportArray() {
    return this.configRepositorySupportArray;
  }

  public int getCount3() {
    return this.count3;
  }

  public Path getPath() {
    return this.path;
  }

  public void update() {
    getCountArray();

    for (int index = 0; index < 5; index++) {
      this.apply(index);
    }

    JsonObject jsonObject = this.getJsonObject(this.path.resolve("index.json"));
    if (jsonObject != null && jsonObject.has("active")) {
      int asInt = jsonObject.get("active").getAsInt();
      if (asInt >= 0 && asInt < 5 && this.configRepositorySupportArray[asInt].enabled) {
        this.count3 = asInt;
      }
    }
  }

  public void apply(int index) {
    getCountArray();
    ConfigRepositorySupport configRepositorySupport = this.configRepositorySupportArray[index];
    JsonObject jsonObject = this.getJsonObject(this.getPath(index));
    JsonObject jsonObject2 = this.getJsonObject(jsonObject);
    if (jsonObject2 == null) {
      configRepositorySupport.enabled = false;
      configRepositorySupport.timestamp = 0L;
      configRepositorySupport.string = getString(index);
    } else {
      configRepositorySupport.enabled = true;
      configRepositorySupport.string =
          jsonObject.has("name") && !jsonObject.get("name").getAsString().isBlank()
              ? jsonObject.get("name").getAsString()
              : getString(index);
      configRepositorySupport.timestamp =
          jsonObject.has("savedAt") ? jsonObject.get("savedAt").getAsLong() : 0L;
    }
  }

  public boolean test(int index) {
    getCountArray();
    ConfigRepositorySupport configRepositorySupport = this.configRepositorySupportArray[index];
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("format", "bunnythekenzo-config");
    jsonObject.addProperty("version", 1);
    jsonObject.addProperty("name", configRepositorySupport.string);
    jsonObject.addProperty("savedAt", System.currentTimeMillis());
    jsonObject.addProperty("client", "2.0.0");
    jsonObject.add("state", this.clientConfig.getJsonObject());
    if (!this.test(this.getPath(index), jsonObject)) {
      return false;
    }

    this.apply(index);
    return true;
  }

  public boolean test2(int count32) {
    getCountArray();
    JsonObject jsonObject = this.getJsonObject(this.getJsonObject(this.getPath(count32)));
    if (jsonObject == null) {
      return false;
    }

    enabled = true;

    try {
      this.clientConfig.apply(jsonObject);
    } finally {
      enabled = false;
    }

    this.count3 = count32;
    this.update2();
    return true;
  }

  public boolean test3(int intValue) {
    getCountArray();

    try {
      Files.deleteIfExists(this.getPath(intValue));
    } catch (IOException failure) {
      ClientInitializer.logger.error(
          "Failed to delete config file {}", this.getPath(intValue).getFileName(), failure);
      return false;
    }

    if (this.count3 == intValue) {
      this.count3 = -1;
      this.update2();
    }

    this.apply(intValue);
    return true;
  }

  public boolean test(int index, String text) {
    getCountArray();
    ConfigRepositorySupport configRepositorySupport = this.configRepositorySupportArray[index];
    String string = this.getString(text);
    if (string.isEmpty()) {
      string = getString(index);
    }

    configRepositorySupport.string = string;
    if (!configRepositorySupport.enabled) {
      return true;
    }

    JsonObject jsonObject = this.getJsonObject(this.getPath(index));
    if (jsonObject == null) {
      return false;
    }

    jsonObject.addProperty("name", string);
    return this.test(this.getPath(index), jsonObject);
  }

  public String getString2(int index) {
    JsonObject jsonObject = this.getJsonObject(this.getPath(index));
    if (this.getJsonObject(jsonObject) == null) {
      return null;
    }

    String text = gson.toJson(jsonObject);
    String string2 = this.getString2(this.configRepositorySupportArray[index].string);
    this.test(this.path.resolve(string2 + ".json"), jsonObject);
    return text;
  }

  public ConfigRepositoryData getConfigRepositoryData(int index, String text) {
    getCountArray();
    if (text != null && !text.isBlank()) {
      JsonObject jsonObject;
      try {
        jsonObject = JsonParser.parseString(text).getAsJsonObject();
      } catch (Exception failure) {
        return new ConfigRepositoryData(false, "Not valid config JSON");
      }

      if (jsonObject.has("version")
          && jsonObject.get("version").isJsonPrimitive()
          && jsonObject.get("version").getAsInt() > 1) {
        return new ConfigRepositoryData(false, "Config is from a newer client");
      }

      JsonObject jsonObject2 = this.getJsonObject(jsonObject);
      if (jsonObject2 == null) {
        return new ConfigRepositoryData(false, "No config data found");
      }

      ConfigRepositorySupport configRepositorySupport = this.configRepositorySupportArray[index];
      String text2 =
          jsonObject.has("name") && !jsonObject.get("name").getAsString().isBlank()
              ? this.getString(jsonObject.get("name").getAsString())
              : configRepositorySupport.string;
      JsonObject jsonObject3 = new JsonObject();
      jsonObject3.addProperty("format", "bunnythekenzo-config");
      jsonObject3.addProperty("version", 1);
      jsonObject3.addProperty("name", text2);
      jsonObject3.addProperty(
          "savedAt",
          jsonObject.has("savedAt")
              ? jsonObject.get("savedAt").getAsLong()
              : System.currentTimeMillis());
      if (jsonObject.has("client")) {
        jsonObject3.addProperty("client", jsonObject.get("client").getAsString());
      }

      jsonObject3.add("state", jsonObject2);
      if (!this.test(this.getPath(index), jsonObject3)) {
        return new ConfigRepositoryData(false, "Couldn't write the slot file");
      }

      this.apply(index);
      return new ConfigRepositoryData(true, "Imported \"" + configRepositorySupport.string + "\"");
    } else {
      return new ConfigRepositoryData(false, "Clipboard is empty");
    }
  }

  public JsonObject getJsonObject(JsonObject jsonObject) {
    getCountArray();
    if (jsonObject == null) {
      return null;
    } else if (jsonObject.has("state") && jsonObject.get("state").isJsonObject()) {
      return jsonObject.getAsJsonObject("state");
    } else {
      return jsonObject.has("modules") && jsonObject.get("modules").isJsonObject()
          ? jsonObject
          : null;
    }
  }

  public Path getPath(int intValue) {
    return this.path.resolve("slot" + (intValue + 1) + ".json");
  }

  public void update2() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("active", this.count3);
    this.test(this.path.resolve("index.json"), jsonObject);
  }

  public JsonObject getJsonObject(Path path2) {
    getCountArray();
    if (!Files.exists(path2)) {
      return null;
    }

    try {
      return JsonParser.parseString(Files.readString(path2)).getAsJsonObject();
    } catch (Exception failure) {
      ClientInitializer.logger.warn(
          "Ignoring unreadable config file {}", path2.getFileName(), failure);
      return null;
    }
  }

  public boolean test(Path path2, JsonObject jsonObject) {
    try {
      Files.createDirectories(this.path);
      Files.writeString(path2, gson.toJson(jsonObject));
      return true;
    } catch (IOException failure) {
      ClientInitializer.logger.error(
          "Failed to write config file {}", path2.getFileName(), failure);
      return false;
    }
  }

  public String getString(String text) {
    getCountArray();
    if (text == null) {
      return "";
    }

    String text2 = text.replaceAll("[\\r\\n\\t]", " ").trim();
    return text2.length() > 24 ? text2.substring(0, 24) : text2;
  }

  public String getString2(String text) {
    getCountArray();
    String text2 = text.replaceAll("[^a-zA-Z0-9-_ ]", "").trim().replace(' ', '_');
    return text2.isEmpty() ? "config" : text2;
  }

  static {
    int[] intValues = new int[4];

    setCountArray(intValues);
  }

  public static void setCountArray(int[] countArray2) {
    countArray = countArray2;
  }

  public static int[] getCountArray() {
    return countArray;
  }
}
