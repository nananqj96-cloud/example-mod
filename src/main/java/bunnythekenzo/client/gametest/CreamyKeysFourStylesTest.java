package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.input.MouseInput;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.text.Text;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.module.CreamyKeysModule;
import bunnythekenzo.client.setting.ChoiceSettingView;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.ui.DropdownScreen;
import bunnythekenzo.client.ui.PanelMenu;
import bunnythekenzo.client.ui.PanelScreen;

public final class CreamyKeysFourStylesTest {
  public static final String[] stringArray =
      new String[] {"Creamy", "Clacky", "Thocky", "Honey Creamy"};
  public static final String[] stringArray2 =
      new String[] {"creamy", "clacky", "thocky", "honey_creamy"};

  public static void run(ClientGameTestContext clientGameTestContext) {
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    ArrayList arrayList = new ArrayList();
    ProtectionPerformanceTest.ensureInitialized();

    SoundInstanceListener soundInstanceListener =
        (soundInstance, weightedSoundSet, floatValue) -> {
          ProtectionPerformanceTest.ensureInitialized();
          if (soundInstance.getId().toString().startsWith("bunnythekenzo:creamy_keys.")) {
            arrayList.add(soundInstance);
          }
        };

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            minecraftClient2.options.pauseOnLostFocus = false;
            minecraftClient2.onWindowFocusChanged(true);
            minecraftClient2.setScreen(getScreen());
            ClientInitializer.getSoundSettings()
                .masterVolumeSetting
                .setNumber(Double.valueOf(60.0));
            CreamyKeysModule creamyKeysModule = getCreamyKeysModule();
            creamyKeysModule.setEnabled(true);
            creamyKeysModule.keyboardSetting.setValue(true);
            creamyKeysModule.mouseSetting.setValue(true);
            creamyKeysModule.keyboardVolumeSetting.setNumber(Double.valueOf(70.0));
            creamyKeysModule.releaseVolumeSetting.setNumber(Double.valueOf(85.0));
            creamyKeysModule.naturalVariationSetting.setNumber(Double.valueOf(0.0));
            apply(
                creamyKeysModule.soundStyleSetting.getChoices().equals(List.of(stringArray)),
                "four exact choices");
            minecraftClient2.getSoundManager().registerListener(soundInstanceListener);
          });

      for (int index = 0; index < stringArray.length; index++) {
        int index2 = index;

        for (int index3 = 0; index3 < 12; index3++) {
          int intValue = index3;
          clientGameTestContext.runOnClient(
              minecraftClient3 -> {
                minecraftClient3.onWindowFocusChanged(true);
                getCreamyKeysModule().soundStyleSetting.setValue(stringArray[index2]);
                arrayList.clear();
                apply(minecraftClient3, 65 + intValue, 1);
                ProtectionPerformanceTest.ensureInitialized();

                apply(minecraftClient3, 65 + intValue, 2);
                apply(arrayList.size() == 1, "repeat does not play an extra stroke");
                apply(minecraftClient3, 65 + intValue, 0);
                apply(minecraftClient3, 65 + intValue, 0);
                apply(arrayList.size() == 2, "exactly one down and one up, no duplicate release");
                String text = "creamy_keys." + stringArray2[index2] + ".";
                apply(
                    getString((SoundInstance) arrayList.getFirst())
                        .equals(text + String.format("press_%02d", intValue + 1)),
                    "correct press asset");
                apply(
                    getString((SoundInstance) arrayList.getLast())
                        .equals(text + String.format("release_%02d", intValue + 1)),
                    "correct release asset");
                apply(
                    ((SoundInstance) arrayList.getFirst()).getPitch()
                        == ((SoundInstance) arrayList.getLast()).getPitch(),
                    "paired tuning");
                apply(
                    Math.abs(
                            ((SoundInstance) arrayList.getLast()).getVolume()
                                    / ((SoundInstance) arrayList.getFirst()).getVolume()
                                - 0.85F)
                        < 0.001,
                    "release volume respects keyboard and client volume");
              });
          clientGameTestContext.waitTicks(3);
        }
      }

      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient4.onWindowFocusChanged(true);
            CreamyKeysModule creamyKeysModule2 = getCreamyKeysModule();
            arrayList.clear();
            creamyKeysModule2.soundStyleSetting.setValue("Creamy");
            apply(minecraftClient4, 87, 1);
            apply(minecraftClient4, 65, 1);
            apply(minecraftClient4, 32, 1);
            creamyKeysModule2.soundStyleSetting.setValue("Thocky");
            minecraftClient4.setScreen(getScreen());
            apply(minecraftClient4, 65, 0);
            apply(minecraftClient4, 87, 0);
            apply(minecraftClient4, 32, 0);
            apply(arrayList.size() == 6, "overlapping keys survive screen transitions");
            apply(
                arrayList.stream()
                    .allMatch(
                        value4 -> {
                          return getString((net.minecraft.client.sound.SoundInstance) (value4))
                              .startsWith("creamy_keys.creamy.");
                        }),
                "held keys retain their original board");
            arrayList.clear();
            apply(minecraftClient4, 344, 1);
            apply(
                minecraftClient4.currentScreen instanceof PanelScreen
                    || minecraftClient4.currentScreen instanceof DropdownScreen,
                "GUI keybind consumed the press");
            apply(minecraftClient4, 344, 0);
            apply(arrayList.size() == 2, "a consumed GUI bind still sounds on both edges");
            minecraftClient4.setScreen(getScreen());
            arrayList.clear();
            apply(minecraftClient4, 66, 1);
            creamyKeysModule2.setEnabled(false);
            apply(minecraftClient4, 66, 0);
            creamyKeysModule2.setEnabled(true);
            apply(minecraftClient4, 66, 0);
            apply(arrayList.size() == 1, "disable clears held keys and suppresses orphan releases");
            arrayList.clear();
            apply(minecraftClient4, 67, 1);
            minecraftClient4.onWindowFocusChanged(false);
            creamyKeysModule2.onTick();
            apply(minecraftClient4, 67, 0);
            minecraftClient4.onWindowFocusChanged(true);
            apply(minecraftClient4, 67, 0);
            apply(minecraftClient4, 67, 1);
            apply(minecraftClient4, 67, 0);
            apply(arrayList.size() == 3, "focus loss cancels holds and the next press works");
            arrayList.clear();
            creamyKeysModule2.keyboardSetting.setValue(false);
            apply(minecraftClient4, 68, 1);
            apply(minecraftClient4, 68, 0);
            apply(arrayList.isEmpty(), "keyboard switch mutes both edges");
            creamyKeysModule2.keyboardSetting.setValue(true);
            creamyKeysModule2.keyboardVolumeSetting.setNumber(Double.valueOf(0.0));
            apply(minecraftClient4, 68, 1);
            apply(minecraftClient4, 68, 0);
            apply(arrayList.isEmpty(), "keyboard volume zero mutes both edges");
            creamyKeysModule2.keyboardVolumeSetting.setNumber(Double.valueOf(70.0));

            for (int intValue2 : new int[] {0, 1, 2, 3, 4}) {
              arrayList.clear();
              apply2(minecraftClient4, intValue2, 1);
              apply2(minecraftClient4, intValue2, 1);
              apply2(minecraftClient4, intValue2, 0);
              apply2(minecraftClient4, intValue2, 0);
              apply(
                  arrayList.size() == 2
                      && getString((SoundInstance) arrayList.getLast()).endsWith("_release"),
                  "mouse bind down/up, including side buttons");
            }

            creamyKeysModule2.soundStyleSetting.setValue("Honey Creamy");
            creamyKeysModule2.releaseVolumeSetting.setNumber(Double.valueOf(65.0));
            JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
            creamyKeysModule2.soundStyleSetting.setValue("Clacky");
            creamyKeysModule2.releaseVolumeSetting.setNumber(Double.valueOf(0.0));
            ClientInitializer.getClientConfig().apply(jsonObject);
            apply(
                creamyKeysModule2.soundStyleSetting.isSelected("Honey Creamy")
                    && creamyKeysModule2.releaseVolumeSetting.floatValue() == 65.0F,
                "style and release volume survive the real config round-trip");
            creamyKeysModule2.releaseVolumeSetting.setNumber(Double.valueOf(85.0));
            GuiModuleEntry guiModuleEntry =
                (bunnythekenzo.client.GuiModuleEntry)
                    (JsonSupport2.getList().stream()
                        .flatMap(
                            collectionData3 -> {
                              return collectionData3.list().stream();
                            })
                        .filter(
                            guiModuleEntry2 -> {
                              return ((bunnythekenzo.client.GuiModuleEntry) (guiModuleEntry2))
                                      .getModule()
                                  == creamyKeysModule2;
                            })
                        .findFirst()
                        .orElseThrow());
            ChoiceSettingView choiceSettingView =
                (bunnythekenzo.client.setting.ChoiceSettingView)
                    (guiModuleEntry.getList().stream()
                        .flatMap(
                            collectionData -> {
                              return collectionData.list().stream();
                            })
                        .filter(
                            settingEntry -> {
                              return ((bunnythekenzo.client.setting.SettingEntry) (settingEntry))
                                  .getName()
                                  .equals("Sound Style");
                            })
                        .findFirst()
                        .orElseThrow());
            apply(
                choiceSettingView.isBound()
                    && choiceSettingView.getChoices().equals(List.of(stringArray)),
                "both GUIs expose all four live choices");
            choiceSettingView.selectIndex(0);
            apply(
                creamyKeysModule2.soundStyleSetting.isSelected("Creamy"),
                "GUI selection changes the audio module");
            choiceSettingView.selectIndex(3);
          });
      clientGameTestContext.getInput().resizeWindow(1600, 900);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            minecraftClient5.setScreen(new DropdownScreen(new TitleScreen()));
          });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();

            PanelMenu panelMenu = ((DropdownScreen) minecraftClient6.currentScreen).getPanelMenu();

            for (int index4 = 0; index4 < panelMenu.computeInt(); index4++) {
              for (int index5 = 0; index5 < panelMenu.computeInt(index4); index5++) {
                if (panelMenu.getGuiModuleEntry(index4, index5).getModule()
                    == getCreamyKeysModule()) {
                  panelMenu.apply(index4, index5);
                  SettingEntry settingEntry2 =
                      (bunnythekenzo.client.setting.SettingEntry)
                          (panelMenu.moduleSettings(index4, index5).stream()
                              .filter(
                                  value6 -> {
                                    return ((bunnythekenzo.client.setting.SettingEntry) (value6))
                                        .getName()
                                        .equals("Sound Style");
                                  })
                              .findFirst()
                              .orElseThrow());
                  panelMenu.focusSetting(index4, index5, settingEntry2);
                }
              }
            }
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.takeScreenshot("creamy-keys-four-styles");
      ClientInitializer.logger.info(
          "Creamy Keys verified: 96 recorded strokes, raw down/up input, repeats, chords, consumed"
              + " GUI binds, focus, mute, mouse and config persistence");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            minecraftClient7.getSoundManager().unregisterListener(soundInstanceListener);
            getCreamyKeysModule().setEnabled(false);
            minecraftClient7.setScreen(new TitleScreen());
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static CreamyKeysModule getCreamyKeysModule() {
    return ClientInitializer.getModuleManager().creamyKeysModule;
  }

  public static Screen getScreen() {
    return new HoppinStandingBeforeTestSupport2(Text.literal("Creamy Keys input test"));
  }

  public static String getString(SoundInstance soundInstance) {
    return soundInstance.getId().getPath();
  }

  public static void apply(MinecraftClient minecraftClient, int intValue, int intValue2) {
    try {
      Method declaredMethod =
          bunnythekenzo.client.runtime.MappedReflection.method(
              minecraftClient.keyboard.getClass(),
              "onKey",
              "method_1466",
              "(JILnet/minecraft/class_11908;)V",
              long.class,
              int.class,
              KeyInput.class);
      declaredMethod.setAccessible(true);
      declaredMethod.invoke(
          minecraftClient.keyboard,
          minecraftClient.getWindow().getHandle(),
          intValue2,
          new KeyInput(intValue, 0, 0));
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError("Raw keyboard input", failure);
    }
  }

  public static void apply2(MinecraftClient minecraftClient, int intValue, int intValue2) {
    try {
      Method declaredMethod =
          bunnythekenzo.client.runtime.MappedReflection.method(
              minecraftClient.mouse.getClass(),
              "onMouseButton",
              "method_1601",
              "(JLnet/minecraft/class_11910;I)V",
              long.class,
              MouseInput.class,
              int.class);
      declaredMethod.setAccessible(true);
      declaredMethod.invoke(
          minecraftClient.mouse,
          minecraftClient.getWindow().getHandle(),
          new MouseInput(intValue, 0),
          intValue2);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError("Raw mouse input", failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Creamy Keys: " + text);
    }
  }
}
