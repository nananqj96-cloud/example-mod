package bunnythekenzo.client.module;

import com.google.common.collect.LinkedHashMultimap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.SkinTextures;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.StringSetting;

public class SkinProtectModule extends Module {
  public static final HttpClient httpClient =
      HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8L)).build();
  public final StringSetting skinIgnSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Skin IGN",
                  "Username whose skin is applied",
                  "67skibidi",
                  16,
                  "Type a username…"));
  public final ChoiceSetting applyToSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Apply To",
                  "Whose skin gets replaced",
                  "Everyone",
                  new String[] {"Everyone", "Others", "Self"}));
  public volatile SkinTextures skinTextures;
  public volatile String string = "";
  public volatile boolean enabled;
  public static ClientInitializer[] clientInitializerArray;

  public SkinProtectModule() {
    super("SkinProtect", "Replaces skins so clips can't dox skins", ModuleCategory.MISC);
  }

  @Override
  public void onEnable() {
    FakeLootDropModule.ensureInitialized();
    if (this.skinTextures == null) {
      this.string = "";
    }

    this.ensureFetched();
  }

  @Override
  public void onTick() {
    this.ensureFetched();
  }

  public SkinTextures getSkinTextures() {
    return this.skinTextures;
  }

  public SkinTextures getSkinTextures(UUID uuid, SkinTextures skinTextures2) {
    SkinTextures skinTextures3 = this.skinTextures;
    return this.isEnabled() && skinTextures3 != null && this.test(uuid)
        ? skinTextures3
        : skinTextures2;
  }

  public boolean test(UUID uuid) {
    FakeLootDropModule.ensureInitialized();
    if (uuid == null) {
      return false;
    } else {
      MinecraftClient instance = MinecraftClient.getInstance();
      UUID uuid2 =
          instance.player == null
              ? instance.getSession().getUuidOrNull()
              : instance.player.getUuid();
      if (this.applyToSetting.isSelected("Everyone")) {
        return true;
      } else {
        return this.applyToSetting.isSelected("Self")
            ? uuid2 != null && uuid2.equals(uuid)
            : uuid2 == null || !uuid2.equals(uuid);
      }
    }
  }

  public void ensureFetched() {
    FakeLootDropModule.ensureInitialized();

    String string2 = ((String) this.skinIgnSetting.getValue()).trim();
    if (!string2.isEmpty() && !this.enabled && !string2.equalsIgnoreCase(this.string)) {
      this.enabled = true;
      this.string = string2;
      CompletableFuture.runAsync(
              () -> {
                this.resolve(string2);
              })
          .whenComplete(
              (value2, throwable) -> {
                this.enabled = false;
              });
    }
  }

  public void resolve(String text) {
    FakeLootDropModule.ensureInitialized();

    try {
      JsonObject jsonObject =
          getJsonObject("https://api.mojang.com/users/profiles/minecraft/" + text);
      if (jsonObject == null || !jsonObject.has("id")) {
        ClientInitializer.logger.warn("[SkinProtect] Unknown username: {}", text);
        return;
      }

      UUID uuid = getUUID(jsonObject.get("id").getAsString());
      String text2 = jsonObject.has("name") ? jsonObject.get("name").getAsString() : text;
      JsonObject jsonObject2 =
          getJsonObject(
              "https://sessionserver.mojang.com/session/minecraft/profile/"
                  + uuid.toString().replace("-", "")
                  + "?unsigned=false");
      if (jsonObject2 == null || !jsonObject2.has("properties")) {
        ClientInitializer.logger.warn("[SkinProtect] No profile textures for {}", text);
        return;
      }

      var linkedHashMultimap =
          LinkedHashMultimap.<String, com.mojang.authlib.properties.Property>create();

      for (JsonElement jsonElement : jsonObject2.getAsJsonArray("properties")) {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        if ("textures".equals(asJsonObject.get("name").getAsString())) {
          String asString = asJsonObject.get("value").getAsString();
          String text3 =
              asJsonObject.has("signature") ? asJsonObject.get("signature").getAsString() : null;
          linkedHashMultimap.put(
              "textures",
              text3 == null
                  ? new Property("textures", asString)
                  : new Property("textures", asString, text3));
        }
      }

      GameProfile gameProfile = new GameProfile(uuid, text2, new PropertyMap(linkedHashMultimap));
      MinecraftClient.getInstance()
          .getSkinProvider()
          .fetchSkinTextures(gameProfile)
          .thenAccept(
              optional -> {
                FakeLootDropModule.ensureInitialized();
                if (optional.isPresent()) {
                  this.skinTextures = optional.get();
                  ClientInitializer.logger.info("[SkinProtect] Loaded skin for {}", text2);
                } else {
                  ClientInitializer.logger.warn(
                      "[SkinProtect] Could not load skin texture for {}", text2);
                }
              });
    } catch (Exception failure) {
      ClientInitializer.logger.warn(
          "[SkinProtect] Failed to fetch skin for {}: {}", text, failure.toString());
    }
  }

  public static JsonObject getJsonObject(String text) throws Exception {
    FakeLootDropModule.ensureInitialized();

    HttpRequest httpRequest =
        HttpRequest.newBuilder(URI.create(text))
            .timeout(Duration.ofSeconds(8L))
            .header("Accept", "application/json")
            .GET()
            .build();
    var httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
    return httpResponse.statusCode() == 200
            && httpResponse.body() != null
            && !((String) httpResponse.body()).isBlank()
        ? JsonParser.parseString((String) httpResponse.body()).getAsJsonObject()
        : null;
  }

  public static UUID getUUID(String text) {
    return UUID.fromString(
        text.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
  }

  static {
    ClientInitializer[] clientInitializerArray2 = new ClientInitializer[5];

    setClientInitializerArray(clientInitializerArray2);
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }
}
