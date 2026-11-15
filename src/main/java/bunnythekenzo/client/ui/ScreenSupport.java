package bunnythekenzo.client.ui;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GambleOverlay;
import bunnythekenzo.client.GambleRiggerStateSupport;

public final class ScreenSupport {
  public static void register() {
    ScreenEvents.AFTER_INIT.register(
        (minecraftClient, screen, intValue, intValue2) -> {
          if (screen instanceof Generic3x3ContainerScreen generic3x3ContainerScreen) {
            GambleRiggerStateSupport gambleRiggerStateSupport =
                ClientInitializer.getModuleManager() == null
                    ? null
                    : ClientInitializer.getModuleManager().gambleRiggerStateSupport;
            if (gambleRiggerStateSupport != null && gambleRiggerStateSupport.isEnabled()) {
              GambleOverlay gambleOverlay =
                  new GambleOverlay(generic3x3ContainerScreen, gambleRiggerStateSupport);
              ScreenEvents.afterRender(screen)
                  .register(
                      (screen2, drawContext, intValue3, intValue4, floatValue) -> {
                        gambleOverlay.apply(drawContext, intValue3, intValue4);
                      });
              ScreenMouseEvents.allowMouseClick(screen)
                  .register(
                      (screen3, click) -> {
                        return !gambleOverlay.test(click.x(), click.y(), click.button());
                      });
            }
          }
        });
  }
}
