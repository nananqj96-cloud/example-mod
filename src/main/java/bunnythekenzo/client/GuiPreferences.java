package bunnythekenzo.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import bunnythekenzo.client.render.SystemFonts;
import bunnythekenzo.client.render.SystemFontsData;
import bunnythekenzo.client.setting.ChoiceSettingView;
import bunnythekenzo.client.setting.ColorSettingView;
import bunnythekenzo.client.setting.NumberSettingView;
import bunnythekenzo.client.setting.SettingCodec;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.ToggleSettingView;

public final class GuiPreferences {
  public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  public static GuiPreferences guiPreferences;
  public Integer integer;
  public final ChoiceSettingView guiStyleControl =
      new ChoiceSettingView(
          "GUI style",
          "The menu opened by your ClickGUI key. You can also switch inside either GUI.",
          0,
          new String[] {"Panel", "Dropdown"});
  public final NumberSettingView backgroundBlurControl =
      new NumberSettingView(
          "Background blur",
          "How hard the world blurs behind the menu",
          7.0F,
          0.0F,
          10.0F,
          1.0F,
          "");
  public final NumberSettingView backdropDimControl =
      new NumberSettingView(
          "Backdrop dim",
          "How far the world darkens behind the menu",
          40.0F,
          0.0F,
          80.0F,
          5.0F,
          "%");
  public final NumberSettingView menuScaleControl =
      new NumberSettingView(
          "Menu scale",
          "Size of the menu, independent of Minecraft's GUI scale",
          100.0F,
          70.0F,
          140.0F,
          5.0F,
          "%");
  public final ToggleSettingView animationsControl =
      new ToggleSettingView("Animations", "Motion throughout the interface", true);
  public final ToggleSettingView tooltipsControl =
      new ToggleSettingView("Tooltips", "Descriptions when the cursor rests on something", true);
  public final ToggleSettingView windowShadowControl =
      new ToggleSettingView("Window shadow", "Depth cast behind the panel", true);
  public final ToggleSettingView filmGrainControl =
      new ToggleSettingView(
          "Film grain", "Fine texture over the backdrop, and no gradient banding", true);
  public final ToggleSettingView reactiveSurfaceControl =
      new ToggleSettingView(
          "Reactive surface", "Lattice and mark cut into the panel, lit by your cursor", true);
  public final ColorSettingView accentColourControl =
      new ColorSettingView(
          "Accent colour",
          "Shared colour for both GUIs, the HUD and default visual effects",
          -40008);
  public final ChoiceSettingView fontControl =
      new ChoiceSettingView(
          "Font",
          "Typeface used throughout the client",
          SystemFonts.computeInt(),
          SystemFonts.getList2());
  public final NumberSettingView masterVolumeControl =
      new NumberSettingView(
          "Master volume", "Level for every interface sound", 60.0F, 0.0F, 100.0F, 5.0F, "%");
  public final ToggleSettingView hoverControl =
      new ToggleSettingView("Hover", "Ticks when the cursor enters something", true);
  public final ToggleSettingView clicksControl =
      new ToggleSettingView("Clicks", "Toggles, tabs, drawers and sliders", true);
  public final ToggleSettingView openAndCloseControl =
      new ToggleSettingView("Open and close", "The swell when the menu appears and leaves", true);
  public final ChoiceSettingView startupSoundControl =
      new ChoiceSettingView(
          "Startup sound",
          "Creamy: the new bunnythekenzo ident. Classic: your original startup tracks.",
          0,
          new String[] {"Creamy", "Classic", "Off"});
  public final ToggleSettingView showHudControl =
      new ToggleSettingView("Show HUD", "Master switch for everything drawn over the game", true);
  public final NumberSettingView hudScaleControl =
      new NumberSettingView(
          "HUD scale", "Size of the on-screen elements", 100.0F, 60.0F, 160.0F, 5.0F, "%");
  public final ColorSettingView hudColourControl =
      new ColorSettingView(
          "HUD colour", "Custom HUD text colour when HUD follows theme is off", -790792);
  public final ToggleSettingView hudFollowsThemeControl =
      new ToggleSettingView(
          "HUD follows theme",
          "Use the shared accent for HUD text; turn off to use HUD colour",
          true);
  public final ToggleSettingView hudShadowControl =
      new ToggleSettingView("HUD shadow", "Dark plate behind HUD text, for busy backgrounds", true);
  public final ToggleSettingView snapWhileDraggingControl =
      new ToggleSettingView(
          "Snap while dragging", "Guides and magnetism when arranging the HUD", true);
  public final List list =
      List.of(
          new CollectionData(
              "Interface",
              new SettingEntry[] {
                this.guiStyleControl,
                this.backgroundBlurControl,
                this.backdropDimControl,
                this.menuScaleControl,
                this.animationsControl,
                this.tooltipsControl,
                this.windowShadowControl,
                this.filmGrainControl,
                this.reactiveSurfaceControl
              }),
          new CollectionData(
              "Appearance", new SettingEntry[] {this.accentColourControl, this.fontControl}),
          new CollectionData(
              "Sound",
              new SettingEntry[] {
                this.masterVolumeControl,
                this.hoverControl,
                this.clicksControl,
                this.openAndCloseControl,
                this.startupSoundControl
              }),
          new CollectionData(
              "HUD",
              new SettingEntry[] {
                this.showHudControl,
                this.hudScaleControl,
                this.hudFollowsThemeControl,
                this.hudColourControl,
                this.hudShadowControl,
                this.snapWhileDraggingControl
              }));

