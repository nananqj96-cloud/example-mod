package bunnythekenzo.client.gametest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.Perspective;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import bunnythekenzo.client.BlockGalleryModel;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.ClientSounds;
import bunnythekenzo.client.CollectionData;
import bunnythekenzo.client.CollectionData3;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.EmoteAnimation;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.EntityGalleryModel;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.hud.HudElement;
import bunnythekenzo.client.hud.HudWidget;
import bunnythekenzo.client.module.CosmeticsModule;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.module.SkyModule;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.render.SkyRenderer;
import bunnythekenzo.client.setting.BlockListSetting;
import bunnythekenzo.client.setting.EntityTypeSetting;
import bunnythekenzo.client.setting.ItemSetting;
import bunnythekenzo.client.setting.Setting;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.SettingWidget;
import bunnythekenzo.client.ui.EmoteWheelScreen;
import bunnythekenzo.client.ui.GalleryItem;
import bunnythekenzo.client.ui.GalleryModel;
import bunnythekenzo.client.ui.PanelScreen;
import bunnythekenzo.client.ui.SelectionDialog;
import bunnythekenzo.client.ui.SelectionGallery;

public class ClientGameTests implements FabricClientGameTest {

  @Override
  public void runTest(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();
    if (Boolean.getBoolean("bunnythekenzo.test.themeSpotify")) {
      ThemePanelBlueTest.run(clientGameTestContext);
    } else if (Boolean.getBoolean("bunnythekenzo.test.creamyKeys")) {
      CreamyKeysFourStylesTest.run(clientGameTestContext);
    } else if (Boolean.getBoolean("bunnythekenzo.test.antiCheatKeybind")) {
      HoppinStandingBeforeTestSupport20.run(clientGameTestContext);
    } else if (Boolean.getBoolean("bunnythekenzo.test.welcome")) {
      WelcomeCueTest.run(clientGameTestContext);
    } else if (Boolean.getBoolean("bunnythekenzo.test.panel")) {
      Panel01OpenTest.run(clientGameTestContext);
    } else if (Boolean.getBoolean("bunnythekenzo.test.dropdown")) {
      DropdownLiveAllModulesTest.run(clientGameTestContext);
    } else if (Boolean.getBoolean("bunnythekenzo.test.profile")) {
      Client67Studio7Test.run(clientGameTestContext);
    } else if (Boolean.getBoolean("bunnythekenzo.test.relog")) {
      RelogTestObserverTest.run(clientGameTestContext);
    } else {
      try (TestSingleplayerContext testSingleplayerContext =
          clientGameTestContext.worldBuilder().create()) {
        testSingleplayerContext.getClientWorld().waitForChunksRender();

        clientGameTestContext.runOnClient(
            minecraftClient -> {
              minecraftClient.options.pauseOnLostFocus = false;
            });

        if (Boolean.getBoolean("bunnythekenzo.test.deepscan")) {
          DeepscanToastStartTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.emoteSkin")) {
          clientGameTestContext.getInput().resizeWindow(1280, 900);
          EmoteSkinTest.run(clientGameTestContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.twerking")) {
          clientGameTestContext.getInput().resizeWindow(1280, 900);
          TwerkingMenuTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.takeTheL")) {
          clientGameTestContext.getInput().resizeWindow(1280, 900);
          TakeTheLMenuTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.passinho")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          PassinhoWheelTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.orangeJustice")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          OrangeJusticeStandingBeforeTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.lushLife")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          LushLifeWheelTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.farex")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          FarexWheelTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.hoppin")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          HoppinStandingBeforeTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.wizox")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          WizoxWheelTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.emotes")) {
          clientGameTestContext.getInput().resizeWindow(1600, 900);
          clientGameTestContext.runOnClient(
              minecraftClient2 -> {
                checkEmotes();
              });
          runEmoteSelection(clientGameTestContext);
          runEmotePreview(clientGameTestContext);
          JerkingOffWheelTest.run(clientGameTestContext);
          ClientInitializer.logger.info(
              "Emotes verified: playable catalogue, remaining blanks and retained routines");
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.jerkingOff")) {
          clientGameTestContext.getInput().resizeWindow(1280, 900);
          JerkingOffWheelTest.run(clientGameTestContext);
          JerkingOffWheelTest.checkConfetti(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.crystalPulse")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          CrystalPulseBaselineTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.hitBox")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          HitboxExpandedTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.autoCrystal")) {
          AutoCrystalTestTest.apply(clientGameTestContext, testSingleplayerContext);
          HoppinStandingBeforeTestSupport11.run(clientGameTestContext);
          HoppinStandingBeforeTestSupport6.run(clientGameTestContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.aimAssist")) {
          HoppinStandingBeforeTestSupport6.run(clientGameTestContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.triggerbot")) {
          TriggerbotSettingsTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.autoTotem")) {
          AutoTotemSettingsTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.anchorMacro")) {
          AnchorTestObserverTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.blockSelector")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          BlockSelectorTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.aurora")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
        } else {
          clientGameTestContext.getInput().resizeWindow(1600, 900);
        }

        clientGameTestContext.waitTicks(2);

        if (Boolean.getBoolean("bunnythekenzo.test.luma")) {
          clientGameTestContext.getInput().resizeWindow(1280, 720);
          LumaFreeFlightTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.aurora")) {
          AuroraBaselineTest.run(clientGameTestContext, testSingleplayerContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.cosmetics")) {
          CosmeticsTest.run(clientGameTestContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.glint")) {
          GlintInfernoInHandTest.run(clientGameTestContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.fakeLoot")) {
          FakeLootWorldBeforeTest.run(clientGameTestContext, testSingleplayerContext);
          FakeLootRulesTest.run(clientGameTestContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.targetEsp")) {
          TargetEspTest.run(clientGameTestContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.planets")) {
          runPlanetModels(clientGameTestContext);
          return;
        }

        if (Boolean.getBoolean("bunnythekenzo.test.rocket")) {
          RebuiltRocketSkyTest.run(clientGameTestContext);
          return;
        }

        clientGameTestContext.runOnClient(
            minecraftClient3 -> {
              update();
              update2();
              update3();
              checkPickers();
              checkEmotes();
            });
        clientGameTestContext.runOnClient(
            minecraftClient4 -> {
              apply2(true);
            });
        clientGameTestContext.waitTicks(20);
        clientGameTestContext.takeScreenshot("hud-all-panels");
        clientGameTestContext.runOnClient(
            minecraftClient5 -> {
              minecraftClient5.setScreen(new PanelScreen());
            });
        clientGameTestContext.waitTicks(20);
        clientGameTestContext.takeScreenshot("menu-modules");
        clientGameTestContext.runOnClient(
            minecraftClient6 -> {
              ((PanelScreen) minecraftClient6.currentScreen).update();
            });
        clientGameTestContext.waitTicks(20);
        clientGameTestContext.takeScreenshot("menu-emotes");
        clientGameTestContext.runOnClient(
            minecraftClient7 -> {
              GuiDialogManager.open(firstBlockPicker());
            });
        clientGameTestContext.waitTicks(20);
        clientGameTestContext.takeScreenshot("picker-over-menu");
        clientGameTestContext.runOnClient(
            minecraftClient8 -> {
              GuiDialogManager.getSelectionDialog().setSearchText("oak");
            });
        clientGameTestContext.waitTicks(10);
        clientGameTestContext.takeScreenshot("picker-search");
        clientGameTestContext.runOnClient(
            minecraftClient9 -> {
              GuiDialogManager.getSelectionDialog().setSearchText("");
              apply(GuiDialogManager.getSelectionDialog().test3(), "colour popup opens");
            });
        clientGameTestContext.waitTicks(10);
        clientGameTestContext.takeScreenshot("picker-colour");
        clientGameTestContext.runOnClient(
            minecraftClient10 -> {
              SelectionDialog selectionDialog = GuiDialogManager.getSelectionDialog();
              ProtectionPerformanceTest.ensureInitialized();

              int int2 = selectionDialog.getVisibleItemCount();
              selectionDialog.setSelectedOnly(true);
              int int22 = selectionDialog.getVisibleItemCount();
              apply(
                  int22 > 0 && int22 < int2,
                  "selected-only narrows the grid (" + int22 + " of " + int2 + ")");
            });
        clientGameTestContext.waitTicks(10);
        clientGameTestContext.takeScreenshot("picker-selected-only");
        clientGameTestContext.runOnClient(
            minecraftClient11 -> {
              GuiDialogManager.closeAll();
              GuiDialogManager.open(
                  new EntityGalleryModel(
                      ClientInitializer.getModuleManager().storageESPModule.containersSetting));
            });
        clientGameTestContext.waitTicks(15);
        clientGameTestContext.takeScreenshot("picker-storage");
        clientGameTestContext.runOnClient(
            minecraftClient12 -> {
              GuiDialogManager.closeAll();
              PanelScreen panelScreen = new PanelScreen();
              minecraftClient12.setScreen(panelScreen);
              apply(panelScreen.test("BlockESP"), "BlockESP settings open");
            });
        clientGameTestContext.waitTicks(25);
        clientGameTestContext.takeScreenshot("settings-blockesp");
        clientGameTestContext.runOnClient(
            minecraftClient13 -> {
              PanelScreen panelScreen2 = new PanelScreen();
              minecraftClient13.setScreen(panelScreen2);
              apply(panelScreen2.test("StorageESP"), "StorageESP settings open");
            });
        clientGameTestContext.waitTicks(25);
        clientGameTestContext.takeScreenshot("settings-storageesp");
        clientGameTestContext.runOnClient(
            minecraftClient14 -> {
              PanelScreen panelScreen3 = new PanelScreen();
              minecraftClient14.setScreen(panelScreen3);
              apply(panelScreen3.test("Sky"), "Sky settings open");
            });
        clientGameTestContext.waitTicks(25);
        clientGameTestContext.takeScreenshot("settings-sky");
        clientGameTestContext.runOnClient(
            minecraftClient15 -> {
              GuiDialogManager.closeAll();
              minecraftClient15.setScreen(null);
              apply2(false);
            });
        clientGameTestContext.waitTicks(2);
        runEmotePreview(clientGameTestContext);
        runEmoteSelection(clientGameTestContext);
        runCosmeticsAlone(clientGameTestContext);
        runSkyAlone(clientGameTestContext);
        runEveryModule(clientGameTestContext);
        ClientInitializer.logger.info("bunnythekenzo wiring verified");
      }
    }
  }

  public static void runEmotePreview(ClientGameTestContext clientGameTestContext) {
    boolean[] flags = new boolean[1];
    boolean[] flags2 = new boolean[1];
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
          flags[0] = emoteCatalog.cancelOnActionControl.getValue();
          flags2[0] = emoteCatalog.automaticF5Control.getValue();
          emoteCatalog.cancelOnActionControl.setValue(true);
          emoteCatalog.automaticF5Control.setValue(true);
          emoteCatalog.apply(computeInt("Drdonutt wholesome moment"));
          minecraftClient.options.forwardKey.setPressed(true);
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          minecraftClient2.options.jumpKey.setPressed(true);
        });
    clientGameTestContext.waitTicks(2);
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              ClientInitializer.getEmoteCatalog().getCount()
                  == computeInt("Drdonutt wholesome moment"),
              "Neon Shuffle keeps playing while walking and jumping");
          minecraftClient3.options.jumpKey.setPressed(false);
          minecraftClient3.options.forwardKey.setPressed(false);
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("emote-neon-shuffle");
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          minecraftClient4.options.forwardKey.setPressed(false);
          minecraftClient4.options.jumpKey.setPressed(false);
          ClientInitializer.getEmoteCatalog().update();
          ClientInitializer.getEmoteCatalog().cancelOnActionControl.setValue(flags[0]);
          ClientInitializer.getEmoteCatalog().automaticF5Control.setValue(flags2[0]);
        });
    clientGameTestContext.waitTicks(2);
  }

  public static void runEmoteSelection(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();

    int intValue = -1;

    for (int index = 0; index < ClientInitializer.getEmoteCatalog().getList().size(); index++) {
      if (((EmoteDefinition) ClientInitializer.getEmoteCatalog().getList().get(index)).test()) {
        intValue = index;
        break;
      }
    }

    int intValue2 = intValue;
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
          ProtectionPerformanceTest.ensureInitialized();

          minecraftClient.setScreen(null);
          emoteCatalog.update();
          Perspective perspective = minecraftClient.options.getPerspective();

          for (int index2 = 0; index2 < emoteCatalog.getList().size(); index2++) {
            if (((EmoteDefinition) emoteCatalog.getList().get(index2)).test()) {
              emoteCatalog.apply(index2);
              apply(
                  !emoteCatalog.test() && minecraftClient.options.getPerspective() == perspective,
                  "blank does not play or switch camera: "
                      + ((EmoteDefinition) emoteCatalog.getList().get(index2)).string());
            }
          }

          for (String text :
              List.of(
                  "Wizox ban this guy",
                  "Farex pull my pearl",
                  "Drdonutt wholesome moment",
                  "Lush life")) {
            int intValue3 = computeInt(text);
            emoteCatalog.apply(intValue3);
            apply(emoteCatalog.getCount() == intValue3, "retained emote starts: " + text);
            emoteCatalog.apply(intValue2);
            apply(emoteCatalog.getCount() == intValue3, "blank leaves current playback intact");
            emoteCatalog.update();
            apply(
                minecraftClient.options.getPerspective() == perspective,
                "stop restores original camera");
          }

          minecraftClient.setScreen(new PanelScreen());
          ((PanelScreen) minecraftClient.currentScreen).update();
          emoteCatalog.apply(intValue2);
          apply(
              minecraftClient.currentScreen instanceof PanelScreen, "blank leaves the panel open");
        });
    clientGameTestContext.waitTicks(25);
    clientGameTestContext.takeScreenshot("emotes-blanks-menu");
    if (intValue2 >= 0) {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            minecraftClient2.setScreen(
                new EmoteWheelScreen(ClientInitializer.getEmoteCatalog(), 66));
          });
      clientGameTestContext.waitTicks(10);
      double[] onClient =
          clientGameTestContext.computeOnClient(
              minecraftClient3 -> {
                double doubleValue =
                    (-Math.PI / 2)
                        + intValue2
                            * Math.PI
                            * 2.0
                            / ClientInitializer.getEmoteCatalog().getList().size();
                double doubleValue2 = 130.0F * OverlayRenderer.computeFloat();
                return new double[] {
                  minecraftClient3.getWindow().getWidth() / 2.0
                      + Math.cos(doubleValue) * doubleValue2,
                  minecraftClient3.getWindow().getHeight() / 2.0
                      + Math.sin(doubleValue) * doubleValue2
                };
              });
      clientGameTestContext.getInput().setCursorPos(onClient[0], onClient[1]);
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("emotes-blanks-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ProtectionPerformanceTest.ensureInitialized();

            Perspective perspective2 = minecraftClient4.options.getPerspective();
            minecraftClient4.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                minecraftClient4.currentScreen == null
                    && !ClientInitializer.getEmoteCatalog().test(),
                "releasing over a blank closes the wheel without playing");
            apply(
                minecraftClient4.options.getPerspective() == perspective2,
                "blank wheel release preserves camera");
          });
    }
  }

  public static int computeInt(String text) {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals(text)) {
        return index;
      }
    }

    throw new AssertionError("Missing emote: " + text);
  }

  public static void runCosmeticsAlone(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();

    clientGameTestContext.runOnClient(
        minecraftClient -> {
          apply(false);
          CosmeticsModule cosmeticsModule = ClientInitializer.getModuleManager().cosmeticsModule;
          cosmeticsModule.wingsSetting.setValue(true);
          cosmeticsModule.crownSetting.setValue(true);
          cosmeticsModule.miniYouSetting.setValue(true);
          cosmeticsModule.update4();
          cosmeticsModule.setEnabled(true);
          minecraftClient.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        });
    clientGameTestContext.waitTicks(24);
    clientGameTestContext.takeScreenshot("cosmetics-idle");

    for (int index = 1; index <= 8; index++) {
      int intValue = index;
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            double doubleValue = minecraftClient2.player.getX() + 0.24;
            double doubleValue2 =
                minecraftClient2.player.getZ() + MathHelper.sin(intValue * 0.55F) * 0.1;
            minecraftClient2.player.setPosition(
                doubleValue, minecraftClient2.player.getY(), doubleValue2);
          });
      clientGameTestContext.waitTicks(2);
    }

    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("cosmetics-motion");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          ClientInitializer.getModuleManager().cosmeticsModule.setEnabled(false);
          minecraftClient3.options.setPerspective(Perspective.FIRST_PERSON);
        });
  }

  public static void runSkyAlone(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.setScreen(null);
          apply(false);
          ClientInitializer.getModuleManager().skyModule.setEnabled(true);
        });
    clientGameTestContext.waitTicks(40);
    clientGameTestContext.takeScreenshot("sky-default");
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          SkyModule skyModule = ClientInitializer.getModuleManager().skyModule;
          skyModule.countSetting.setNumber(Double.valueOf(1400.0));
          skyModule.sizeSetting.setNumber(Double.valueOf(0.16));
          skyModule.opacitySetting.setNumber(Double.valueOf(100.0));
          skyModule.radiusSetting.setNumber(Double.valueOf(14.0));
          skyModule.blendSetting.setValue("Soft");
        });
    clientGameTestContext.waitTicks(40);
    clientGameTestContext.takeScreenshot("sky-motes-soft");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          minecraftClient3.player.setPitch(-40.0F);
        });
    clientGameTestContext.waitTicks(20);
    clientGameTestContext.takeScreenshot("sky-motes-soft-up");
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          ClientInitializer.getModuleManager().skyModule.blendSetting.setValue("Glow");
        });
    clientGameTestContext.waitTicks(20);
    clientGameTestContext.takeScreenshot("sky-motes-glow-up");
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          minecraftClient5.player.setPitch(0.0F);
        });
    clientGameTestContext.waitTicks(20);
    clientGameTestContext.takeScreenshot("sky-motes-glow");
    runDeepSky(clientGameTestContext);
    runSkyColour(clientGameTestContext);
  }

  public static void runPlanetModels(ClientGameTestContext clientGameTestContext) {
    Runnable onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              SkyModule skyModule = ClientInitializer.getModuleManager().skyModule;
              ProtectionPerformanceTest.ensureInitialized();

              LinkedHashMap linkedHashMap = new LinkedHashMap();

              for (Setting setting2 :
                  (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (skyModule.getSettings())) {
                linkedHashMap.put(setting2, setting2.toJson());
              }

              boolean enabled = skyModule.isEnabled();
              boolean hudHidden = minecraftClient.options.hudHidden;
              int value2 = minecraftClient.options.getFov().getValue();
              return () -> {
                linkedHashMap.forEach(
                    (referenceArgument23_0, referenceArgument23_1) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument23_0)
                            .fromJson((com.google.gson.JsonElement) referenceArgument23_1));
                skyModule.setEnabled(enabled);
                minecraftClient.options.getFov().setValue(value2);
                minecraftClient.options.hudHidden = hudHidden;
              };
            });

    try {
      runPlanetPreview(clientGameTestContext);
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            onClient.run();
          });
    }
  }

  public static void runPlanetPreview(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          SkyModule skyModule = ClientInitializer.getModuleManager().skyModule;
          skyModule.setEnabled(true);
          skyModule.skySetting.setValue(true);
          skyModule.planetSetting.setValue(true);
          skyModule.moonSetting.setValue(true);
          skyModule.iceWorldSetting.setValue(true);
          skyModule.oceanWorldSetting.setValue(true);
          skyModule.volcanicWorldSetting.setValue(true);
          skyModule.particlesSetting.setValue(false);
          skyModule.rocketSetting.setValue(false);
          skyModule.shootingStarsSetting.setValue(false);
          skyModule.supernovaSetting.setValue(false);
          skyModule.motionSetting.setNumber(Double.valueOf(100.0));
          skyModule.followThemeSetting.setValue(false);
          skyModule.colourSetting.apply(Integer.valueOf(-40008));
          minecraftClient.options.hudHidden = true;
          minecraftClient.options.getFov().setValue(70);
        });
    clientGameTestContext.waitTicks(20);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          apply(SkyRenderer.test(), "planet render pass remains healthy");
          apply(minecraftClient2, SkyRenderer.getAmountArray3());
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("rebuilt-planet-sky");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          minecraftClient3.options.getFov().setValue(30);
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("rebuilt-planet-detail");
    clientGameTestContext.waitTicks(160);
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          apply(minecraftClient4, SkyRenderer.getAmountArray3());
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("rebuilt-planet-animated");
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          apply(minecraftClient5, SkyRenderer.getAmountArray4());
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("rebuilt-moon-detail");
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          apply(minecraftClient6, SkyRenderer.getAmountArray5());
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("rebuilt-ice-detail");
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          apply(minecraftClient7, SkyRenderer.getAmountArray6());
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("ocean-world-detail");
    float[] onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient8 -> {
              return SkyRenderer.getAmountArray6();
            });
    clientGameTestContext.waitTicks(160);
    clientGameTestContext.runOnClient(
        minecraftClient9 -> {
          ProtectionPerformanceTest.ensureInitialized();

          float[] amountArray6 = SkyRenderer.getAmountArray6();
          apply(
              amountArray6[0] != onClient[0] || amountArray6[1] != onClient[1],
              "ocean world moves with Motion enabled");
          apply(minecraftClient9, amountArray6);
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("ocean-world-animated");
    clientGameTestContext.runOnClient(
        minecraftClient10 -> {
          minecraftClient10.options.getFov().setValue(70);
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("ocean-world-sky");
    clientGameTestContext.runOnClient(
        minecraftClient11 -> {
          minecraftClient11.options.getFov().setValue(30);
          apply(minecraftClient11, SkyRenderer.getAmountArray7());
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("volcanic-world-detail");
    float[] onClient2 =
        clientGameTestContext.computeOnClient(
            minecraftClient12 -> {
              return SkyRenderer.getAmountArray7();
            });
    clientGameTestContext.waitTicks(160);
    clientGameTestContext.runOnClient(
        minecraftClient13 -> {
          ProtectionPerformanceTest.ensureInitialized();

          float[] amountArray7 = SkyRenderer.getAmountArray7();
          apply(
              amountArray7[0] != onClient2[0] || amountArray7[1] != onClient2[1],
              "volcanic world moves with Motion enabled");
          apply(minecraftClient13, amountArray7);
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("volcanic-world-animated");
    clientGameTestContext.runOnClient(
        minecraftClient14 -> {
          ClientInitializer.getModuleManager()
              .skyModule
              .motionSetting
              .setNumber(Double.valueOf(0.0));
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("volcanic-world-frozen");
    float[][] onClient3 =
        clientGameTestContext.computeOnClient(
            minecraftClient15 -> {
              return new float[][] {
                SkyRenderer.getAmountArray3(),
                SkyRenderer.getAmountArray4(),
                SkyRenderer.getAmountArray5(),
                SkyRenderer.getAmountArray6(),
                SkyRenderer.getAmountArray7()
              };
            });
    clientGameTestContext.waitTicks(24);
    clientGameTestContext.takeScreenshot("volcanic-world-frozen-later");
    clientGameTestContext.runOnClient(
        minecraftClient16 -> {
          float[][] floatValuesArray =
              new float[][] {
                SkyRenderer.getAmountArray3(),
                SkyRenderer.getAmountArray4(),
                SkyRenderer.getAmountArray5(),
                SkyRenderer.getAmountArray6(),
                SkyRenderer.getAmountArray7()
              };
          ProtectionPerformanceTest.ensureInitialized();

          for (int index = 0; index < onClient3.length; index++) {
            apply(
                Arrays.equals(onClient3[index], floatValuesArray[index]),
                "Motion 0 freezes world " + index);
          }

          apply(SkyRenderer.test(), "all planet materials rendered without an exception");
          SkyModule skyModule2 = ClientInitializer.getModuleManager().skyModule;
          skyModule2.oceanWorldSetting.setValue(false);
          skyModule2.volcanicWorldSetting.setValue(false);
          apply(
              !SkyModule.test17() && !SkyModule.test18(),
              "new worlds can be switched off independently");
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("volcanic-world-disabled");
    clientGameTestContext.runOnClient(
        minecraftClient17 -> {
          ClientInitializer.getModuleManager().skyModule.oceanWorldSetting.setValue(true);
          ClientInitializer.getModuleManager().skyModule.volcanicWorldSetting.setValue(true);
          ClientInitializer.getModuleManager()
              .skyModule
              .motionSetting
              .setNumber(Double.valueOf(100.0));
          minecraftClient17.options.getFov().setValue(70);
          minecraftClient17.options.hudHidden = false;
        });
  }

  public static void runDeepSky(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          ClientInitializer.getModuleManager().skyModule.particlesSetting.setValue(false);
          minecraftClient.options.hudHidden = true;
          minecraftClient.player.setPitch(-32.0F);
          minecraftClient.player.setYaw(0.0F);
        });
    clientGameTestContext.waitTicks(30);
    clientGameTestContext.takeScreenshot("sky-space");
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          minecraftClient2.player.setYaw(120.0F);
        });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.takeScreenshot("sky-space-east");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          minecraftClient3.player.setYaw(240.0F);
        });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.takeScreenshot("sky-space-west");
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          minecraftClient4.player.setPitch(-72.0F);
        });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.takeScreenshot("sky-zenith");
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          SkyRenderer.update();
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          apply(minecraftClient6, SkyRenderer.getAmountArray2());
        });
    clientGameTestContext.waitTicks(26);
    clientGameTestContext.takeScreenshot("sky-nova-flash");
    clientGameTestContext.waitTicks(30);
    clientGameTestContext.takeScreenshot("sky-nova-wave");
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          minecraftClient7.player.setPitch(-32.0F);
        });
    clientGameTestContext.runOnClient(
        minecraftClient8 -> {
          apply(minecraftClient8, SkyRenderer.getAmountArray3());
        });
    clientGameTestContext.waitTicks(10);
    clientGameTestContext.takeScreenshot("sky-planet");
    clientGameTestContext.waitTicks(90);
    clientGameTestContext.runOnClient(
        minecraftClient9 -> {
          apply(minecraftClient9, SkyRenderer.getAmountArray3());
        });
    clientGameTestContext.waitTicks(4);
    clientGameTestContext.takeScreenshot("sky-planet-later");
    clientGameTestContext.runOnClient(
        minecraftClient10 -> {
          apply(minecraftClient10, SkyRenderer.getAmountArray4());
        });
    clientGameTestContext.waitTicks(6);
    clientGameTestContext.takeScreenshot("sky-moon");
    clientGameTestContext.runOnClient(
        minecraftClient11 -> {
          apply(minecraftClient11, SkyRenderer.getAmountArray5());
        });
    clientGameTestContext.waitTicks(6);
    clientGameTestContext.takeScreenshot("sky-iceworld");
    clientGameTestContext.runOnClient(
        minecraftClient12 -> {
          SkyRenderer.update6();
        });
    clientGameTestContext.waitTicks(2);
    clientGameTestContext.runOnClient(
        minecraftClient13 -> {
          apply(minecraftClient13, SkyRenderer.getAmountArray8());
        });
    clientGameTestContext.waitTicks(6);
    clientGameTestContext.takeScreenshot("sky-meteor");
    clientGameTestContext.runOnClient(
        minecraftClient14 -> {
          SkyRenderer.update2();
          SkyRenderer.update3();
        });
    clientGameTestContext.waitTicks(45);
    clientGameTestContext.runOnClient(
        minecraftClient15 -> {
          SkyRenderer.update3();
        });
    clientGameTestContext.runOnClient(
        minecraftClient16 -> {
          apply(minecraftClient16, SkyRenderer.getAmountArray());
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.takeScreenshot("sky-rocket");
    clientGameTestContext.runOnClient(
        minecraftClient17 -> {
          SkyRenderer.update4();
        });
    clientGameTestContext.waitTicks(22);
    clientGameTestContext.runOnClient(
        minecraftClient18 -> {
          apply(minecraftClient18, SkyRenderer.getAmountArray());
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.takeScreenshot("sky-rocket-boost");
    clientGameTestContext.runOnClient(
        minecraftClient19 -> {
          ClientInitializer.getModuleManager().skyModule.particlesSetting.setValue(true);
          minecraftClient19.options.hudHidden = false;
          minecraftClient19.player.setPitch(0.0F);
        });
    clientGameTestContext.waitTicks(5);
  }

  public static void runSkyColour(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.player.setPitch(-25.0F);
          ColorPalette.apply(-12592897);
        });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.takeScreenshot("sky-colour-theme");
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          SkyModule skyModule = ClientInitializer.getModuleManager().skyModule;
          skyModule.followThemeSetting.setValue(false);
          skyModule.colourSetting.apply(Integer.valueOf(-8585382));
        });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.takeScreenshot("sky-colour-swatch");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          SkyModule skyModule2 = ClientInitializer.getModuleManager().skyModule;
          skyModule2.followThemeSetting.setValue(true);
          skyModule2.colourSetting.apply(Integer.valueOf(-40008));
          ColorPalette.apply(-40008);
          minecraftClient3.player.setPitch(0.0F);
        });
    clientGameTestContext.waitTicks(5);
  }

  public static void apply(MinecraftClient minecraftClient, float[] floatValues) {
    ProtectionPerformanceTest.ensureInitialized();
    if (floatValues != null && minecraftClient.player != null) {
      minecraftClient.player.setYaw(floatValues[0]);
      minecraftClient.player.setPitch(floatValues[1]);
    }
  }

  public static void runEveryModule(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          apply(true);
        });
    clientGameTestContext.waitTicks(60);
    clientGameTestContext.takeScreenshot("every-module-on");
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          minecraftClient2.setScreen(new ChatScreen("", false));
        });
    clientGameTestContext.waitTicks(20);
    clientGameTestContext.takeScreenshot("hud-editor");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          minecraftClient3.setScreen(null);
          apply(false);
        });
    clientGameTestContext.waitTicks(20);
  }

  public static void apply(boolean flag) {
    ProtectionPerformanceTest.ensureInitialized();

    for (Module module2 :
        (java.lang.Iterable<Module>)
            (java.lang.Iterable<?>) (ClientInitializer.getModuleManager().getList())) {
      module2.setEnabled(flag);
    }
  }

  public static void update() {
    ProtectionPerformanceTest.ensureInitialized();

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    apply(moduleManager != null, "module manager built");
    apply(
        moduleManager.getList().size() >= 53,
        "all modules registered (found " + moduleManager.getList().size() + ")");
    apply(ClientInitializer.getThemeManager() != null, "theme manager built");
    apply(ClientInitializer.getClientConfig() != null, "config manager built");
    apply(ClientInitializer.getNotificationsHud() != null, "notifications built");
    apply(ClientInitializer.getSpotifyBridge() != null, "spotify service built");
    apply(
        moduleManager.cosmeticsModule != null
            && "Cosmetics".equals(moduleManager.cosmeticsModule.getName()),
        "Cosmetics module registered");
    apply(
        moduleManager.fakeLootDropModule != null
            && "Fake Loot Drop".equals(moduleManager.fakeLootDropModule.getName()),
        "Fake Loot Drop module registered");
    boolean enabled = moduleManager.fakeLootDropModule.isEnabled();
    List list = (List) moduleManager.fakeLootDropModule.itemReplacementsSetting.getValue();
    moduleManager.fakeLootDropModule.itemReplacementsSetting.resetToDefault();
    ItemStack itemStack = new ItemStack(Items.GRASS_BLOCK, 37);
    moduleManager.fakeLootDropModule.setEnabled(true);
    ItemStack itemStack2 = moduleManager.fakeLootDropModule.getItemStack(itemStack);
    apply(
        itemStack2.isOf(Items.ANCIENT_DEBRIS) && itemStack2.getCount() == 37,
        "Fake Loot Drop switches item and preserves count");
    apply(
        itemStack.isOf(Items.GRASS_BLOCK) && itemStack.getCount() == 37,
        "Fake Loot Drop leaves the real stack untouched");
    moduleManager.fakeLootDropModule.setEnabled(enabled);
    moduleManager.fakeLootDropModule.itemReplacementsSetting.apply(list);
    apply(
        Stream.of(
                moduleManager.cosmeticsModule.wingsSetting,
                moduleManager.cosmeticsModule.crownSetting,
                moduleManager.cosmeticsModule.miniYouSetting)
            .allMatch(Objects::nonNull),
        "Cosmetics exposes its wardrobe switches");

    for (Module module2 :
        (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (moduleManager.getList())) {
      apply(module2.getName() != null && !module2.getName().isBlank(), "module has a name");
      apply(module2.getModuleCategory() != null, module2.getName() + " has a category");
      apply(module2.getKeybindSetting() != null, module2.getName() + " has a keybind");
    }
  }

  public static void update2() {
    ProtectionPerformanceTest.ensureInitialized();

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    List list = JsonSupport2.getList();
    apply(!list.isEmpty(), "categories built");
    ArrayList arrayList = new ArrayList();

    for (CollectionData3 collectionData3 :
        (java.lang.Iterable<CollectionData3>) (java.lang.Iterable<?>) (list)) {
      for (GuiModuleEntry guiModuleEntry :
          (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (collectionData3.list())) {
        if (guiModuleEntry.getModule() != null) {
          arrayList.add(guiModuleEntry);
        }
      }
    }

    apply(
        arrayList.size() == moduleManager.getList().size(),
        "every module reachable from the menu ("
            + arrayList.size()
            + " of "
            + moduleManager.getList().size()
            + ")");

    for (GuiModuleEntry guiModuleEntry2 :
        (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (arrayList)) {
      Module module2 = guiModuleEntry2.getModule();
      boolean enabled = module2.isEnabled();
      guiModuleEntry2.apply(!enabled);
      apply(
          module2.isEnabled() != enabled || test(module2), module2.getName() + " switch is bound");
      guiModuleEntry2.apply(enabled);
      apply(module2.isEnabled() == enabled, module2.getName() + " switch restored");
      int index = 0;

      for (CollectionData collectionData :
          (java.lang.Iterable<CollectionData>)
              (java.lang.Iterable<?>) (guiModuleEntry2.getList())) {
        for (SettingEntry settingEntry :
            (java.lang.Iterable<SettingEntry>) (java.lang.Iterable<?>) (collectionData.list())) {
          apply(
              settingEntry.isBound(),
              module2.getName() + " / " + settingEntry.getName() + " is bound");
          apply(
              SettingWidget.getSettingWidget(settingEntry) != null,
              module2.getName() + " / " + settingEntry.getName() + " has a row");
          index++;
        }
      }

      apply(
          index == module2.getSettings().size(),
          module2.getName()
              + " exposes all "
              + module2.getSettings().size()
              + " settings (found "
              + index
              + ")");
    }
  }

  public static void update3() {
    ProtectionPerformanceTest.ensureInitialized();

    List list3 = JsonSupport2.getList3();
    apply(list3.size() >= 14, "HUD panels built (" + list3.size() + ")");

    for (HudWidget hudWidget : (java.lang.Iterable<HudWidget>) (java.lang.Iterable<?>) (list3)) {
      apply(hudWidget.getString2() != null && !hudWidget.getString2().isBlank(), "panel has an id");
      apply(
          hudWidget.getString22() != null && !hudWidget.getString22().isBlank(),
          hudWidget.getString2() + " has a name");
      if (hudWidget instanceof HudElement hudElement) {
        boolean enabled2 = hudElement.isEnabled2();
        hudElement.setEnabled2(!enabled2);
        apply(hudElement.isEnabled2() != enabled2, hudElement.getString2() + " switch is bound");
        hudElement.setEnabled2(enabled2);
      }
    }

    long longValue =
        list3.stream()
            .map(
                (referenceArgument24_0) ->
                    ((bunnythekenzo.client.hud.HudWidget) referenceArgument24_0).getString2())
            .distinct()
            .count();
    apply(longValue == list3.size(), "HUD panel ids are unique");
  }

  public static void checkEmotes() {
    ProtectionPerformanceTest.ensureInitialized();

    EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
    apply(emoteCatalog != null, "emote manager built");
    apply(
        emoteCatalog.getList().stream()
            .map(
                (referenceArgument25_0) ->
                    ((bunnythekenzo.client.EmoteDefinition) referenceArgument25_0).string())
            .toList()
            .equals(
                List.of(
                    "Take the L",
                    "Hoppin dih",
                    "Wizox ban this guy",
                    "Farex pull my pearl",
                    "Twerking",
                    "Jerking off",
                    "Orange justice",
                    "Passinho do jamal",
                    "Lush life",
                    "Drdonutt wholesome moment")),
        "only the requested ten names are registered, once each");
    apply(
        !((EmoteDefinition) emoteCatalog.getList().get(computeInt("Lush life"))).test(),
        "Lush life has its own playable routine");
    apply(
        !((EmoteDefinition) emoteCatalog.getList().get(computeInt("Hoppin dih"))).test(),
        "Hoppin dih has its own playable routine");
    apply(
        emoteCatalog.getList().stream()
            .allMatch(
                value2 -> {
                  ProtectionPerformanceTest.ensureInitialized();
                  return ((bunnythekenzo.client.EmoteDefinition) (value2)).test()
                      ? ((bunnythekenzo.client.EmoteDefinition) (value2)).soundEvent() == null
                          && ((bunnythekenzo.client.EmoteDefinition) (value2)).timestamp() == 0L
                          && ((bunnythekenzo.client.EmoteDefinition) (value2)).amount() == 0.0F
                      : ((bunnythekenzo.client.EmoteDefinition) (value2)).soundEvent() != null
                          && ((bunnythekenzo.client.EmoteDefinition) (value2)).timestamp() > 0L
                          && ((bunnythekenzo.client.EmoteDefinition) (value2)).amount() > 0.0F;
                }),
        "playable routines have timing and audio, blanks remain empty");
    apply(
        emoteCatalog.getList().stream()
                    .filter(
                        (referenceArgument26_0) ->
                            ((bunnythekenzo.client.EmoteDefinition) referenceArgument26_0).enabled())
                    .count()
                == 2L
            && ((EmoteDefinition) emoteCatalog.getList().get(computeInt("Hoppin dih"))).enabled()
            && ((EmoteDefinition)
                    emoteCatalog.getList().get(computeInt("Drdonutt wholesome moment")))
                .enabled(),
        "Hoppin dih and Neon Shuffle are the mobile emotes");
    EmoteDefinition emoteDefinition =
        (EmoteDefinition) emoteCatalog.getList().get(computeInt("Wizox ban this guy"));
    EmoteDefinition emoteDefinition2 =
        (EmoteDefinition) emoteCatalog.getList().get(computeInt("Farex pull my pearl"));
    EmoteDefinition emoteDefinition3 =
        (EmoteDefinition) emoteCatalog.getList().get(computeInt("Drdonutt wholesome moment"));
    apply(
        emoteDefinition.soundEvent() == ClientSounds.soundEvent30
            && emoteDefinition.emoteAnimation() == EmoteAnimation.WIZOX_BAN
            && emoteDefinition.amount() == 136.6F
            && emoteDefinition.timestamp() == 32000L,
        "Wizox original choreography and retained soundtrack");
    apply(
        emoteDefinition2.soundEvent() == ClientSounds.soundEvent28
            && emoteDefinition2.emoteAnimation() == EmoteAnimation.FAREX_PULL_MY_PEARL
            && emoteDefinition2.amount() == 128.4F
            && emoteDefinition2.timestamp() == 13200L,
        "Farex choreography and existing audio");
    apply(
        emoteDefinition3.soundEvent() == ClientSounds.soundEvent29
            && emoteDefinition3.emoteAnimation() == EmoteAnimation.SHUFFLE
            && emoteDefinition3.amount() == 124.0F
            && emoteDefinition3.timestamp() == 38900L,
        "Drdonutt routine preserved");

    for (SettingEntry settingEntry :
        (java.lang.Iterable<SettingEntry>)
            (java.lang.Iterable<?>) (emoteCatalog.getCollectionData().list())) {
      apply(
          SettingWidget.getSettingWidget(settingEntry) != null,
          "emote setting " + settingEntry.getName() + " has a row");
    }

    int size = emoteCatalog.getList().size();

    for (int index = 0; index < size; index++) {
      double doubleValue = (-Math.PI / 2) + index * Math.PI * 2.0 / size;
      float floatValue = 100.0F + (float) Math.cos(doubleValue) * 70.0F;
      float floatValue2 = 100.0F + (float) Math.sin(doubleValue) * 70.0F;
      apply(
          EmoteCatalog.computeInt(floatValue, floatValue2, 100.0F, 100.0F, 30.0F, 90.0F, size)
              == index,
          "emote wheel slice " + index + " maps correctly");
    }

    apply(
        EmoteCatalog.computeInt(100.0F, 100.0F, 100.0F, 100.0F, 30.0F, 90.0F, size) == -1,
        "emote wheel keeps a dead zone");
  }

  public static void checkPickers() {
    ProtectionPerformanceTest.ensureInitialized();
    int n = 0;
    for (Module module :
        (java.lang.Iterable<Module>)
            (java.lang.Iterable<?>) (ClientInitializer.getModuleManager().getList())) {
      for (Setting setting :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module.getSettings())) {
        GalleryModel blockGalleryModel;
        Iterator iterator;
        Objects.requireNonNull(setting);
        if ((blockGalleryModel =
                (switch (bunnythekenzo.client.runtime.TypeDispatch.select(
                    new Class<?>[] {
                      BlockListSetting.class, EntityTypeSetting.class, ItemSetting.class
                    },
                    (Object) setting,
                    0)) {
                  case 0 -> {
                    BlockListSetting blockListSetting = (BlockListSetting) setting;
                    yield new BlockGalleryModel(
                        blockListSetting, () -> -16711736, blockListSetting.getName());
                  }
                  case 1 -> {
                    EntityTypeSetting entityTypeSetting = (EntityTypeSetting) setting;
                    yield new EntityGalleryModel(entityTypeSetting);
                  }
                  case 2 -> {
                    ItemSetting itemSetting = (ItemSetting) setting;
                    yield new SelectionGallery(itemSetting);
                  }
                  default -> null;
                }))
            == null) continue;
        ClientGameTests.apply(
            blockGalleryModel.getString() != null, module.getName() + " picker has a title");
        ClientGameTests.apply(
            !blockGalleryModel.getList2().isEmpty(),
            module.getName() + " / " + setting.getName() + " picker has cells");
        iterator = blockGalleryModel.getList2().iterator();
        while (iterator.hasNext()) {
          GalleryItem galleryItem = (GalleryItem) iterator.next();
          ClientGameTests.apply(galleryItem.getItemStack() != null, "picker cell has an icon");
          ClientGameTests.apply(galleryItem.getString2() != null, "picker cell has a label");
        }
        GuiDialogManager.open(blockGalleryModel);
        ClientGameTests.apply(GuiDialogManager.hasDialog(), "picker opens");
        GuiDialogManager.closeAll();
        ClientGameTests.apply(!GuiDialogManager.hasDialog(), "picker closes");
        ++n;
      }
    }
    ClientGameTests.apply(n >= 3, "every picker setting checked (" + n + ")");
  }

  public static boolean test(Module module2) {
    ProtectionPerformanceTest.ensureInitialized();

    boolean enabled = module2.isEnabled();
    module2.setEnabled(!enabled);
    boolean flag = module2.isEnabled() == enabled;
    module2.setEnabled(enabled);
    return flag;
  }

  public static void apply2(boolean flag) {
    ProtectionPerformanceTest.ensureInitialized();

    for (HudWidget hudWidget :
        (java.lang.Iterable<HudWidget>) (java.lang.Iterable<?>) (JsonSupport2.getList3())) {
      hudWidget.setEnabled2(flag);
    }
  }

  public static GalleryModel firstBlockPicker() {
    ProtectionPerformanceTest.ensureInitialized();

    for (Module module2 :
        (java.lang.Iterable<Module>)
            (java.lang.Iterable<?>) (ClientInitializer.getModuleManager().getList())) {
      for (Setting setting2 :
          (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (module2.getSettings())) {
        if (setting2 instanceof BlockListSetting blockListSetting) {
          return new BlockGalleryModel(
              blockListSetting,
              () -> {
                return -16711736;
              },
              blockListSetting.getName());
        }
      }
    }

    throw new AssertionError("bunnythekenzo wiring: no block picker in the module graph");
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("bunnythekenzo wiring: " + text);
    }
  }
}
