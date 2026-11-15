package bunnythekenzo.client.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.gui.screen.TitleScreen;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.HudStyle;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.ProfileStorage;
import bunnythekenzo.client.SpotifyBridge;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.WorldSignal;
import bunnythekenzo.client.WorldSignalSupport;
import bunnythekenzo.client.hud.SpotifyHud;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.module.SpotifyHUDModule;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.ColorSettingView;
import bunnythekenzo.client.ui.DropdownScreen;
import bunnythekenzo.client.ui.PanelScreen;

public final class ThemePanelBlueTest {
  public static void run(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.getInput().resizeWindow(1600, 900);
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.options.pauseOnLostFocus = false;
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled = true;
          checkThemes();
          minecraftClient.setScreen(new PanelScreen());
        });
    clientGameTestContext.waitTicks(15);
    clientGameTestContext.takeScreenshot("theme-panel-blue");
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              ColorPalette.getInstance() == ClientInitializer.getThemeManager().getColorPalette(),
              "panel uses shared theme");
          minecraftClient2.setScreen(new DropdownScreen());
          GuiPreferences.getGuiPreferences().accentColourControl.apply(-13248858);
          GuiPreferences.getGuiPreferences().update();
          apply(
              ClientInitializer.getThemeManager().getColorPalette().getCount() == -13248858,
              "dropdown change updates HUD theme");
          checkSpotify();
        });
    clientGameTestContext.waitTicks(15);
    clientGameTestContext.takeScreenshot("theme-dropdown-green");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          minecraftClient3.setScreen(new PanelScreen());
        });
    clientGameTestContext.waitTicks(15);
    clientGameTestContext.takeScreenshot("theme-panel-green");
    clientGameTestContext.setScreen(TitleScreen::new);
    ClientInitializer.logger.info(
        "Theme/Spotify regression passed: shared theme, persistence, defaults, overrides, scaled"
            + " controls, dragging and wheel");
  }

  public static void checkThemes() {
    GuiPreferences guiPreferences = GuiPreferences.getGuiPreferences();
    ThemeManager themeManager = ClientInitializer.getThemeManager();
    ProtectionPerformanceTest.ensureInitialized();

    themeManager.test("Emerald");
    guiPreferences.update();
    apply(
        themeManager.getColorPalette().getString2().equals("Emerald"),
        "unrelated settings preserve preset");
    guiPreferences.accentColourControl.apply(-12016129);
    guiPreferences.update();
    apply(
        themeManager.getColorPalette() == ColorPalette.getInstance()
            && ColorPalette.getInstance() == ColorPalette.colorPalette,
        "custom colour replaces preset everywhere");
    apply(themeManager.getColorPalette().getCount() == -12016129, "HUD follows custom colour");
    apply(
        HudStyle.computeInt() == ColorPalette.getInstance().computeInt(),
        "HUD text follows by default");
    JsonObject jsonObject = themeManager.getJsonObject();
    guiPreferences.accentColourControl.apply(-65536);
    guiPreferences.update();
    themeManager.apply(jsonObject);
    apply(
        ColorPalette.getInstance().getCount() == -12016129
            && guiPreferences.accentColourControl.computeInt() == -12016129,
        "custom theme round trip and picker sync");
    themeManager.test("Rainbow");
    guiPreferences.update();
    apply(
        themeManager.getColorPalette().getString2().equals("Rainbow"),
        "applying settings preserves rainbow");
    themeManager.apply(jsonObject);
    ColorSetting colorSetting = new ColorSetting("test", "test", -49508).getColorSetting();
    apply(colorSetting.getInteger() == ColorPalette.getInstance().getCount(), "live default");
    colorSetting.fromJson(new JsonPrimitive(-49508));
    apply(
        colorSetting.getInteger() == ColorPalette.getInstance().getCount(),
        "legacy factory swatch migrates");
    colorSetting.apply(Integer.valueOf(-5517841));
    JsonElement jsonElement = colorSetting.toJson();
    colorSetting.resetToDefault();
    apply(
        colorSetting.getInteger() == ColorPalette.getInstance().getCount(),
        "reset returns to current theme");
    colorSetting.fromJson(jsonElement);
    apply(colorSetting.getInteger() == -5517841, "custom override survives reload");
    colorSetting.apply(Integer.valueOf(-49508));
    jsonElement = colorSetting.toJson();
    colorSetting.resetToDefault();
    colorSetting.fromJson(jsonElement);
    apply(
        colorSetting.getInteger() == -49508, "explicit factory colour stays custom in new configs");
    ModuleManager moduleManager = ClientInitializer.getModuleManager();

    for (ColorSetting colorSetting2 :
        new ColorSetting[] {
          moduleManager.customBlockOutlineModule.colorSetting,
          moduleManager.hitParticlesModule.colorSetting,
          moduleManager.client67JumpCirclesModule.colorSetting,
          moduleManager.customCrosshairModule.colorSetting,
          moduleManager.skyModule.colourSetting
        }) {
      colorSetting2.resetToDefault();
      apply(
          colorSetting2.getInteger() == ColorPalette.getInstance().getCount(),
          "module default: " + colorSetting2.getName());
    }

    GuiModuleEntry guiModuleEntry =
        (bunnythekenzo.client.GuiModuleEntry)
            (JsonSupport2.getList().stream()
                .flatMap(
                    collectionData3 -> {
                      return ((java.util.List) (collectionData3.list())).stream();
                    })
                .filter(
                    value2 -> {
                      return ((bunnythekenzo.client.GuiModuleEntry) (value2)).getModule()
                          == moduleManager.hitParticlesModule;
                    })
                .findFirst()
                .orElseThrow());
    ColorSettingView toggleSettingViewSupport2 =
        (bunnythekenzo.client.setting.ColorSettingView)
            (guiModuleEntry.getList().stream()
                .flatMap(
                    collectionData -> {
                      return ((java.util.List) (collectionData.list())).stream();
                    })
                .filter(
                    value3 -> {
                      return ((bunnythekenzo.client.setting.SettingEntry) (value3))
                          .getName()
                          .equals("Color");
                    })
                .findFirst()
                .orElseThrow());
    toggleSettingViewSupport2.apply(-5517841);
    toggleSettingViewSupport2.update();
    apply(
        toggleSettingViewSupport2.computeInt() == ColorPalette.getInstance().getCount(),
        "GUI Default returns to live accent");
  }

  public static void checkSpotify() {
    ProtectionPerformanceTest.ensureInitialized();

    try {
      SpotifyBridge spotifyBridge = new SpotifyBridge();
      StringWriter stringWriter = new StringWriter();
      Field declaredField = SpotifyBridge.class.getDeclaredField("bufferedWriter");
      declaredField.setAccessible(true);
      declaredField.set(spotifyBridge, new BufferedWriter(stringWriter));
      Method declaredMethod =
          SpotifyBridge.class.getDeclaredMethod("handleStatusMessage", String.class);
      declaredMethod.setAccessible(true);
      declaredMethod.invoke(
          spotifyBridge,
          "{\"active\":true,\"title\":\"Test\",\"artist\":\"Test\",\"playing\":true,\"posMs\":10000,\"durMs\":200000,\"canSeek\":true,\"vol\":70}");
      SpotifyHUDModule spotifyHUDModule = new SpotifyHUDModule();
      spotifyHUDModule.setEnabled(true);
      SpotifyHud spotifyHud =
          new SpotifyHud(spotifyHUDModule, spotifyBridge, ClientInitializer.getThemeManager());
      Field declaredField2 = JsonSupport2.class.getDeclaredField("list2");
      declaredField2.setAccessible(true);
      Object value = declaredField2.get(null);
      declaredField2.set(null, List.of(spotifyHud));
      boolean flag = GuiPreferences.getGuiPreferences().animationsControl.getValue();
      float value2 = GuiPreferences.getGuiPreferences().hudScaleControl.getValue();
      GuiPreferences.getGuiPreferences().animationsControl.setValue(false);
      GuiPreferences.getGuiPreferences().update();

      try {
        for (float floatValue : new float[] {1.0F, 0.55F}) {
          stringWriter.getBuffer().setLength(0);
          spotifyHud.apply(1.4F);
          GuiPreferences.getGuiPreferences().hudScaleControl.setValue(135.0F);
          spotifyHud.apply2(0.31F, 0.41F);
          FontManager instance = FontManager.getInstance();
          WorldSignal worldSignal =
              (bunnythekenzo.client.WorldSignal)
                  (WorldSignalSupport.getList(
                          instance, 40.0F, 30.0F, 1000.0F, 600.0F, floatValue, true)
                      .stream()
                      .filter(
                          value3 -> {
                            return ((bunnythekenzo.client.WorldSignal) (value3)).hudWidget()
                                == spotifyHud;
                          })
                      .findFirst()
                      .orElseThrow());

          for (float floatValue2 : new float[] {176.0F, 200.0F, 224.0F}) {
            render(instance, worldSignal, floatValue, floatValue2, 22.0F);
            apply(
                !WorldSignalSupport.test() && WorldSignalSupport.test2(),
                "transport captures without moving card");
            WorldSignalSupport.update();
          }

          String text = stringWriter.toString();
          String text2 = System.lineSeparator();
          String text3;
          String text4 = text3 = System.lineSeparator();
          String text5 = text2;
          apply(
              text.equals("PREV" + text5 + "PLAYPAUSE" + text4 + "NEXT" + text3),
              "transport reaches bridge exactly once");
          render(instance, worldSignal, floatValue, 78.5F, 80.0F);
          render2(instance, worldSignal, floatValue, 163.5F, 80.0F);
          WorldSignalSupport.update();
          apply(
              stringWriter.toString().contains("VOLUME 25")
                  && stringWriter.toString().contains("VOLUME 75"),
              "volume drag reaches app");
          apply(
              spotifyHud.getAmount5() == 0.31F && spotifyHud.getAmount6() == 0.41F,
              "slider does not move HUD");
          render(instance, worldSignal, floatValue, 117.0F, 60.0F);
          render2(instance, worldSignal, floatValue, 158.0F, 60.0F);
          WorldSignalSupport.update();
          apply(
              stringWriter.toString().contains("SEEK 100000"), "seek drag commits final position");
          WorldSignalSupport.test(
              instance,
              40.0F,
              30.0F,
              1000.0F,
              600.0F,
              floatValue,
              computeFloat(worldSignal, 100.0F),
              computeFloat2(worldSignal, 80.0F),
              1.0);
          apply(
              stringWriter.toString().contains("VOLUME 80") && spotifyHud.getAmount7() == 1.4F,
              "wheel changes volume without resizing");
          render(instance, worldSignal, floatValue, 18.0F, 80.0F);
          WorldSignalSupport.update();
          apply(stringWriter.toString().contains("VOLUME 0"), "speaker mutes Spotify");
          render(instance, worldSignal, floatValue, 18.0F, 80.0F);
          WorldSignalSupport.update();
          apply(spotifyBridge.getClientData4().count2() == 80, "speaker restores prior volume");
          render(instance, worldSignal, floatValue, 20.0F, 40.0F);
          apply(WorldSignalSupport.test(), "album area still moves HUD");
          WorldSignalSupport.update();
        }

        declaredMethod.invoke(spotifyBridge, "{\"active\":false}");
        apply(!spotifyHud.test3(), "idle card has no invisible controls");
        apply(!spotifyHud.test(224.0F, 22.0F), "inactive transport does not send commands");
      } finally {
        WorldSignalSupport.update();
        declaredField2.set(null, value);
        GuiPreferences.getGuiPreferences().animationsControl.setValue(flag);
        GuiPreferences.getGuiPreferences().hudScaleControl.setValue(value2);
        GuiPreferences.getGuiPreferences().update();
      }
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static float computeFloat(WorldSignal worldSignal, float floatValue) {
    return Math.round(worldSignal.amount())
        + floatValue * worldSignal.amount3() / worldSignal.hudWidget().getAmount8();
  }

  public static float computeFloat2(WorldSignal worldSignal, float floatValue) {
    return Math.round(worldSignal.amount2())
        + floatValue * worldSignal.amount4() / worldSignal.hudWidget().getAmount9();
  }

  public static void render(
      FontManager fontManager,
      WorldSignal worldSignal,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    apply(
        WorldSignalSupport.test(
            fontManager,
            40.0F,
            30.0F,
            1000.0F,
            600.0F,
            floatValue,
            computeFloat(worldSignal, floatValue2),
            computeFloat2(worldSignal, floatValue3)),
        "HUD press consumed");
  }

  public static void render2(
      FontManager fontManager,
      WorldSignal worldSignal,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    WorldSignalSupport.render(
        fontManager,
        40.0F,
        30.0F,
        1000.0F,
        600.0F,
        floatValue,
        computeFloat(worldSignal, floatValue2),
        computeFloat2(worldSignal, floatValue3));
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}
