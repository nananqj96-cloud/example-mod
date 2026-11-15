package bunnythekenzo.client.gametest;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.input.MouseInput;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.ProfileArtworkSupport;
import bunnythekenzo.client.ProfileDefaults;
import bunnythekenzo.client.ProfileStorage;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.MenuRenderContext;
import bunnythekenzo.client.ui.MenuTab;
import bunnythekenzo.client.ui.PanelScreen;
import bunnythekenzo.client.ui.ProfilePage;
import bunnythekenzo.client.ui.WelcomeScreen;

public final class Client67Studio7Test {

  public static void run(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.getInput().resizeWindow(1600, 900);
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.options.pauseOnLostFocus = false;
          update();
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled = false;
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled5 = true;
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled4 = true;
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled3 = false;
          ProfileStorage.getProfileStorage().getProfileDefaults().treeMap.clear();
          GuiPreferences.getGuiPreferences().guiStyleControl.selectIndex(0);
          GuiPreferences.getGuiPreferences().animationsControl.setValue(true);
          GuiPreferences.getGuiPreferences().update();
          minecraftClient.setScreen(new WelcomeScreen(new TitleScreen()));
        });
    clientGameTestContext.waitTicks(35);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();

          if (minecraftClient2.currentScreen instanceof WelcomeScreen welcomeScreen
              && !welcomeScreen.test()) {
            minecraftClient2.currentScreen.keyPressed(new KeyInput(256, 0, 0));
          }
        });
    ProtectionPerformanceTest.ensureInitialized();

    clientGameTestContext.waitTicks(3);
    clientGameTestContext.takeScreenshot("bunnythekenzo-welcome");
    clientGameTestContext.getInput().resizeWindow(854, 480);
    clientGameTestContext.waitTicks(10);
    clientGameTestContext.takeScreenshot("bunnythekenzo-welcome-small");
    clientGameTestContext.getInput().resizeWindow(1600, 900);
    clientGameTestContext.waitTicks(5);
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          apply(
              minecraftClient3.currentScreen instanceof WelcomeScreen, "welcome screen is active");
          ProtectionPerformanceTest.ensureInitialized();

          apply(
              "Welcome to bunnythekenzo".equals(minecraftClient3.currentScreen.getTitle().getString()),
              "welcome uses the bunnythekenzo name");
          minecraftClient3.currentScreen.keyPressed(new KeyInput(268, 0, 0));

          for (int index = 0; index < 30; index++) {
            minecraftClient3.currentScreen.keyPressed(new KeyInput(261, 0, 0));
          }

          minecraftClient3.currentScreen.keyPressed(new KeyInput(257, 0, 0));
          apply(
              minecraftClient3.currentScreen instanceof WelcomeScreen
                  && !ProfileStorage.getProfileStorage().getProfileDefaults().enabled,
              "invalid name cannot complete onboarding");

          for (char character : "Client67Studio".toCharArray()) {
            minecraftClient3.currentScreen.charTyped(new CharInput(character, 0));
          }

          minecraftClient3.currentScreen.keyPressed(new KeyInput(257, 0, 0));
          apply(
              ((WelcomeScreen) minecraftClient3.currentScreen).isEnabled3(),
              "continue begins the saved profile transition");
          apply(
              ProfileStorage.getProfileStorage().getProfileDefaults().enabled,
              "completion is persisted");

          apply(
              "Client67Studio".equals(ProfileStorage.getProfileStorage().getString()),
              "typed username is saved");
          apply(minecraftClient3);
        });
    clientGameTestContext.waitTicks(25);
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          apply(
              minecraftClient4.currentScreen instanceof PanelScreen,
              "continue opens the Profile panel");
        });
    clientGameTestContext.takeScreenshot("bunnythekenzo-profile-menu");
    Runnable onClient =
        clientGameTestContext.computeOnClient(HoppinStandingBeforeTestSupport9::enableReplacement);

    try {
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(HoppinStandingBeforeTestSupport9::apply);
      clientGameTestContext.takeScreenshot("bunnythekenzo-skinprotect-menu");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            onClient.run();
          });
    }

    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          ProtectionPerformanceTest.ensureInitialized();

          long[] longValues = new long[] {35L, 0L, 82L, 46L, 0L, 124L, 58L};

          for (int index2 = 0; index2 < longValues.length; index2++) {
            ProfileStorage.getProfileStorage()
                .getProfileDefaults()
                .treeMap
                .put(LocalDate.now().minusDays(6 - index2).toString(), longValues[index2] * 60000L);
          }

          render(minecraftClient6, 420.0F, 120.0F);

          for (char character2 : "Client67Studio".toCharArray()) {
            minecraftClient6.currentScreen.charTyped(new CharInput(character2, 0));
          }

          render(minecraftClient6, 728.0F, 120.0F);

          apply(
              "Client67Studio".equals(ProfileStorage.getProfileStorage().getString()),
              "projected Save button saves the edited name");
        });
    clientGameTestContext.waitTicks(10);
    clientGameTestContext.takeScreenshot("bunnythekenzo-profile-week");
    checkRotation(clientGameTestContext);
    clientGameTestContext.getInput().resizeWindow(854, 480);
    clientGameTestContext.waitTicks(15);
    clientGameTestContext.takeScreenshot("bunnythekenzo-profile-small");
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              OverlayRenderer.computeFloat4() >= 824.0F
                  && OverlayRenderer.computeFloat5() >= 513.0F,
              "panel fits a small window");
          minecraftClient7.setScreen(new TitleScreen());
        });
    clientGameTestContext.waitTicks(10);
    clientGameTestContext.runOnClient(
        minecraftClient8 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              !(minecraftClient8.currentScreen instanceof WelcomeScreen),
              "completed onboarding does not reappear");
        });
    clientGameTestContext.getInput().resizeWindow(1600, 900);

    try (TestSingleplayerContext testSingleplayerContext =
        clientGameTestContext.worldBuilder().create()) {
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient9.setScreen(panelScreen);
            panelScreen.update2();
          });
      clientGameTestContext.waitTicks(35);
      clientGameTestContext.takeScreenshot("bunnythekenzo-profile-skin");
      clientGameTestContext.runOnClient(HoppinStandingBeforeTestSupport9::apply);
      Runnable onClient2 =
          clientGameTestContext.computeOnClient(
              HoppinStandingBeforeTestSupport9::enableReplacement);

      try {
        clientGameTestContext.waitTicks(10);
        clientGameTestContext.runOnClient(HoppinStandingBeforeTestSupport9::apply);
        clientGameTestContext.takeScreenshot("bunnythekenzo-skinprotect-world");
      } finally {
        clientGameTestContext.runOnClient(
            minecraftClient10 -> {
              onClient2.run();
            });
      }

      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ProfileStorage.getProfileStorage().getTimestamp3() > 0L,
                "live playtime is measured");
            apply(
                ProfileStorage.getProfileStorage().getProfileDefaults().count > 0,
                "joining records a session");
            render(minecraftClient11, 710.0F, 34.0F);
            apply(
                minecraftClient11.currentScreen instanceof WelcomeScreen,
                "Replay welcome button opens the introduction");
          });
      clientGameTestContext.waitTicks(5);
      long onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient12 -> {
                return ProfileStorage.getProfileStorage().getTimestamp3();
              });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ProfileStorage.getProfileStorage().getTimestamp3() == onClient3,
                "paused singleplayer does not count playtime");
            if (!((WelcomeScreen) minecraftClient13.currentScreen).test()) {
              minecraftClient13.currentScreen.keyPressed(new KeyInput(256, 0, 0));
            }

            minecraftClient13.currentScreen.keyPressed(new KeyInput(268, 0, 0));

            for (int index3 = 0; index3 < 30; index3++) {
              minecraftClient13.currentScreen.keyPressed(new KeyInput(261, 0, 0));
            }

            for (char character3 : "Client67Returned".toCharArray()) {
              minecraftClient13.currentScreen.charTyped(new CharInput(character3, 0));
            }

            minecraftClient13.currentScreen.keyPressed(new KeyInput(257, 0, 0));
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            apply(
                minecraftClient14.currentScreen instanceof PanelScreen, "replay returns to panel");
            minecraftClient14.currentScreen.keyPressed(new KeyInput(258, 0, 0));
            minecraftClient14.currentScreen.keyPressed(new KeyInput(257, 0, 0));
            apply(
                "Client67Returned".equals(ProfileStorage.getProfileStorage().getString()),
                "reopened editor reflects updated name");
          });
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            ProfileStorage.getProfileStorage().getProfileDefaults().enabled4 = false;
            apply(ProfileStorage.getProfileStorage().test2(), "profile saved to disk");
            minecraftClient15.setScreen(null);
          });
    }

    ClientInitializer.logger.info(
        "Client67 profile checks passed: onboarding, projected Save/Replay, weekly activity, full"
            + " skin rotation, dragging, reduced motion, resize and persistence");
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    Path path =
        FabricLoader.getInstance()
            .getConfigDir()
            .resolve("bunnythekenzo")
            .resolve("profiles")
            .resolve(minecraftClient.getSession().getUuidOrNull() + ".json");

    try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
      ProfileDefaults profileDefaults = new Gson().fromJson(bufferedReader, ProfileDefaults.class);
      apply(
          profileDefaults.enabled && "Client67Studio".equals(profileDefaults.string2),
          "saved profile reloads from disk");
    } catch (IOException failure) {
      throw new AssertionError("Profile cannot be reloaded", failure);
    }
  }

  public static void render(MinecraftClient minecraftClient, float floatValue, float floatValue2) {
    ProtectionPerformanceTest.ensureInitialized();

    Screen currentScreen = minecraftClient.currentScreen;
    Click click = getClick(minecraftClient, floatValue, floatValue2);
    currentScreen.mouseClicked(click, false);
    if (minecraftClient.currentScreen == currentScreen) {
      currentScreen.mouseReleased(click);
    }
  }

  public static Click getClick(
      MinecraftClient minecraftClient, float floatValue, float floatValue2) {
    float floatValue3 =
        OverlayRenderer.computeFloat() / minecraftClient.getWindow().getScaleFactor();
    ClientData3 clientData3 =
        ((PanelScreen) minecraftClient.currentScreen)
            .getMenuRenderContext()
            .getClientData3(floatValue, 54.0F + floatValue2);
    return new Click(
        clientData3.amount() * floatValue3,
        clientData3.amount2() * floatValue3,
        new MouseInput(0, 0));
  }

  public static ProfileArtworkSupport getProfileArtworkSupport(MinecraftClient minecraftClient) {
    try {
      Field declaredField = MenuRenderContext.class.getDeclaredField("map");
      declaredField.setAccessible(true);
      Object value =
          ((Map)
                  declaredField.get(
                      ((PanelScreen) minecraftClient.currentScreen).getMenuRenderContext()))
              .get(MenuTab.PROFILE);
      Field declaredField2 = ProfilePage.class.getDeclaredField("profileArtworkSupport");
      declaredField2.setAccessible(true);
      return (ProfileArtworkSupport) declaredField2.get(value);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static float computeFloat(MinecraftClient minecraftClient) {
    try {
      Field declaredField = ProfileArtworkSupport.class.getDeclaredField("amount");
      declaredField.setAccessible(true);
      return declaredField.getFloat(getProfileArtworkSupport(minecraftClient));
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void checkRotation(ClientGameTestContext clientGameTestContext) {
    float onClient = clientGameTestContext.computeOnClient(Client67Studio7Test::computeFloat);
    ProtectionPerformanceTest.ensureInitialized();

    float floatValue = 0.0F;

    for (int index = 0; index < 50 && floatValue < Math.PI * 2; index++) {
      clientGameTestContext.waitTicks(10);
      float onClient2 = clientGameTestContext.computeOnClient(Client67Studio7Test::computeFloat);
      float floatValue2 = onClient2 - onClient;
      if (floatValue2 < -Math.PI) {
        floatValue2 += (float) (Math.PI * 2);
      }

      apply(
          floatValue2 > 0.0F && floatValue2 < Math.PI,
          "automatic skin rotation keeps moving in one direction");
      floatValue += floatValue2;
      onClient = onClient2;
    }

    apply(
        floatValue >= Math.PI * 2,
        "skin completes a full automatic revolution with legacy pause selected");
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          float floatValue3 = computeFloat(minecraftClient);
          minecraftClient.currentScreen.mouseClicked(
              getClick(minecraftClient, 140.0F, 220.0F), false);
          ProtectionPerformanceTest.ensureInitialized();

          minecraftClient.currentScreen.mouseDragged(
              getClick(minecraftClient, 205.0F, 220.0F), 65.0, 0.0);
          apply(
              Math.abs(computeFloat(minecraftClient) - floatValue3) > 0.2F,
              "projected drag turns the skin");
        });
    float onClient3 = clientGameTestContext.computeOnClient(Client67Studio7Test::computeFloat);
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              computeFloat(minecraftClient2) == onClient3,
              "holding the skin pauses automatic rotation");
          minecraftClient2.currentScreen.mouseReleased(getClick(minecraftClient2, 205.0F, 220.0F));
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(computeFloat(minecraftClient3) != onClient3, "releasing the skin resumes rotation");
          AnimationPreferences.setEnabled(false);
          getProfileArtworkSupport(minecraftClient3).update2();
          getProfileArtworkSupport(minecraftClient3).apply3((float) Math.PI);
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.takeScreenshot("bunnythekenzo-profile-back");
    float onClient4 = clientGameTestContext.computeOnClient(Client67Studio7Test::computeFloat);
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(computeFloat(minecraftClient4) == onClient4, "reduced motion stops skin rotation");
          minecraftClient4.currentScreen.keyPressed(new KeyInput(262, 0, 0));
          apply(
              computeFloat(minecraftClient4) != onClient4,
              "arrow keys still turn the skin with reduced motion");
          getProfileArtworkSupport(minecraftClient4).update2();
          AnimationPreferences.setEnabled(true);
        });
  }

  public static void update() {
    ProtectionPerformanceTest.ensureInitialized();

    apply(ProfileDefaults.test("Client67_Studio-7"), "allowed username");
    apply(
        !ProfileDefaults.test("ab")
            && !ProfileDefaults.test(" ")
            && !ProfileDefaults.test("a".repeat(25)),
        "invalid username rejected");
    ProfileDefaults profileDefaults = new ProfileDefaults();
    LocalDate localDate = LocalDate.now();
    profileDefaults.apply(60000L, localDate.minusDays(1L));
    profileDefaults.apply(120000L, localDate);
    apply(
        profileDefaults.timestamp == 180000L
            && (Long) profileDefaults.getList(localDate).get(6) == 120000L,
        "daily activity sums correctly");
    apply(
        profileDefaults.getList(localDate).size() == 7
            && (Long) profileDefaults.getList(localDate).get(0) == 0L,
        "empty days stay zero");
    profileDefaults.treeMap.put(localDate.minusDays(35L).toString(), 60000L);
    profileDefaults.normalize();
    apply(profileDefaults.treeMap.size() == 2, "old daily buckets are pruned");

    apply("1h 1m".equals(ProfileDefaults.getString(3660000L)), "duration formatting");
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Profile: " + text);
    }
  }
}
