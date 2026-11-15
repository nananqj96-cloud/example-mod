package bunnythekenzo.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class ConfigWriterSupport3 {
  public static Object getObject() {
    return new GsonBuilder().setPrettyPrinting().create();
  }

  public static Object getObject2() {
    return new JsonObject();
  }

  public static Object getObject3() {
    return new JsonArray();
  }

  public static void apply(Object value, String text, Object value2) {
    ((JsonObject) value).add(text, (JsonElement) value2);
  }

  public static void apply(Object value, String text, boolean flag) {
    ((JsonObject) value).addProperty(text, flag);
  }

  public static boolean test(Object value, String text) {
    return ((JsonObject) value).has(text);
  }

  public static boolean test2(Object value, String text) {
    ConfigRepository.getCountArray();
    JsonElement jsonElement = ((JsonObject) value).get(text);
    return jsonElement != null && jsonElement.isJsonObject();
  }

  public static Object getObject(Object value, String text) {
    return ((JsonObject) value).getAsJsonObject(text);
  }

  public static Object getObject2(Object value, String text) {
    return ((JsonObject) value).get(text);
  }

  public static boolean test3(Object value, String text) {
    return ((JsonObject) value).get(text).getAsBoolean();
  }

  public static void apply(Object value, Object value2) {
    ((JsonArray) value).add((JsonElement) value2);
  }

  public static Object getObject(String text) {
    return JsonParser.parseString(text).getAsJsonObject();
  }

  public static String getString(Object value, Object value2) {
    return ((Gson) value).toJson((JsonElement) value2);
  }

  public static JsonObject getJsonObject(Object value) {
    return (JsonObject) value;
  }
}
