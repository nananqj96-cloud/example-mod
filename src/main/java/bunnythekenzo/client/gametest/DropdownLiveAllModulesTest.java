package bunnythekenzo.client.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.input.MouseInput;
import net.minecraft.item.Items;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.CollectionData;
import bunnythekenzo.client.CollectionData3;
import bunnythekenzo.client.ConfigSummary;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.ProfileStorage;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.setting.BlockListSettingView;
import bunnythekenzo.client.setting.ChoiceSettingView;
import bunnythekenzo.client.setting.ColorSettingView;
import bunnythekenzo.client.setting.EntityTypeSettingView;
import bunnythekenzo.client.setting.GlintStyleSettingView;
import bunnythekenzo.client.setting.ItemReplacementSettingView;
import bunnythekenzo.client.setting.ItemSetting;
import bunnythekenzo.client.setting.ItemSettingView;
import bunnythekenzo.client.setting.KeybindSettingView;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.NumberSettingView;
import bunnythekenzo.client.setting.Setting;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.StringSettingView;
import bunnythekenzo.client.setting.ToggleSettingView;
import bunnythekenzo.client.ui.DropdownScreen;
import bunnythekenzo.client.ui.MenuTab;
import bunnythekenzo.client.ui.PanelMenu;
import bunnythekenzo.client.ui.PanelScreen;
import bunnythekenzo.client.ui.ScreenSupport4;

