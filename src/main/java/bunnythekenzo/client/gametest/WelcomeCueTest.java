package bunnythekenzo.client.gametest;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.input.MouseInput;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.ProfileDefaults;
import bunnythekenzo.client.ProfileStorage;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.DropdownScreen;
import bunnythekenzo.client.ui.PanelScreen;
import bunnythekenzo.client.ui.WelcomeScreen;

public final class WelcomeCueTest {

  public static void run(ClientGameTestContext clientGameTestContext) {
    AtomicLong atomicLong = new AtomicLong(1000000000L);
    ProtectionPerformanceTest.ensureInitialized();

    clientGameTestContext.getInput().resizeWindow(1600, 900);
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.options.pauseOnLostFocus = false;
          GuiPreferences.getGuiPreferences().animationsControl.setValue(true);
          GuiPreferences.getGuiPreferences().update();
          GuiPreferences.getGuiPreferences().guiStyleControl.selectIndex(0);
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled = false;
          ProfileStorage.getProfileStorage().getProfileDefaults().string2 = "";
          apply(
              "your name".equals(ProfileStorage.getProfileStorage().getString()),
              "unset display name never uses the account IGN");
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled2 = true;
          minecraftClient.setScreen(new WelcomeScreen(new TitleScreen(), atomicLong::get));
        });
    clientGameTestContext.getInput().setCursorPos(20.0, 20.0);
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              !getWelcomeScreen(minecraftClient2).test(),
              "introduction waits on its presentation clock");
          minecraftClient2.currentScreen.charTyped(new CharInput(90, 0));
          render(minecraftClient2, 600.0F, 445.0F);
          apply(
              !getWelcomeScreen(minecraftClient2).isEnabled3()
                  && !ProfileStorage.getProfileStorage().getProfileDefaults().enabled,
              "hidden controls cannot finish onboarding");
        });
    if (Boolean.getBoolean("bunnythekenzo.test.welcome.capture")) {
      for (int index = 0; index <= 240; index++) {
        atomicLong.set(1000000000L + Math.round(index / 30.0 * 1.0E9));
        clientGameTestContext.waitTicks(1);
        clientGameTestContext.takeScreenshot(String.format("welcome-film-%04d", index));
      }
    } else {
      float[] floatValues = new float[] {0.16F, 0.68F, 1.56F, 2.6F, 4.1F, 4.8F, 5.5F, 6.35F, 7.6F};

      for (int index2 = 0; index2 < floatValues.length; index2++) {
        render(clientGameTestContext, atomicLong, floatValues[index2], "welcome-cue-" + index2);
      }
    }

    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          apply(getWelcomeScreen(minecraftClient3).test(), "presentation reaches its form");
        });
    clientGameTestContext.getInput().resizeWindow(854, 480);
    clientGameTestContext.waitTicks(5);
    clientGameTestContext.takeScreenshot("welcome-small");
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          apply(
              getWelcomeScreen(minecraftClient4).test(),
              "resizing never restarts the introduction");
        });
    clientGameTestContext.getInput().resizeWindow(1600, 900);
    clientGameTestContext.waitTicks(5);
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          ProtectionPerformanceTest.ensureInitialized();

          apply(minecraftClient5, 257);
          apply(
              !getWelcomeScreen(minecraftClient5).isEnabled3()
                  && !ProfileStorage.getProfileStorage().getProfileDefaults().enabled,
              "invalid names keep the form open");

          for (char character : "Studio67".toCharArray()) {
            minecraftClient5.currentScreen.charTyped(new CharInput(character, 0));
          }

          apply(minecraftClient5, 257);
          apply(
              getWelcomeScreen(minecraftClient5).isEnabled3(),
              "successful save begins the exit transition");

          apply(
              "Studio67".equals(ProfileStorage.getProfileStorage().getString()),
              "name is saved before exit");
          apply(minecraftClient5, "Studio67");
          apply(minecraftClient5, 257);
        });
    atomicLong.addAndGet(250000000L);
    clientGameTestContext.waitTicks(2);
    clientGameTestContext.takeScreenshot("welcome-exit");
    atomicLong.addAndGet(400000000L);
    clientGameTestContext.waitTicks(22);
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          apply(
              minecraftClient6.currentScreen instanceof PanelScreen,
              "exit completes into the selected panel");
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("welcome-profile-arrival");
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled = false;
          minecraftClient7.setScreen(new WelcomeScreen(minecraftClient7.currentScreen));
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.runOnClient(
        minecraftClient8 -> {
          ProtectionPerformanceTest.ensureInitialized();

          apply(minecraftClient8, 77);
          apply(
              !ProfileStorage.getProfileStorage().getProfileDefaults().enabled2,
              "intro can be muted immediately");
          apply(minecraftClient8, 256);
          apply(
              getWelcomeScreen(minecraftClient8).test()
                  && !ProfileStorage.getProfileStorage().getProfileDefaults().enabled,
              "Escape skips the film without completing the profile");
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.takeScreenshot("welcome-skipped");
    clientGameTestContext.runOnClient(
        minecraftClient9 -> {
          minecraftClient9.currentScreen.keyPressed(new KeyInput(258, 0, 1));
          apply(minecraftClient9, 257);
          apply(minecraftClient9, 258);
          apply(minecraftClient9, 257);
        });
    clientGameTestContext.waitTicks(20);
    clientGameTestContext.runOnClient(
        minecraftClient10 -> {
          apply(
              minecraftClient10.currentScreen instanceof DropdownScreen,
              "keyboard selection opens Dropdown after exit");
        });
    clientGameTestContext.runOnClient(
        minecraftClient11 -> {
          GuiPreferences.getGuiPreferences().animationsControl.setValue(false);
          GuiPreferences.getGuiPreferences().update();
          ProfileStorage.getProfileStorage().getProfileDefaults().string2 = "";
          ProfileStorage.getProfileStorage().getProfileDefaults().enabled = false;
          minecraftClient11.setScreen(new WelcomeScreen(new TitleScreen()));
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.takeScreenshot("welcome-no-motion");
    clientGameTestContext.runOnClient(
        minecraftClient12 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              !AnimationPreferences.isEnabled() && getWelcomeScreen(minecraftClient12).test(),
              "disabled motion shows the complete form immediately");
          apply(minecraftClient12, 256);
          apply(
              minecraftClient12.currentScreen instanceof TitleScreen,
              "disabled motion also completes exit immediately");
          apply(
              ProfileStorage.getProfileStorage().getProfileDefaults().string2.isEmpty(),
              "Maybe later never manufactures an account name");

          apply(
              "your name".equals(ProfileStorage.getProfileStorage().getString()),
              "dismissed setup keeps the neutral display name");
          apply(minecraftClient12, "");
          minecraftClient12.setScreen(new WelcomeScreen(new TitleScreen()));
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.runOnClient(
        minecraftClient13 -> {
          render(minecraftClient13, 440.0F, 490.0F);
          apply(
              minecraftClient13.currentScreen instanceof TitleScreen,
              "Maybe later button also completes setup with no name");
          apply(minecraftClient13, "");
          GuiPreferences.getGuiPreferences().animationsControl.setValue(true);
          GuiPreferences.getGuiPreferences().update();
        });
    clientGameTestContext.runOnClient(
        minecraftClient14 -> {
          minecraftClient14.setScreen(new WelcomeScreen(new TitleScreen()));
        });
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.runOnClient(
        minecraftClient15 -> {
          minecraftClient15.setScreen(new TitleScreen());
        });
    atomicLong.set(1000000000L);
    clientGameTestContext.runOnClient(
        minecraftClient16 -> {
          minecraftClient16.setScreen(new WelcomeScreen(new TitleScreen(), atomicLong::get));
        });
    clientGameTestContext.waitTicks(3);
    render(clientGameTestContext, atomicLong, 1.56F, "welcome-reopened");
    clientGameTestContext.getInput().resizeWindow(854, 480);
    clientGameTestContext.waitTicks(3);
    clientGameTestContext.takeScreenshot("welcome-hero-small");
    clientGameTestContext.runOnClient(
        minecraftClient17 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              !getWelcomeScreen(minecraftClient17).test(),
              "resize preserves an active introduction");
          float float4 = OverlayRenderer.computeFloat4();
          float float5 = OverlayRenderer.computeFloat5();
          float floatValue = Math.max(18.0F, Math.min(float4, float5) * 0.035F);
          render2(minecraftClient17, float4 - floatValue - 60.0F, float5 - floatValue - 14.0F);
          apply(
              getWelcomeScreen(minecraftClient17).test(),
              "small-window skip button follows physical mouse coordinates");
          minecraftClient17.setScreen(new TitleScreen());
        });
    ClientInitializer.logger.info(
        "67 welcome checks passed: neutral name, empty hint, disk persistence, musical cues, input"
            + " gating, resize, skip, mute, save, both GUIs, reduced motion and GPU reopen");
  }

  public static void apply(MinecraftClient minecraftClient, String text) {
    ProtectionPerformanceTest.ensureInitialized();

    Path path =
        FabricLoader.getInstance()
            .getConfigDir()
            .resolve("bunnythekenzo")
            .resolve("profiles")
            .resolve(minecraftClient.getSession().getUuidOrNull() + ".json");

    try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
      ProfileDefaults profileDefaults = new Gson().fromJson(bufferedReader, ProfileDefaults.class);
      profileDefaults.normalize();
      apply(
          profileDefaults.enabled && text.equals(profileDefaults.string2),
          "profile name survives disk reload");
    } catch (IOException failure) {
      throw new AssertionError("Cannot reload saved welcome profile", failure);
    }
  }

  public static void render(
      ClientGameTestContext clientGameTestContext,
      AtomicLong atomicLong,
      float floatValue,
      String text) {
    atomicLong.set(1000000000L + Math.round(floatValue * 1.0E9));
    clientGameTestContext.waitTicks(2);
    clientGameTestContext.takeScreenshot(text);
  }

  public static WelcomeScreen getWelcomeScreen(MinecraftClient minecraftClient) {
    return (WelcomeScreen) minecraftClient.currentScreen;
  }

  public static void apply(MinecraftClient minecraftClient, int intValue) {
    minecraftClient.currentScreen.keyPressed(new KeyInput(intValue, 0, 0));
  }

  public static void render(MinecraftClient minecraftClient, float floatValue, float floatValue2) {
    float float4 = OverlayRenderer.computeFloat4();
    float float5 = OverlayRenderer.computeFloat5();
    float floatValue3 =
        Math.min(1.0F, Math.min((float4 - 28.0F) / 860.0F, (float5 - 28.0F) / 552.0F));
    render2(
        minecraftClient,
        (float4 - 860.0F * floatValue3) / 2.0F + floatValue * floatValue3,
        (float5 - 552.0F * floatValue3) / 2.0F + floatValue2 * floatValue3);
  }

  public static void render2(MinecraftClient minecraftClient, float floatValue, float floatValue2) {
    float floatValue3 =
        OverlayRenderer.computeFloat() / minecraftClient.getWindow().getScaleFactor();
    minecraftClient.currentScreen.mouseClicked(
        new Click(floatValue * floatValue3, floatValue2 * floatValue3, new MouseInput(0, 0)),
        false);
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Welcome: " + text);
    }
  }
}
