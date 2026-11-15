package bunnythekenzo.client;

import com.google.gson.JsonObject;
import bunnythekenzo.client.setting.SettingCodec;

public final class ConfigSummary {
  public String string;
  public String string2;
  public boolean enabled;
  public JsonObject jsonObject;

  public ConfigSummary(String text, String text2, boolean flag) {
    this(text, text2, flag, null);
  }

  public ConfigSummary(String string3, String string22, boolean enabled2, JsonObject jsonObject2) {
    this.string = string3;
    this.string2 = string22;
    this.enabled = enabled2;
    this.jsonObject = jsonObject2;
  }

  public String getString() {
    return this.string;
  }

  public void setString(String string3) {
    this.string = string3;
  }

  public String getString2() {
    return this.enabled ? this.string2 : "Nothing saved yet";
  }

  public String getString22() {
    return this.string2;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public JsonObject getJsonObject() {
    return this.jsonObject;
  }

  public void update() {
    this.jsonObject = JsonSupport2.getJsonObject();
    if (ClientInitializer.getClientConfig() != null) {
      this.jsonObject.add("client", ClientInitializer.getClientConfig().getJsonObject());
    }

    JsonObject jsonObject2 = new JsonObject();

    for (CollectionData collectionData :
        (java.lang.Iterable<CollectionData>)
            (java.lang.Iterable<?>) (GuiPreferences.getGuiPreferences().getList())) {
      jsonObject2.add(collectionData.string(), SettingCodec.getJsonObject(collectionData.list()));
    }

    this.jsonObject.add("preferences", jsonObject2);
    this.enabled = true;
    int value = JsonSupport2.computeInt();
    this.string2 = value + (value == 1 ? " module" : " modules") + "  ·  just updated";
  }

  public void update2() {
    if (this.jsonObject != null) {
      ClientConfig clientConfig = ClientInitializer.getClientConfig();
      boolean enabled2 = ConfigRepository.enabled;
      ConfigRepository.enabled = true;

      try {
        if (clientConfig != null) {
          if (this.jsonObject.has("client") && this.jsonObject.get("client").isJsonObject()) {
            clientConfig.apply(this.jsonObject.getAsJsonObject("client"));
          } else if (this.jsonObject.has("state") && this.jsonObject.get("state").isJsonObject()) {
            clientConfig.apply(this.jsonObject.getAsJsonObject("state"));
          } else {
            clientConfig.apply(this.jsonObject);
          }
        }

        JsonSupport2.apply(this.jsonObject);
        if (this.jsonObject.has("preferences")
            && this.jsonObject.get("preferences").isJsonObject()) {
          JsonObject asJsonObject = this.jsonObject.getAsJsonObject("preferences");

          for (CollectionData collectionData :
              (java.lang.Iterable<CollectionData>)
                  (java.lang.Iterable<?>) (GuiPreferences.getGuiPreferences().getList())) {
            SettingCodec.apply(
                asJsonObject.getAsJsonObject(collectionData.string()), collectionData.list());
          }

          GuiPreferences.getGuiPreferences().update();
        }

        JsonSupport2.update3();
        JsonSupport2.update4();
        GuiPreferences.getGuiPreferences().update3();
        if (clientConfig != null) {
          clientConfig.update3();
        }
      } finally {
        ConfigRepository.enabled = enabled2;
      }
    }
  }

  public void apply(JsonObject jsonObject2, String string22) {
    this.jsonObject = jsonObject2;
    this.enabled = true;
    this.string2 = string22;
  }
}
