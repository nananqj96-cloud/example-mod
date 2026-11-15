package bunnythekenzo.client.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.function.Consumer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.ConfigSummary;

public final class ConfigFileDialogs {
  public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  public static final String string = ".67cfg";
  public static volatile boolean enabled;

  public static boolean isEnabled() {
    return enabled;
  }

  public static void importConfig(Consumer consumer) {
    if (!enabled) {
      enabled = true;
      Thread thread =
          new Thread(
              () -> {
                try {
                  String text =
                      TinyFileDialogs.tinyfd_openFileDialog(
                          "Import a bunnythekenzo config",
                          getString(),
                          null,
                          "bunnythekenzo config (*.67cfg, *.json)",
                          false);
                  if (text != null) {
                    ConfigSummary configSummary = getConfigSummary(Path.of(text));
                    if (configSummary != null) {
                      MinecraftClient.getInstance()
                          .execute(
                              () -> {
                                consumer.accept(configSummary);
                              });
                    }

                    return;
                  }
                } catch (Exception failure) {
                  ClientInitializer.logger.error("Import failed", failure);
                  return;
                } finally {
                  enabled = false;
                }
              },
              "bunnythekenzo config import");
      thread.setDaemon(true);
      thread.start();
    }
  }

  public static void exportConfig(ConfigSummary configSummary, Runnable runnable) {
    if (!enabled) {
      enabled = true;
      String text = getString() + getString(configSummary.getString()) + ".67cfg";
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("name", configSummary.getString());
      jsonObject.addProperty("summary", configSummary.getString22());
      jsonObject.add(
          "payload",
          configSummary.getJsonObject() == null ? new JsonObject() : configSummary.getJsonObject());
      Thread thread =
          new Thread(
              () -> {
                try {
                  String text2 =
                      TinyFileDialogs.tinyfd_saveFileDialog(
                          "Export " + configSummary.getString(), text, null, "bunnythekenzo config");
                  if (text2 != null) {
                    Path path = Path.of(text2);
                    Files.createDirectories(path.toAbsolutePath().getParent());

                    try (BufferedWriter bufferedWriter =
                        Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                      gson.toJson(jsonObject, bufferedWriter);
                    }

                    MinecraftClient.getInstance().execute(runnable);
                    return;
                  }
                } catch (Exception failure) {
                  ClientInitializer.logger.error("Export failed", failure);
                  return;
                } finally {
                  enabled = false;
                }
              },
              "bunnythekenzo config export");
      thread.setDaemon(true);
      thread.start();
    }
  }

  public static ConfigSummary getConfigSummary(Path path) {
    try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);
      if (jsonObject == null) {
        return null;
      }

      JsonObject jsonObject2 =
          jsonObject.has("payload") ? jsonObject.getAsJsonObject("payload") : jsonObject;
      String text = jsonObject.has("name") ? jsonObject.get("name").getAsString() : getString(path);
      String text2 =
          jsonObject.has("summary") && !jsonObject.get("summary").getAsString().isBlank()
              ? jsonObject.get("summary").getAsString()
              : "imported";
      ConfigSummary configSummary = new ConfigSummary(text, "", false);
      configSummary.apply(
          jsonObject2, text2.contains("imported") ? text2 : text2 + "  ·  imported");
      return configSummary;
    } catch (Exception failure) {
      ClientInitializer.logger.error("Could not read the config {}", path, failure);
      return null;
    }
  }

  public static String getString() {
    Path path = FabricLoader.getInstance().getConfigDir().resolve("bunnythekenzo");

    try {
      Files.createDirectories(path);
    } catch (Exception failure) {
    }

    return path.toAbsolutePath() + File.separator;
  }

  public static String getString(Path path) {
    String text = path.getFileName().toString();
    int intValue = text.lastIndexOf(46);
    return intValue > 0 ? text.substring(0, intValue) : text;
  }

  public static String getString(String text) {
    String text2 = text.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9 _-]", "").trim();
    return text2.isEmpty() ? "config" : text2.replace(' ', '-');
  }
}
