package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.MovementOverrides;
import bunnythekenzo.client.ui.PanelScreen;

public final class HoppinStandingBeforeTestSupport20 {
  public static void run(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();

    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return JsonSupport2.getJsonObject();
            });

    try (TestSingleplayerContext testSingleplayerContext =
        clientGameTestContext.worldBuilder().create()) {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient2.options.pauseOnLostFocus = false;
            minecraftClient2.setScreen(null);

            for (GuiModuleEntry guiModuleEntry : MovementOverrides.guiModules()) {
              guiModuleEntry.apply(false);
              guiModuleEntry.apply(-1);
            }

            JsonObject jsonObject = JsonSupport2.getJsonObject();

            for (GuiModuleEntry guiModuleEntry2 : MovementOverrides.guiModules()) {
              jsonObject
                  .getAsJsonObject("modules")
                  .getAsJsonObject(guiModuleEntry2.getString())
                  .addProperty("key", 83);
            }

            JsonSupport2.apply(jsonObject);

            for (GuiModuleEntry guiModuleEntry3 : MovementOverrides.guiModules()) {
              apply(
                  !guiModuleEntry3.test3(),
                  "loaded S binding cleared for " + guiModuleEntry3.getString());
              apply(!guiModuleEntry3.test(), "loading keeps modules disabled");
            }

            apply(
                !MovementOverrides.test(83) && minecraftClient2.currentScreen == null,
                "S passes through to gameplay after loading an old config");
            GuiModuleEntry guiModuleEntry4 = MovementOverrides.guiModuleEntry;
            guiModuleEntry4.apply(83);

            apply(
                !MovementOverrides.test(83) && minecraftClient2.currentScreen == null,
                "a newly assigned S binding cannot open the section");

            guiModuleEntry4.apply(true);

            apply(
                !MovementOverrides.test(83) && guiModuleEntry4.test(),
                "S cannot toggle a module while moving backward");
            guiModuleEntry4.apply(false);
            guiModuleEntry4.apply(75);
            JsonObject jsonObject2 = JsonSupport2.getJsonObject();
            guiModuleEntry4.apply(-1);
            JsonSupport2.apply(jsonObject2);
            apply(guiModuleEntry4.computeInt() == 75, "other saved keybindings survive loading");

            apply(
                MovementOverrides.test(75)
                    && minecraftClient2.currentScreen instanceof PanelScreen
                    && !guiModuleEntry4.test(),
                "other keybindings still open the confirmation without enabling a module");

            minecraftClient2.setScreen(null);

            guiModuleEntry4.apply(true);
            apply(
                MovementOverrides.test(75)
                    && !guiModuleEntry4.test()
                    && minecraftClient2.currentScreen == null,
                "other keybindings can still disable a module");
            GameMenuScreen gameMenuScreen = new GameMenuScreen(true);
            minecraftClient2.setScreen(gameMenuScreen);

            apply(
                !MovementOverrides.test(75) && minecraftClient2.currentScreen == gameMenuScreen,
                "a key press cannot replace an open screen with the anti-cheat section");

            guiModuleEntry4.apply(true);

            apply(
                !MovementOverrides.test(75) && guiModuleEntry4.test(),
                "typing in an open screen cannot toggle an enabled module");
            guiModuleEntry4.apply(false);
            minecraftClient2.setScreen(null);
          });
      ClientInitializer.logger.info(
          "Anti-cheat keybinds verified: old S bindings cleared, S passes through, other binds"
              + " preserved, open screens protected");

      if (Boolean.getBoolean("bunnythekenzo.test.antiCheatMovement")) {
        GameTestSupport.runInWorld(clientGameTestContext);
      }
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            JsonSupport2.apply(onClient);
          });
      clientGameTestContext.setScreen(TitleScreen::new);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}