  public static GuiPreferences getGuiPreferences() {
    if (guiPreferences == null) {
      guiPreferences = new GuiPreferences();
      guiPreferences.update2();
      guiPreferences.update();
    }

    return guiPreferences;
  }

  public List getList() {
    return this.list;
  }

  public float computeFloat() {
    return this.backgroundBlurControl.getValue();
  }

  public float computeFloat2() {
    return this.backdropDimControl.getValue() / 100.0F;
  }

  public float computeFloat3() {
    return this.menuScaleControl.getValue() / 100.0F;
  }

  public boolean test() {
    return this.tooltipsControl.getValue();
  }

  public boolean test2() {
    return this.windowShadowControl.getValue();
  }

  public boolean test3() {
    return this.reactiveSurfaceControl.getValue();
  }

  public float computeFloat4() {
    return this.hudScaleControl.getValue() / 100.0F;
  }

  public String getString() {
    return ((SystemFontsData) SystemFonts.getList().get(this.fontControl.getSelectedIndex()))
        .string();
  }

  public void update() {
    AnimationPreferences.setEnabled(this.animationsControl.getValue());
    ClientAudio.amount = this.masterVolumeControl.getValue() / 100.0F;
    ClientAudio.enabled2 = this.hoverControl.getValue();
    ClientAudio.enabled3 = this.clicksControl.getValue();
    ClientAudio.enabled = this.openAndCloseControl.getValue();
    if (this.integer == null || this.integer != this.accentColourControl.computeInt()) {
      ColorPalette.apply(this.accentColourControl.computeInt());
      this.integer = this.accentColourControl.computeInt();
    }
  }

  public void apply(int integer2) {
    this.accentColourControl.apply(integer2);
    this.integer = integer2;
  }

  public static Path getPath() {
    return FabricLoader.getInstance()
        .getConfigDir()
        .resolve("bunnythekenzo")
        .resolve("settings.json");
  }

  public void update2() {
    Path path = getPath();
    if (Files.exists(path)) {
      try {
        label68:
        {
          try (BufferedReader bufferedReader =
              Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);
            if (jsonObject != null) {
              for (CollectionData collectionData :
                  (java.lang.Iterable<CollectionData>) (java.lang.Iterable<?>) (this.list)) {
                JsonObject asJsonObject = jsonObject.getAsJsonObject(collectionData.string());
                if (asJsonObject != null) {
                  SettingCodec.apply(asJsonObject, collectionData.list());
                }
              }
              break label68;
            }
          }

          return;
        }
      } catch (Exception failure) {
        ClientInitializer.logger.warn("Could not read {}; using defaults", path, failure);
      }
    }
  }

  public void update3() {
    Path path = getPath();

    try {
      Files.createDirectories(path.getParent());
      JsonObject jsonObject = new JsonObject();

      for (CollectionData collectionData :
          (java.lang.Iterable<CollectionData>) (java.lang.Iterable<?>) (this.list)) {
        jsonObject.add(collectionData.string(), SettingCodec.getJsonObject(collectionData.list()));
      }

      try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        gson.toJson(jsonObject, bufferedWriter);
      }
    } catch (IOException failure) {
      ClientInitializer.logger.error("Could not write {}", path, failure);
    }
  }
}