public final class DropdownLiveAllModulesTest {
  public static void run(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();

    clientGameTestContext.getInput().resizeWindow(1600, 900);
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.options.pauseOnLostFocus = false;
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled = true;
          PanelMenu.getPanelLayout().update2();
          GuiPreferences.getGuiPreferences().animationsControl.setValue(true);
          GuiPreferences.getGuiPreferences().update();
          minecraftClient.setScreen(ScreenSupport4.getScreen(1, new TitleScreen()));
        });
    clientGameTestContext.waitTicks(30);
    checkMotion(clientGameTestContext);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();

          PanelMenu panelMenu = getPanelMenu(minecraftClient2);
          int index = 0;
          int intValue = 0;
          apply(
              panelMenu.computeInt() == JsonSupport2.getList().size(),
              "all categories are present");

          for (int index2 = 0; index2 < panelMenu.computeInt(); index2++) {
            CollectionData3 collectionData3 = (CollectionData3) JsonSupport2.getList().get(index2);
            apply(
                panelMenu.getCollectionData3(index2) == collectionData3,
                "category identity shared with panel");
            apply(
                panelMenu.computeInt(index2) == collectionData3.list().size(), "complete category");

            for (int index3 = 0; index3 < panelMenu.computeInt(index2); index3++) {
              apply(
                  panelMenu.getGuiModuleEntry(index2, index3) == collectionData3.list().get(index3),
                  "live module identity");
              List list =
                  panelMenu.getGuiModuleEntry(index2, index3).getList().stream()
                      .flatMap(
                          (java.util.function.Function<
                                  bunnythekenzo.client.CollectionData, java.util.stream.Stream<?>>)
                              (collectionData -> {
                                return ((java.util.List) (collectionData.list())).stream();
                              }))
                      .toList();
              apply(
                  panelMenu.moduleSettings(index2, index3).equals(list),
                  "every setting from every group is present");
              if (panelMenu.getGuiModuleEntry(index2, index3).getModule() != null) {
                for (Setting setting :
                    (java.lang.Iterable<Setting>)
                        (java.lang.Iterable<?>)
                            (panelMenu
                                .getGuiModuleEntry(index2, index3)
                                .getModule()
                                .getSettings())) {
                  if (setting instanceof NumberSetting numberSetting) {
                    SettingEntry settingEntry =
                        (bunnythekenzo.client.setting.SettingEntry)
                            (list.stream()
                                .filter(
                                    value3 -> {
                                      return ((bunnythekenzo.client.setting.SettingEntry) (value3))
                                          .getName()
                                          .equals(setting.getName());
                                    })
                                .findFirst()
                                .orElseThrow());
                    apply(
                        settingEntry instanceof NumberSettingView numberSettingView
                            && Math.abs(numberSettingView.getStep() - numberSetting.getStep())
                                < 1.0E-5,
                        "slider exposes exact backing increment: " + setting.getName());
                  }
                }
              }

              index++;
              intValue += list.size();
            }
          }

          apply(index > 70 && intValue > 200, "real registry loaded");
          ClientInitializer.logger.info(
              "Dropdown parity: {} categories, {} modules, {} settings",
              panelMenu.computeInt(),
              index,
              intValue);
        });
    clientGameTestContext.takeScreenshot("dropdown-live-all-modules");
    checkLongList(clientGameTestContext);

    for (Class type :
        new Class[] {
          ToggleSettingView.class,
          NumberSettingView.class,
          Number.class,
          ChoiceSettingView.class,
          StringSettingView.class,
          KeybindSettingView.class,
          ColorSettingView.class,
          BlockListSettingView.class,
          EntityTypeSettingView.class,
          ItemSettingView.class,
          GlintStyleSettingView.class,
          ItemReplacementSettingView.class
        }) {
      checkSetting(clientGameTestContext, type);
    }

    checkWidgets(clientGameTestContext);
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          PanelMenu panelMenu2 = getPanelMenu(minecraftClient3);
          ProtectionPerformanceTest.ensureInitialized();

          int intValue2 = panelMenu2.computeInt() - 1;
          apply(panelMenu2.getCollectionData3(intValue2).enabled(), "special category present");
          apply(minecraftClient3, panelMenu2.getClientData3(intValue2, 70.0F, 28.0F));
          apply(
              panelMenu2.test2() && !panelMenu2.test(intValue2),
              "existing confirmation gate preserved");
          apply(minecraftClient3, 256);
          apply(!panelMenu2.test2() && !panelMenu2.isEnabled2(), "gate cancel stays in dropdown");
          apply(minecraftClient3, panelMenu2.getClientData3(intValue2, 70.0F, 28.0F));
          apply(minecraftClient3, 257);
          apply(!panelMenu2.test2() && panelMenu2.test(intValue2), "confirmed category accessible");
          apply(minecraftClient3, panelMenu2.getClientData3(0, 70.0F, 28.0F));
          apply(!panelMenu2.test(0), "header folds");
        });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.takeScreenshot("dropdown-live-special-category");
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          ProtectionPerformanceTest.ensureInitialized();

          PanelMenu panelMenu3 = getPanelMenu(minecraftClient4);
          apply(minecraftClient4, panelMenu3.getClientData3(0, 70.0F, 28.0F));
          ClientData3 clientData3 = panelMenu3.getClientData3(2, 85.0F, 28.0F);
          minecraftClient4.currentScreen.mouseClicked(getClick(clientData3), false);
          ClientData3 clientData32 =
              new ClientData3(clientData3.amount() + 65.0F, clientData3.amount2() + 12.0F);
          minecraftClient4.currentScreen.mouseDragged(getClick(clientData32), 65.0, 12.0);
          minecraftClient4.currentScreen.mouseReleased(getClick(clientData32));
          ClientData3 clientData33 = panelMenu3.getClientData3(2, 85.0F, 28.0F);
          apply(
              Math.abs(clientData33.amount() - clientData32.amount()) < 0.01F
                  && Math.abs(clientData33.amount2() - clientData32.amount2()) < 0.01F,
              "compact panel follows the dragged cursor");
          apply(panelMenu3.test(2), "drag preserves expansion");
          panelMenu3.resetLayout();
          apply(minecraftClient4, panelMenu3.getClientData3(1));
          apply(minecraftClient4, 256);
          apply(!panelMenu3.isEnabled2(), "Escape dismisses workspace menu first");
          apply(minecraftClient4, panelMenu3.getClientData3(0));
        });
    clientGameTestContext.waitTicks(25);
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          apply(
              minecraftClient5.currentScreen instanceof PanelScreen,
              "style switch reaches panel GUI");
          update();
          minecraftClient5.setScreen(ScreenSupport4.getScreen(1, new TitleScreen()));
        });
    clientGameTestContext.waitTicks(25);
    clientGameTestContext.getInput().resizeWindow(854, 480);
    clientGameTestContext.waitTicks(12);
    checkSetting(clientGameTestContext, NumberSettingView.class);
    checkSetting(clientGameTestContext, ColorSettingView.class);
    clientGameTestContext.takeScreenshot("dropdown-live-small-window");
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          PanelMenu panelMenu4 = getPanelMenu(minecraftClient6);
          ProtectionPerformanceTest.ensureInitialized();

          for (int index4 = 0; index4 < panelMenu4.computeInt(); index4++) {
            ClientData3 clientData34 = panelMenu4.getClientData3(index4, 80.0F, 28.0F);
            apply(
                clientData34.amount() >= 0.0F
                    && clientData34.amount() < OverlayRenderer.computeFloat4()
                    && clientData34.amount2() >= 0.0F
                    && clientData34.amount2() < OverlayRenderer.computeFloat5(),
                "every category reachable at 854x480");
          }

          apply(minecraftClient6, 344);
        });
    clientGameTestContext.waitTicks(25);
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          ProtectionPerformanceTest.ensureInitialized();

          apply(minecraftClient7.currentScreen instanceof TitleScreen, "close returns to parent");
          apply(!GuiDialogManager.hasDialog(), "close releases picker");
          GuiPreferences.getGuiPreferences().animationsControl.setValue(false);
          GuiPreferences.getGuiPreferences().update();
          minecraftClient7.setScreen(new DropdownScreen(new TitleScreen()));
        });
    clientGameTestContext.waitTicks(5);
    checkSetting(clientGameTestContext, NumberSettingView.class);
    clientGameTestContext.runOnClient(
        minecraftClient8 -> {
          apply(minecraftClient8, 256);
          GuiPreferences.getGuiPreferences().animationsControl.setValue(true);
          GuiPreferences.getGuiPreferences().update();
        });
    clientGameTestContext.waitTicks(20);
  }

  public static void checkMotion(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          ProtectionPerformanceTest.ensureInitialized();

          minecraftClient.setScreen(new DropdownScreen(new TitleScreen()));
          apply(
              getPanelMenu(minecraftClient).computeFloat() == 0.0F,
              "opening waits for the first rendered frame");
        });
    clientGameTestContext.waitTicks(2);
    clientGameTestContext.takeScreenshot("dropdown-motion-opening");
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();

          PanelMenu panelMenu = getPanelMenu(minecraftClient2);
          apply(panelMenu.computeFloat() == 1.0F, "entrance settles fully");
          boolean flag = panelMenu.test(0);
          minecraftClient2.currentScreen.mouseClicked(
              getClick(panelMenu.getClientData3(0, 80.0F, 18.0F)), false);
          minecraftClient2.currentScreen.close();
          apply(
              panelMenu.isEnabled2() && !panelMenu.test(),
              "closing keeps the screen alive for its departure");
          apply(panelMenu.test(0) == flag, "closing a held header does not toggle its saved state");
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.takeScreenshot("dropdown-motion-closing");
    clientGameTestContext.waitTicks(10);
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          apply(
              minecraftClient3.currentScreen instanceof TitleScreen,
              "animated exit returns to the parent");
          minecraftClient3.setScreen(new DropdownScreen(new TitleScreen()));
        });
    clientGameTestContext.waitTicks(1);
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          PanelMenu panelMenu2 = getPanelMenu(minecraftClient4);
          ProtectionPerformanceTest.ensureInitialized();

          boolean flag2 = panelMenu2.test(0);
          apply(minecraftClient4, panelMenu2.getClientData3(0, 80.0F, 18.0F));
          apply(
              panelMenu2.test(0) != flag2,
              "moving category receives input at its rendered position");
          ClientData3 clientData3 = panelMenu2.getClientData3(0, 80.0F, 18.0F);
          ClientData3 clientData32 =
              new ClientData3(clientData3.amount() + 40.0F, clientData3.amount2() + 18.0F);
          minecraftClient4.currentScreen.mouseClicked(getClick(clientData3), false);
          minecraftClient4.currentScreen.mouseDragged(getClick(clientData32), 40.0, 18.0);
          minecraftClient4.currentScreen.mouseReleased(getClick(clientData32));
          ClientData3 clientData33 = panelMenu2.getClientData3(0, 80.0F, 18.0F);
          apply(
              Math.abs(clientData33.amount() - clientData32.amount()) < 0.01F
                  && Math.abs(clientData33.amount2() - clientData32.amount2()) < 0.01F,
              "drag tracks the cursor during the scaled entrance");
          float value4 = panelMenu2.computeFloat();
          panelMenu2.update();
          panelMenu2.update();
          apply(panelMenu2.computeFloat() == value4, "interrupted entrance does not jump on close");
        });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          apply(minecraftClient5.currentScreen instanceof TitleScreen, "rapid open-close finishes");
          DropdownScreen dropdownScreen = new DropdownScreen(new TitleScreen());
          minecraftClient5.setScreen(dropdownScreen);
          dropdownScreen.close();
          apply(
              dropdownScreen.getPanelMenu().test(), "close before rendering finishes immediately");
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          apply(
              minecraftClient6.currentScreen instanceof TitleScreen,
              "unrendered close returns to parent");
          minecraftClient6.setScreen(new DropdownScreen(new TitleScreen()));
        });
    clientGameTestContext.waitTicks(2);
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          getPanelMenu(minecraftClient7).resetLayout();
        });
    clientGameTestContext.waitTicks(16);
  }

  public static void checkLongList(ClientGameTestContext clientGameTestContext) {
    int[] onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              ProtectionPerformanceTest.ensureInitialized();

              PanelMenu panelMenu = getPanelMenu(minecraftClient);
              int intValue = 0;

              for (int index = 1; index < panelMenu.computeInt(); index++) {
                if (!panelMenu.getCollectionData3(index).enabled()
                    && panelMenu.computeInt(index) > panelMenu.computeInt(intValue)) {
                  intValue = index;
                }
              }

              apply(panelMenu.computeInt(intValue) > 12, "long category available");
              render(minecraftClient, panelMenu.getClientData3(intValue, 90.0F, 220.0F), -1000.0);
              return new int[] {intValue, panelMenu.computeInt(intValue) - 1};
            });
    clientGameTestContext.waitTicks(14);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();

          PanelMenu panelMenu2 = getPanelMenu(minecraftClient2);
          apply(
              panelMenu2.computeFloat2(onClient[0]) > 0.0F,
              "scroll reaches the end of a long category");
          apply(
              minecraftClient2, panelMenu2.getClientData3(onClient[0], onClient[1], 184.0F, 15.0F));
          apply(
              panelMenu2.test(onClient[0], onClient[1]), "last module opens with projected input");
        });
    clientGameTestContext.waitTicks(14);
    clientGameTestContext.takeScreenshot("dropdown-live-last-module");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          ProtectionPerformanceTest.ensureInitialized();

          PanelMenu panelMenu3 = getPanelMenu(minecraftClient3);
          apply(
              minecraftClient3, panelMenu3.getClientData3(onClient[0], onClient[1], 120.0F, 46.0F));
          apply(minecraftClient3, 75);
          apply(
              panelMenu3.getGuiModuleEntry(onClient[0], onClient[1]).computeInt() == 75,
              "module bind edits live value");
          apply(
              minecraftClient3, panelMenu3.getClientData3(onClient[0], onClient[1], 120.0F, 46.0F));
          apply(minecraftClient3, 261);
          apply(
              panelMenu3.getGuiModuleEntry(onClient[0], onClient[1]).computeInt() == -1,
              "module bind clears");
          panelMenu3.getGuiModuleEntry(onClient[0], onClient[1]).apply(false);
          apply(
              minecraftClient3, panelMenu3.getClientData3(onClient[0], onClient[1], 40.0F, 20.0F));
          apply(
              panelMenu3.getGuiModuleEntry(onClient[0], onClient[1]).test(),
              "toggle changes live backing");
          panelMenu3.getGuiModuleEntry(onClient[0], onClient[1]).apply(false);
        });
  }

  public static HoppinStandingBeforeTestData2 getHoppinStandingBeforeTestData2(
      PanelMenu panelMenu, Class type) {
    ProtectionPerformanceTest.ensureInitialized();

    for (int index = 0; index < panelMenu.computeInt(); index++) {
      if (!panelMenu.getCollectionData3(index).enabled()) {
        for (int index2 = 0; index2 < panelMenu.computeInt(index); index2++) {
          for (SettingEntry _c :
              (java.lang.Iterable<SettingEntry>)
                  (java.lang.Iterable<?>) (panelMenu.moduleSettings(index, index2))) {
            if ((type.isInstance(_c)
                    || type == Number.class
                        && _c instanceof NumberSettingView numberSettingView
                        && numberSettingView.getStep() < 1.0F)
                && _c.isVisible()) {
              return new HoppinStandingBeforeTestData2(index, index2, _c);
            }
          }
        }
      }
    }

    throw new AssertionError("No relevant setting of type " + type.getSimpleName());
  }

  public static void checkSetting(ClientGameTestContext clientGameTestContext, Class type) {
    List onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return JsonSupport2.getList();
            });
    HoppinStandingBeforeTestData2 onClient2 =
        clientGameTestContext.computeOnClient(
            minecraftClient2 -> {
              ProtectionPerformanceTest.ensureInitialized();
              if (type == ItemSettingView.class) {
                ArrayList arrayList = new ArrayList(onClient);
                CollectionData3 collectionData3 = (CollectionData3) arrayList.getFirst();
                ArrayList arrayList2 = new ArrayList(collectionData3.list());
                arrayList2.add(
                    new GuiModuleEntry(
                        "Item picker test",
                        "Test fixture",
                        new CollectionData[] {
                          new CollectionData(
                              "General",
                              new SettingEntry[] {
                                new ItemSettingView(
                                    new ItemSetting("Item", "Choose an item", Items.DIAMOND))
                              })
                        }));
                arrayList.set(
                    0,
                    new CollectionData3(
                        collectionData3.string(), collectionData3.string2(), arrayList2));
                JsonSupport2.setList(arrayList);
              }

              minecraftClient2.setScreen(new DropdownScreen(new TitleScreen()));
              PanelMenu panelMenu = getPanelMenu(minecraftClient2);
              HoppinStandingBeforeTestData2 hoppinStandingBeforeTestData2 =
                  getHoppinStandingBeforeTestData2(panelMenu, type);
              panelMenu.apply(
                  hoppinStandingBeforeTestData2.count(), hoppinStandingBeforeTestData2.count2());
              return hoppinStandingBeforeTestData2;
            });
    clientGameTestContext.waitTicks(26);
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          getPanelMenu(minecraftClient3)
              .focusSetting(onClient2.count(), onClient2.count2(), onClient2._c());
        });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          ProtectionPerformanceTest.ensureInitialized();

          PanelMenu panelMenu2 = getPanelMenu(minecraftClient4);
          SettingEntry settingEntry = onClient2._c();
          float floatValue = 0.0F;
          float floatValue2 = 99.0F;
          if (settingEntry instanceof NumberSettingView numberSettingView) {
            floatValue =
                Math.clamp(
                    Math.round(
                            (numberSettingView.getMinimum() + numberSettingView.getMaximum())
                                / 2.0F
                                / numberSettingView.getStep())
                        * numberSettingView.getStep(),
                    numberSettingView.getMinimum(),
                    numberSettingView.getMaximum());
            floatValue2 =
                16.0F
                    + 166.0F
                        * (floatValue - numberSettingView.getMinimum())
                        / (numberSettingView.getMaximum() - numberSettingView.getMinimum());
          }

          ClientData3 clientData3 =
              panelMenu2.settingPoint(
                  onClient2.count(),
                  onClient2.count2(),
                  settingEntry,
                  floatValue2,
                  settingEntry instanceof ToggleSettingView
                      ? 22.0F
                      : (settingEntry instanceof NumberSettingView ? 64.0F : 51.0F));
          boolean flag =
              settingEntry instanceof ToggleSettingView toggleSettingView
                  && toggleSettingView.getValue();
          int intValue =
              settingEntry instanceof ChoiceSettingView choiceSettingView
                  ? choiceSettingView.getSelectedIndex()
                  : -1;
          apply(minecraftClient4, clientData3);
          if (settingEntry instanceof ToggleSettingView toggleSettingView2) {
            apply(toggleSettingView2.getValue() != flag, "toggle setting writes through");
          }

          if (settingEntry instanceof NumberSettingView numberSettingView2) {
            float value2 = numberSettingView2.getValue();
            apply(Math.abs(value2 - floatValue) < 0.001, "projected slider snaps to actual step");
            apply(minecraftClient4, 263);
            apply(
                Math.abs(
                        numberSettingView2.getValue()
                            - Math.max(
                                numberSettingView2.getMinimum(),
                                value2 - numberSettingView2.getStep()))
                    < 0.001,
                "keyboard uses actual slider step");
            minecraftClient4.currentScreen.mouseClicked(getClick(clientData3), false);
            ClientData3 clientData32 =
                panelMenu2.settingPoint(
                    onClient2.count(), onClient2.count2(), settingEntry, 900.0F, 64.0F);
            minecraftClient4.currentScreen.mouseDragged(getClick(clientData32), 1000.0, 0.0);
            minecraftClient4.currentScreen.mouseReleased(getClick(clientData32));
            apply(
                numberSettingView2.getValue() == numberSettingView2.getMaximum(),
                "drag clamps outside panel");
          }

          if (settingEntry instanceof ChoiceSettingView choiceSettingView2) {
            apply(
                choiceSettingView2.getSelectedIndex()
                    == (intValue + 1) % choiceSettingView2.getChoices().size(),
                "mode cycles actual options");
          }

          if (settingEntry instanceof KeybindSettingView toggleSettingViewSupport) {
            apply(minecraftClient4, 344);
            apply(
                toggleSettingViewSupport.computeInt() == 344 && !panelMenu2.isEnabled2(),
                "binding captures menu shortcut");
            apply(minecraftClient4, clientData3);
            apply(minecraftClient4, 256);
            apply(
                toggleSettingViewSupport.computeInt() == -1 && !panelMenu2.isEnabled2(),
                "setting bind clears without closing");
            apply(minecraftClient4, clientData3);
            Click click = getClick(clientData3);
            minecraftClient4.currentScreen.mouseClicked(
                new Click(click.x(), click.y(), new MouseInput(1, 0)), false);
            apply(
                toggleSettingViewSupport.computeInt() == 1,
                "setting bind supports mouse buttons like panel GUI");
            apply(minecraftClient4, clientData3);
            apply(minecraftClient4, 256);
          }

          if (settingEntry instanceof StringSettingView toggleSettingViewSupport4) {
            apply(minecraftClient4, 261);
            minecraftClient4.currentScreen.charTyped(new CharInput(81, 0));
            minecraftClient4.currentScreen.charTyped(new CharInput(128640, 0));
            apply(
                toggleSettingViewSupport4.getValue().startsWith("Q"), "screen forwards characters");
            apply(minecraftClient4, 259);
            apply(toggleSettingViewSupport4.getValue().equals("Q"), "Unicode backspace");
            apply(minecraftClient4, 256);
            apply(!panelMenu2.isEnabled2(), "Escape ends text editing first");
          }

          if (settingEntry instanceof ColorSettingView) {
            apply(panelMenu2.test3(), "live color opens shared picker");
          }

          if (settingEntry instanceof BlockListSettingView
              || settingEntry instanceof EntityTypeSettingView
              || settingEntry instanceof ItemSettingView
              || settingEntry instanceof GlintStyleSettingView
              || settingEntry instanceof ItemReplacementSettingView) {
            apply(GuiDialogManager.hasDialog(), "complex setting opens panel picker");
          }
        });
    clientGameTestContext.waitTicks(10);
    clientGameTestContext.takeScreenshot("dropdown-live-" + type.getSimpleName());
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          ProtectionPerformanceTest.ensureInitialized();

          PanelMenu panelMenu3 = getPanelMenu(minecraftClient5);
          if (onClient2._c() instanceof ColorSettingView toggleSettingViewSupport2) {
            apply(minecraftClient5, panelMenu3.getClientData32(157.0F, 213.0F));
            apply(toggleSettingViewSupport2.computeInt() == -11744784, "preset updates live color");
            apply(minecraftClient5, 256);
            apply(!panelMenu3.isEnabled2(), "Escape closes color picker first");
          } else if (GuiDialogManager.hasDialog()) {
            apply(minecraftClient5, 256);
            apply(!panelMenu3.isEnabled2(), "Escape closes complex picker first");
          }
        });
    clientGameTestContext.waitTicks(10);
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          JsonSupport2.setList(onClient);
        });
  }

  public static void checkWidgets(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.setScreen(new DropdownScreen(new TitleScreen()));
          getPanelMenu(minecraftClient).resetLayout();
        });
    ProtectionPerformanceTest.ensureInitialized();

    clientGameTestContext.waitTicks(24);

    for (MenuTab menuTab :
        new MenuTab[] {
          MenuTab.CONFIGS, MenuTab.PROFILE, MenuTab.HUD_EDITOR, MenuTab.EMOTES, MenuTab.SETTINGS
        }) {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            apply(minecraftClient2, getPanelMenu(minecraftClient2).getClientData3(menuTab));
            apply(
                getPanelMenu(minecraftClient2).getMenuTab() == menuTab,
                "bottom widget opens " + menuTab);
          });
      clientGameTestContext.waitTicks(22);
      clientGameTestContext.takeScreenshot(
          "dropdown-classic-widget-" + menuTab.name().toLowerCase(Locale.ROOT));
      if (menuTab == MenuTab.PROFILE) {
        clientGameTestContext.runOnClient(
            minecraftClient3 -> {
              ProtectionPerformanceTest.ensureInitialized();

              PanelMenu panelMenu = getPanelMenu(minecraftClient3);
              apply(minecraftClient3, panelMenu.getClientData3(420.0F, 158.0F));

              for (int intValue : "DropdownStudio".codePoints().toArray()) {
                minecraftClient3.currentScreen.charTyped(new CharInput(intValue, 0));
              }

              apply(minecraftClient3, panelMenu.getClientData3(728.0F, 158.0F));

              apply(
                  "DropdownStudio".equals(ProfileStorage.getProfileStorage().getString()),
                  "profile widget saves the shared profile");
            });
      }

      if (menuTab == MenuTab.CONFIGS) {
        clientGameTestContext.runOnClient(
            minecraftClient4 -> {
              JsonSupport2.getList4().clear();
              ClientInitializer.getModuleManager().customFOVModule.setEnabled(true);
            });
        clientGameTestContext.waitTicks(5);
        clientGameTestContext.runOnClient(
            minecraftClient5 -> {
              ProtectionPerformanceTest.ensureInitialized();

              apply(
                  minecraftClient5, getPanelMenu(minecraftClient5).getClientData3(300.0F, 320.0F));
              apply(JsonSupport2.getList4().size() == 1, "config widget creates a saved setup");
              ConfigSummary configSummary = (ConfigSummary) JsonSupport2.getList4().getFirst();
              apply(
                  configSummary.isEnabled() && configSummary.getJsonObject().has("client"),
                  "saved setup includes live modules");
              ClientInitializer.getModuleManager().customFOVModule.setEnabled(false);
            });
        clientGameTestContext.waitTicks(12);
        clientGameTestContext.runOnClient(
            minecraftClient6 -> {
              apply(
                  minecraftClient6, getPanelMenu(minecraftClient6).getClientData3(440.0F, 151.0F));
              apply(
                  ClientInitializer.getModuleManager().customFOVModule.isEnabled(),
                  "config widget restores ordinary modules");
              ConfigSummary configSummary2 = (ConfigSummary) JsonSupport2.getList4().getFirst();
              ProtectionPerformanceTest.ensureInitialized();

              SettingEntry settingEntry =
                  getHoppinStandingBeforeTestData2(getPanelMenu(minecraftClient6), Number.class)
                      ._c();
              NumberSettingView numberSettingView = (NumberSettingView) settingEntry;
              numberSettingView.setValue(numberSettingView.getMinimum());
              GuiPreferences.getGuiPreferences().masterVolumeControl.setValue(35.0F);
              configSummary2.update();
              numberSettingView.setValue(numberSettingView.getMaximum());
              GuiPreferences.getGuiPreferences().masterVolumeControl.setValue(80.0F);
              configSummary2.update2();
              apply(
                  numberSettingView.getValue() == numberSettingView.getMinimum()
                      && GuiPreferences.getGuiPreferences().masterVolumeControl.getValue() == 35.0F,
                  "config round-trip preserves module settings and client preferences");
              ConfigSummary configSummary3 =
                  new ConfigSummary("Legacy", "", true, JsonSupport2.getJsonObject());
              configSummary3.update2();
            });
      }
    }

    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          ProtectionPerformanceTest.ensureInitialized();

          apply(minecraftClient7, 256);
          apply(
              getPanelMenu(minecraftClient7).getMenuTab() == null
                  && !getPanelMenu(minecraftClient7).isEnabled2(),
              "Escape dismisses widget before dropdown");
        });
    clientGameTestContext.getInput().resizeWindow(854, 480);
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.runOnClient(
        minecraftClient8 -> {
          apply(minecraftClient8, getPanelMenu(minecraftClient8).getClientData3(MenuTab.PROFILE));
        });
    clientGameTestContext.waitTicks(20);
    clientGameTestContext.takeScreenshot("dropdown-classic-profile-small");
    clientGameTestContext.runOnClient(
        minecraftClient9 -> {
          apply(minecraftClient9, 256);
        });
    clientGameTestContext.getInput().resizeWindow(1600, 900);
    clientGameTestContext.waitTicks(12);
  }

  public static void update() {
    Path configDir = FabricLoader.getInstance().getConfigDir();

    try {
      JsonObject asJsonObject =
          JsonParser.parseString(Files.readString(configDir.resolve("bunnythekenzo.json")))
              .getAsJsonObject();
      apply(
          asJsonObject
              .getAsJsonObject("modules")
              .equals(
                  ClientInitializer.getClientConfig().getJsonObject().getAsJsonObject("modules")),
          "closing saves all live module settings and binds");
      JsonObject asJsonObject2 =
          JsonParser.parseString(
                  Files.readString(configDir.resolve("bunnythekenzo").resolve("modules.json")))
              .getAsJsonObject();
      JsonElement jsonElement = JsonParser.parseString(JsonSupport2.getJsonObject().toString());
      apply(asJsonObject2.equals(jsonElement), "closing saves GUI-managed modules and settings");
    } catch (IOException failure) {
      throw new AssertionError("Cannot read saved dropdown configuration", failure);
    }
  }

  public static PanelMenu getPanelMenu(MinecraftClient minecraftClient) {
    apply(minecraftClient.currentScreen instanceof DropdownScreen, "dropdown active");
    return ((DropdownScreen) minecraftClient.currentScreen).getPanelMenu();
  }

  public static void apply(MinecraftClient minecraftClient, int intValue) {
    minecraftClient.currentScreen.keyPressed(new KeyInput(intValue, 0, 0));
  }

  public static Click getClick(ClientData3 clientData3) {
    float floatValue =
        OverlayRenderer.computeFloat() / MinecraftClient.getInstance().getWindow().getScaleFactor();
    float floatValue2 =
        OverlayRenderer.computeFloat4() / 2.0F * 0.19999999F + clientData3.amount() * 0.8F;
    float floatValue3 =
        OverlayRenderer.computeFloat5() / 2.0F * 0.19999999F + clientData3.amount2() * 0.8F;
    return new Click(floatValue2 * floatValue, floatValue3 * floatValue, new MouseInput(0, 0));
  }

  public static void apply(MinecraftClient minecraftClient, ClientData3 clientData3) {
    minecraftClient.currentScreen.mouseClicked(getClick(clientData3), false);
    minecraftClient.currentScreen.mouseReleased(getClick(clientData3));
  }

  public static void render(
      MinecraftClient minecraftClient, ClientData3 clientData3, double doubleValue) {
    Click click = getClick(clientData3);
    minecraftClient.currentScreen.mouseScrolled(click.x(), click.y(), 0.0, doubleValue);
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Dropdown: " + text);
    }
  }
}
